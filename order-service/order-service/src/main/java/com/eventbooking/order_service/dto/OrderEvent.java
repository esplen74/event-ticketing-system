package com.eventbooking.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private String eventId;
    private String email;
    private String message; // ex: "Thanks for your order"
    private String type;    // CONFIRMATION, REMINDER, CANCELLATION
    private String scheduledAt; // ISO datetime string
}