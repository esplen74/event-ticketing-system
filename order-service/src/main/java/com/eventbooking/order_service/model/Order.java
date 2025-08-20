package com.eventbooking.order_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;      // ID của người dùng đặt vé
    private String eventId;     // ID của sự kiện
    private int quantity;       // Số lượng vé

    private String email;       // Email người đặt (thêm mới)

    private LocalDateTime scheduledAt; // Thời điểm dự kiến gửi thông báo (thêm mới)

    private LocalDateTime createdAt;   // Ngày tạo order
}
