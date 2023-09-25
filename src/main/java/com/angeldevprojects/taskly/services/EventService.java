package com.angeldevprojects.taskly.services;

import com.angeldevprojects.taskly.exceptions.AppException;
import com.angeldevprojects.taskly.models.Event;
import com.angeldevprojects.taskly.repositories.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(Event event){
        return eventRepository.save(event);
    }

    public Event update(Event updatedEvent, Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            throw new AppException("The event doesn't exist", HttpStatus.BAD_REQUEST);
        }

        updatedEvent.setId(id);

        return eventRepository.save(updatedEvent);
    }

    public Optional<Event> getById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public void delete(Long userId) {
        eventRepository.deleteById(userId);
    }
}
