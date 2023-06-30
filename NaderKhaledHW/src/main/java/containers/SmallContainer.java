package containers;

public class SmallContainer extends Container {

    // Constructor for the SmallContainer class
    public SmallContainer(int price) {
        // Call the constructor of the superclass (Container) with specific values
        super(new double[]{2.59, 2.43, 6.06}, 500, price);
    }

    // Overridden method from the Container class for getting the volume
    @Override
    public double getVolume() {
        // Calculate the volume based on the size
        return this.size[0] * this.size[1] * this.size[2];
    }
}
