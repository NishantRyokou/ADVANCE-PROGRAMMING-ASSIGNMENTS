/**
 * OrderStorage interface - Defines contract for storing orders
 * Interface Segregation Principle: Small, role-specific interface
 * Open/Closed Principle: New storage mechanisms can be added without modifying existing code
 */
public interface OrderStorage {
    boolean saveOrder(Order order);

    Order getOrder(String orderId);

    Order[] getOrdersByCustomer(String customerId);

    String getStorageType();
}
