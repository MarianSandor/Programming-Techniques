package presentation;

import businessLogic.classes.ClientsBLL;
import businessLogic.classes.OrderItemsBLL;
import businessLogic.classes.OrdersBLL;
import businessLogic.classes.ProductsBLL;
import model.Clients;
import model.Products;

/** CommandInterpreter Class
 *
 * Interprets the commands receives as String and acts accordingly.
 */

public class CommandInterpreter {
    private int lastOrderId;
    private int lastClientId;

    private ClientsBLL clientsBLL;
    private ProductsBLL productsBLL;
    private OrderItemsBLL orderItemsBLL;
    private OrdersBLL ordersBLL;

    public CommandInterpreter() {
        this.lastClientId = -1;
        this.lastOrderId = -1;

        this.clientsBLL = new ClientsBLL();
        this.productsBLL = new ProductsBLL();
        this.orderItemsBLL = new OrderItemsBLL();
        this.ordersBLL = new OrdersBLL();
    }

    /**
     * Detects when the commands regarding a order are done and generates the bill for that order.
     */
    private void updateOrder() {
        if (lastOrderId == -1)
            return;

        this.ordersBLL.setOrderTotal(lastOrderId);
        lastOrderId = -1;
        this.lastClientId = -1;
    }

    /**
     * Executes the commands on the Clients table.
     * @param command A string containing the command.
     * @param data A string vector containing the parameters of the command.
     */
    private void clientCommand(String command, String[] data) {
        this.updateOrder();

        switch (command) {
            case "Insert":
                this.clientsBLL.insertClient(new Clients(data[0], data[1]));
                break;
            case "Update":
                this.clientsBLL.updateClient(new Clients(data[0], data[1]), new Clients(data[2], data[3]));
                break;
            case "Delete":
                this.clientsBLL.deleteClient(new Clients(data[0], data[1]));
                break;
            case "Report":
                this.clientsBLL.generateReports();
                break;
        }
    }

    /**
     * Executes the commands on the Products table.
     * @param command A string containing the command.
     * @param data A string vector containing the parameters of the command.
     */
    private void productCommand(String command, String[] data) {
        this.updateOrder();

        switch (command) {
            case "Insert":
                this.productsBLL.insertProduct(new Products(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2])));
                break;
            case "Update":
                this.productsBLL.updateProduct(new Products(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2])), new Products(data[3], Integer.getInteger(data[4]), Double.parseDouble(data[5])));
                break;
            case "Delete":
                this.productsBLL.deleteProduct(new Products(data[0]));
                break;
            case "Report":
                this.productsBLL.generateReports();
                break;
        }
    }

    /**
     * Executes the commands on the Orders and OrderItems tables.
     * @param command A string containing the command.
     * @param data A string vector containing the parameters of the command.
     */
    private void orderCommand(String command, String[] data) {

        if (command.equals("Report")) {
            this.updateOrder();
            this.ordersBLL.generateReports();
        }
        else {
            Clients client = new Clients(data[0]);
            int clientId = this.clientsBLL.getClientID(client);

            if (clientId == -1)
                return;

            if (this.lastClientId != clientId) {
                this.updateOrder();
                this.lastOrderId = this.ordersBLL.createOrder(client, 0);
            }

            this.orderItemsBLL.insert(this.lastOrderId, data[1], Integer.parseInt(data[2]));
            this.lastClientId = clientId;
        }
    }

    /**
     * Parses the command identifying what it implies.
     * @param command A string containing a command.
     */
    public void interpretCommand(String command) {
        String[] splitCommand = command.split(":");
        String[] type = splitCommand[0].split(" ");
        String[] data = null;

        if (splitCommand.length == 2) {
            data = splitCommand[1].split(",");

            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].trim();
            }
        }

        if (type[0].equals("Order")) {
            this.orderCommand(type[0], data);
        } else if (type[1].equals("order")) {
            this.orderCommand(type[0], data);
        } else if (type[1].equals("client")) {
            this.clientCommand(type[0], data);
        } else if (type[1].equals("product")) {
            this.productCommand(type[0], data);
        }
    }

}
