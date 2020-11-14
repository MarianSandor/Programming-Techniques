package businessLayer.restaurant;

import businessLayer.Order;
import businessLayer.menu.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface IRestaurantProcessing {

    HashMap<String, Double> getMenu();
    HashSet<MenuItem> getItems();
    HashMap<Order, ArrayList<MenuItem>> getOrders();


    void createNewMenuItem(String name, double price);
    void createNewMenuItem(String name, double price, ArrayList<String> items);
    void deleteMenuItem(String name);
    void editMenuItem(String name, double newPrice);
    void editMenuItem(String name, String newName);
    void editMenuItem(String name, ArrayList<String> newItems);

    void createNewOrder(int table, ArrayList<String> items);
    double computePriceOrder(int table);
    void generateBill(int table);
}
