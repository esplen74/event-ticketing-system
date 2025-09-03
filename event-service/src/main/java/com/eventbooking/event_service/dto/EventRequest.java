package com.eventbooking.event_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequest {
    private String title;
    private String description;
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private String imageUrl;
    private List<String> gallery;

    private int totalTickets;
    private BigDecimal price;
}
