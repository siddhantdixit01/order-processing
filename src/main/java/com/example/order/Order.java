package com.example.order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String orderId;
    private final String customerId;
    private final List<Item> items;
    private final double totalAmount;

    private double amountPaid; // track cumulative payments for partials
    private OrderStatus status;
    private final List<Event> eventHistory = new ArrayList<>();

    public Order(String orderId, String customerId, List<Item> items, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.amountPaid = 0.0;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<Item> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public double getAmountPaid() { return amountPaid; }
    public OrderStatus getStatus() { return status; }

    public void setStatus(OrderStatus status) { this.status = status; }
    public List<Event> getEventHistory() { return eventHistory; }

    public void addEvent(Event event) { eventHistory.add(event); }

    public void applyPayment(double amount) {
        this.amountPaid += amount;
        if (this.amountPaid >= this.totalAmount) {
            this.status = OrderStatus.PAID;
        } else if (this.amountPaid > 0) {
            this.status = OrderStatus.PARTIALLY_PAID;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", amountPaid=" + amountPaid +
                ", status=" + status +
                '}';
    }
}
