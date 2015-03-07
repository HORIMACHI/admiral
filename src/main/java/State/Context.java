package State;

/**
 * Created by hiroto on 2015/02/24.
 */
public interface Context {
    public abstract void setState(State state);
    public abstract State getState() ;
    public abstract boolean transition(State state,String dest);
    public abstract boolean transitionAdjuster(Context context,State state);
    public abstract boolean transitionBokou(State state);
}
