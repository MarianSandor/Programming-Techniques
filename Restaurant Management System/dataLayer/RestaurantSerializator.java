package dataLayer;

import businessLayer.restaurant.Restaurant;

import java.io.*;

public class RestaurantSerializator {

    public static Restaurant Deserialize (String fileName) {
        File simulatorSer = new File(fileName);
        Restaurant restaurant = new Restaurant();

        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            restaurant = (Restaurant) in.readObject();

            in.close();
            file.close();
        } catch (IOException e) {
                System.out.println("IOException is caught");
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        }

        return restaurant;
    }

    public static void Serialize(Restaurant restaurant, String fileName) {
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("./" + fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(restaurant);

            out.close();
            file.close();
        }
        catch(IOException e)
        {
            System.out.println("IOException is caught");
            e.printStackTrace();
        }
    }
}
