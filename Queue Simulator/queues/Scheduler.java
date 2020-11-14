package queues;

import queues.Server;
import queues.Task;
import strategy_patter.ConcreteStrategyQueue;
import strategy_patter.SelectionPolicy;
import strategy_patter.Strategy;

import java.util.ArrayList;

/** Scheduler class
 *
 * -it has the responsibility of coordinating the queues and clients
 * -provides method for adding clients to a specific queue determined by the selected strategy
 */

public class Scheduler {

    private ArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer, SelectionPolicy selectionPolicy) {
        this.servers = new ArrayList<Server>();
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        this.changeStrategy(selectionPolicy);
        this.initialize();
    }

    /** initialize methid
     *
     * -creates the queues
     */
    private void initialize() {
        for (int i = 0; i < this.maxNoServers; i++) {
            this.servers.add(new Server(maxTasksPerServer));
        }
    }

    /** changeStrategy method
     *
     * -sets the current strategy to the one given as parameter
     * @param policy
     */
    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            this.strategy = new ConcreteStrategyQueue();
        }
    }

    /** dispatchTask
     *
     * -using the strategy selected previously, it adds the client to one of the queues
     * @param t
     */
    public void dispatchTask(Task t) {
        this.strategy.addTask(servers, t);
    }

    /** updateServers method
     *
     * -it starts a queue in case it has clients redirected to it
     */
    public void updateServers() {
        Thread thread;

        for (Server server : this.getServers()) {
            if (!server.getFlag() && server.getTasks() != null) {
                thread = new Thread(server);
                thread.start();
            }
        }
    }

    public ArrayList<Server> getServers() {
        return this.servers;
    }

    @Override
    public String toString() {
        String toPrint = "";

        for (int i = 0; i < this.getServers().size(); i++) {
            toPrint += "Queue ";
            toPrint += (i + 1);
            toPrint += ": ";
            toPrint += this.getServers().get(i).toString();
        }

        return toPrint;
    }

}
