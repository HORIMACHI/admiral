package Task;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by horiba on 2015/02/24.
 */
public final class TaskQueue {
    public static List<Task> queue;
    static {
        queue = new LinkedList<>();
    }
    private TaskQueue(){};
}
