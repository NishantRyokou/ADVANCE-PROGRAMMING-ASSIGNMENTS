/**
 * SMSNotification class - Implements SMS notification
 * Single Responsibility: Handles only SMS notification logic
 * Liskov Substitution Principle: Can be used wherever NotificationChannel is expected
 */
public class SMSNotification implements NotificationChannel {
    private String phoneNumber;

    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean sendNotification(Order order) {
        if (!validateDetails()) {
            System.out.println("❌ SMS validation failed");
            return false;
        }

        System.out.println("📱 Sending SMS notification...");
        System.out.println("   To: " + phoneNumber);
        System.out.println("   Message: Order " + order.getOrderId() + " confirmed!");
        System.out.println("   Amount: ₹" + order.getAmount());
        System.out.println("✓ SMS sent successfully");
        order.setNotificationChannel("SMS");

        return true;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }

    @Override
    public boolean validateDetails() {
        return phoneNumber != null && phoneNumber.length() == 10 && phoneNumber.matches("\\d+");
    }
}
