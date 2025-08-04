package com.eventbooking.event_service.service.ịmpl;

import com.eventbooking.event_service.dto.EventRequest;
import com.eventbooking.event_service.model.Event;
import com.eventbooking.event_service.repository.EventRepository;
import com.eventbooking.event_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    @Override
    public Event createEvent(EventRequest request) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .date(request.getDate())
                .totalTickets(request.getTotalTickets())
                .availableTickets(request.getTotalTickets())
                .build();
        return repository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    @Override
    public Event getEventById(String id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Event> searchEvents(String keyword) {
        return repository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public Event updateEvent(String id, EventRequest request) {
        Event existing = getEventById(id);
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setLocation(request.getLocation());
        existing.setDate(request.getDate());
        existing.setTotalTickets(request.getTotalTickets());
        existing.setAvailableTickets(request.getTotalTickets());
        return repository.save(existing);
    }

    @Override
    public void deleteEvent(String id) {
        repository.deleteById(id);
    }
}