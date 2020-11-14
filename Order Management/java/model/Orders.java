package model;

/** Orders Class
 *
 * Maps the Orders table format form the database.
 */

public class Orders {
    private int id;
    private int clientId;
    private double total;

    public Orders(int id, int clientId, double total) {
        this.id = id;
        this.clientId = clientId;
        this.total = total;
    }

    public Orders(int clientId, double total) { this(-1, clientId, total); }

    public Orders() {
        this(-1, -1, -1);
    }

    public int getId() {
        return this.id;
    }

    public int getClientId() {
        return this.clientId;
    }

    public double getTotal() {
        return this.total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order id: " + this.id + " Client id: " + this.clientId + " Total: " + this.total;
    }
}
