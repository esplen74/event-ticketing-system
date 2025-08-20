package com.eventbooking.notification_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String eventId;

    private String email;

    private String message;

    private String type; // e.g. CONFIRMATION, REMINDER, CANCELLATION, PAYMENT

    private String status; // e.g. PENDING, SENT, FAILED, COMPLETED

    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}