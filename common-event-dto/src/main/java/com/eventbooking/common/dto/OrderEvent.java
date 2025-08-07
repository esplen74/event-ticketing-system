package main.java.com.eventbooking.common.dto;

@Data
public class OrderEvent {
    private Long orderId;
    private String userId;
    private String eventId;
    private String email;
    private String message;
    private String type;
    private String scheduledAt;
}