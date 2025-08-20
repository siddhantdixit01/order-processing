package com.example.order;

import java.time.Instant;

public abstract class Event {
    protected final String eventId;
    protected final Instant timestamp;
    protected final String eventType;

    protected Event(String eventId, Instant timestamp, String eventType) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    public String getEventId() { return eventId; }
    public Instant getTimestamp() { return timestamp; }
    public String getEventType() { return eventType; }
}
