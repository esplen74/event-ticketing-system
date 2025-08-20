package com.eventbooking.event_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventRequest {
    private String title;
    private String description;
    private String location;
    private LocalDate date;
    private int totalTickets;
}
