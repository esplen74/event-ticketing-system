package com.eventbooking.order_service.service;

import com.eventbooking.order_service.dto.OrderEvent;
import com.eventbooking.order_service.kafka.OrderProducer;
import com.eventbooking.order_service.model.Order;
import com.eventbooking.order_service.repository.OrderRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final ObjectMapper objectMapper;

    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        Order saved = orderRepository.save(order);

        // Create DTO OrderEvent
        OrderEvent event = new OrderEvent();
        event.setOrderId(saved.getId());
        event.setEventId(saved.getEventId());
        event.setEmail(saved.getEmail());
        event.setMessage("Cảm ơn bạn đã đặt vé");
        event.setType("CONFIRMATION");
        event.setScheduledAt(saved.getScheduledAt() != null
                ? saved.getScheduledAt().toString()
                : LocalDateTime.now().plusMinutes(5).toString());

        try {
            // Convert object -> JSON
            String jsonMessage = objectMapper.writeValueAsString(event);

            // Sent Kafka
            orderProducer.sendOrderEvent(jsonMessage);

            System.out.println("✅ Sent message to Kafka: " + jsonMessage);
        } catch (Exception e) {
            throw new RuntimeException("❌ Error when send message Kafka", e);
        }

        return saved;
    }

}