import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private String orderId;
    private String customerId;
    private double amount;
    private OrderType orderType;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String paymentMethod;
    private String notificationChannel;

    public Order(String customerId, double amount, OrderType orderType) {
        this.orderId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.amount = amount;
        this.orderType = orderType;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNotificationChannel() {
        return notificationChannel;
    }

    public void setNotificationChannel(String notificationChannel) {
        this.notificationChannel = notificationChannel;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", orderType=" + orderType +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
