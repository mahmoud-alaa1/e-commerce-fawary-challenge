import java.util.*;

public class CheckoutService {

    public static void checkout(Customer customer) {
        Cart cart = customer.getCart();

        handleEmptyCart(cart);
        handleExpiredProducts(cart);
        List<IShippableItem> itemsToShip = extractShippableItems(cart);


        double subtotal = calculateSubtotal(cart);
        double shippingFees = ShippingService.calculateShippingFee(itemsToShip);
        double total = subtotal + shippingFees;

        ensureCustomerHasEnoughBalance(customer, total);

        //succeful checkout
        reduceProductQuantities(cart);
        customer.reduceBalance(total);

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

    private static List<Product> getExpiredItems(Cart cart) {
        List<Product> expiredItems = new ArrayList<>();
        cart.getItems().forEach((product, _) -> {
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

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product.isShippable()) {
                for (int i = 0; i < quantity; i++) {
                    items.add(product);
                }
            }
        }
        return items;
    }

    private static void printReceipt(Cart cart, double subtotal, double shipping, double total, double remainingBalance) {
        System.out.println("** Checkout Receipt **");
        System.out.printf("%-5s %-25s %-10s %-10s\n", "Qty", "Product", "Unit Price", "Total");
        System.out.println("------------------------------------------------------------");

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            int qty = entry.getValue();
            Product product = entry.getKey();
            double lineTotal = qty * product.getPrice();
            System.out.printf("%-5d %-25s %-10.2f %-10.2f\n", qty, product.getName(), product.getPrice(), lineTotal);
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("%-35s : %.2f\n", "Subtotal", subtotal);
        System.out.printf("%-35s : %.2f\n", "Shipping", shipping);
        System.out.printf("%-35s : %.2f\n", "Total Paid", total);
        System.out.printf("%-35s : %.2f\n", "Balance Left", remainingBalance);
    }
}
