package businessLayer.menu;

public interface MenuItem {

    double computePrice();
    String getName();
    double getPrice();
    void setName(String name);
    void setPrice(double price);
}
