package com.example.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Event parse(String jsonLine) throws Exception {
        if (jsonLine == null || jsonLine.isBlank()) return null;

        JsonNode node = mapper.readTree(jsonLine);

        String eventId = text(node, "eventId");
        String tsRaw   = text(node, "timestamp");
        String type    = text(node, "eventType");

        Instant timestamp = parseInstant(tsRaw);

        switch (type) {
            case "OrderCreated" -> {
                String orderId   = text(node, "orderId");
                String customerId= text(node, "customerId");
                List<Item> items = mapper.convertValue(node.get("items"), new TypeReference<List<Item>>() {});
                double total     = node.path("totalAmount").asDouble();
                return new OrderCreatedEvent(eventId, timestamp, type, orderId, customerId, items, total);
            }
            case "PaymentReceived" -> {
                String orderId = text(node, "orderId");
                double amount  = node.path("amountPaid").asDouble();
                return new PaymentReceivedEvent(eventId, timestamp, type, orderId, amount);
            }
            case "ShippingScheduled" -> {
                String orderId = text(node, "orderId");
                String shipRaw = text(node, "shippingDate");
                Instant ship   = parseInstant(shipRaw);
                return new ShippingScheduledEvent(eventId, timestamp, type, orderId, ship);
            }
            case "OrderCancelled" -> {
                String orderId = text(node, "orderId");
                String reason  = text(node, "reason");
                return new OrderCancelledEvent(eventId, timestamp, type, orderId, reason);
            }
            default -> {
                System.out.println("[Warning] Unsupported event type: " + type);
                return null;
            }
        }
    }

    private static String text(JsonNode node, String field) {
        JsonNode n = node.get(field);
        if (n == null || n.isNull()) throw new IllegalArgumentException("Missing field: " + field);
        return n.asText();
    }

    /**
     * Parses ISO-8601 instants with or without trailing 'Z' and timezone.
     * Accepts forms like:
     *  - 2025-07-29T10:00:00Z
     *  - 2025-07-29T10:00:00+05:30
     *  - 2025-07-29T10:00:00 (assumed UTC)
     */
    private static Instant parseInstant(String iso) {
        try {
            // Zoned/offset form (e.g., ...Z or ...+05:30)
            if (iso.endsWith("Z") || iso.contains("+") || iso.contains("-") && iso.indexOf('-') > 4) {
                return Instant.parse(iso);
            }
            // No zone: assume UTC by appending Z
            return Instant.parse(iso + "Z");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp: " + iso, e);
        }
    }
}
