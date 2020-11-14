package presentationLayer.chefGUI;

import businessLayer.Order;
import businessLayer.menu.MenuItem;
import businessLayer.restaurant.IRestaurantProcessing;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;


public class ChefView extends JFrame {

    private IRestaurantProcessing restaurant;

    private JPanel mainPanel = new JPanel();
    private ArrayList<Order> orders = new ArrayList<>();

    public ChefView(IRestaurantProcessing restaurant) {
        this.restaurant = restaurant;

        this.initializeOrders();

        this.createPanel();

        this.setContentPane(this.mainPanel);
        this.pack();
        this.setSize(400, 400);
        this.setTitle("Chef Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void createPanel() {
        this.mainPanel.removeAll();

        String[] columnNames = {"Order ID", "Table", "Products"};
        String[][] data = new String[this.orders.size()][3];

        int i = 0;
        for (Order order : this.orders) {
            data[i][0] = String.valueOf(order.getOrderID());
            data[i][1] = String.valueOf(order.getTable());
            StringBuilder productList = new StringBuilder();

            for (MenuItem menuItem : this.restaurant.getOrders().get(order)) {
                productList.append(menuItem.getName());
                productList.append(", ");
            }
            productList.delete(productList.length() - 2, productList.length());

            data[i][2] = productList.toString();

            i++;
        }

        JTable table = new JTable(data, columnNames);
        TableColumnModel columnModel = table.getColumnModel();
        table.setFillsViewportHeight(true);
        columnModel.getColumn(0).setPreferredWidth(55);
        columnModel.getColumn(1).setPreferredWidth(55);
        columnModel.getColumn(2).setPreferredWidth(290);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateOrders(Order order) {
        if (this.orders.contains(order)) {
            this.orders.remove(order);
        } else {
            this.orders.add(order);
        }
    }

    public void updatePanel() {
        this.createPanel();
        this.revalidate();
        this.repaint();
    }

    public void initializeOrders() {
        for (Order order : this.restaurant.getOrders().keySet()) {
            this.orders.add(order);
        }
    }
}
