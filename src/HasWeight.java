public class HasWeight implements Shippable {
    private double weight;

    public HasWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
