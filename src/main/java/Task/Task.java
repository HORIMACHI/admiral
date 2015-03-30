package Task;

import State.Context;
/**
 * Created by hiroto on 2015/02/25.
 */
public interface Task {
    //Taskの処理内容
    public abstract void execute(Context context);
    public abstract int getPriority();//タスクの優先度を10段階で評価する.
    //タスク終了時に呼ぶ
    public default void end() {
        TaskQueue.queue.remove(this);
    }
}
