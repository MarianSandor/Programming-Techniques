package businessLayer.menu;

import java.io.Serializable;

public class BaseProduct implements MenuItem, Serializable {

    private String name;
    private double price;

    public BaseProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double computePrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Base product: " + this.name + " Price: " + this.price;
    }
}
