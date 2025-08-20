package com.eventbooking.event_service.controller;

import com.eventbooking.event_service.dto.EventRequest;
import com.eventbooking.event_service.model.Event;
import com.eventbooking.event_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @PostMapping
    public Event create(@RequestBody EventRequest request) {
        return service.createEvent(request);
    }

    @GetMapping
    public List<Event> getAll() {
        return service.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable String id) {
        return service.getEventById(id);
    }

    @GetMapping("/search")
    public List<Event> search(@RequestParam String keyword) {
        return service.searchEvents(keyword);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable String id, @RequestBody EventRequest request) {
        return service.updateEvent(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteEvent(id);
    }
}
