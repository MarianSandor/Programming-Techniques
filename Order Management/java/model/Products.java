package model;

/** Products Class
 *
 * Maps the Products table format from the database.
 */

public class Products {

    private int id;
    private String name;
    private int amount;
    private double price;

    public Products(int id, String name, int amount, double price) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public Products(String name, int amount, double price) { this(-1, name, amount, price); }

    public Products(String name) { this(-1, name, -1, -1); }

    public Products() {
        this(-1, null, -1, -1);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return this.amount;
    }

    public double getPrice() {
        return this.price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product id: " + this.id + " Name: " + this.name + " Amount: " + this.amount + " Price: " + this.price;
    }
}
