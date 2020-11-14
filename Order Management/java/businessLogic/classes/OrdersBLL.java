package businessLogic.classes;

import dataAccess.operations.ClientsDAO;
import dataAccess.operations.OrderItemsDAO;
import dataAccess.operations.OrdersDAO;
import model.Clients;
import model.OrderItems;
import model.OrderReport;
import model.Orders;
import presentation.Report;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** OrdersBLL Class
 *
 * Defines the Orders table business logic.
 */

public class OrdersBLL {

    protected static final Logger LOGGER = Logger.getLogger(OrdersBLL.class.getName());

    private OrdersDAO ordersDAO;
    private OrderItemsDAO orderItemsDAO;
    private ClientsDAO clientsDAO;

    public OrdersBLL() {
        this.ordersDAO = new OrdersDAO();
        this.clientsDAO = new ClientsDAO();
        this.orderItemsDAO = new OrderItemsDAO();
    }

    /**
     * Creates a new order in the Orders table and returns its id.
     * @param client A Clients class object
     * @param total The total of the order.
     * @return The id of the created order.
     */
    public int createOrder(Clients client, double total) {
        int clientID = clientsDAO.getId(client);
        int orderID = -1;
        Orders order = null;

        if (clientID == -1) {
            LOGGER.log(Level.WARNING, "OrdersBLL  Could not create order " +  client.getName() + " " + client.getAddress() + " is not in the database!");
            return -1;
        }
        client.setId(clientID);

        order = new Orders(client.getId(), total);
        orderID = ordersDAO.insert(order);

        return orderID;
    }

    /**
     * Calculates the total cost of all the items from the specified order and updates it.
     * @param orderId The id of the order.
     * @return True if the update is done successfully.
     */
    public boolean setOrderTotal(int orderId) {
        List<OrderItems> orderItems = this.orderItemsDAO.getItems(orderId);
        double total = 0;

        if (orderItems == null) {
            this.ordersDAO.deleteById(orderId);
            return false;
        }

        for (OrderItems item : orderItems) {
            total += item.getCost();
        }

        Orders order = this.ordersDAO.findById(orderId);
        order.setTotal(total);

        this.ordersDAO.update(order);
        generateBill(orderId);

        return true;
    }

    /**
     * Generates the Orders report using the Report class.
     */
    public void generateReports() {
        List<OrderReport> orderReports = this.ordersDAO.report();

        if (orderReports == null) {
            LOGGER.log(Level.WARNING, "OrdersBLL  could not generate reports");
            return;
        }

        Report.generateReport(orderReports);
    }

    /**
     * Generates the bill for the specified order using the Report class.
     * @param orderId The order id.
     */
    public void generateBill(int orderId) {
        List<OrderReport> orderReports = this.ordersDAO.bill(orderId);

        if (orderReports == null) {
            LOGGER.log(Level.WARNING, "OrdersBLL  could not generate bill");
            return;
        }

        Report.generateBill(orderReports);
    }

}
