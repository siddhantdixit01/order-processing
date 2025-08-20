# Event-Driven Order Processing System (Java)

A simplified, event-driven backend simulation for an e-commerce platform.
It processes order events such as Order Creation, Payment Receipt, Shipping, and Cancellation, while notifying observers (Logger, Alert) when status changes.

## Features

- Event ingestion from a JSON file (line-by-line JSON objects).
- Supports events:
  - OrderCreated
  - PaymentReceived
  - ShippingScheduled
  - OrderCancelled
- Updates Order state based on events.
- Maintains event history inside each order.
- Implements the Observer Pattern:
  - LoggerObserver → logs status changes
  - AlertObserver → prints critical alerts
- Handles unknown/unsupported events gracefully.

## Tech Stack

- Java 17
- Maven (build + dependencies)
- Jackson (JSON parsing)

## Project Structure
```
order-processing/
 ├── pom.xml
 ├── events.txt
 └── src/main/java/com/example/order/
      ├── Main.java
      ├── Order.java
      ├── OrderStatus.java
      ├── Event.java
      ├── OrderCreatedEvent.java
      ├── PaymentReceivedEvent.java
      ├── ShippingScheduledEvent.java
      ├── OrderCancelledEvent.java
      ├── Observer.java
      ├── LoggerObserver.java
      ├── AlertObserver.java
      ├── EventProcessor.java
      └── EventParser.java
```
## Setup & Run

1. Clone & Navigate
```
git clone <your-repo-url>
cd order-processing
```
2. Build Project
```
mvn clean compile
```
4. Run the Application
```
mvn exec:java
```

## Example Input File (events.txt)

Create a file in the project root:
```
{"eventId": "e1", "timestamp": "2025-07-29T10:00:00", "eventType": "OrderCreated", "orderId": "ORD001", "customerId": "CUST001", "items": [{"itemId": "P001", "qty": 2}], "totalAmount": 100.00}
{"eventId": "e2", "timestamp": "2025-07-29T10:05:00", "eventType": "PaymentReceived", "orderId": "ORD001", "amountPaid": 100.00}
{"eventId": "e3", "timestamp": "2025-07-29T10:10:00", "eventType": "ShippingScheduled", "orderId": "ORD001", "shippingDate": "2025-07-30T09:00:00"}
{"eventId": "e4", "timestamp": "2025-07-29T10:15:00", "eventType": "OrderCancelled", "orderId": "ORD002", "reason": "Customer Request"}
```
## Expected Output
When you run with the above events, you should see:
```
[Logger] Event processed: OrderCreated for Order ORD001 | Status: PENDING
[Logger] Event processed: PaymentReceived for Order ORD001 | Status: PAID
[Logger] Event processed: ShippingScheduled for Order ORD001 | Status: SHIPPED
[ALERT] Sending alert for Order ORD001: Status changed to SHIPPED
[Logger] Event processed: OrderCancelled for Order ORD002 | Status: CANCELLED
[ALERT] Sending alert for Order ORD002: Status changed to CANCELLED
```

Thank you!
