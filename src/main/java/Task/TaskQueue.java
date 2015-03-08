package Task;

import java.util.LinkedList;
/**
 * Created by horiba on 2015/02/24.
 */
public final class TaskQueue {
    public static LinkedList<Task> queue;
    static {
        queue = new LinkedList<>();
    }
    private TaskQueue(){};
}
