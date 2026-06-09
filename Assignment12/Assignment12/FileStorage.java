import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileStorage class - Implements file-based storage for orders
 * Single Responsibility: Handles only file storage logic
 * Liskov Substitution Principle: Can be used wherever OrderStorage is expected
 */
public class FileStorage implements OrderStorage {
    private String storageDirectory;
    private static final String FILE_EXTENSION = ".txt";

    public FileStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
        // Create directory if it doesn't exist
        File dir = new File(storageDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public boolean saveOrder(Order order) {
        try {
            System.out.println("💾 Saving order to file...");
            System.out.println("   Order ID: " + order.getOrderId());
            System.out.println("   Customer ID: " + order.getCustomerId());
            System.out.println("   Amount: ₹" + order.getAmount());

            String fileName = storageDirectory + File.separator + order.getOrderId() + FILE_EXTENSION;
            FileWriter writer = new FileWriter(fileName);

            writer.write("Order ID: " + order.getOrderId() + "\n");
            writer.write("Customer ID: " + order.getCustomerId() + "\n");
            writer.write("Amount: " + order.getAmount() + "\n");
            writer.write("Order Type: " + order.getOrderType() + "\n");
            writer.write("Status: " + order.getStatus() + "\n");
            writer.write("Created At: " + order.getCreatedAt() + "\n");
            writer.write("Payment Method: " + order.getPaymentMethod() + "\n");
            writer.write("Notification Channel: " + order.getNotificationChannel() + "\n");

            writer.close();
            System.out.println("✓ Order saved to file successfully");
            return true;
        } catch (IOException e) {
            System.out.println("✗ Failed to save order to file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Order getOrder(String orderId) {
        try {
            System.out.println("🔍 Retrieving order from file: " + orderId);
            String fileName = storageDirectory + File.separator + orderId + FILE_EXTENSION;
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("✗ Order file not found");
                return null;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String customerId = null;
            double amount = 0;
            OrderType orderType = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Customer ID: ")) {
                    customerId = line.substring("Customer ID: ".length());
                } else if (line.startsWith("Amount: ")) {
                    amount = Double.parseDouble(line.substring("Amount: ".length()));
                } else if (line.startsWith("Order Type: ")) {
                    orderType = OrderType.valueOf(line.substring("Order Type: ".length()));
                }
            }

            reader.close();

            if (customerId != null && orderType != null) {
                Order order = new Order(customerId, amount, orderType);
                System.out.println("✓ Order retrieved from file");
                return order;
            }

            return null;
        } catch (IOException e) {
            System.out.println("✗ Failed to retrieve order from file: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Order[] getOrdersByCustomer(String customerId) {
        try {
            System.out.println("🔍 Retrieving orders for customer from files: " + customerId);
            File dir = new File(storageDirectory);
            File[] files = dir.listFiles((d, name) -> name.endsWith(FILE_EXTENSION));

            List<Order> orders = new ArrayList<>();

            if (files != null) {
                for (File file : files) {
                    Order order = getOrder(file.getName().replace(FILE_EXTENSION, ""));
                    if (order != null && order.getCustomerId().equals(customerId)) {
                        orders.add(order);
                    }
                }
            }

            System.out.println("✓ Found " + orders.size() + " orders for customer");
            return orders.toArray(new Order[0]);
        } catch (Exception e) {
            System.out.println("✗ Failed to retrieve orders from files: " + e.getMessage());
            return new Order[0];
        }
    }

    @Override
    public String getStorageType() {
        return "File";
    }
}
