package com.eventbooking.notification_service.kafka.consumer;

import com.eventbooking.notification_service.dto.Notification;
import com.eventbooking.notification_service.dto.OrderEvent;
import com.eventbooking.notification_service.dto.PaymentSuccessEvent;
import com.eventbooking.notification_service.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

            log.info("✅ Notification from Order was saved for orderId={}", event.getOrderId());
        } catch (Exception e) {
            log.error("❌ Error logic Kafka message: {}", e.getMessage(), e);
        }
    }
@KafkaListener(topics = "payment-success", groupId = "notification-service-group")
public void handlePaymentSuccess(String message) {
    try {
        log.info("📩 Received payment-success message: {}", message);

        PaymentSuccessEvent event = objectMapper.readValue(message, PaymentSuccessEvent.class);
        Long orderId = Long.parseLong(event.getOrderId());

        // Lấy thông tin notification cũ nếu đã có
        Optional<Notification> existing = notificationService.getByOrderId(orderId);

        String email = "unknown";
        String eventId = "N/A";

        if (existing.isPresent()) {
            Notification old = existing.get();
            email = old.getEmail();
            eventId = old.getEventId();

            // Update lại notification cũ
            old.setStatus("COMPLETED");
            old.setMessage("Thanh toán thành công, mã giao dịch: " + event.getPaymentId());
            old.setScheduledAt(LocalDateTime.now());
            old.setCreatedAt(LocalDateTime.now());

            notificationService.updateNotification(old);
            log.info("✅ Updated existing notification for orderId={}", orderId);
        } else {
            // Nếu không có notification trước đó, tạo mới
            Notification notification = new Notification();
            notification.setOrderId(orderId);
            notification.setEventId(eventId);
            notification.setEmail(email);
            notification.setMessage("Thanh toán thành công, mã giao dịch: " + event.getPaymentId());
            notification.setType("PAYMENT");
            notification.setStatus("COMPLETED");
            notification.setScheduledAt(LocalDateTime.now());
            notification.setCreatedAt(LocalDateTime.now());

            notificationService.createNotification(notification);
            log.info("✅ Created new notification for orderId={}", orderId);
        }

    } catch (Exception e) {
        log.error("❌ Error processing payment-success: {}", e.getMessage(), e);
    }
}



}