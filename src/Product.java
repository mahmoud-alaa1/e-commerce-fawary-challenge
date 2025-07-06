import java.time.LocalDate;

public class Product implements IShippableItem {
    private String name;
    private double price;
    private int quantity;

    private Double weight;
    private LocalDate expiryDate;

    public Product(String name, double price, int quantity, Double weight, LocalDate expiryDate) {

        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Product name cannot be null or empty");
        if (price < 0)
            throw new IllegalArgumentException("Product price cannot be negative");
        if (quantity < 0)
            throw new IllegalArgumentException("Product quantity cannot be negative");
        if (weight != null && weight <= 0)
            throw new IllegalArgumentException("Product weight must be positive or null");
        if (expiryDate != null && expiryDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Product expiry date cannot be in the past");


        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.expiryDate = expiryDate;
    }


    public boolean isShippable() {
        return weight != null;
    }

    public boolean isExpirable() {
        return expiryDate != null;
    }

    public boolean isExpired() {
        if (!isExpirable())
            throw new IllegalStateException("Product is not expirable");
        return expiryDate.isBefore(LocalDate.now());
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantityBy(int amount) {
        if (amount > quantity)
            throw new IllegalArgumentException("Out of stock");
        quantity -= amount;
    }

    public void reduceQuantityTo(int qty) {
        if (qty > quantity)
            throw new IllegalArgumentException("The passed quantity should be less than current quantity");
        quantity = qty;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getWeight() {
        if (!this.isShippable()) {
            throw new IllegalArgumentException("This item isn't shipable");
        }
        return this.weight;
    }

}
