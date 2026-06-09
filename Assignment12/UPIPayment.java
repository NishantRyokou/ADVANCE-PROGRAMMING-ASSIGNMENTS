/**
 * UPIPayment class - Implements UPI payment processing
 * Single Responsibility: Handles only UPI payment logic
 * Liskov Substitution Principle: Can be used wherever PaymentProcessor is expected
 */
public class UPIPayment implements PaymentProcessor {
    private String upiId;
    private String mobileNumber;

    public UPIPayment(String upiId, String mobileNumber) {
        this.upiId = upiId;
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean processPayment(Order order) {
        if (!validatePaymentDetails()) {
            System.out.println("❌ UPI validation failed");
            return false;
        }

        // Simulate payment processing
        System.out.println("📱 Processing UPI payment...");
        System.out.println("   UPI ID: " + upiId);
        System.out.println("   Amount: ₹" + order.getAmount());

        // Simulate successful payment (95% success rate)
        boolean success = Math.random() > 0.05;

        if (success) {
            System.out.println("✓ UPI payment successful");
            order.setPaymentMethod("UPI");
        } else {
            System.out.println("✗ UPI payment failed");
        }

        return success;
    }

    @Override
    public String getPaymentMethodName() {
        return "UPI";
    }

    @Override
    public boolean validatePaymentDetails() {
        return upiId != null && upiId.contains("@") &&
               mobileNumber != null && mobileNumber.length() == 10;
    }
}
