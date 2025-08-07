package com.eventbooking.notification_service.dto;

import lombok.Data;

@Data
public class PaymentSuccessEvent {
    private String orderId;
    private String status;
    private String paymentId;
    private String method;
    private double amount;
    private String email;
}