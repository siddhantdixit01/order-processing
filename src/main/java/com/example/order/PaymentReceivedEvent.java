package com.example.order;

import java.time.Instant;

public class PaymentReceivedEvent extends Event {
    private final String orderId;
    private final double amountPaid;

    public PaymentReceivedEvent(String eventId, Instant timestamp, String eventType,
                                String orderId, double amountPaid) {
        super(eventId, timestamp, eventType);
        this.orderId = orderId;
        this.amountPaid = amountPaid;
    }

    public String getOrderId() { return orderId; }
    public double getAmountPaid() { return amountPaid; }
}
