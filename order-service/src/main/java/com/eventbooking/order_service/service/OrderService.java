package com.eventbooking.order_service.service;

import com.eventbooking.order_service.dto.OrderEvent;
import com.eventbooking.order_service.kafka.producer.OrderProducer;
import com.eventbooking.order_service.model.Order;
import com.eventbooking.order_service.repository.OrderRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final ObjectMapper objectMapper;

    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        Order saved = orderRepository.save(order);

        // Create OrderEvent DTO
        OrderEvent event = new OrderEvent();
        event.setOrderId(saved.getId());
        event.setUserId(saved.getUserId());
        event.setEventId(saved.getEventId());
        event.setQuantity(saved.getQuantity());
        event.setEmail(saved.getEmail());
        event.setMessage("Cảm ơn bạn đã đặt vé");
        event.setType("CONFIRMATION");
        event.setScheduledAt(
                saved.getScheduledAt() != null
                        ? saved.getScheduledAt().toString()
                        : LocalDateTime.now().plusMinutes(5).toString()
        );

        try {
            // Convert object -> JSON
            String jsonMessage = objectMapper.writeValueAsString(event);

            // Gửi Kafka topic: order-created (có thể đổi tên topic cho rõ ràng hơn)
            orderProducer.sendOrderEvent(jsonMessage);
            try {
                // Đợi 5 giây
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            orderProducer.sendOrderCreatedEvent(jsonMessage);

            System.out.println("✅ Sent message to Kafka: " + jsonMessage);
        } catch (Exception e) {
            throw new RuntimeException("❌ Error when sending message to Kafka", e);
        }

        return saved;
    }
}
