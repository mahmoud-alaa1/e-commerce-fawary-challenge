import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Product tv = new Product("T.V", 350, 100, 2.0, null);
        Product cheese = new Product("Cheese", 500, 36, 0.3, LocalDate.now().plusMonths(1));

        Customer mahmoud = new Customer("Mahmoud Alaa", 15000);
        Cart mahmoudCart = mahmoud.getCart();
        mahmoudCart.addProduct(tv, 3);
        mahmoudCart.addProduct(cheese, 5);

        CheckoutService.checkout(mahmoud);
    }
}
