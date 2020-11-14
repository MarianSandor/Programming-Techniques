package task6;

import task1.MonitoredData;
import task1.Task1;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task6 {

    public static List<String> activities;

    private static void printFile() {
        try {
            FileWriter writer = new FileWriter("Tasks\\Task_6.txt");

            for (String activity : activities) {
                writer.write(activity);
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void perform() {

        activities = new ArrayList<String>();

        activities = Task1.monitoredData.stream()
                .map(MonitoredData::getActivityLabel)
                .distinct()
                .filter(x -> {
                    long a = Task1.monitoredData.stream()
                                .filter(y -> y.getActivityLabel().equals(x))
                                .filter(y -> Duration.between(LocalDateTime.parse(y.getStartTime().replace(" ", "T")),
                                                LocalDateTime.parse(y.getEndTime().replace(" ", "T"))).toMinutes() < 5)
                                .count();

                    long b = Task1.monitoredData.stream()
                                .filter(z -> z.getActivityLabel().equals(x))
                                .count();

                    return a > Math.floorDiv(b * 90, 100);
                })
                .collect(Collectors.toList());

        printFile();
    }
}
