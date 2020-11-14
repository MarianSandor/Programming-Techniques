package dataAccess.operations;

import dataAccess.connection.ConnectionFactory;
import model.Clients;
import model.OrderReport;
import model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/** OrdersDAO Class
 *
 * Provides specific queries for the Orders table.
 */

public class OrdersDAO extends AbstractDAO<Orders> {

    public OrdersDAO() {
        super();
    }

    /**
     * Checks if given client has at least one order.
     * @param client Client class object.
     * @return True if the client has at least one order.
     */
    public boolean hasOrder(Clients client) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Orders WHERE clientId = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, client.getId());

            resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Orders" + "DAO:hasOrder " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return false;
    }

    /**
     * Executes a query for retrieving all the data regarding the orders in the OrderReport format.
     * @return A list with all the orders in the database.
     */
    public List<OrderReport> report() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT o.id, c.name, p.name, oi.amount, oi.cost, o.total " +
                         "FROM Orders o JOIN OrderItems oi ON (o.id = oi.orderId) JOIN Clients c ON (o.clientId = c.id) JOIN Products p ON (oi.productId = p.id)";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            List<OrderReport> list = new ArrayList<OrderReport>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String client = resultSet.getString(2);
                String product = resultSet.getString(3);
                int amount = resultSet.getInt(4);
                double cost = resultSet.getDouble(5);
                double total = resultSet.getDouble(6);

                list.add(new OrderReport(id, client, product, amount, cost, total));
            }

            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Orders" + "DAO:report " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Executes a query for retrieving the data for a specific order.
     * The result is interpreted as the bill for the client which owns the order.
     * @param orderId The order id.
     * @return The report for that order.
     */
    public List<OrderReport> bill(int orderId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT o.id, c.name, p.name, oi.amount, oi.cost, o.total " +
                "FROM Orders o JOIN OrderItems oi ON (o.id = oi.orderId) JOIN Clients c ON (o.clientId = c.id) JOIN Products p ON (oi.productId = p.id) WHERE o.id = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);

            resultSet = statement.executeQuery();

            List<OrderReport> list = new ArrayList<OrderReport>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String client = resultSet.getString(2);
                String product = resultSet.getString(3);
                int amount = resultSet.getInt(4);
                double cost = resultSet.getDouble(5);
                double total = resultSet.getDouble(6);

                list.add(new OrderReport(id, client, product, amount, cost, total));
            }

            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Orders" + "DAO:bill " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }
}
