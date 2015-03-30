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
    public static void sort() {
        for (int j = 0; j < queue.size(); j++) {
            for (int i = 0; i < queue.size() - 1; i++) {
                if (queue.get(i).getPriority() < queue.get(i + 1).getPriority()) {
                    Task t = queue.get(i);//i番目を保管
                    //入れ替え
                    queue.set(i, queue.get(i + 1));
                    queue.set(i, t);
                    //参照を消しておく.
                    t = null;
                }
            }
        }
    }
}
