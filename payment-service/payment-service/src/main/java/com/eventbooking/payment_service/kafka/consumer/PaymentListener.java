package com.eventbooking.payment_service.kafka.consumer;

import com.eventbooking.payment_service.dto.PaymentSuccessEvent;
import com.eventbooking.payment_service.model.Payment;
import com.eventbooking.payment_service.repository.PaymentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "order-created", groupId = "payment-group")
    public void handleOrderCreated(String message) {
        try {
            JsonNode json = objectMapper.readTree(message);
            String orderId = json.get("orderId").asText();
            double amount = json.has("amount") ? json.get("amount").asDouble() : 0.0;
            String email = json.has("email") ? json.get("email").asText() : null;
            String paymentId = "PAY-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 10000);

            // Giả lập xử lý thanh toán
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setAmount(amount);
            payment.setMethod("CARD"); // giả lập dùng CARD
            payment.setStatus("SUCCESS");
            payment.setCreatedAt(LocalDateTime.now());

            payment = paymentRepository.save(payment);

            // Gửi event kafka
            PaymentSuccessEvent event = new PaymentSuccessEvent(
                    orderId,
                    "SUCCESS",
                    paymentId,
                    payment.getMethod(),
                    payment.getAmount(),
                    email

            );
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("payment-success", eventJson);

            System.out.println("✅ Payment processed and sent: " + eventJson);

        } catch (Exception e) {
            System.err.println("❌ Error processing order-created: " + e.getMessage());
        }
    }
}