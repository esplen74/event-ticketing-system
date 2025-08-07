package com.eventbooking.payment_service.service;

import com.eventbooking.payment_service.model.Payment;
import com.eventbooking.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment processPayment(String orderId, double amount) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus("SUCCESS");
        payment.setMethod("CARD"); // giả lập
        payment.setCreatedAt(LocalDateTime.now());

        return repository.save(payment);
    }
}