package com.example.order;

public interface Observer {
    void notify(Order order, Event event);
}
