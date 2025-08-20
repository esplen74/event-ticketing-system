package com.eventbooking.order_service.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderEvent(String message) {
        kafkaTemplate.send("order-events", message);
    }

    public void sendOrderCreatedEvent(String message) {
        kafkaTemplate.send("order-created", message);
    }
}