package model;

/** OrderReport Class
 *
 * Maps a virtual table format from the database obtained by the query used to generate the orders report and the bills.
 */

public class OrderReport {
    private int id;
    private String client;
    private String product;
    private int amount;
    private double cost;
    private double total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderReport(int id, String client, String product, int amount, double cost, double total) {
        this.id = id;
        this.client = client;
        this.product = product;
        this.amount = amount;
        this.cost = cost;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order ID: " + this.id + " Client: " + this.client + " Product: " + this.product + " Amount: " + this.amount + " Cost: " + this.cost + " Total: " + this.total;
    }
}
