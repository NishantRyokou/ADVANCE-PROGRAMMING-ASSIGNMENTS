/**
 * CreditCardPayment class - Implements credit card payment processing
 * Single Responsibility: Handles only credit card payment logic
 * Liskov Substitution Principle: Can be used wherever PaymentProcessor is expected
 */
public class CreditCardPayment implements PaymentProcessor {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(Order order) {
        if (!validatePaymentDetails()) {
            System.out.println("❌ Credit card validation failed");
            return false;
        }

        // Simulate payment processing
        System.out.println("💳 Processing credit card payment...");
        System.out.println("   Card: " + maskCardNumber(cardNumber));
        System.out.println("   Amount: ₹" + order.getAmount());

        // Simulate successful payment (90% success rate)
        boolean success = Math.random() > 0.1;

        if (success) {
            System.out.println("✓ Credit card payment successful");
            order.setPaymentMethod("Credit Card");
        } else {
            System.out.println("✗ Credit card payment failed");
        }

        return success;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }

    @Override
    public boolean validatePaymentDetails() {
        return cardNumber != null && cardNumber.length() == 16 &&
               cardHolderName != null && !cardHolderName.isEmpty() &&
               expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}") &&
               cvv != null && cvv.length() == 3;
    }

    private String maskCardNumber(String cardNumber) {
        return "****-****-****-" + cardNumber.substring(12);
    }
}
