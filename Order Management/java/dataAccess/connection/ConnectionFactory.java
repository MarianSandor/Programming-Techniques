package dataAccess.connection;

import java.sql.*;
import java.util.logging.Logger;

/** ConnectionFactory Class
 *
 * Creates the connection with the database as well as provides the methods for closing the connection, statement and ResultSet.
 */

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/shop?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "parola123";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the connection.
     * @return A new connection to the database.
     */
    private Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Returns the created connection.
     * @return The newly created connection.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes the connection.
     * @param connection A connection with the database.
     */
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the statement.
     * @param statement The statement defying a query.
     */
    public static void close(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the resultSet
     * @param resultSet Result set of an executed query.
     */
    public static void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
