package containers;

import items.Item;

import java.util.ArrayList;

public abstract class Container {

    // A list of Item objects that this container holds
    public  ArrayList<Item> items;

    // The size of the container represented as an array of doubles
    public double[] size;

    // The weight capacity of the container
    public  int weightCapacity;

    // The price of the container
    public int price;

    // Constructor for the Container class
    protected Container(double[] size, int weightCapacity, int price) {
        // Initialize the size, weight capacity, and price of the container
        this.size = size;
        this.weightCapacity = weightCapacity;
        this.price = price;

        // Initialize the items list
        this.items = new ArrayList<>();
    }

    // Abstract method for getting the volume, to be implemented by subclasses
    protected abstract double getVolume();
}
