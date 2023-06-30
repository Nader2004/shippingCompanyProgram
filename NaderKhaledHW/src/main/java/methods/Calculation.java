package methods;

import containers.BigContainer;
import containers.SmallContainer;
import items.Item;

import java.util.ArrayList;

public class Calculation {

    // Define class properties
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<BigContainer> bigContainers = new ArrayList<>();
    private ArrayList<SmallContainer> smallContainers = new ArrayList<>();

    // Getter and setter methods for class properties

    // This method returns the list of items.
    public ArrayList<Item> getItems() {
        return this.items;
    }

    // This method sets the list of items. This can be used to replace the entire list of items with a new list.
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    // This method sets the list of big containers. This can be used to replace the entire list of big containers with a new list.
    public void setBigContainers(ArrayList<BigContainer> bigContainers) {
        this.bigContainers = bigContainers;
    }

    // This method sets the list of small containers. This can be used to replace the entire list of small containers with a new list.
    public void setSmallContainers(ArrayList<SmallContainer> smallContainers) {
        this.smallContainers = smallContainers;
    }

    // This method returns the list of big containers.
    public ArrayList<BigContainer> getBigContainers() {
        return this.bigContainers;
    }

    // This method returns the list of small containers.
    public ArrayList<SmallContainer> getSmallContainers() {
        return this.smallContainers;
    }

    // This method is used to add an item to the list of items.
    public void addItem(Item item) {
        this.items.add(item);
    }

    // This method is used to remove an item from the list of items given its index.
    // If the index is out of range (i.e., there are fewer items than the given index), no item is removed.
    public void removeItem(int index) {
        try {
           if (index < this.items.size()) {
            this.items.remove(index);
          }
        } catch (Exception e) {
            System.out.println("something went wrong with the index. Maybe it's out of range");
        }
    }


    // Method to calculate the total volume of all items
    private int getTotalVolume() {
        int totalVolume = 0;
        for (Item item : this.items) {
            totalVolume += item.calculateVolume();
        }
        return totalVolume;
    }

    // Method to calculate the total weight of all items
    private int getTotalWeight() {
        int totalWeight = 0;
        for (Item item : this.items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    // Method to calculate the best shipping method (in terms of number of big and small containers)
    public void bestShipping() {
        // Initialize a big and a small container
        BigContainer bigContainer = new BigContainer();
        SmallContainer smallContainer = new SmallContainer(1000);

        // Calculate the total volume and total weight
        int totalVol = getTotalVolume();
        int totalWeight = getTotalWeight();

        // Calculate the number of big and small containers needed
        double bigCNum = totalVol / bigContainer.getVolume();
        double remainingVol = totalVol % bigContainer.getVolume();
        double smallCNum = remainingVol / smallContainer.getVolume();

        // If there's remaining volume, add an extra container based on the total weight
        if ((remainingVol % smallContainer.getVolume()) > 0) {
            if (totalWeight < 500) {
                bigCNum++;
            } else {
                smallCNum++;
            }
        }

        // Add the calculated number of big containers to the list
        for (int i = 0; i < bigCNum; i++) {
            this.bigContainers.add(bigContainer);
        }

        // Add the calculated number of small containers to the list
        for (int i = 0; i < smallCNum; i++) {
            this.smallContainers.add(smallContainer);
        }

        // Replace small containers with big ones until there's only one small container left
        while (this.smallContainers.size() > 1) {
            this.bigContainers.add(bigContainer);
            this.smallContainers.remove(smallContainer);
        }
    }

    // Method to calculate the shipping price based on the number of big and small containers
    public int shippingPrice() {
        // Initialize a big container and two types of small containers
        BigContainer bigContainer = new BigContainer();
        SmallContainer smallContainer = new SmallContainer(1000);
        SmallContainer smallContainerExtra = new SmallContainer(1200);

        // Calculate the cost for big containers and small containers
        int bigCCost = this.bigContainers.size() * bigContainer.price;
        int smallCCost = getTotalWeight() < 500 ? this.smallContainers.size() * smallContainer.price : this.smallContainers.size() * smallContainerExtra.price;

        // Return the total cost
        return bigCCost + smallCCost;
    }
}
