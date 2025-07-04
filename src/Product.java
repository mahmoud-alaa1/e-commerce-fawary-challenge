public class Product {
    private String name;
    private double price;
    private int quantity;

    private Expirable expirableBehavior;
    private Shippable shippableBehavior;

    public Product(String name, double price, int quantity, Expirable expirableBehavior,
                   Shippable shippableBehavior) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expirableBehavior = expirableBehavior;
        this.shippableBehavior = shippableBehavior;
    }

    public boolean isExpirable() {
        return expirableBehavior != null;
    }

    public boolean isExpired() {
        if (expirableBehavior == null)
            throw new IllegalStateException("Product is not expirable");
        return isExpirable() && expirableBehavior.isExpired();
    }

    public boolean isShippable() {
        return shippableBehavior != null;
    }

    public double getWeight() {
        if (shippableBehavior == null)
            throw new IllegalStateException("Product is not shippable");
        return shippableBehavior.getWeight();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantityBy(int amount) {
        if (amount > quantity)
            throw new IllegalArgumentException("Insufficient stock");
        quantity -= amount;
    }
    public void reduceQuantityTo(int qty) {
        if (qty >= quantity)
            throw new IllegalArgumentException("The quantity passed is greater than the stock");
        quantity = qty;
    }
}
