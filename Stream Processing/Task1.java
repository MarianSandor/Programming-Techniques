package task1;

import task1.MonitoredData;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task1 {

    public static ArrayList<MonitoredData> monitoredData;

    private static void printFile() {
        try {
            FileWriter writer = new FileWriter("Tasks\\Task_1.txt");

            for (MonitoredData md : monitoredData) {
                writer.write(md.toString());
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void perform(String filePath) {
        try {
            monitoredData = new ArrayList<MonitoredData>();

            String reg = "([\\t]+)";
            Stream<String> rows = Files.lines(Paths.get(filePath));

            monitoredData = (ArrayList<MonitoredData>) rows
                    .map(x -> x.split(reg))
                    .map(x -> new MonitoredData(x[0], x[1], x[2]))
                    .collect(Collectors.toList());

            rows.close();

            printFile();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public ArrayList<MonitoredData> getMonitoredData() {
        return monitoredData;
    }
}
