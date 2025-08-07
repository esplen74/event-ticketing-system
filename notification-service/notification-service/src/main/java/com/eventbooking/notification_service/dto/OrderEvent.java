package com.eventbooking.notification_service.dto;

import lombok.Data;

@Data
public class OrderEvent {
    private Long orderId;
    private String userId;
    private String eventId;
    private Integer quantity;
    private String email;
    private String message;
    private String type;
    private String scheduledAt; // ISO format string
}