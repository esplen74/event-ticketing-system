package com.eventbooking.ticket_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String userId;
    private String eventId;
    private LocalDateTime bookingTime;
    private boolean paid;
}
