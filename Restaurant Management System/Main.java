import businessLayer.restaurant.Restaurant;
import dataLayer.RestaurantSerializator;
import presentationLayer.administatorGUI.AdministratorController;
import presentationLayer.administatorGUI.AdministratorView;
import presentationLayer.chefGUI.ChefController;
import presentationLayer.chefGUI.ChefView;
import presentationLayer.waiterGUI.WaiterController;
import presentationLayer.waiterGUI.WaiterView;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Restaurant restaurant;

        if (args.length == 1) {
            restaurant = RestaurantSerializator.Deserialize(args[0]);
        } else {
            restaurant = new Restaurant();
        }

        AdministratorView administratorView = new AdministratorView(restaurant);
        AdministratorController administratorController = new AdministratorController(administratorView, restaurant);

        WaiterView waiterView = new WaiterView(restaurant);
        WaiterController waiterController = new WaiterController(waiterView, restaurant);

        ChefView chefView = new ChefView(restaurant);
        ChefController chefController = new ChefController(chefView, restaurant);

        restaurant.addObserver(chefController);
    }
}
