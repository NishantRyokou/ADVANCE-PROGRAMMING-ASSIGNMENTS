/**
 * PushNotification class - Implements push notification
 * Single Responsibility: Handles only push notification logic
 * Liskov Substitution Principle: Can be used wherever NotificationChannel is expected
 */
public class PushNotification implements NotificationChannel {
    private String deviceToken;

    public PushNotification(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public boolean sendNotification(Order order) {
        if (!validateDetails()) {
            System.out.println("❌ Push notification validation failed");
            return false;
        }

        System.out.println("🔔 Sending push notification...");
        System.out.println("   Device Token: " + maskToken(deviceToken));
        System.out.println("   Title: Order Confirmed");
        System.out.println("   Body: Your order " + order.getOrderId() + " is confirmed!");
        System.out.println("   Amount: ₹" + order.getAmount());
        System.out.println("✓ Push notification sent successfully");
        order.setNotificationChannel("Push");

        return true;
    }

    @Override
    public String getChannelName() {
        return "Push";
    }

    @Override
    public boolean validateDetails() {
        return deviceToken != null && !deviceToken.isEmpty() && deviceToken.length() > 10;
    }

    private String maskToken(String token) {
        if (token.length() <= 10) return token;
        return token.substring(0, 5) + "..." + token.substring(token.length() - 5);
    }
}
