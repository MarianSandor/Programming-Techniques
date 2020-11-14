import task1.Task1;
import task2.Task2;
import task3.Task3;
import task4.Task4;
import task5.Task5;
import task6.Task6;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Input file path expected.");
            return;
        }

        new File("Tasks").mkdir();

        Task1.perform(args[0]);
        Task2.perform();
        Task3.perform();
        Task4.perform();
        Task5.perform();
        Task6.perform();

    }
}
