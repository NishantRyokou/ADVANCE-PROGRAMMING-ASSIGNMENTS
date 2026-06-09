import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DatabaseStorage class - Implements database storage for orders
 * Single Responsibility: Handles only database storage logic
 * Liskov Substitution Principle: Can be used wherever OrderStorage is expected
 */
public class DatabaseStorage implements OrderStorage {
    private Map<String, Order> orderDatabase;
    private Map<String, List<String>> customerOrders;

    public DatabaseStorage() {
        this.orderDatabase = new HashMap<>();
        this.customerOrders = new HashMap<>();
    }

    @Override
    public boolean saveOrder(Order order) {
        try {
            System.out.println("💾 Saving order to database...");
            System.out.println("   Order ID: " + order.getOrderId());
            System.out.println("   Customer ID: " + order.getCustomerId());
            System.out.println("   Amount: ₹" + order.getAmount());

            // Save order
            orderDatabase.put(order.getOrderId(), order);

            // Update customer orders index
            customerOrders.computeIfAbsent(order.getCustomerId(), k -> new ArrayList<>())
                    .add(order.getOrderId());

            System.out.println("✓ Order saved to database successfully");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Failed to save order to database: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Order getOrder(String orderId) {
        System.out.println("🔍 Retrieving order from database: " + orderId);
        Order order = orderDatabase.get(orderId);
        if (order != null) {
            System.out.println("✓ Order found in database");
        } else {
            System.out.println("✗ Order not found in database");
        }
        return order;
    }

    @Override
    public Order[] getOrdersByCustomer(String customerId) {
        System.out.println("🔍 Retrieving orders for customer: " + customerId);
        List<String> orderIds = customerOrders.getOrDefault(customerId, new ArrayList<>());
        Order[] orders = new Order[orderIds.size()];

        for (int i = 0; i < orderIds.size(); i++) {
            orders[i] = orderDatabase.get(orderIds.get(i));
        }

        System.out.println("✓ Found " + orders.length + " orders for customer");
        return orders;
    }

    @Override
    public String getStorageType() {
        return "Database";
    }
}
