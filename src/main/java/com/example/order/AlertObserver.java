package com.example.order;

public class AlertObserver implements Observer {
    @Override
    public void notify(Order order, Event event) {
        boolean critical = (event instanceof OrderCancelledEvent)
                || order.getStatus() == OrderStatus.SHIPPED;
        if (critical) {
            System.out.println("[ALERT] Sending alert for Order " + order.getOrderId() +
                    ": Status changed to " + order.getStatus());
        }
    }
}
