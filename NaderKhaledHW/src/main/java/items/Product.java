package items;

// Declare the class as abstract since it contains abstract methods
public abstract class Product {

    // The name of the product
    private final String name;

    // The size of the product represented as an array of doubles
    protected double[] size = new double[3];

    // The weight of the product
    protected double weight;

    // Constructor for the Product class
    public Product(String name) {
        // Initialize the name of the product
        this.name = name;
    }

    // Getter for the name field
    public String getName() {
        return this.name;
    }

    // Getter for the weight field
    public double getWeight() {
        return this.weight;
    }

    // Abstract method for calculating volume, to be implemented by subclasses
    public abstract double calculateVolume();

    // Abstract method for printing product information, to be implemented by subclasses
    public abstract void printProductInfo();
}
