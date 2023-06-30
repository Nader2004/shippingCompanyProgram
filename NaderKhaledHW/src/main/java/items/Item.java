package items;

public class Item extends Product {

    // Constructor of the Item class
    public Item(String name) {
        // Call the superclass (Product) constructor
        super(name);
        // Depending on the product name, assign different size and weight values
        switch (name) {
            case "Laptop" -> {
                // Assign the size and weight for Laptop
                this.size = new double[]{0.6, 0.5, 0.5};
                this.weight = 6.5;
            }
            case "Mouse" -> {
                // Assign the size and weight for Mouse
                this.size = new double[]{0.3, 0.3, 0.2};
                this.weight = 0.2;
            }
            case "Desktop" -> {
                // Assign the size and weight for Desktop
                this.size = new double[]{1, 1.5, 0.5};
                this.weight = 20;
            }
            case "LCD Screen" -> {
                // Assign the size and weight for LCD Screen
                this.size = new double[]{1.2, 1.4, 0.8};
                this.weight = 2.6;
            }
        }
    }

    // Override the calculateVolume method from the Product class
    @Override
    public double calculateVolume() {
        // Calculate the volume of the item based on its dimensions
        return size[0] * size[1] * size[2];
    }

    // Override the printProductInfo method from the Product class
    @Override
    public void printProductInfo() {
        // Print the item information: name, volume, and weight
        System.out.println("Item name: " + this.getName() + ", volume: " + this.calculateVolume() + " m^3, weight: " + this.getWeight() + " kg");
    }
}
