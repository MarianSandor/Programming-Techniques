package dataAccess.operations;

import dataAccess.connection.ConnectionFactory;
import model.Clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/** ClientsDAO Class
 *
 * Provides specific queries for the Clients table.
 */

public class ClientsDAO extends AbstractDAO<Clients> {

    public ClientsDAO() {
        super();
    }

    /**
     * Executes a query for finding the id by name.
     * @param client Client class object for which the id is requested.
     * @return The id of the given client.
     */
    public int getId(Clients client) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM Clients WHERE name = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, client.getName());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

            return -1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Clients" + "DAO:getId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return -1;
    }

    /**
     * Executes a query for retrieving all the data from the Clients table.
     * @return A list with all the clients from the database.
     */
    public List<Clients> report() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id, name, address FROM Clients";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            return super.createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Clients" + "DAO:report " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }
}
