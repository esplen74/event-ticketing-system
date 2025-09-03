package com.eventbooking.event_service.service.ịmpl;

import com.eventbooking.event_service.dto.EventRequest;
import com.eventbooking.event_service.model.Event;
import com.eventbooking.event_service.repository.EventRepository;
import com.eventbooking.event_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    @Override
    public Event createEvent(EventRequest request) {
        // số vé còn lại = tổng vé khi tạo mới
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .dateTime(request.getDateTime())   // ⬅️ dùng LocalDateTime
                .imageUrl(request.getImageUrl())
                .gallery(request.getGallery())
                .totalTickets(request.getTotalTickets())
                .availableTickets(request.getTotalTickets())
                .price(request.getPrice())         // ⬅️ dùng BigDecimal
                .build();

        return repository.save(event);
    }

    @Override
    @Transactional
    public Event updateEvent(String id, EventRequest request) {
        Event existing = getEventById(id);

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setLocation(request.getLocation());
        existing.setDateTime(request.getDateTime());
        existing.setImageUrl(request.getImageUrl());
        existing.setGallery(request.getGallery());
        existing.setPrice(request.getPrice());

        // Giữ đúng số vé đã bán khi thay đổi totalTickets
        int newTotal = request.getTotalTickets();
        int sold = existing.getTotalTickets() - existing.getAvailableTickets();

        if (newTotal < sold) {
            throw new IllegalArgumentException(
                    "totalTickets không thể nhỏ hơn số vé đã bán: " + sold
            );
        }

        existing.setTotalTickets(newTotal);
        existing.setAvailableTickets(newTotal - sold); // còn lại = tổng mới - đã bán

        return repository.save(existing);
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
    public void deleteEvent(String id) {
        repository.deleteById(id);
    }
}