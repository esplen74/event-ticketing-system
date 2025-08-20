package com.eventbooking.email_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentSuccessEvent {
    private Long orderId;
    private String status;
    private String paymentId;
    private String method;
    private double amount;
    private String email;
}


