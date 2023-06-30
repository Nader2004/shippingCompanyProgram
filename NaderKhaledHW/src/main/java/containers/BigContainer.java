package containers;

public class BigContainer extends Container {

    // Constructor for the BigContainer class
    public BigContainer() {
        // Call the constructor of the superclass (Container) with specific values
        super(new double[]{2.59, 2.43, 12.01}, Integer.MAX_VALUE,1800);
    }

    // Overridden method from the Container class for getting the volume
    @Override
    public double getVolume() {
        // Calculate the volume based on the size
        return this.size[0] * this.size[1] * this.size[2];
    }
}
