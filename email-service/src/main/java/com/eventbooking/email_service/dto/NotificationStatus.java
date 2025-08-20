package com.eventbooking.email_service.dto;

import lombok.Data;

@Data
public class NotificationStatus {
    private Long orderId;
    private String transactionId;
    private String type;   // CONFIRMATION / etc.
    private String status; // SENT / FAILED / PENDING / COMPLETED
}