package model;

/** OrderItems Class
 *
 * Maps the OrderItems table format from the database.
 */

public class OrderItems {

    private int orderId;
    private int productId;
    private int amount;
    private double cost;

    public OrderItems(int orderId, int productId, int amount, double cost) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.cost = cost;
    }

    public OrderItems() {
        this(-1, -1, -1, -1);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "OrderItems OrderId: " + this.orderId + " ProductId: " + this.productId + " Amount: " + this.amount + " Cost: " + this.cost;
    }
}
