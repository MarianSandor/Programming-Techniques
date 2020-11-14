package task4;

import task1.MonitoredData;
import task1.Task1;
import task2.Task2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task4 {

    public static Map<Integer, Map<String, Integer>>  activityCountPerDay;

    private static void printFile() {
        try {
            FileWriter writer = new FileWriter("Tasks\\Task_4.txt");

            for (Integer i : activityCountPerDay.keySet()) {
                writer.write("Day " + i);
                writer.write("\n");
                for (String activity : activityCountPerDay.get(i).keySet()) {
                    writer.write("Activity: " + activity + " --- " + activityCountPerDay.get(i).get(activity));
                    writer.write("\n");
                }
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void perform() {

        activityCountPerDay = new HashMap<Integer, Map<String, Integer>>();
        AtomicInteger index = new AtomicInteger(0);

        activityCountPerDay = Task1.monitoredData.stream()
                    .flatMap(x -> Stream.of(x.getStartTime(), x.getEndTime()))
                    .map(x -> x.split(" ")[0])
                    .distinct()
                    .collect(Collectors.toMap(
                            x -> index.incrementAndGet(),
                            x -> Task1.monitoredData.stream()
                                    .map(MonitoredData::getActivityLabel)
                                    .distinct()
                                    .collect(Collectors.toMap(
                                            y -> y,
                                            y -> Math.toIntExact(Task1.monitoredData.stream()
                                                    .filter(z -> z.getActivityLabel().equals(y))
                                                    .filter(z -> z.getStartTime().split(" ")[0].equals(x))
                                                    .count()
                                            )
                                    ))
                    ));

        printFile();

    }
}
