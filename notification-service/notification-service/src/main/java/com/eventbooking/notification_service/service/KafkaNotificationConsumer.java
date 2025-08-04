package com.eventbooking.notification_service.service;

import com.eventbooking.notification_service.dto.Notification;
import com.eventbooking.notification_service.dto.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "order-events", groupId = "notification-service-group")
    public void consumeOrderEvent(String message) {
        try {
            log.info("📩 Receive message from Kafka: {}", message);

            // Convert JSON -> OrderEvent
            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);

            // Tạo Notification entity
            Notification notification = new Notification();
            notification.setOrderId(event.getOrderId());
            notification.setEventId(event.getEventId());
            notification.setEmail(event.getEmail());
            notification.setMessage(event.getMessage());
            notification.setType(event.getType());
            notification.setStatus("PENDING");
            notification.setScheduledAt(event.getScheduledAt() != null
                    ? LocalDateTime.parse(event.getScheduledAt())
                    : LocalDateTime.now());
            notification.setCreatedAt(LocalDateTime.now());

            // Lưu vào DB
            notificationService.createNotification(notification);

            log.info("✅ Notification was saved for orderId={}", event.getOrderId());
        } catch (Exception e) {
            log.error("❌ Error logic Kafka message: {}", e.getMessage(), e);
        }
    }
}