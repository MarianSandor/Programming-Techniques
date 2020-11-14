package presentationLayer.chefGUI;

import businessLayer.Order;
import businessLayer.restaurant.IRestaurantProcessing;

import java.util.Observable;
import java.util.Observer;

public class ChefController implements Observer {

    private IRestaurantProcessing restaurant;
    private ChefView view;

    public ChefController(ChefView view, IRestaurantProcessing restaurant) {
        this.restaurant = restaurant;
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Order) {
            Order order = (Order) arg;

            view.updateOrders(order);
            view.updatePanel();
        }
    }
}
