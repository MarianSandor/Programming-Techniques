package businessLogic.classes;

import businessLogic.validators.Validator;
import dataAccess.operations.OrderItemsDAO;
import dataAccess.operations.ProductsDAO;
import model.Products;
import presentation.Report;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** ProductsBLL Class
 *
 * Defines the Products table business logic.
 */

public class ProductsBLL {
    protected static final Logger LOGGER = Logger.getLogger(ProductsBLL.class.getName());

    private ProductsDAO productsDAO;
    private OrderItemsDAO orderItemsDAO;

    public ProductsBLL() {
        this.orderItemsDAO = new OrderItemsDAO();
        this.productsDAO = new ProductsDAO();
    }

    /**
     * Validates a field using Validator class.
     * @param product A Products class object.
     * @return True if the fields of the given object contains only valid values.
     */
    private boolean checkFields(Products product) {
        if (!Validator.validProductName(product.getName())) {
            LOGGER.log(Level.WARNING, "ProductBLL: insertProduct Invalid Product Name: " + product.getName());
            return false;
        }

        if (!Validator.validAmount(product.getAmount())) {
            LOGGER.log(Level.WARNING, "ProductBLL: insertProduct Invalid Product Amount: " + product.getAmount());
            return false;
        }

        if (!Validator.validPrice(product.getPrice())) {
            LOGGER.log(Level.WARNING, "ProductBLL: insertProduct Invalid Product Price: " + product.getPrice());
            return false;
        }

        return true;
    }

    /**
     * Inserts the product in the Products table.
     * @param product A Products class object.
     * @return True if the insertion is done successfully.
     */
    public boolean insertProduct(Products product) {
        int id = productsDAO.getId(product);
        Products existingProduct = null;

        if (!this.checkFields(product)) {
            return false;
        }

        if (id == -1) {
            productsDAO.insert(product);
        } else {
            existingProduct = productsDAO.findById(id);
            productsDAO.update(new Products(id, product.getName(), product.getAmount() + existingProduct.getAmount(), product.getPrice()));
        }

        return true;
    }

    /**
     * Updates the product from the Products table.
     * @param product A Products class object.
     * @param newProduct A Products class object.
     * @return True if the update is done successfully.
     */
    public boolean updateProduct(Products product, Products newProduct) {
        int id = productsDAO.getId(product);

        if (!this.checkFields(newProduct)) {
            return false;
        }

        if (id == -1) {
            LOGGER.log(Level.WARNING, "ProductBLL  Could not update " +  product.getName() + " " + product.getPrice() + " is not in the database!");
            return false;
        }

        newProduct.setId(id);
        productsDAO.update(newProduct);

        return true;
    }

    /**
     * Deletes the product from the Products table.
     * @param product A Products class object.
     * @return True if the deletion is done successfully.
     */
    public boolean deleteProduct(Products product) {
        int id = productsDAO.getId(product);

        if (id == -1) {
            LOGGER.log(Level.WARNING, "ProductsBLL  Could not delete " +  product.getName() + " " + product.getPrice() + " is not in the database!");
            return false;
        }
        product.setId(id);
        if (orderItemsDAO.isOrderItem(product)){
            LOGGER.log(Level.WARNING, "ProductsBLL  Could not delete " +  product.getName() + " " + product.getPrice() + " is a order item!");
            return false;
        }

        productsDAO.deleteById(id);

        return true;
    }

    /**
     * Obtains the specific product's id from the Products table.
     * @param product A Products class object.
     * @return The id of the given product.
     */
    public int getProductId(Products product) {
        int id = productsDAO.getId(product);

        if (id == -1) {
            LOGGER.log(Level.WARNING, "ProductsBLL  Could not delete " +  product.getName() + " " + product.getPrice() + " is not in the database!");
            return -1;
        }

        return id;
    }

    /**
     * Generates the Products table report using the Report class.
     */
    public void generateReports() {
        List<Products> productsList = this.productsDAO.report();

        if (productsList == null) {
            LOGGER.log(Level.WARNING, "ProductsBLL  could not generate reports");
            return;
        }

        Report.generateReport(productsList);
    }
}
