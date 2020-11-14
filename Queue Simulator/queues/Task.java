package queues;

/** Task class
 *
 * -can be interpreted as a client identified by an ID
 * -it has an arrival time which states at which point in time he will be added to the queue
 * -it also has a processing time meaning how long does it have to stay in the front of the queue before he leaves
 * -the waiting time is the value in seconds before the client reaches the front position in the queue
 */

public class Task implements Comparable {

    private int ID;
    private int arrivalTime;
    private int processingTime;
    private int waitingTime;

    public Task(int ID, int arrivalTime, int processingTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.waitingTime = 0;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getProcessingTime() {
        return this.processingTime;
    }

    public int getWaitingTime() { return this.waitingTime; }

    /** drecreaseProcessingTime method
     *
     * -decrements by 1 the processing time of the client
     */
    public void decreaseProcessingTime() {
        this.processingTime = this.processingTime - 1;
    }

    /** increaseWaitingTime method
     *
     * -increments the waiting time of the client
     */
    public void increaseWaitingTime() {
        this.waitingTime = this.waitingTime + 1;
    }

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;

        return this.arrivalTime - task.arrivalTime;
    }

    @Override
    public String toString() {
        return "(" + this.ID + "," + this.arrivalTime + "," + this.processingTime + ");";
    }
}
