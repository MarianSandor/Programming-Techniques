package task5;

import task1.MonitoredData;
import task1.Task1;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Task5 {
    public static Map<String, Duration>  activityDuration;

    private static void printFile() {
        try {
            FileWriter writer = new FileWriter("Tasks\\Task_5.txt");

            writer.write("Time format is: hours:minutes:seconds\n\n");

            for (String activity : activityDuration.keySet()) {
                Duration duration = activityDuration.get(activity);
                writer.write(activity + " --- ");
                writer.write(String.format("%d:%02d:%02d",
                        duration.toHours(),
                        duration.toMinutesPart(),
                        duration.toSecondsPart()));
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void perform() {

        activityDuration = new HashMap<String, Duration>();

        activityDuration = Task1.monitoredData.stream()
                .map(MonitoredData::getActivityLabel)
                .distinct()
                .collect(Collectors.toMap(
                        x -> x,
                        x -> Task1.monitoredData.stream()
                                .filter(y -> y.getActivityLabel().equals(x))
                                .map(y -> Duration.between(LocalDateTime.parse(y.getStartTime().replace(" ", "T")),
                                                            LocalDateTime.parse(y.getEndTime().replace(" ", "T"))))
                                .reduce(Duration.ZERO, Duration::plus)
                        ));

        printFile();
    }
}
