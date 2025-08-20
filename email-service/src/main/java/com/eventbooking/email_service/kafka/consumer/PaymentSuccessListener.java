package com.eventbooking.email_service.kafka.consumer;

import com.eventbooking.email_service.dto.NotificationStatus;
import com.eventbooking.email_service.dto.PaymentSuccessEvent;
import com.eventbooking.email_service.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentSuccessListener {

    private final MailService mailService;

    public PaymentSuccessListener(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(topics = "payment-success", groupId = "mail-group")
    public void handlePaymentSuccess(String messageJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PaymentSuccessEvent event = mapper.readValue(messageJson, PaymentSuccessEvent.class);

            boolean sent = mailService.sendConfirmationEmail(event.getEmail(), event.getOrderId(), event.getPaymentId());

            // Gửi trạng thái mail (SENT hoặc FAILED) qua Kafka
            NotificationStatus status = new NotificationStatus();
            status.setOrderId(event.getOrderId());
            status.setStatus(sent ? "SENT" : "FAILED");

            // gửi sang topic khác nếu bạn có consumer update DB
            mailService.sendStatusToKafka(status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}