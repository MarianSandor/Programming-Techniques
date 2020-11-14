package model;

/** Clients Class
 *
 * Maps the Clients table format from the database.
 */

public class Clients {

    private int id;
    private String name;
    private String address;

    public Clients(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Clients(String name) { this(-1, name, null); }

    public Clients(String name, String address) {this(-1, name, address);}

    public Clients() {
        this(-1, null, null);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client id: " + this.id + " Name: " + this.name + " Address: " + this.address;
    }
}
