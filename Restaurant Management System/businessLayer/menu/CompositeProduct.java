package businessLayer.menu;

import java.io.Serializable;
import java.util.ArrayList;

public class CompositeProduct implements MenuItem, Serializable {

    private ArrayList<MenuItem> composition;
    private String name;
    private double price;

    public CompositeProduct(String name, double price, ArrayList<MenuItem> composition) {
        this.name = name;
        this.price = price;
        this.composition = composition;
    }

    public CompositeProduct(String name, double price) {
        this(name, price, new ArrayList<MenuItem>());
    }

    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComposition(ArrayList<MenuItem> composition) {
        if (composition != null)
            this.composition = composition;
    }

    public ArrayList<MenuItem> getComposition() {
        return this.composition;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public void addItem(MenuItem menuItem) {
        this.composition.add(menuItem);
    }

    public void deleteItem(MenuItem menuItem) {
        this.composition.remove(menuItem);
    }

    public boolean contains(String item) {
        boolean flag = false;

        for (MenuItem menuItem : this.composition) {
            if (menuItem.getName().equals(item)) {
                flag = true;
                break;
            } else if (menuItem instanceof CompositeProduct) {
                CompositeProduct compositeProduct = (CompositeProduct) menuItem;

                if (compositeProduct.contains(item)) {
                    flag = true;
                    break;
                }

            }
        }

        return flag;
    }

    @Override
    public double computePrice() {
        double price = this.price;

        for (MenuItem menuItem : this.composition) {
            price += menuItem.computePrice();
        }

        return price;
    }

    @Override
    public String toString() {
        String print = "Composite product: " + this.name + " price: " + this.computePrice() + " contains:\n";

        for (MenuItem menuItem : this.composition) {
            print += menuItem;
            print += "\n";
        }

        return print;
    }
}
