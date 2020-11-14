package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Clients;
import model.OrderReport;
import model.Products;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/** Report Class
 *
 * Generates the pdf files for the reports and bills
 */

public class Report {
    private static int clientReportIndex = 0;
    private static int productReportIndex = 0;
    private static int orderReportIndex = 0;
    private static int billIndex = 0;
    private static int messageIndex = 0;
    private static int index;

    /**
     * Increments the index for each type of reports.
     * The index is used such that the reports don't overwrite themselves.
     * @param object Model class object.
     * @param <T> Model class.
     */
    private static <T> void setIndex(T object) {
        if (object instanceof Clients) {
            clientReportIndex++;
            index = clientReportIndex;
        } else if (object instanceof Products) {
            productReportIndex++;
            index = productReportIndex;
        } else if (object instanceof OrderReport) {
            orderReportIndex++;
            index = orderReportIndex;
        }
    }

    /**
     * Creates a directory named "Reports" in which all the generated reports will be stored.
     */
    private static void createDirectoryReports() {
        if (clientReportIndex == 0 && productReportIndex == 0 && orderReportIndex == 0) {
            new File("Reports").mkdir();
        }
    }

    /**
     * Creates a directory named "Bills" in which all the generated bills will be stored.
     */
    private static void createDirectoryBills() {
        if (billIndex == 0) {
            new File("Bills").mkdir();
        }
    }

    /**
     * Creates a directory named "Messages" in which all the generated messages will be stored.
     */
    private static void createDirectoryMessages() {
        if (messageIndex == 0) {
            new File("Messages").mkdir();
        }
    }

    /**
     * Generates a pdf file in which the objects received as parameters are displayed in a table format.
     * @param objects List of objects defining rows in the database table
     * @param <T> Model class.
     */
    public static <T> void generateReport(List<T> objects) {
        createDirectoryReports();
        setIndex(objects.get(0));

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Reports\\" + objects.get(0).getClass().getSimpleName() + "_" + index + ".pdf"));
            document.open();

            PdfPTable table = new PdfPTable(objects.get(0).getClass().getDeclaredFields().length);
            addTableHeader(table, getFieldsName(objects.get(0).getClass()));

            for (T object : objects) {
                addRows(table, object);
            }

            document.addTitle(objects.get(0).getClass().getSimpleName() + " Report");
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a pdf file containing information about a specific command.
     * @param orderReport List of OrderReport objects defining an order.
     */
    public static void generateBill(List<OrderReport> orderReport) {
        createDirectoryBills();
        billIndex++;

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Bills\\" + orderReport.get(0).getClient() + "_" + billIndex + ".pdf"));
            document.open();

            PdfPTable table = new PdfPTable(orderReport.get(0).getClass().getDeclaredFields().length);
            addTableHeader(table, getFieldsName(orderReport.get(0).getClass()));

            for (OrderReport order : orderReport) {
                addRows(table, order);
            }

            document.addTitle(orderReport.get(0).getClient() + " Bill");
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a pdf file containing the message received as parameter.
     * @param message Message to be written to the pdf.
     */
    public static void generateUnderStockMessage(String message) {
        createDirectoryMessages();
        messageIndex++;

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Messages\\Message_" + messageIndex + ".pdf"));
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Phrase p = Paragraph.getInstance(message);

            document.add(p);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a String vector with fields name of the given class.
     * @param tClass Class identifying the table from the database
     * @param <T> Model class.
     * @return String vector with all the table attributes.
     */
    private static <T> String[] getFieldsName(Class<T> tClass) {
        String[] fields = new String[tClass.getDeclaredFields().length];
        int index = 0;

        for (Field field :tClass.getDeclaredFields()) {
            fields[index] = field.getName();
            index++;
        }

        return fields;
    }

    /**
     * Adds the strings received as parameters as the header of the table.
     * @param table Table object to be added to the pdf.
     * @param headers String vector with all the table attributes.
     */
    private static void addTableHeader(PdfPTable table, String[] headers) {
        Stream.of(headers).forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Inserts the object's attributes as a new line in the table.
     * @param table Table object to be added to the pdf.
     * @param object Model class object containing the values for the attributes.
     * @param <T> Model class.
     */
    private static <T> void addRows(PdfPTable table, T object) {
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                table.addCell(value.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
