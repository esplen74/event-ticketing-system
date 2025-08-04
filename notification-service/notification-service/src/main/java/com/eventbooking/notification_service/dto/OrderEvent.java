package com.eventbooking.notification_service.dto;

import lombok.Data;

@Data
public class OrderEvent {
    private Long orderId;
    private String eventId;
    private String email;
    private String message;
    private String type; // CONFIRMATION, REMINDER, CANCELLATION
    private String scheduledAt; // ISO String
}