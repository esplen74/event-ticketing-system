package com.eventbooking.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private Long orderId;         // ID đơn hàng
    private String userId;        // ID người dùng đặt vé
    private String eventId;       // ID sự kiện
    private int quantity;         // Số lượng vé
    private String email;         // Email người đặt
    private String message;       // Thông điệp gửi (ex: "Cảm ơn bạn đã đặt vé")
    private String type;          // CONFIRMATION, REMINDER, CANCELLATION
    private String scheduledAt;   // ISO datetime string (khi gửi notification)
}
