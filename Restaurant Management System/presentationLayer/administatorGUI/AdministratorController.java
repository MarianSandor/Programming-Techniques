package presentationLayer.administatorGUI;

import businessLayer.menu.MenuItem;
import businessLayer.restaurant.IRestaurantProcessing;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdministratorController {

    private IRestaurantProcessing restaurant;
    private AdministratorView view;

    public AdministratorController(AdministratorView view, IRestaurantProcessing restaurant) {
        this.restaurant = restaurant;
        this.view = view;

        this.view.addHomeListener(new HomeListener());
        this.view.addCreateItemListener(new CreateItemListener());
        this.view.addDeleteItemListener(new DeleteItemListener());
        this.view.addEditItemListener(new EditItemListener());
        this.view.addViewMenuListener(new ViewMenuListener());
        this.view.addBaseProductListener(new BaseProductListener());
        this.view.addCompositeProductListener(new CompositeProductListener());
        this.view.addAddBaseProductListener(new AddBaseProductListener());
        this.view.addAddCompositeProductListener(new AddCompositeProductListener());
        this.view.addDeleteListener(new DeleteListener());
        this.view.addSelectListener(new SelectListener());
        this.view.addEditListener(new EditListener());
    }

    class HomeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setPanel(AdministratorPanels.MENU);
        }
    }

    class CreateItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setPanel(AdministratorPanels.CREATE);
        }
    }

    class DeleteItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setPanel(AdministratorPanels.DELETE);
        }
    }

    class EditItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setPanel(AdministratorPanels.SELECT);
        }
    }

    class ViewMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setPanel(AdministratorPanels.VIEW);
        }
    }

    class BaseProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setPanel(AdministratorPanels.BASEPRODUCT);
        }
    }

    class CompositeProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setPanel(AdministratorPanels.COMPOSITEPRODUCT);
        }
    }

    class AddBaseProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String productName = view.getProductName();
            double productPrice = Double.parseDouble(view.getProductPrice());

            restaurant.createNewMenuItem(productName, productPrice);

            view.setProductName("");
            view.setProductPrice("");
        }
    }

    class AddCompositeProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String productName = view.getProductName();
            double productPrice = -1;
            ArrayList<String> items = new ArrayList<String>();

            if (!view.getProductPrice().isEmpty())
                productPrice = Double.parseDouble(view.getProductPrice());

            for (JCheckBox checkBox : view.getCheckBoxes()) {
                if (checkBox.isSelected()) {
                    items.add(checkBox.getText());
                }
            }

            restaurant.createNewMenuItem(productName, productPrice, items);

            view.setProductName("");
            view.setProductPrice("");

            view.setPanel(AdministratorPanels.COMPOSITEPRODUCT);
        }
    }


    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (JCheckBox checkBox : view.getCheckBoxes()) {
                if (checkBox.isSelected()) {
                    restaurant.deleteMenuItem(checkBox.getText());
                }
            }

            view.setPanel(AdministratorPanels.DELETE);
        }
    }

    class SelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String item = "";

            for (JCheckBox checkBox : view.getCheckBoxes()) {
                if (checkBox.isSelected()) {
                    item = checkBox.getText();
                    break;
                }
            }

            for (MenuItem menuItem : restaurant.getItems()) {
                if (menuItem.getName().equals(item)) {
                    view.setOldProductName(menuItem.getName());
                    view.setOldProductPrice(menuItem.getPrice());
                    break;
                }
            }

            view.setPanel(AdministratorPanels.EDIT);
        }
    }

    class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String productName = view.getProductName();
            double productPrice = -1;

            if (!view.getProductPrice().isEmpty())
                productPrice = Double.parseDouble(view.getProductPrice());

            if (!productName.isEmpty()) {
                restaurant.editMenuItem(view.getOldProductName(), productName);
                view.setOldProductName(productName);
            }


            if (productPrice != -1) {
                restaurant.editMenuItem(view.getOldProductName(), productPrice);
                view.setOldProductPrice(productPrice);
            }

            view.setProductName("");
            view.setProductPrice("");
        }
    }
}
