package com.eventbooking.event_service.repository;

import com.eventbooking.event_service.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByTitleContainingIgnoreCase(String keyword);
}