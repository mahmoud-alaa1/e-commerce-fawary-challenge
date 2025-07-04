import java.util.*;

public class CheckoutService {

    public static void checkout(Customer customer) {
        Cart cart = customer.getCart();

        handleEmptyCart(cart);
        handleExpiredProducts(cart);
        handleEnoughStocks(cart);

        double subtotal = calculateSubtotal(cart);
        double shippingFees = calculateShipping(cart);
        double total = subtotal + shippingFees;

        ensureCustomerHasEnoughBalance(customer, total);

        //succeful checkout
        reduceProductQuantities(cart);
        customer.reduceBalance(total);

        List<IShippableItem> itemsToShip = extractShippableItems(cart);
        ShippingService.ship(itemsToShip);

        printReceipt(cart, subtotal, shippingFees, total, customer.getBalance());
        cart.clear();
    }


    private static void handleEmptyCart(Cart cart) {
        if (cart.isEmpty())
            throw new IllegalStateException("Cart is empty");
    }

    private static void handleExpiredProducts(Cart cart) {
        List<Product> expired = getExpiredItems(cart);
        if (!expired.isEmpty()) {
            System.out.println("The following items are expired:");
            for (int i = 0; i < expired.size(); i++) {
                Product p = expired.get(i);
                System.out.println((i + 1) + ". " + p.getName());
            }
            throw new IllegalStateException("Please remove the expired products and try again");
        }
    }
    private static void handleEnoughStocks(Cart cart) {
        List<String> insufficientStockItems = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int requestedQty = entry.getValue();

            if (product.getQuantity() < requestedQty) {
                String msg = String.format("%s (available: %d, requested: %d)",
                        product.getName(), product.getQuantity(), requestedQty);
                insufficientStockItems.add(msg);
            }
        }

        if (!insufficientStockItems.isEmpty()) {
            System.out.println("The following items do not have enough stock:");
            for (int i = 0; i < insufficientStockItems.size(); i++) {
                System.out.println((i + 1) + ". " + insufficientStockItems.get(i));
            }
            throw new IllegalStateException("Please fix quantities and try again.");
        }
    }

    private static List<Product> getExpiredItems(Cart cart) {
        List<Product> expiredItems = new ArrayList<>();
        cart.getItems().forEach((product, qty) -> {
            if (product.isExpirable() && product.isExpired()) {
                expiredItems.add(product);
            }
        });
        return expiredItems;
    }

    private static double calculateSubtotal(Cart cart) {
        double subtotal = 0.0;
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            subtotal += product.getPrice() * quantity;
        }
        return subtotal;
    }

    private static double calculateShipping(Cart cart) {
        //rate 1$ / 0.36 kg
        double rate = 1 / 0.36;
        double shippingFee = 0;

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            shippingFee += product.getWeight() * quantity * rate;
        }

        return shippingFee;
    }

    private static void ensureCustomerHasEnoughBalance(Customer customer, double total) {
        if (customer.getBalance() < total)
            throw new IllegalStateException("Customer balance is not enough");
    }

    private static void reduceProductQuantities(Cart cart) {
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            entry.getKey().reduceQuantityBy(entry.getValue());
        }
    }

    private static List<IShippableItem> extractShippableItems(Cart cart) {
        List<IShippableItem> items = new ArrayList<>();
        for (Product product : cart.getItems().keySet()) {
            if (product instanceof IShippableItem) {
                items.add((IShippableItem) product);
            }
        }
        return items;
    }

    private static void printReceipt(Cart cart, double subtotal, double shipping, double total, double remainingBalance) {
        System.out.println("** Checkout Receipt **");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            System.out.printf("%dx %s - %.2f\n", entry.getValue(), entry.getKey().getName(), entry.getKey().getPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal      : %.2f\n", subtotal);
        System.out.printf("Shipping      : %.2f\n", shipping);
        System.out.printf("Total Paid    : %.2f\n", total);
        System.out.printf("Balance Left  : %.2f\n", remainingBalance);
    }
}
