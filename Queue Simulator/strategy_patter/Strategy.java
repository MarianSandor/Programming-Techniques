package strategy_patter;

import queues.Server;
import queues.Task;

import java.util.ArrayList;

/** Strategy interface
 *
 * -defines the role of the strategy classes, that being of adding clients to queues
 */

public interface Strategy {

    public void addTask(ArrayList<Server> servers, Task t);
}
