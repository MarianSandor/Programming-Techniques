package dataAccess.operations;

import dataAccess.connection.ConnectionFactory;
import model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/** ProductsDAO
 *
 * Provides specific queries for the Products table.
 */

public class ProductsDAO extends AbstractDAO<Products> {

    public ProductsDAO() {
        super();
    }

    /**
     * Executes a query for finding the id by name.
     * @param product Products class object for which the id is requested.
     * @return The id of the product.
     */
    public int getId(Products product) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM Products WHERE name = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, product.getName());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

            return -1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Products" + "DAO:getId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return -1;
    }

    /**
     * Executes a query for retrieving a product by its name.
     * @param name The name of a product.
     * @return The Products object having the given name.
     */
    public Products getProduct(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Products WHERE name = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, name);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Products(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("amount"), resultSet.getDouble("price"));
            }

            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Products" + "DAO:getId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Executes a query for retrieving all the data from the Products table.
     * @return A list with all the products in the database.
     */
    public List<Products> report() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id, name, amount, price FROM Products";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            return super.createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Products" + "DAO:report " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }
}
