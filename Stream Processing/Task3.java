package task3;

import task1.MonitoredData;
import task1.Task1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task3 {

    public static Map<String, Integer> activityCount;

    private static void printFile() {
        try {
            FileWriter writer = new FileWriter("Tasks\\Task_3.txt");

            for (String s : activityCount.keySet()) {
                writer.write(s + " --- " + activityCount.get(s));
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void perform() {

        activityCount = new HashMap<>();

        activityCount = Task1.monitoredData.stream()
                    .map(MonitoredData::getActivityLabel)
                    .distinct()
                    .collect(Collectors.toMap(
                            x -> x,
                            x -> Math.toIntExact(Task1.monitoredData.stream()
                                    .filter(y -> y.getActivityLabel().equals(x))
                                    .count())));

        printFile();
    }
}
