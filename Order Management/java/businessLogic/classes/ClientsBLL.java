package businessLogic.classes;

import businessLogic.validators.Validator;
import dataAccess.operations.ClientsDAO;
import dataAccess.operations.OrdersDAO;
import model.Clients;
import presentation.Report;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** ClientsBLL
 *
 * Defines the Clients table business logic.
 */

public class ClientsBLL {
    protected static final Logger LOGGER = Logger.getLogger(ClientsBLL.class.getName());

    private ClientsDAO clientsDAO;
    private OrdersDAO ordersDAO;

    public ClientsBLL() {
        this.clientsDAO = new ClientsDAO();
        this.ordersDAO = new OrdersDAO();
    }

    /**
     * Validates a field using Validator class.
     * @param client A Clients class object.
     * @return True if its fields contain valid values.
     */
    private boolean checkFields(Clients client) {
        if (!Validator.validName(client.getName())) {
            LOGGER.log(Level.WARNING, "ClientBLL Invalid Clinet Name: " + client.getName());
            return false;
        }

        if (!Validator.validAddress(client.getAddress())) {
            LOGGER.log(Level.WARNING, "ClientBLL Invalid Clinet Address: " + client.getAddress());
            return false;
        }

        return true;
    }

    /**
     * Inserts a client in the Clients table.
     * @param client A Clients class object.
     * @return True if the insertion is done successfully.
     */
    public boolean insertClient(Clients client) {

        if (!this.checkFields(client)) {
            return false;
        }

        if (clientsDAO.getId(client) != -1) {
            LOGGER.log(Level.WARNING, "ClientBLL  Could not insert " +  client.getName() + " " + client.getAddress() + " is already in the database!");
            return false;
        }

        clientsDAO.insert(client);

        return true;
    }

    /**
     * Updates a client from the Clients table.
     * @param client A Clients class object.
     * @param newClient A Clients class object.
     * @return True if the update is done successfully.
     */
    public boolean updateClient(Clients client, Clients newClient) {
        int id = clientsDAO.getId(client);

        if (!this.checkFields(newClient)) {
            return false;
        }

        if (id == -1) {
            LOGGER.log(Level.WARNING, "ClientBLL  Could not update " +  client.getName() + " " + client.getAddress() + " is not in the database!");
            return false;
        }

        newClient.setId(id);
        clientsDAO.update(newClient);

        return true;
    }

    /**
     * Deletes a client from the Clients table.
     * @param client A Clients class object.
     * @return True if the deletion is done successfully.
     */
    public boolean deleteClient(Clients client) {
        int id = clientsDAO.getId(client);

        if (id == -1) {
            LOGGER.log(Level.WARNING, "ClientBLL  Could not delete " +  client.getName() + " " + client.getAddress() + " is not in the database!");
            return false;
        }
        client.setId(id);
        if (ordersDAO.hasOrder(client)){
            LOGGER.log(Level.WARNING, "ClientBLL  Could not delete " +  client.getName() + " " + client.getAddress() + " there are orders!");
            return false;
        }

        clientsDAO.deleteById(id);

        return true;
    }

    /**
     * Obtains the specific client's id from the Clients table.
     * @param client A Clients class object.
     * @return The id of the given client.
     */
    public int getClientID(Clients client) {
        int id = clientsDAO.getId(client);

        if (id == -1) {
            LOGGER.log(Level.WARNING, "ClientBLL Client " +  client.getName() + " " + client.getAddress() + " is not in the database!");
            return -1;
        }

        return id;
    }

    /**
     * Generates the Clients table report using the Report class.
     */
    public void generateReports() {
        List<Clients> clientsList = this.clientsDAO.report();

        if (clientsList == null) {
            LOGGER.log(Level.WARNING, "ClientsBLL  could not generate reports");
            return;
        }

        Report.generateReport(clientsList);
    }
}
