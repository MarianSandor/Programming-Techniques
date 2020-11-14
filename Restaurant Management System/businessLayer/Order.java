package businessLayer;

import businessLayer.menu.MenuItem;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {

    private int orderID;
    private Date date;
    private int table;

    public Order(int orderID, int table) {
        this.orderID = orderID;
        this.date = new Date();
        this.table = table;
    }

    public int getOrderID() {
        return orderID;
    }

    public Date getDate() {
        return date;
    }

    public int getTable() {
        return table;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 31 * hash + this.orderID;
        hash = 31 * hash + this.date.hashCode();
        hash = 31 * hash + this.table;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Order))
            return false;

        Order order = (Order) obj;

        if (this.orderID == order.orderID)
            return true;

        return false;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return "Order ID: " + this.orderID + "\nDate: " + dateFormat.format(this.date) + "\nTable: " + this.table;
    }
}
