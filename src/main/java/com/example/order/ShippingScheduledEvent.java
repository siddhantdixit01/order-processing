package com.example.order;

import java.time.Instant;

public class ShippingScheduledEvent extends Event {
    private final String orderId;
    private final Instant shippingDate;

    public ShippingScheduledEvent(String eventId, Instant timestamp, String eventType,
                                  String orderId, Instant shippingDate) {
        super(eventId, timestamp, eventType);
        this.orderId = orderId;
        this.shippingDate = shippingDate;
    }

    public String getOrderId() { return orderId; }
    public Instant getShippingDate() { return shippingDate; }
}
