package com.eventbooking.email_service.service;

import com.eventbooking.email_service.dto.NotificationStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MailService(JavaMailSender mailSender, KafkaTemplate<String, String> kafkaTemplate) {
        this.mailSender = mailSender;
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendConfirmationEmail(String to, Long orderId, String transactionId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom("phanviethuy670@gmail.com");
            message.setSubject("Thanh toán thành công !!");
            message.setText("Cảm ơn bạn đã đặt vé. Mã giao dịch của bạn là: " + transactionId);
            System.out.println("Bắt đầu gửi email đến: " + to);
            mailSender.send(message);
            System.out.println("Đã gửi email thành công đến: " + to);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public void sendStatusToKafka(NotificationStatus status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(status);
            kafkaTemplate.send("notification-status-updated", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
