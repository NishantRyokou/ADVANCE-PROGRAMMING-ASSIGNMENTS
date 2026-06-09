/**
 * NotificationChannel interface - Defines contract for sending notifications
 * Interface Segregation Principle: Small, role-specific interface
 * Open/Closed Principle: New notification channels can be added without modifying existing code
 */
public interface NotificationChannel {
    boolean sendNotification(Order order);

    String getChannelName();

    boolean validateDetails();
}
