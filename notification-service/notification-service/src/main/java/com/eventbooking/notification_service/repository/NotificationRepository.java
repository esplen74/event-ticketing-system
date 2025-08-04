package com.eventbooking.notification_service.repository;

import com.eventbooking.notification_service.dto.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatusAndScheduledAt(String status, LocalDateTime scheduledAt);
}
