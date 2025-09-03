package com.eventbooking.event_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "events")
public class Event {
    @Id
    private String id;

    private String title;
    private String description;
    private String location;
    private LocalDateTime dateTime;
    private String imageUrl;
    private List<String> gallery;
    private int totalTickets;
    private int availableTickets;
    private BigDecimal price;
    private String category;
}