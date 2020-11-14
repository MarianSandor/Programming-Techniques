package dataAccess.operations;

import dataAccess.connection.ConnectionFactory;
import model.OrderItems;
import model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/** OrderItemsDAO
 *
 * Provides specific queries for the OrderItems table.
 */

public class OrderItemsDAO extends AbstractDAO<OrderItems> {

    public OrderItemsDAO() {
        super();
    }

    /**
     * Checks is a given product is present in at least one order.
     * @param product A products class object.
     * @return True if the product is part of any order.
     */
    public boolean isOrderItem(Products product) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM OrderItems WHERE productId = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, product.getId());

            resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderItems" + "DAO:isOrderItem " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return false;
    }

    /**
     * Executes a query for retrieving all the items from a specific order.
     * @param orderId The order id.
     * @return A list of OrderItems class object that are part of the given order.
     */
    public List<OrderItems> getItems(int orderId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM OrderItems WHERE orderId = ?";
        List<OrderItems> result = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, orderId);

            resultSet = statement.executeQuery();

            result = super.createObjects(resultSet);

            if (result.isEmpty())
                return null;

            return result;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderItems" + "DAO:isOrderItem " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }
}
