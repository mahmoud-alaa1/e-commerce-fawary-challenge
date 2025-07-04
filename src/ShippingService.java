import java.util.List;

public class ShippingService {
    static final public double shippingRate=0.336;

    public  static  double calculateShippingFee(List<IShippableItem> items){
        double totalFee=0;
        for (IShippableItem item : items) {
            totalFee += item.getWeight()*shippingRate;
        }
        return totalFee;
    }

    public static void ship(List<IShippableItem> items) {
        if (items.isEmpty()) return;

        System.out.println("** Shipment notice **");
        System.out.printf("%-25s %-10s\n", "Product", "Weight");
        System.out.println("---------------------------------");

        double totalWeight = 0;

        for (IShippableItem item : items) {
            System.out.printf("%-25s %-10.2fkg\n", item.getName(), item.getWeight());
            totalWeight += item.getWeight();
        }

        System.out.println("---------------------------------");
        System.out.printf("Total package weight: %.2fkg\n", totalWeight);
    }
}
