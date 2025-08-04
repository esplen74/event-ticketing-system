package com.eventbooking.notification_service.service;


import com.eventbooking.notification_service.dto.Notification;
import com.eventbooking.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus("PENDING");
        return notificationRepository.save(notification);
    }

    // get notification from status và scheduledAt
    public List<Notification> getNotificationsByStatusAndScheduledAt(String status, LocalDateTime scheduledAt) {
        return notificationRepository.findByStatusAndScheduledAt(status, scheduledAt);
    }

    // Update status
    public Notification updateStatus(Long id, String status) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(status);
        if ("SENT".equalsIgnoreCase(status)) {
            notification.setSentAt(LocalDateTime.now());
        }
        return notificationRepository.save(notification);
    }
}