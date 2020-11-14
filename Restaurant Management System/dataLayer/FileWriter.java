package dataLayer;

import businessLayer.Order;
import businessLayer.menu.MenuItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FileWriter {

    public static void writeBill(Order order, ArrayList<MenuItem> items, double total) {

        try {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            PrintWriter writer = new PrintWriter("Bills/Bill_" + order.getOrderID() + ".txt", StandardCharsets.UTF_8);

            writer.write(order.toString());
            writer.write("\n\nItems:\n");

            for (MenuItem menuItem : items) {
                int characters = 30;
                characters -= menuItem.getName().length();
                writer.write(menuItem.getName() + " ");

                for (int i = 0; i < characters; i++) {
                    writer.write(".");
                }

                writer.write(" " + df.format(menuItem.computePrice()) + "\n");
            }

            writer.write("\nTotal: " + df.format(total));

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
