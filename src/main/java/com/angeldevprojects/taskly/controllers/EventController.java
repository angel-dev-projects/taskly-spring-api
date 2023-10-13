package com.angeldevprojects.taskly.controllers;

import com.angeldevprojects.taskly.dtos.EventDto;
import com.angeldevprojects.taskly.models.Event;
import com.angeldevprojects.taskly.services.EventService;
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
@RequestMapping("/api/v0/events")
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(HttpServletRequest request, @RequestBody Event event) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(eventService.create(token, event));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody EventDto updatedEvent, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(eventService.update(updatedEvent, eventId, token));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Optional<Event>> getEvent(@PathVariable Long eventId, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(eventService.getById(eventId, token));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(eventService.getAll(token));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable Long eventId, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String message = eventService.delete(eventId, token);
        return ResponseEntity.ok(new ApiResponse(message));
    }
}
