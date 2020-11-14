package simulation;

import queues.Scheduler;
import queues.Server;
import queues.Task;
import strategy_patter.SelectionPolicy;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/** SimulationManager class
 *
 * -simulates the working process in which clients come, are added to a queue, are served and then they leave
 */

public class SimulationManager implements Runnable{

    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int numberOfServers;
    private int numberOfTasks;
    private SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private ArrayList<Task> generatedTasks;

    private BufferedWriter writer;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int minArrivalTime, int maxArrivalTime, int numberOfTasks, int numberOfServers, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfTasks = numberOfTasks;
        this.selectionPolicy = selectionPolicy;
        this.generatedTasks = new ArrayList<Task>();
        this.scheduler = new Scheduler(this.numberOfServers, this.numberOfTasks / this.numberOfServers + 1, selectionPolicy);

        try {
            this.writer = new BufferedWriter(new FileWriter("Output.txt"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.generateTasks();
    }

    /** generateTasks method
     *
     * -randomly generates the clients with all their attributes
     * -the clients are then added to a list which is then sorted by their arrival time
     */
    private void generateTasks() {
        Random random = new Random();
        int ID;
        int arrivalTime;
        int processingTime;

        for (int i = 0; i < this.numberOfTasks; i++) {
            ID = i + 1;
            arrivalTime = this.minArrivalTime + random.nextInt(this.maxArrivalTime - this.minArrivalTime + 1);
            processingTime = this.minProcessingTime + random.nextInt(this.maxProcessingTime - this.minProcessingTime + 1);

            this.generatedTasks.add(new Task(ID, arrivalTime, processingTime));
        }

        Collections.sort(this.generatedTasks);
    }

    /** averageWaitingTime method
     *
     * -computes the average waiting time of the clients in the queue
     * -the formula is: waited period until processing / number of clients
     * -it only takes in consideration the clients which have been processed
     * @return double
     */
    private double averageWaitingTime() {
        int totalWaitingTime = 0;
        int totalProcessedTasks = 0;

        for (Server server : this.scheduler.getServers()) {
            totalWaitingTime += server.getTotalWaitingTime();
            totalProcessedTasks += server.getTotalProcessedTasks();
        }

        return (double) totalWaitingTime / totalProcessedTasks;
    }

    /** printTasks method
     *
     * -prints the clientes which have not yet been added to any queue
     * @throws IOException
     */
    private void printTasks() throws IOException {
        this.writer.write("Waiting clients: ");
        for (int i = 0; i < this.generatedTasks.size(); i++) {
            if ((i + 1) % 10 == 0) {
                this.writer.write("\n");
            }

            this.writer.write(this.generatedTasks.get(i).toString());
        }

        this.writer.write("\n");
    }

    /** printTime
     *
     * -prints the current time of the simulation on the screen
     * @param currentTime
     */
    private void printTime(int currentTime) {
        System.out.print("\b\b\b\b\b\b");
        for (int i = 0; i <= currentTime / 10; i++) {
            System.out.print("\b");
        }
        System.out.print("Time: " + currentTime);
    }

    /** decreaseProcessingTime
     *
     * -decrements by one the processing time of all the clients that are in front of their queue
     */
    private void decreaseProcessingTime() {
        for (Server server : this.scheduler.getServers()) {
            Task tasks[] = server.getTasks();

            if (tasks != null)
                tasks[0].decreaseProcessingTime();
        }
    }

    /** increaseWaitingTime
     *
     * -increment by one the waiting time of all the clients that not in front of their queue
     */
    private void increaseWaitingTime() {
        for (Server server : this.scheduler.getServers()) {
            Task tasks[] = server.getTasks();

            if (tasks != null) {
                for (int i = 1; i < tasks.length; i++) {
                    tasks[i].increaseWaitingTime();
                }
            }
        }
    }

    /** closeServers()
     *
     * -forces the queues to exit from their run method using the flag
     */
    private void closeServers() {
        for (Server server : this.scheduler.getServers()) {
            server.setFlag(false);
        }
    }

    /** run method
     *
     * -implements the behaviour of the simulation
     * -first it moves all the clients with the arrival time equal to the current simulation time to the queues
     * -then it updates the queues
     * -writes the current state to the output file
     * -updates the timestamps of the clients in the queues
     * -in the end, the average waiting time is computed and written to the output file
     */
    @Override
    public void run() {
        int currentTime = 0;
        Iterator iterator;
        Task task;

        while (currentTime < timeLimit) {

            try {
                iterator = this.generatedTasks.iterator();
                while (iterator.hasNext()) {
                    task = (Task) iterator.next();
                    if (task.getArrivalTime() == currentTime) {
                        this.scheduler.dispatchTask(task);
                        iterator.remove();
                    }
                }
                this.scheduler.updateServers();

                this.printTime(currentTime);

                this.writer.write("Time " + currentTime + "\n");
                this.printTasks();
                this.writer.write(this.scheduler.toString() + "\n");


                this.decreaseProcessingTime();
                this.increaseWaitingTime();

                currentTime++;

                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            DecimalFormat df2 = new DecimalFormat("#.##");
            this.writer.write("Average waiting time: " + df2.format(this.averageWaitingTime()));
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.closeServers();
        System.out.println("\nDone");
    }
}
