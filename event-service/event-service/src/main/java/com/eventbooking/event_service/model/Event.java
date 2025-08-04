package com.eventbooking.event_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

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
    private LocalDate date;
    private int totalTickets;
    private int availableTickets;
}