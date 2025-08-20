package com.example.order;

import java.time.Instant;

public class OrderCancelledEvent extends Event {
    private final String orderId;
    private final String reason;

    public OrderCancelledEvent(String eventId, Instant timestamp, String eventType,
                               String orderId, String reason) {
        super(eventId, timestamp, eventType);
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() { return orderId; }
    public String getReason() { return reason; }
}
