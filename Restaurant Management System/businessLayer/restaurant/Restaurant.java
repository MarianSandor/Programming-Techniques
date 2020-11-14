package businessLayer.restaurant;

import businessLayer.Order;
import businessLayer.menu.BaseProduct;
import businessLayer.menu.CompositeProduct;
import businessLayer.menu.MenuItem;
import dataLayer.FileWriter;
import dataLayer.RestaurantSerializator;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Restaurant extends Observable implements IRestaurantProcessing, Serializable {

    private HashSet<MenuItem> menu;
    private HashMap<Order, ArrayList<MenuItem>> orders;
    private int id;

    public Restaurant() {
        this.menu = new HashSet<MenuItem>();
        this.orders = new HashMap<Order, ArrayList<MenuItem>>();
        this.id = 1;
    }

    @Override
    public HashSet<MenuItem> getItems() {
        return this.menu;
    }

    @Override
    public HashMap<String, Double> getMenu() {
        HashMap<String, Double> menuList = new HashMap<String, Double>();

        for (MenuItem menuItem : this.menu) {
            menuList.put(menuItem.getName(), menuItem.computePrice());
        }

        return menuList;
    }

    @Override
    public HashMap<Order, ArrayList<MenuItem>> getOrders() {
        return this.orders;
    }

    protected boolean isWellFormed() {
        ArrayList<Integer> tables = new ArrayList<Integer>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (Order order : this.orders.keySet()) {
            if (!tables.contains(order.getTable())) {
                tables.add(order.getTable());
            } else {
                return false;
            }

            if (!ids.contains(order.getOrderID())) {
                ids.add(order.getOrderID());
            } else {
                return false;
            }
        }

        for (MenuItem menuItem : this.menu) {
            if (menuItem instanceof CompositeProduct) {
                CompositeProduct compositeProduct = (CompositeProduct) menuItem;

                for (MenuItem item : compositeProduct.getComposition()) {
                    if (!this.menu.contains(item)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }


    /**
     * @pre The item is not already in the menu
     * @pre price > 0
     * @post menu.size() ++
     * @post invariants are preserved
     */
    @Override
    public void createNewMenuItem(String name, double price) {
        assert price > 0 : "Price is negative";

        for (MenuItem menuItem : this.menu) {
            assert !menuItem.getName().equals(name) : "Item " + name + " already in the menu";
        }

        boolean successfulAdd = this.menu.add(new BaseProduct(name, price));
        assert successfulAdd : "Failed to create menu item " + name;

        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }

    /**
     * @pre The item is not already in the menu
     * @pre the items exist in the menu
     * @pre price > 0
     * @post menu.size() ++
     * @post invariants are preserved
     */
    @Override
    public void createNewMenuItem(String name, double price, ArrayList<String> items) {
        assert price > 0 : "Price is negative";

        for (MenuItem menuItem : this.menu) {
            assert !menuItem.getName().equals(name) : "Item " + name + " already in the menu";
        }

        boolean itemIsInMenu;
        CompositeProduct compositeProduct = new CompositeProduct(name, price);

        for (String item : items) {
            itemIsInMenu = false;
            for (MenuItem menuItem : this.menu) {
                if (menuItem.getName().equals(item)) {
                    compositeProduct.addItem(menuItem);
                    itemIsInMenu = true;
                    break;
                }
            }

            assert itemIsInMenu : "Item " + item + " not in the menu";
        }

        boolean successfulEdit = this.menu.add(compositeProduct);
        assert successfulEdit : "Failed to create menu item " + name;

        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }

    /**
     * @pre The item exists in the menu
     * @post menu.size() --
     * @post invariants are preserved
     */
    @Override
    public void deleteMenuItem(String name) {
        boolean itemIsInMenu = false;
        for (MenuItem menuItem : this.menu) {
            if (menuItem.getName().equals(name)) {
                    itemIsInMenu = true;
                    break;
            }
        }
        assert itemIsInMenu : "Item " + name + " not in the menu";

        Iterator iter = this.menu.iterator();
        MenuItem menuItem = null;
        boolean successfulDelete = false;

        while (iter.hasNext()) {
            menuItem = (MenuItem) iter.next();

            if (menuItem.getName().equals(name)) {
                iter.remove();
                successfulDelete = true;
            } else if (menuItem instanceof CompositeProduct){
                CompositeProduct compositeProduct = (CompositeProduct) menuItem;

                if (compositeProduct.contains(name)) {
                    iter.remove();
                    successfulDelete = true;
                }
            }
        }
        assert successfulDelete : "Failed to delete item " + name;
        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }

    /**
     * @pre The item exists in the menu
     * @pre newPrice > 0
     * @post menuItem.price = newPrice where menuItem.getName().equals(name)
     * @post invariants are preserved
     */
    @Override
    public void editMenuItem(String name, double newPrice) {
        assert newPrice > 0 : "Price is negative";

        boolean itemIsInMenu = false;
        for (MenuItem menuItem : this.menu) {
            if (menuItem.getName().equals(name))
                itemIsInMenu = true;
        }
        assert itemIsInMenu : "Item " + name + " not in the menu";

        for (MenuItem menuItem : this.menu) {
            if (menuItem.getName().equals(name)) {
                menuItem.setPrice(newPrice);
                break;
            }
        }

        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }

    /**
     * @pre The item exists in the menu
     * @post menuItem.name = newName where menuItem.getName().equals(name)
     * @post invariants are preserved
     */
    @Override
    public void editMenuItem(String name, String newName) {
        boolean itemIsInMenu = false;
        for (MenuItem menuItem : this.menu) {
            if (menuItem.getName().equals(name))
                itemIsInMenu = true;
        }
        assert itemIsInMenu : "Item " + name + " not in the menu";

        for (MenuItem menuItem : this.menu) {
            if (menuItem.getName().equals(name)) {
                menuItem.setName(newName);
                break;
            }
        }

        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }

    /**
     * @pre The item exists in the menu
     * @pre The newItems exist in the menu
     * @post menuItem.setComposition(newItems) where menuItem.getName().equals(name)
     * @post invariants are preserved
     */
    @Override
    public void editMenuItem(String name, ArrayList<String> newItems) {
        CompositeProduct compositeProduct = null;
        ArrayList<MenuItem> composition = null;
        boolean itemIsInMenu;

        for (MenuItem menuItem : this.menu) {
            if (menuItem.getName().equals(name)) {
                if (menuItem instanceof CompositeProduct)
                    compositeProduct = (CompositeProduct) menuItem;
                break;
            }
        }

        assert compositeProduct != null : "Item " + name + " not in the menu";

        composition = new ArrayList<MenuItem>();

        for (String item : newItems) {
            itemIsInMenu = false;

            for (MenuItem menuItem : this.menu) {
                if (menuItem.getName().equals(item)) {
                    composition.add(menuItem);
                    itemIsInMenu = true;
                    break;
                }
            }

            assert itemIsInMenu : "Item " + item + " not in the menu";
        }

        compositeProduct.setComposition(composition);

        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }

    /**
     * @pre table > 0
     * @pre All the items exist in the menu
     * @post orders.size() ++
     * @post id ++
     * @post invariants are preserved
     */
    @Override
    public void createNewOrder(int table, ArrayList<String> items) {
        assert table > 0 : "Table number is negative";

        Order order = new Order(this.id, table);
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        boolean itemIsInMenu;

        for (String item : items) {
            itemIsInMenu = false;

            for (MenuItem menuItem : this.menu) {
                if (menuItem.getName().equals(item)) {
                    menuItems.add(menuItem);
                    itemIsInMenu = true;
                    break;
                }
            }

            assert itemIsInMenu : "Item " + item + " not in the menu";
        }

        this.orders.put(order, menuItems);

        if (this.id == 1) {
            new File("Bills").mkdir();
        }
        this.id++;

        setChanged();
        notifyObservers(order);

        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }

    /**
     * @pre table > 0
     * @post price > 0
     */
    @Override
    public double computePriceOrder(int table) {
        assert table > 0 : "Table number is negative";

        Order currOrder = null;
        double price = 0;

        for (Order order : this.orders.keySet()) {
            if (order.getTable() == table) {
                currOrder = order;
                break;
            }
        }

        if (currOrder == null)
            return -1;

        for (MenuItem menuItem : this.orders.get(currOrder)) {
            price += menuItem.computePrice();
        }

        assert price > 0 : "Price is negative";

        setChanged();
        notifyObservers(currOrder);

        assert isWellFormed() : "The invariants are not preserved";
        return price;
    }

    /**
     * @pre table > 0
     * @post orders.size() --
     * @post invariants are preserved
     */
    @Override
    public void generateBill(int table) {
        assert table > 0 : "Table number is negative";

        Order toDelete = null;

        for (Order order : this.orders.keySet()) {
            if (order.getTable() == table) {
                toDelete = order;
                FileWriter.writeBill(order, this.orders.get(order), this.computePriceOrder(table));
                break;
            }
        }

        if (toDelete != null)
            this.orders.remove(toDelete);

        assert isWellFormed() : "The invariants are not preserved";
        RestaurantSerializator.Serialize(this, "restaurant.ser");
    }
}
