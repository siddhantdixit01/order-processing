package com.example.order;

public class LoggerObserver implements Observer {
    @Override
    public void notify(Order order, Event event) {
        System.out.println("[Logger] Event processed: " + event.getEventType() +
                " for Order " + order.getOrderId() +
                " | Status: " + order.getStatus());
    }
}
