package com.example.order;

import java.util.*;

public class EventProcessor {
    private final Map<String, Order> orders = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(Order order, Event event) {
        for (Observer o : observers) {
            o.notify(order, event);
        }
    }

    public Optional<Order> getOrder(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public void processEvent(Event event) {
        if (event == null) {
            System.out.println("[Warning] Received null event, skipping.");
            return;
        }

        switch (event.getEventType()) {
            case "OrderCreated" -> handleOrderCreated((OrderCreatedEvent) event);
            case "PaymentReceived" -> handlePaymentReceived((PaymentReceivedEvent) event);
            case "ShippingScheduled" -> handleShippingScheduled((ShippingScheduledEvent) event);
            case "OrderCancelled" -> handleOrderCancelled((OrderCancelledEvent) event);
            default -> System.out.println("[Warning] Unknown event type: " + event.getEventType());
        }
    }

    private void handleOrderCreated(OrderCreatedEvent e) {
        if (orders.containsKey(e.getOrderId())) {
            System.out.println("[Info] Order " + e.getOrderId() + " already exists. Ignoring duplicate creation.");
            return;
        }
        Order order = new Order(e.getOrderId(), e.getCustomerId(), e.getItems(), e.getTotalAmount());
        orders.put(order.getOrderId(), order);
        order.addEvent(e);
        // status already PENDING on creation
        notifyObservers(order, e);
    }

    private void handlePaymentReceived(PaymentReceivedEvent e) {
        Order order = orders.get(e.getOrderId());
        if (order == null) {
            System.out.println("[Warning] Payment for unknown order " + e.getOrderId() + ". Ignoring.");
            return;
        }
        order.applyPayment(e.getAmountPaid());
        order.addEvent(e);
        notifyObservers(order, e);
    }

    private void handleShippingScheduled(ShippingScheduledEvent e) {
        Order order = orders.get(e.getOrderId());
        if (order == null) {
            System.out.println("[Warning] Shipping scheduled for unknown order " + e.getOrderId() + ". Ignoring.");
            return;
        }
        order.setStatus(OrderStatus.SHIPPED);
        order.addEvent(e);
        notifyObservers(order, e);
    }

    private void handleOrderCancelled(OrderCancelledEvent e) {
        Order order = orders.get(e.getOrderId());
        if (order == null) {
            System.out.println("[Warning] Cancellation for unknown order " + e.getOrderId() + ". Ignoring.");
            return;
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.addEvent(e);
        notifyObservers(order, e);
    }
}
