import java.util.List;

public class ShippingService {
    public static void ship(List<IShippableItem> items) {
        if (items.isEmpty()) return;

        System.out.println("** Shipment notice **");
        System.out.println("Shipping rate is: " + CheckoutService.shippingRate  );
        double totalWeight = 0;

        for (IShippableItem item : items) {
            System.out.println(item.getName() + "\t\t" + item.getWeight() + "kg");
            totalWeight += item.getWeight();
        }

        System.out.printf("Total package weight: %.2fkg\n", totalWeight);
    }
}
