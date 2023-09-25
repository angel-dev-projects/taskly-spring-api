package com.angeldevprojects.taskly.controllers;

import com.angeldevprojects.taskly.models.Event;
import com.angeldevprojects.taskly.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0/events")
public class EventController {
    private final EventService eventService;

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.create(event));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent) {
        return ResponseEntity.ok(eventService.update(updatedEvent, eventId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Optional<Event>> getEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getById(eventId));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.delete(eventId);
    }
}
