package com.angeldevprojects.taskly.services;

import com.angeldevprojects.taskly.config.jwt.UserAuthProvider;
import com.angeldevprojects.taskly.dtos.EventDto;
import com.angeldevprojects.taskly.dtos.UserDto;
import com.angeldevprojects.taskly.exceptions.AppException;
import com.angeldevprojects.taskly.mappers.UserMapper;
import com.angeldevprojects.taskly.models.Event;
import com.angeldevprojects.taskly.models.User;
import com.angeldevprojects.taskly.repositories.EventRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;
    private final UserMapper userMapper;

    public EventService(EventRepository eventRepository, UserService userService, UserAuthProvider userAuthProvider, UserMapper userMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
        this.userMapper = userMapper;
    }

    public Event create(String token, Event event) {
        if (StringUtils.isBlank(event.getTitle()) || StringUtils.isBlank(event.getDescription()) || StringUtils.isBlank(event.getStart()) || StringUtils.isBlank(event.getEnd())) {
            throw new AppException("Title, description, start date and end date are required", HttpStatus.BAD_REQUEST);
        }

        if (event.getTitle().length() > 30) {
            throw new AppException("The title must have between 0 and 30 characters", HttpStatus.BAD_REQUEST);
        }

        if (event.getDescription().length() > 200) {
            throw new AppException("The description must have between 0 and 200 characters", HttpStatus.BAD_REQUEST);
        }

        User user = getUserFromToken(token);
        event.setUser(user);
        return eventRepository.save(event);
    }

    public Event update(EventDto updatedEvent, Long eventId, String token) {
        String title = updatedEvent.title();
        String description = updatedEvent.description();

        if (title != null && title.length() > 30) {
            throw new AppException("The title must have between 0 and 30 characters", HttpStatus.BAD_REQUEST);
        }

        if (description != null && description.length() > 200) {
            throw new AppException("The description must have between 0 and 200 characters", HttpStatus.BAD_REQUEST);
        }

        Event event = getEventIfBelongsToUser(eventId, token);
        BeanUtils.copyProperties(updatedEvent, event, getNullPropertyNames(updatedEvent));
        return eventRepository.save(event);
    }

    public Optional<Event> getById(Long eventId, String token) {
        getEventIfBelongsToUser(eventId, token);
        return eventRepository.findById(eventId);
    }

    public List<Event> getAll(String token) {
        User user = getUserFromToken(token);
        return eventRepository.findByUser(user);
    }

    public String delete(Long eventId, String token) {
        getEventIfBelongsToUser(eventId, token);
        eventRepository.deleteById(eventId);
        return "Event with id " + eventId + " deleted";
    }

    private User getUserFromToken(String token) {
        String username = userAuthProvider.extractUsernameFromToken(token);
        UserDto userDto = userService.findByUsername(username);
        return userMapper.toUser(userDto);
    }

    private Event getEventIfBelongsToUser(Long eventId, String token) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isEmpty()) {
            throw new AppException("The event doesn't exist", HttpStatus.BAD_REQUEST);
        }

        Event event = optionalEvent.get();
        User user = getUserFromToken(token);

        if (!event.getUser().getId().equals(user.getId())) {
            throw new AppException("The event does not belong to the user", HttpStatus.BAD_REQUEST);
        }

        return event;
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
