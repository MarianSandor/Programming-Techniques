package task2;

import task1.MonitoredData;
import task1.Task1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Task2 {

    public static long differentDays;

    private static void printFile() {
        try {
            FileWriter writer = new FileWriter("Tasks\\Task_2.txt");

            writer.write("Number of different days: " + differentDays);

            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void perform() {

        differentDays = Task1.monitoredData.stream()
                    .flatMap(x -> Stream.of(x.getStartTime(), x.getEndTime()))
                    .map(x -> x.split(" ")[0])
                    .distinct()
                    .count();

        printFile();
    }
}
