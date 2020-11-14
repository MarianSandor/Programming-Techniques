package businessLogic.classes;

import dataAccess.operations.OrderItemsDAO;
import dataAccess.operations.ProductsDAO;
import model.OrderItems;
import model.Products;
import presentation.Report;

import java.util.logging.Level;
import java.util.logging.Logger;

/** OrderItemsBLL
 *
 * Defines the OrderItems table business logic.
 */

public class OrderItemsBLL {
    protected static final Logger LOGGER = Logger.getLogger(OrderItemsBLL.class.getName());

    private OrderItemsDAO orderItemsDAO;
    private ProductsDAO productsDAO;

    public OrderItemsBLL() {
        this.orderItemsDAO = new OrderItemsDAO();
        this.productsDAO = new ProductsDAO();
    }

    /**
     * Inserts an item in the OrderItems table.
     * @param orderId The order id.
     * @param productName The product name.
     * @param amount The amount.
     * @return True if the insertion is done successfully.
     */
    public boolean insert(int orderId, String productName, int amount) {
        Products product = productsDAO.getProduct(productName);
        OrderItems orderItems = null;

        if (product == null) {
            LOGGER.log(Level.WARNING, "OrderItemsBLL Could not create orderItem: Product = " + productName + " not in the database!");
            return false;
        }

        if (product.getAmount() < amount) {
            LOGGER.log(Level.WARNING, "OrderItemsBLL Could not create orderItem: OrderItem quantity = " + amount + " In stock quantity = " + product.getAmount() + " Not enough " + product.getName() + " !");
            Report.generateUnderStockMessage("OrderItem quantity = " + amount + " In stock quantity = " + product.getAmount() + " Not enough " + product.getName() + " !");
            return false;
        }

        orderItems =  new OrderItems(orderId, product.getId(), amount, product.getPrice() * amount);
        orderItemsDAO.insert(orderItems);
        productsDAO.update(new Products(product.getId(), product.getName(), product.getAmount() - amount, product.getPrice()));

        return true;
    }
}
