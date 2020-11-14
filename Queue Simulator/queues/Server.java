package queues;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/** Server class
 *
 * -represents the queue for the clients
 * -it simulates the process in which clients form a queue waiting to be processed
 */

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int totalWaitingTime;
    private int totalProcessedTasks;
    private boolean flag;

    public Server(int limit) {
        this.waitingPeriod = new AtomicInteger(0);
        this.totalWaitingTime = 0;
        this.totalProcessedTasks = 0;
        this.tasks = new ArrayBlockingQueue<Task>(limit);
        this.flag = false;
    }

    /** addTask method
     *
     * -adds a new client to the queue
     * -increments the waiting period of the queue by the client's processing time
     * @param task
     */
    public void addTask(Task task) {
        this.tasks.add(task);
        waitingPeriod.set(waitingPeriod.intValue() + task.getProcessingTime());
    }

    /** getTasks method
     *
     * -creates and array with the clients from the queue
     * @return Task[]
     */
    public Task[] getTasks() {
        if (this.tasks.isEmpty())
            return null;

        Task tasks[] = new Task[this.tasks.size()];
        int i = 0;

        for (Task task : this.tasks) {
            tasks[i] = task;
            i++;
        }

        return tasks;
    }

    public int getTotalWaitingTime() {
        return this.totalWaitingTime;
    }

    public int getTotalProcessedTasks() {
        return this.totalProcessedTasks;
    }

    public int getWaitingPeriod() {
        return this.waitingPeriod.intValue();
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) { this.flag = flag; }


    /** run method
     *
     * -implements the queue behavior
     * -the clients are taken one by one in order and processed
     */
    @Override
    public void run() {
        Task currTask;
        this.flag = true;

        while(this.flag) {

            try {
                currTask = this.tasks.peek();

                Thread.sleep(currTask.getProcessingTime() * 1000);
                waitingPeriod.set(waitingPeriod.intValue() - currTask.getProcessingTime());
                this.totalWaitingTime += this.tasks.take().getWaitingTime();
                this.totalProcessedTasks++;

                if (this.tasks.isEmpty()) {
                    this.flag = false;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public String toString() {
        String toString = "";
        if (!this.tasks.isEmpty()) {
            int i = 1;

            for (Task task : this.tasks) {
                if (i == 10) {
                    toString += "\n";
                    i = 1;
                }
                toString += task;
            }
        }
        else {
            toString += "Closed";
        }
        toString += "\n";

        return toString;
    }
}
