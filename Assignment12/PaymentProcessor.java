/**
 * PaymentProcessor interface - Defines contract for payment processing
 * Interface Segregation Principle: Small, role-specific interface
 * Open/Closed Principle: New payment methods can be added without modifying existing code
 */
public interface PaymentProcessor {
    boolean processPayment(Order order);

    String getPaymentMethodName();

    boolean validatePaymentDetails();
}
