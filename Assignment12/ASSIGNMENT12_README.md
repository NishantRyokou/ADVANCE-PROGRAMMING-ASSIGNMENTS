# Assignment 12: E-Commerce Order Processing System

A professional e-commerce order processing system demonstrating SOLID principles in Java.

## System Features

- **Multiple Payment Methods:** Credit Card, UPI, Wallet
- **Multiple Notification Channels:** Email, SMS, Push
- **Different Order Types:** Regular, Discounted, Priority
- **Multiple Storage Options:** Database, File

## SOLID Principles

### 1. Single Responsibility Principle (SRP)
Each class has one responsibility:
- `CreditCardPayment` - Credit card payment logic only
- `EmailNotification` - Email notification logic only
- `DatabaseStorage` - Database storage logic only

### 2. Open/Closed Principle (OCP)
Add new features without modifying existing code:
```java
// Add new payment method
public class GooglePayPayment implements PaymentProcessor { ... }

// Add new notification
public class WhatsAppNotification implements NotificationChannel { ... }

// Add new storage
public class CloudStorage implements OrderStorage { ... }
```

### 3. Liskov Substitution Principle (LSP)
All implementations are interchangeable:
```java
PaymentProcessor p1 = new CreditCardPayment(...);
PaymentProcessor p2 = new UPIPayment(...);
// Both work identically in OrderService
```

### 4. Interface Segregation Principle (ISP)
Small, focused interfaces:
- `PaymentProcessor` - 3 payment methods
- `NotificationChannel` - 3 notification methods
- `OrderStorage` - 4 storage methods

### 5. Dependency Inversion Principle (DIP)
Depend on abstractions, not concrete implementations:
```java
public class OrderService {
    private PaymentProcessor paymentProcessor;        // Abstraction
    private NotificationChannel notificationChannel;  // Abstraction
    private OrderStorage orderStorage;                // Abstraction
}
```

## File Structure

### Core Classes
- `Order.java` - Order data model
- `OrderType.java` - Order type enum
- `OrderStatus.java` - Order status enum

### Payment Processing
- `PaymentProcessor.java` - Interface
- `CreditCardPayment.java` - Implementation
- `UPIPayment.java` - Implementation
- `WalletPayment.java` - Implementation

### Notifications
- `NotificationChannel.java` - Interface
- `EmailNotification.java` - Implementation
- `SMSNotification.java` - Implementation
- `PushNotification.java` - Implementation

### Storage
- `OrderStorage.java` - Interface
- `DatabaseStorage.java` - Implementation
- `FileStorage.java` - Implementation

### Service
- `OrderService.java` - Orchestrates order processing
- `Assignment12Demo.java` - Demo with 6 scenarios

## Usage Example

```java
// Create order
Order order = new Order("CUST001", 5000, OrderType.REGULAR);

// Create dependencies
PaymentProcessor payment = new CreditCardPayment(
    "1234567890123456", "Raj Kumar", "12/25", "123"
);
NotificationChannel notification = new EmailNotification("raj@example.com");
OrderStorage storage = new DatabaseStorage();

// Create service and process
OrderService service = new OrderService(payment, notification, storage);
service.processOrder(order);
```

## Compilation and Execution

```bash
cd Assignment12
javac *.java
java Assignment12Demo
```

## Order Processing Flow

1. Create Order
2. Validate Order
3. Process Payment (using injected PaymentProcessor)
4. Send Notification (using injected NotificationChannel)
5. Save Order (using injected OrderStorage)
6. Return Result

## Payment Methods

### Credit Card
- Validates: 16-digit card number, expiry (MM/YY), 3-digit CVV
- Success rate: 90%

### UPI
- Validates: UPI ID (contains @), 10-digit mobile number
- Success rate: 95%

### Wallet
- Validates: Wallet ID, sufficient balance
- Success rate: 100% (if balance sufficient)

## Notification Channels

### Email
- Validates: Email format (contains @ and .)
- Sends order confirmation

### SMS
- Validates: 10-digit phone number
- Sends order confirmation

### Push
- Validates: Device token (>10 characters)
- Sends push notification

## Storage Options

### Database Storage
- In-memory HashMap storage
- Fast access
- Data lost on restart

### File Storage
- Persistent file storage
- Survives application restart
- Slower I/O operations

## Order Types

### Regular Order
- Standard processing
- Price multiplier: 1.0

### Discounted Order
- 15% discount applied
- Price multiplier: 0.85

### Priority Order
- Priority processing
- Price multiplier: 1.0

## Demo Scenarios

1. **Credit Card + Email + Database**
2. **UPI + SMS + File Storage**
3. **Wallet + Push + Database**
4. **Different Storage Mechanisms**
5. **Discounted Order**
6. **Priority Order**

## Key Design Patterns

- **Strategy Pattern** - Different payment/notification/storage strategies
- **Dependency Injection** - Constructor injection of dependencies
- **Template Method** - OrderService defines processing steps

## Extensibility

### Add New Payment Method
```java
public class GooglePayPayment implements PaymentProcessor {
    @Override
    public boolean processPayment(Order order) { ... }
    @Override
    public String getPaymentMethodName() { return "Google Pay"; }
    @Override
    public boolean validatePaymentDetails() { ... }
}
```

### Add New Notification Channel
```java
public class WhatsAppNotification implements NotificationChannel {
    @Override
    public boolean sendNotification(Order order) { ... }
    @Override
    public String getChannelName() { return "WhatsApp"; }
    @Override
    public boolean validateDetails() { ... }
}
```

### Add New Storage Mechanism
```java
public class CloudStorage implements OrderStorage {
    @Override
    public boolean saveOrder(Order order) { ... }
    @Override
    public Order getOrder(String orderId) { ... }
    @Override
    public Order[] getOrdersByCustomer(String customerId) { ... }
    @Override
    public String getStorageType() { return "Cloud"; }
}
```

## Benefits of SOLID Design

✓ **Maintainability** - Each class has single responsibility
✓ **Extensibility** - Add new features without modifying existing code
✓ **Testability** - Easy to mock dependencies for unit testing
✓ **Flexibility** - Swap implementations easily
✓ **Reusability** - Components can be reused in different contexts
✓ **Loose Coupling** - Classes depend on abstractions

## Summary

This system demonstrates professional software engineering with:
- Clean, maintainable code
- All 5 SOLID principles applied
- Multiple design patterns
- Easy to extend
- Production-ready quality
