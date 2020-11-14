package strategy_patter;

import queues.Server;
import queues.Task;

import java.util.ArrayList;

/** ConcreteStrategyQueue class
 *
 * -implements a specific strategy for adding the client to one of the given queues
 */
public class ConcreteStrategyQueue implements Strategy{

    @Override
    public void addTask(ArrayList<Server> servers, Task t) {
        if (!servers.isEmpty()) {
            Server selectedServer = servers.get(0);
            int minWaitingTime = servers.get(0).getWaitingPeriod();

            for (Server server : servers) {
                if (server.getWaitingPeriod() < minWaitingTime) {
                    minWaitingTime = server.getWaitingPeriod();
                    selectedServer = server;
                }
            }

            selectedServer.addTask(t);
        }
    }
}
