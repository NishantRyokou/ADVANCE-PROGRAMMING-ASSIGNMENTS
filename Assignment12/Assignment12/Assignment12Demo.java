public class Assignment12Demo {
    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     E-Commerce Order Processing System - SOLID Principles      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

        demonstrateScenario1();

        demonstrateScenario2();

        demonstrateScenario3();

        demonstrateScenario4();

        demonstrateScenario5();

        demonstrateScenario6();

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    Demo Complete                               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }

    private static void demonstrateScenario1() {
        System.out.println("\n▶ Scenario 1: Credit Card + Email + Database");
        System.out.println("━".repeat(70));

        Order order1 = new Order("CUST001", 5000, OrderType.REGULAR);

        PaymentProcessor creditCardPayment = new CreditCardPayment(
                "1234567890123456",
                "Raj Kumar",
                "12/25",
                "123"
        );

        NotificationChannel emailNotification = new EmailNotification("raj@example.com");

        OrderStorage databaseStorage = new DatabaseStorage();

        OrderService orderService = new OrderService(
                creditCardPayment,
                emailNotification,
                databaseStorage
        );

        orderService.processOrder(order1);
    }

    private static void demonstrateScenario2() {
        System.out.println("\n▶ Scenario 2: UPI + SMS + File Storage");
        System.out.println("━".repeat(70));

        Order order2 = new Order("CUST002", 3000, OrderType.REGULAR);

        PaymentProcessor upiPayment = new UPIPayment(
                "priya.sharma@upi",
                "9876543210"
        );

        NotificationChannel smsNotification = new SMSNotification("9876543210");

        OrderStorage fileStorage = new FileStorage("./orders");

        OrderService orderService = new OrderService(
                upiPayment,
                smsNotification,
                fileStorage
        );

        orderService.processOrder(order2);
    }

    private static void demonstrateScenario3() {
        System.out.println("\n▶ Scenario 3: Wallet + Push Notification + Database");
        System.out.println("━".repeat(70));

        Order order3 = new Order("CUST003", 2000, OrderType.REGULAR);

        PaymentProcessor walletPayment = new WalletPayment(
                "WALLET_AMIT_001",
                5000
        );

        NotificationChannel pushNotification = new PushNotification(
                "device_token_abc123xyz789"
        );

        OrderStorage databaseStorage = new DatabaseStorage();

        OrderService orderService = new OrderService(
                walletPayment,
                pushNotification,
                databaseStorage
        );

        orderService.processOrder(order3);
    }

    private static void demonstrateScenario4() {
        System.out.println("\n▶ Scenario 4: Different Storage Mechanisms");
        System.out.println("━".repeat(70));

        Order order4 = new Order("CUST004", 7500, OrderType.DISCOUNTED);

        System.out.println("\n📊 Using Database Storage:");
        OrderStorage dbStorage = new DatabaseStorage();
        dbStorage.saveOrder(order4);

        System.out.println("\n📊 Using File Storage:");
        OrderStorage fileStorage = new FileStorage("./orders");
        fileStorage.saveOrder(order4);

        System.out.println("\n📊 Retrieving orders:");
        Order[] customerOrders = dbStorage.getOrdersByCustomer("CUST004");
        System.out.println("   Found " + customerOrders.length + " orders in database");
    }

    private static void demonstrateScenario5() {
        System.out.println("\n▶ Scenario 5: Discounted Order");
        System.out.println("━".repeat(70));

        Order discountedOrder = new Order("CUST005", 10000, OrderType.DISCOUNTED);
        double finalAmount = discountedOrder.getAmount() * discountedOrder.getOrderType().getPriceMultiplier();

        System.out.println("\n🏷️ Discount Applied:");
        System.out.println("   Original Amount: ₹" + discountedOrder.getAmount());
        System.out.println("   Discount: " + ((1 - discountedOrder.getOrderType().getPriceMultiplier()) * 100) + "%");
        System.out.println("   Final Amount: ₹" + finalAmount);

        PaymentProcessor payment = new CreditCardPayment(
                "9876543210987654",
                "Neeta Verma",
                "11/24",
                "456"
        );

        NotificationChannel notification = new EmailNotification("neeta@example.com");
        OrderStorage storage = new DatabaseStorage();

        OrderService orderService = new OrderService(payment, notification, storage);
        orderService.processOrder(discountedOrder);
    }

    private static void demonstrateScenario6() {
        System.out.println("\n▶ Scenario 6: Priority Order");
        System.out.println("━".repeat(70));

        Order priorityOrder = new Order("CUST006", 15000, OrderType.PRIORITY);

        System.out.println("\n⭐ Priority Order Details:");
        System.out.println("   Order Type: " + priorityOrder.getOrderType().getDescription());
        System.out.println("   Amount: ₹" + priorityOrder.getAmount());
        System.out.println("   Priority Processing: Yes");

        PaymentProcessor payment = new UPIPayment(
                "vijay.singh@upi",
                "9123456789"
        );

        NotificationChannel notification = new PushNotification(
                "priority_device_token_xyz"
        );
        OrderStorage storage = new FileStorage("./orders");

        OrderService orderService = new OrderService(payment, notification, storage);
        orderService.processOrder(priorityOrder);
    }
}
