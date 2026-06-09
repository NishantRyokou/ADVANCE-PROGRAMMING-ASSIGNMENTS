/**
 * OrderStatus enum - Represents different states of an order
 * Single Responsibility: Defines order status constants
 */
public enum OrderStatus {
    PENDING("Order is pending"),
    PAYMENT_PROCESSING("Payment is being processed"),
    PAYMENT_COMPLETED("Payment completed successfully"),
    PAYMENT_FAILED("Payment failed"),
    NOTIFICATION_SENT("Notification sent to customer"),
    COMPLETED("Order completed"),
    CANCELLED("Order cancelled");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
