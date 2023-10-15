package com.angeldevprojects.taskly.services;

import com.angeldevprojects.taskly.config.jwt.UserAuthProvider;
import com.angeldevprojects.taskly.dtos.ContactDto;
import com.angeldevprojects.taskly.dtos.UserDto;
import com.angeldevprojects.taskly.exceptions.AppException;
import com.angeldevprojects.taskly.mappers.UserMapper;
import com.angeldevprojects.taskly.models.Contact;
import com.angeldevprojects.taskly.models.User;
import com.angeldevprojects.taskly.repositories.ContactRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;
    private final UserMapper userMapper;

    public ContactService(ContactRepository contactRepository, UserService userService, UserAuthProvider userAuthProvider, UserMapper userMapper) {
        this.contactRepository = contactRepository;
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
        this.userMapper = userMapper;
    }

    public Contact create(String token, Contact contact) {
        if (StringUtils.isBlank(contact.getName()) || StringUtils.isBlank(contact.getPhoneNumber())) {
            throw new AppException("Name and phone number are required", HttpStatus.BAD_REQUEST);
        }

        User user = getUserFromToken(token);
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    public Contact update(ContactDto updatedContact, Long contactId, String token) {
        Contact contact = getContactIfBelongsToUser(contactId, token);
        BeanUtils.copyProperties(updatedContact, contact, getNullPropertyNames(updatedContact));
        return contactRepository.save(contact);
    }

    public Optional<Contact> getById(Long contactId, String token) {
        getContactIfBelongsToUser(contactId, token);
        return contactRepository.findById(contactId);
    }

    public List<Contact> getAll(String token) {
        User user = getUserFromToken(token);
        return contactRepository.findByUser(user);
    }

    public String delete(Long contactId, String token) {
        getContactIfBelongsToUser(contactId, token);
        contactRepository.deleteById(contactId);
        return "Contact with id " + contactId + " deleted";
    }

    private Contact getContactIfBelongsToUser(Long contactId, String token) {
        Optional<Contact> optionalContact = contactRepository.findById(contactId);

        if (optionalContact.isEmpty()) {
            throw new AppException("The contact doesn't exist", HttpStatus.BAD_REQUEST);
        }

        Contact contact = optionalContact.get();
        User user = getUserFromToken(token);

        if (!contact.getUser().getId().equals(user.getId())) {
            throw new AppException("The contact does not belong to the user", HttpStatus.BAD_REQUEST);
        }

        return contact;
    }

    private User getUserFromToken(String token) {
        String username = userAuthProvider.extractUsernameFromToken(token);
        UserDto userDto = userService.findByUsername(username);
        return userMapper.toUser(userDto);
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
