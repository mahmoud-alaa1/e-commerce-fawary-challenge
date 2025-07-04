import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        Product scratchCard = new Product("Scratch Card", 50.0, 10, null, null);
        Product cheese = new Product(
                "Cheese",
                100.0,
                5,
                new HasExpiryDate(LocalDate.of(2025, 7, 10)),
                new HasWeight(0.4)
        );
    }
}