/**
 * OrderService class - Orchestrates order processing
 * Single Responsibility: Coordinates order creation and processing
 * Dependency Inversion Principle: Depends on abstractions (PaymentProcessor, NotificationChannel, OrderStorage)
 * Uses dependency injection for all dependencies
 */
public class OrderService {
    private PaymentProcessor paymentProcessor;
    private NotificationChannel notificationChannel;
    private OrderStorage orderStorage;

    public OrderService(PaymentProcessor paymentProcessor, 
                       NotificationChannel notificationChannel, 
                       OrderStorage orderStorage) {
        this.paymentProcessor = paymentProcessor;
        this.notificationChannel = notificationChannel;
        this.orderStorage = orderStorage;
    }

    public boolean processOrder(Order order) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Processing Order: " + order.getOrderId());
        System.out.println("=".repeat(70));

        // Step 1: Validate order
        if (!validateOrder(order)) {
            System.out.println("❌ Order validation failed");
            order.setStatus(OrderStatus.CANCELLED);
            return false;
        }

        // Step 2: Process payment
        order.setStatus(OrderStatus.PAYMENT_PROCESSING);
        if (!processPayment(order)) {
            System.out.println("❌ Payment processing failed");
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            return false;
        }

        order.setStatus(OrderStatus.PAYMENT_COMPLETED);

        // Step 3: Send notification
        if (!sendNotification(order)) {
            System.out.println("⚠️ Notification failed, but order is processed");
        } else {
            order.setStatus(OrderStatus.NOTIFICATION_SENT);
        }

        // Step 4: Save order
        if (!saveOrder(order)) {
            System.out.println("⚠️ Order storage failed, but order is processed");
        } else {
            order.setStatus(OrderStatus.COMPLETED);
        }

        System.out.println("\n✓ Order processing completed successfully");
        System.out.println("=".repeat(70));
        return true;
    }

    private boolean validateOrder(Order order) {
        System.out.println("\n📋 Validating order...");
        System.out.println("   Order ID: " + order.getOrderId());
        System.out.println("   Customer ID: " + order.getCustomerId());
        System.out.println("   Amount: ₹" + order.getAmount());
        System.out.println("   Order Type: " + order.getOrderType().getDescription());

        boolean valid = order.getAmount() > 0 && 
                       order.getCustomerId() != null && 
                       !order.getCustomerId().isEmpty();

        if (valid) {
            System.out.println("✓ Order validation successful");
        } else {
            System.out.println("✗ Order validation failed");
        }

        return valid;
    }

    private boolean processPayment(Order order) {
        System.out.println("\n💳 Processing payment using: " + paymentProcessor.getPaymentMethodName());
        return paymentProcessor.processPayment(order);
    }

    private boolean sendNotification(Order order) {
        System.out.println("\n📢 Sending notification using: " + notificationChannel.getChannelName());
        return notificationChannel.sendNotification(order);
    }

    private boolean saveOrder(Order order) {
        System.out.println("\n💾 Saving order using: " + orderStorage.getStorageType());
        return orderStorage.saveOrder(order);
    }

    public Order retrieveOrder(String orderId) {
        return orderStorage.getOrder(orderId);
    }

    public Order[] retrieveCustomerOrders(String customerId) {
        return orderStorage.getOrdersByCustomer(customerId);
    }
}
