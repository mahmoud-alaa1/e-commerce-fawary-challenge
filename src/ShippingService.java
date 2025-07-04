import java.util.List;

public class ShippingService {
    static final public double shippingRate=0.336;

    public  static  double calculateShippingFee(List<IShippableItem> items){
        double totalFee=0;
        for (IShippableItem item : items) {
            totalFee += item.getWeight();
        }
        return totalFee;
    }

    public static void ship(List<IShippableItem> items) {
        if (items.isEmpty()) return;

        System.out.println("** Shipment notice **");
        System.out.println("Shipping rate is: " + shippingRate  );
        double totalWeight = 0;

        for (IShippableItem item : items) {
            System.out.println(item.getName() + "\t\t" + item.getWeight() + "kg");
            totalWeight += item.getWeight();
        }

        System.out.printf("Total package weight: %.2fkg\n", totalWeight);
    }
}
