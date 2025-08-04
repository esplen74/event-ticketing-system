package com.eventbooking.notification_service.controller;

import com.eventbooking.notification_service.dto.Notification;
import com.eventbooking.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Create new notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    // API lấy notification theo status và scheduledAt
    @GetMapping
    public ResponseEntity<List<Notification>> getByStatusAndScheduledAt(
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledAt) {
        return ResponseEntity.ok(notificationService.getNotificationsByStatusAndScheduledAt(status, scheduledAt));
    }

    // API cập nhật status
    @PutMapping("/{id}/status")
    public ResponseEntity<Notification> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(notificationService.updateStatus(id, status));
    }
}