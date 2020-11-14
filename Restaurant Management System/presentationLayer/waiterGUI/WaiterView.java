package presentationLayer.waiterGUI;

import businessLayer.Order;
import businessLayer.restaurant.IRestaurantProcessing;
import presentationLayer.administatorGUI.AdministratorPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class WaiterView extends JFrame {

    private IRestaurantProcessing restaurant;

    private JPanel mainPanel = new JPanel();

    private JButton homeBtn = new JButton("Home");
    private JButton createOrderBtn = new JButton("Create Order");
    private JButton generateBillBtn = new JButton("Generate Bill");
    private JButton viewMenuBtn = new JButton("View Menu");
    private JButton selectTableOrderBtn = new JButton("Select Table");
    private JButton generateBtn = new JButton("Generate");
    private JButton createBtn = new JButton("Create");

    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    private ArrayList<JCheckBox> checkBoxesTableOrder = new ArrayList<JCheckBox>();
    private ArrayList<JCheckBox> checkBoxesTableBill = new ArrayList<>();
    private boolean[] tableAvailable = new boolean[10];

    private int tableNo;

    public WaiterView(IRestaurantProcessing restaurant) {
        this.restaurant = restaurant;

        Arrays.fill(this.tableAvailable, Boolean.TRUE);
        this.initializeTable();

        this.setPanel(WaiterPanles.MENU);
    }

    private void setHomePanel() {
        this.mainPanel.removeAll();

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(createOrderBtn);
        row1.add(generateBillBtn);

        row2.setLayout(new FlowLayout());
        row2.add(viewMenuBtn);

        this.mainPanel.setLayout(new GridLayout(2, 1));
        this.mainPanel.add(row1);
        this.mainPanel.add(row2);
    }

    private void setViewMenuPanel() {
        this.mainPanel.removeAll();

        String[] columnNames = {"Product", "Price"};
        String[][] data = new String[this.restaurant.getMenu().size()][2];

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        int i = 0;
        for (String product : this.restaurant.getMenu().keySet()) {
            data[i][0] = product;
            i++;
        }

        i = 0;
        for (double price : this.restaurant.getMenu().values()) {
            data[i][1] = df.format(price);
            i++;
        }

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension (200, 100));

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(homeBtn, BorderLayout.SOUTH);
    }

    private void setCreateOrderPanel() {
        this.mainPanel.removeAll();

        JPanel south = new JPanel();
        JPanel north = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();
        JPanel checkboxList = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(createBtn);

        row2.setLayout(new FlowLayout());
        row2.add(homeBtn);

        south.setLayout(new GridLayout(2,1));
        south.add(row1);
        south.add(row2);

        row3.setLayout(new FlowLayout());
        row3.add(new JLabel("Table No. " + this.tableNo));

        row4.setLayout(new FlowLayout());
        row4.add(new JLabel("Select Products"));

        north.setLayout(new GridLayout(2,1));
        north.add(row3);
        north.add(row4);

        this.updateCheckboxList();

        checkboxList.setLayout(new BoxLayout(checkboxList, BoxLayout.Y_AXIS));
        for (JCheckBox jCheckBox : this.checkBoxes) {
            checkboxList.add(jCheckBox);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension (200, 100));

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(north, BorderLayout.NORTH);
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(south, BorderLayout.SOUTH);
    }

    private void setTableOrderPanel() {
        this.mainPanel.removeAll();

        JPanel south = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel checkboxList = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();

        row1.setLayout(new FlowLayout());
        row1.add(selectTableOrderBtn);

        row2.setLayout(new FlowLayout());
        row2.add(homeBtn);

        south.setLayout(new GridLayout(2,1));
        south.add(row1);
        south.add(row2);

        this.updateCheckboxListOrder();

        checkboxList.setLayout(new BoxLayout(checkboxList, BoxLayout.Y_AXIS));
        for (JCheckBox jCheckBox : this.checkBoxesTableOrder) {
            checkboxList.add(jCheckBox);
            buttonGroup.add(jCheckBox);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension (200, 100));

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(new JLabel("Select Table"), BorderLayout.NORTH);
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(south, BorderLayout.SOUTH);
    }

    private void setTableBillPanel() {
        this.mainPanel.removeAll();

        JPanel south = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel checkboxList = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();

        row1.setLayout(new FlowLayout());
        row1.add(generateBtn);

        row2.setLayout(new FlowLayout());
        row2.add(homeBtn);

        south.setLayout(new GridLayout(2,1));
        south.add(row1);
        south.add(row2);

        this.updateCheckboxListBill();

        checkboxList.setLayout(new BoxLayout(checkboxList, BoxLayout.Y_AXIS));
        for (JCheckBox jCheckBox : this.checkBoxesTableBill) {
            checkboxList.add(jCheckBox);
            buttonGroup.add(jCheckBox);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension (200, 100));

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(new JLabel("Select Table"), BorderLayout.NORTH);
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(south, BorderLayout.SOUTH);
    }

    public void setPanel(WaiterPanles panel) {
        int width = 0;
        int height = 0;
        String title = null;

        if (panel == WaiterPanles.MENU) {
            this.setHomePanel();
            width = 400;
            height = 200;
            title = "Waiter Interface - Home";
        }else if (panel == WaiterPanles.CREATE) {
            this.setCreateOrderPanel();
            width = 400;
            height = 400;
            title = "Waiter Interface - Create Order";
        } else if (panel == WaiterPanles.VIEW) {
            this.setViewMenuPanel();
            width = 400;
            height = 400;
            title = "Waiter Interface - View Menu";
        } else if (panel == WaiterPanles.TABLEORDER) {
            this.setTableOrderPanel();
            width = 400;
            height = 400;
            title = "Waiter Interface - Select Table";
        } else if (panel == WaiterPanles.TABLEBILL) {
            this.setTableBillPanel();
            width = 400;
            height = 400;
            title = "Waiter Interface - Generate Bill";
        }

        this.setContentPane(this.mainPanel);
        this.pack();
        this.setSize(width, height);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    public void initializeTable() {
        for (Order order : this.restaurant.getOrders().keySet()) {
            this.tableAvailable[order.getTable() - 1] = false;
        }
    }

    public void setTable(int index, boolean taken) {
        if (index < this.tableAvailable.length) {
            this.tableAvailable[index] = taken;
        }
    }

    public ArrayList<JCheckBox> getCheckBoxes() {
        return this.checkBoxes;
    }

    public ArrayList<JCheckBox> getCheckBoxesTableOrder() {
        return this.checkBoxesTableOrder;
    }

    public ArrayList<JCheckBox> getCheckBoxesTableBill() {
        return this.checkBoxesTableBill;
    }

    public void setTableNo(int table) {
        this.tableNo = table;
    }

    public int getTableNo() {
        return this.tableNo;
    }

    private void updateCheckboxList() {
        this.checkBoxes.clear();

        for (String item : this.restaurant.getMenu().keySet()) {
            this.checkBoxes.add(new JCheckBox(item));
        }
    }

    private void updateCheckboxListOrder() {
        this.checkBoxesTableOrder.clear();

        for (int i = 0; i < this.tableAvailable.length; i++) {
            if (this.tableAvailable[i]) {
                this.checkBoxesTableOrder.add(new JCheckBox(String.valueOf(i + 1)));
            }
        }
    }

    public void updateCheckboxListBill() {
        this.checkBoxesTableBill.clear();

        for (int i = 0; i < this.tableAvailable.length; i++) {
            if (!this.tableAvailable[i]) {
                this.checkBoxesTableBill.add(new JCheckBox(String.valueOf(i + 1)));
            }
        }
    }

    public void addViewMenuListener(ActionListener actionListener) {
        viewMenuBtn.addActionListener(actionListener);
    }

    public void addHomeListener(ActionListener actionListener) {
        homeBtn.addActionListener(actionListener);
    }

    public void addCreateOrderListener(ActionListener actionListener) {
        createOrderBtn.addActionListener(actionListener);
    }

    public void addCreateListener(ActionListener actionListener) {
        createBtn.addActionListener(actionListener);
    }

    public void addGenerateBillListener(ActionListener actionListener) {
        generateBillBtn.addActionListener(actionListener);
    }

    public void addSelectTableOrderListener(ActionListener actionListener) {
        selectTableOrderBtn.addActionListener(actionListener);
    }

    public void addGenerateListener(ActionListener actionListener) {
        generateBtn.addActionListener(actionListener);
    }
}
