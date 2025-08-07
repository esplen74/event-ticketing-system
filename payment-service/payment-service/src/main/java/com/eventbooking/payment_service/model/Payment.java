package com.eventbooking.payment_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "payments")
public class Payment {
    @Id
    private String id;
    private String orderId;
    private String status; // SUCCESS, FAILED
    private String method; // giả lập: CARD, MOMO
    private double amount;
    private LocalDateTime createdAt;
}