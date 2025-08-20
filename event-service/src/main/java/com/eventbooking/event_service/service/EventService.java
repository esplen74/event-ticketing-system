package com.eventbooking.event_service.service;

import com.eventbooking.event_service.dto.EventRequest;
import com.eventbooking.event_service.model.Event;

import java.util.List;

public interface EventService {
    Event createEvent(EventRequest request);
    List<Event> getAllEvents();
    Event getEventById(String id);
    List<Event> searchEvents(String keyword);
    Event updateEvent(String id, EventRequest request);
    void deleteEvent(String id);
}
