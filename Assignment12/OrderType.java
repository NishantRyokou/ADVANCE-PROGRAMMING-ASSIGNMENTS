/**
 * OrderType enum - Represents different types of orders
 * Single Responsibility: Defines order type constants
 */
public enum OrderType {
    REGULAR("Regular Order", 1.0),
    DISCOUNTED("Discounted Order", 0.85),
    PRIORITY("Priority Order", 1.0);

    private final String description;
    private final double priceMultiplier;

    OrderType(String description, double priceMultiplier) {
        this.description = description;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDescription() {
        return description;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}
