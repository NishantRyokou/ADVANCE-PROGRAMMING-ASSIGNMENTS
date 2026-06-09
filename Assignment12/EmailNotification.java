/**
 * EmailNotification class - Implements email notification
 * Single Responsibility: Handles only email notification logic
 * Liskov Substitution Principle: Can be used wherever NotificationChannel is expected
 */
public class EmailNotification implements NotificationChannel {
    private String emailAddress;

    public EmailNotification(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean sendNotification(Order order) {
        if (!validateDetails()) {
            System.out.println("❌ Email validation failed");
            return false;
        }

        System.out.println("📧 Sending email notification...");
        System.out.println("   To: " + emailAddress);
        System.out.println("   Subject: Order Confirmation - " + order.getOrderId());
        System.out.println("   Message: Your order has been placed successfully!");
        System.out.println("   Amount: ₹" + order.getAmount());
        System.out.println("✓ Email sent successfully");
        order.setNotificationChannel("Email");

        return true;
    }

    @Override
    public String getChannelName() {
        return "Email";
    }

    @Override
    public boolean validateDetails() {
        return emailAddress != null && emailAddress.contains("@") && emailAddress.contains(".");
    }
}
