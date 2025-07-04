import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // --- Shared Products ---
        Product tv = new Product("T.V", 350, 3, 2.0, null);
        Product cheese = new Product("Cheese", 100, 5, 0.3, LocalDate.now().plusDays(10));
        Product mobileScratch = new Product("Mobile Scratch", 20, 10, null, null);
        Product expiredPizza = new Product("Pizza", 50, 10, 0.2, LocalDate.now().minusDays(3));

        // -----------------------------------
        System.out.println("Test 1: Successful Checkout");
        try {
            Customer mahmoud = new Customer("Mahmoud Alaa", 15000);
            Cart cart = mahmoud.getCart();
            cart.addProduct(tv, 2);
            cart.addProduct(cheese, 2);
            cart.addProduct(mobileScratch, 2);
            CheckoutService.checkout(mahmoud);
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }

        // -----------------------------------
        System.out.println("\nTest 2: Cart Contains Expired Product");
        try {
            Customer user2 = new Customer("Ahmed", 10000);
            Cart cart = user2.getCart();
            cart.addProduct(expiredPizza, 1);
            cart.addProduct(cheese, 1);
            CheckoutService.checkout(user2);
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }

        // -----------------------------------
        System.out.println("\nTest 3: Product Out of Stock");
        try {
            Customer user3 = new Customer("Mona", 10000);
            Cart cart = user3.getCart();
            cart.addProduct(tv, 10); // Only 1 left from before
            CheckoutService.checkout(user3);
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }

        // -----------------------------------
        System.out.println("\nTest 4: Empty Cart");
        try {
            Customer user4 = new Customer("Sarah", 5000);
            CheckoutService.checkout(user4);
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }

        // -----------------------------------
        System.out.println("\nTest 5: Insufficient Balance");
        try {
            Customer user5 = new Customer("Ziad", 10); // Very low balance
            Cart cart = user5.getCart();
            cart.addProduct(cheese, 3);
            cart.addProduct(tv, 1);
            CheckoutService.checkout(user5);
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }
}
