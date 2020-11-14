package presentationLayer.waiterGUI;

import businessLayer.restaurant.IRestaurantProcessing;
import presentationLayer.administatorGUI.AdministratorController;
import presentationLayer.administatorGUI.AdministratorPanels;
import presentationLayer.administatorGUI.AdministratorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaiterController {

    private IRestaurantProcessing restaurant;
    private WaiterView view;

    public WaiterController(WaiterView view, IRestaurantProcessing restaurant) {
        this.restaurant = restaurant;
        this.view = view;

        this.view.addHomeListener(new HomeListener());
        this.view.addCreateOrderListener(new CreateOrderListener());
        this.view.addCreateListener(new CreateListener());
        this.view.addGenerateBillListener(new GenerateBillListener());
        this.view.addViewMenuListener(new ViewMenuListener());
        this.view.addGenerateListener(new GenerateListener());
        this.view.addSelectTableOrderListener(new SelectTableOrderListener());
    }

    class HomeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            view.setPanel(WaiterPanles.MENU);
        }
    }

    class ViewMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            view.setPanel(WaiterPanles.VIEW);
        }
    }

    class GenerateBillListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            view.setPanel(WaiterPanles.TABLEBILL);
        }
    }

    class CreateOrderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            view.setPanel(WaiterPanles.TABLEORDER);
        }
    }

    class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> items = new ArrayList<>();

            for (JCheckBox checkBox : view.getCheckBoxes()) {
                if (checkBox.isSelected()) {
                    items.add(checkBox.getText());
                }
            }

            restaurant.createNewOrder(view.getTableNo(), items);

            view.setTable(view.getTableNo() - 1, false);
            view.setPanel(WaiterPanles.MENU);
        }
    }

    class SelectTableOrderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (JCheckBox checkBox : view.getCheckBoxesTableOrder()) {
                if (checkBox.isSelected()) {
                    view.setTableNo(Integer.parseInt(checkBox.getText()));
                    break;
                }
            }

            System.out.println(view.getTableNo());

            view.setPanel(WaiterPanles.CREATE);
        }
    }

    class GenerateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (JCheckBox checkBox : view.getCheckBoxesTableBill()) {
                if (checkBox.isSelected()) {
                    restaurant.generateBill(Integer.parseInt(checkBox.getText()));
                    view.setTable(Integer.parseInt(checkBox.getText()) - 1, true);
                    break;
                }
            }

            view.setPanel(WaiterPanles.TABLEBILL);
            view.setPanel(WaiterPanles.MENU);
        }
    }
}
