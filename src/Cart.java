import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive");

        int currentInCart = items.getOrDefault(product, 0);

        if (currentInCart + quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Total in cart exceeds stock for: " + product.getName());
        }

        items.put(product, currentInCart + quantity);
    }
    public void removeProduct(Product product, int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive");

        int currentInCart = items.getOrDefault(product, 0);

        if (currentInCart < quantity) {
            throw new IllegalArgumentException("cart has less than the target quantity for: " + product.getName());
        }

        items.put(product, currentInCart - quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}
