package com.angeldevprojects.taskly.controllers;

import com.angeldevprojects.taskly.dtos.ContactDto;
import com.angeldevprojects.taskly.dtos.EventDto;
import com.angeldevprojects.taskly.models.Contact;
import com.angeldevprojects.taskly.models.Event;
import com.angeldevprojects.taskly.services.ContactService;
import com.angeldevprojects.taskly.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0/contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> createContact(HttpServletRequest request, @RequestBody Contact contact) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(contactService.create(token, contact));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Contact> updateEvent(@PathVariable Long contactId, @RequestBody ContactDto updatedContact, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(contactService.update(updatedContact, contactId, token));
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<Optional<Contact>> getContact(@PathVariable Long contactId, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(contactService.getById(contactId, token));
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllEvents(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(contactService.getAll(token));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable Long contactId, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String message = contactService.delete(contactId, token);
        return ResponseEntity.ok(new ApiResponse(message));
    }
}
