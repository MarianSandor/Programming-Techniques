package presentationLayer.administatorGUI;

import businessLayer.restaurant.IRestaurantProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdministratorView extends JFrame {

    private IRestaurantProcessing restaurant;

    private JPanel mainPanel = new JPanel();

    private JButton homeBtn = new JButton("Home");
    private JButton createItemBtn = new JButton("Create Item");
    private JButton deleteItemBtn = new JButton("Delete Item");
    private JButton editItemBtn = new JButton("Edit Item");
    private JButton viewMenuBtn = new JButton("View Menu");
    private JButton baseProductBtn = new JButton("Base Product");
    private JButton compositeProductBtn = new JButton("Composite Product");
    private JButton deleteBtn = new JButton("Delete Products");
    private JButton addBaseProductBtn = new JButton("Add Product");
    private JButton addCompositeProductBtn = new JButton("Add Product");
    private JButton selectBtn = new JButton("Select Product");
    private JButton editBtn = new JButton("Edit Product");

    private JTextField ProductName = new JTextField(15);
    private JTextField ProductPrice = new JTextField(10);

    private JTextField oldProductName = new JTextField(15);
    private JTextField oldProductPrice = new JTextField(10);

    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

    public AdministratorView(IRestaurantProcessing restaurant) {
        this.restaurant = restaurant;

        this.oldProductName.setEditable(false);
        this.oldProductPrice.setEditable(false);

        this.setPanel(AdministratorPanels.MENU);
    }

    private void setMenuPanel() {
        this.mainPanel.removeAll();

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(createItemBtn);
        row1.add(deleteItemBtn);
        row1.add(editItemBtn);

        row2.setLayout(new FlowLayout());
        row2.add(viewMenuBtn);

        this.mainPanel.setLayout(new GridLayout(2, 1));
        this.mainPanel.add(row1);
        this.mainPanel.add(row2);
    }

    private void setCreatePanel() {
        this.mainPanel.removeAll();

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(baseProductBtn);
        row1.add(compositeProductBtn);

        row2.setLayout(new FlowLayout());
        row2.add(homeBtn);

        this.mainPanel.setLayout(new GridLayout(2, 1));
        this.mainPanel.add(row1);
        this.mainPanel.add(row2);
    }

    private void setBaseProductPanel() {
        this.mainPanel.removeAll();

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(new JLabel("Product Name"));
        row1.add(ProductName);

        row2.setLayout(new FlowLayout());
        row2.add(new JLabel("Product Price"));
        row2.add(ProductPrice);

        row3.setLayout(new FlowLayout());
        row3.add(addBaseProductBtn);

        row4.setLayout(new FlowLayout());
        row4.add(homeBtn);

        this.mainPanel.setLayout(new GridLayout(4, 1));
        this.mainPanel.add(row1);
        this.mainPanel.add(row2);
        this.mainPanel.add(row3);
        this.mainPanel.add(row4);
    }

    private void setCompositeProductPanel() {
        this.mainPanel.removeAll();

        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel checkboxList = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(new JLabel("Product Name"));
        row1.add(ProductName);

        row2.setLayout(new FlowLayout());
        row2.add(new JLabel("Product Price"));
        row2.add(ProductPrice);

        north.setLayout(new GridLayout(2,1));
        north.add(row1);
        north.add(row2);

        row3.setLayout(new FlowLayout());
        row3.add(addCompositeProductBtn);

        row4.setLayout(new FlowLayout());
        row4.add(homeBtn);

        south.setLayout(new GridLayout(2,1));
        south.add(row3);
        south.add(row4);

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

    private void setDeleteItemPanel() {
        this.mainPanel.removeAll();

        JPanel south = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel checkboxList = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(deleteBtn);

        row2.setLayout(new FlowLayout());
        row2.add(homeBtn);

        south.setLayout(new GridLayout(2,1));
        south.add(row1);
        south.add(row2);

        this.updateCheckboxList();

        checkboxList.setLayout(new BoxLayout(checkboxList, BoxLayout.Y_AXIS));
        for (JCheckBox jCheckBox : this.checkBoxes) {
            checkboxList.add(jCheckBox);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension (200, 100));

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(new JLabel("Select Products"), BorderLayout.NORTH);
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(south, BorderLayout.SOUTH);
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

    private void setSelectItemPanle() {
        this.mainPanel.removeAll();

        JPanel south = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel checkboxList = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();

        row1.setLayout(new FlowLayout());
        row1.add(selectBtn);

        row2.setLayout(new FlowLayout());
        row2.add(homeBtn);

        south.setLayout(new GridLayout(2,1));
        south.add(row1);
        south.add(row2);

        this.updateCheckboxList();

        checkboxList.setLayout(new BoxLayout(checkboxList, BoxLayout.Y_AXIS));
        for (JCheckBox jCheckBox : this.checkBoxes) {
            checkboxList.add(jCheckBox);
            buttonGroup.add(jCheckBox);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension (200, 100));

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(new JLabel("Choose Product"), BorderLayout.NORTH);
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(south, BorderLayout.SOUTH);
    }

    private void setEditItemPanle() {
        this.mainPanel.removeAll();

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();
        JPanel row5 = new JPanel();
        JPanel row6 = new JPanel();

        row1.setLayout(new FlowLayout());
        row1.add(new JLabel("Old Product Name"));
        row1.add(oldProductName);

        row2.setLayout(new FlowLayout());
        row2.add(new JLabel("New Product Name"));
        row2.add(ProductName);

        row3.setLayout(new FlowLayout());
        row3.add(new JLabel("Old Product Price"));
        row3.add(oldProductPrice);

        row4.setLayout(new FlowLayout());
        row4.add(new JLabel("New Product Price"));
        row4.add(ProductPrice);

        row5.setLayout(new FlowLayout());
        row5.add(editBtn);

        row6.setLayout(new FlowLayout());
        row6.add(homeBtn);

        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.add(row1);
        this.mainPanel.add(row2);
        this.mainPanel.add(row3);
        this.mainPanel.add(row4);
        this.mainPanel.add(row5);
        this.mainPanel.add(row6);
    }

    public void setPanel(AdministratorPanels panel) {
        int width = 0;
        int height = 0;
        String title = null;

        if (panel == AdministratorPanels.MENU) {
            this.setMenuPanel();
            width = 400;
            height = 200;
            title = "Administrator Interface - Home";
        }else if (panel == AdministratorPanels.CREATE) {
            this.setCreatePanel();
            width = 400;
            height = 150;
            title = "Adminitrator Interface - Create";
        } else if (panel == AdministratorPanels.BASEPRODUCT) {
            this.setBaseProductPanel();
            width = 400;
            height = 200;
            title = "Adminitrator Interface - Create Base Product";
        } else if (panel == AdministratorPanels.COMPOSITEPRODUCT) {
            this.setCompositeProductPanel();
            width = 400;
            height = 400;
            title = "Adminitrator Interface - Create Composite Product";
        } else if (panel == AdministratorPanels.DELETE) {
            this.setDeleteItemPanel();
            width = 400;
            height = 400;
            title = "Adminitrator Interface - Delete Product";
        } else if (panel == AdministratorPanels.VIEW) {
            this.setViewMenuPanel();
            width = 400;
            height = 400;
            title = "Adminitrator Interface - View Menu";
        } else if (panel == AdministratorPanels.SELECT) {
            this.setSelectItemPanle();
            width = 400;
            height = 400;
            title = "Adminitrator Interface - Select Product";
        } else if (panel == AdministratorPanels.EDIT) {
            this.setEditItemPanle();
            width = 400;
            height = 400;
            title = "Adminitrator Interface - Edit Product";
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

    public ArrayList<JCheckBox> getCheckBoxes() {
        return this.checkBoxes;
    }

    private void updateCheckboxList() {
        this.checkBoxes.clear();

        for (String item : this.restaurant.getMenu().keySet()) {
            this.checkBoxes.add(new JCheckBox(item));
        }
    }

    public void setOldProductPrice(double price) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        this.oldProductPrice.setText(df.format(price));
    }

    public void setOldProductName(String name) {
        this.oldProductName.setText(name);
    }

    public String getProductName() {
        return this.ProductName.getText();
    }

    public String getProductPrice() {
        return this.ProductPrice.getText();
    }

    public String getOldProductName() {
        return this.oldProductName.getText();
    }

    public String getOldProductPrice() {
        return this.oldProductPrice.getText();
    }

    public void setProductName(String text) {
        this.ProductName.setText(text);
    }

    public void setProductPrice(String text) {
        this.ProductPrice.setText(text);
    }

    public void addCreateItemListener(ActionListener actionListener) {
        createItemBtn.addActionListener(actionListener);
    }

    public void addDeleteItemListener(ActionListener actionListener) {
        deleteItemBtn.addActionListener(actionListener);
    }

    public void addEditItemListener(ActionListener actionListener) {
        editItemBtn.addActionListener(actionListener);
    }

    public void addViewMenuListener(ActionListener actionListener) {
        viewMenuBtn.addActionListener(actionListener);
    }

    public void addHomeListener(ActionListener actionListener) {
        homeBtn.addActionListener(actionListener);
    }

    public void addBaseProductListener(ActionListener actionListener) {
        baseProductBtn.addActionListener(actionListener);
    }

    public void addCompositeProductListener(ActionListener actionListener) {
        compositeProductBtn.addActionListener(actionListener);
    }

    public void addAddBaseProductListener(ActionListener actionListener) {
        addBaseProductBtn.addActionListener(actionListener);
    }

    public void addAddCompositeProductListener(ActionListener actionListener) {
        addCompositeProductBtn.addActionListener(actionListener);
    }

    public void addDeleteListener(ActionListener actionListener) {
        deleteBtn.addActionListener(actionListener);
    }

    public void addSelectListener(ActionListener actionListener) {
        selectBtn.addActionListener(actionListener);
    }

    public void addEditListener(ActionListener actionListener) {
        editBtn.addActionListener(actionListener);
    }
}
