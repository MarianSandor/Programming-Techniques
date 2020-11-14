import queues.Server;
import queues.Task;
import simulation.SimulationManager;
import strategy_patter.SelectionPolicy;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String argv[]) {

        if (argv.length != 1) {
            System.out.println("Expected one argument: <file_path>");
        } else {

            try {
                int timeLimit;
                int maxProcessingTime;
                int minProcessingTime;
                int minArrivalTime;
                int maxArrivalTime;
                int numberOfServers;
                int numberOfTasks;

                File file = new File(argv[0]);
                Scanner reader = new Scanner(file);

                numberOfTasks = readNumber(reader);
                numberOfServers = readNumber(reader);
                timeLimit = readNumber(reader);
                minArrivalTime = readNumber(reader);
                maxArrivalTime = readNumber(reader);
                minProcessingTime = readNumber(reader);
                maxProcessingTime = readNumber(reader);

                SimulationManager s = new SimulationManager(timeLimit, maxProcessingTime, minProcessingTime, minArrivalTime, maxArrivalTime, numberOfTasks, numberOfServers, SelectionPolicy.SHORTEST_QUEUE);

                Thread t = new Thread(s);
                t.start();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static int readNumber(Scanner reader) {
        while (reader.hasNext()) {
            if (reader.hasNextInt()) {
                return reader.nextInt();
            } else {
                reader.next();
            }
        }

        return 0;
    }
}
