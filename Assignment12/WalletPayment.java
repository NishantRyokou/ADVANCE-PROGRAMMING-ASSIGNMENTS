/**
 * WalletPayment class - Implements wallet payment processing
 * Single Responsibility: Handles only wallet payment logic
 * Liskov Substitution Principle: Can be used wherever PaymentProcessor is expected
 */
public class WalletPayment implements PaymentProcessor {
    private String walletId;
    private double walletBalance;

    public WalletPayment(String walletId, double walletBalance) {
        this.walletId = walletId;
        this.walletBalance = walletBalance;
    }

    @Override
    public boolean processPayment(Order order) {
        if (!validatePaymentDetails()) {
            System.out.println("❌ Wallet validation failed");
            return false;
        }

        if (walletBalance < order.getAmount()) {
            System.out.println("❌ Insufficient wallet balance");
            System.out.println("   Required: ₹" + order.getAmount());
            System.out.println("   Available: ₹" + walletBalance);
            return false;
        }

        // Process payment
        System.out.println("💰 Processing wallet payment...");
        System.out.println("   Wallet ID: " + walletId);
        System.out.println("   Amount: ₹" + order.getAmount());

        // Deduct from wallet
        walletBalance -= order.getAmount();
        System.out.println("✓ Wallet payment successful");
        System.out.println("   Remaining balance: ₹" + walletBalance);
        order.setPaymentMethod("Wallet");

        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Wallet";
    }

    @Override
    public boolean validatePaymentDetails() {
        return walletId != null && !walletId.isEmpty() && walletBalance >= 0;
    }

    public double getWalletBalance() {
        return walletBalance;
    }
}
