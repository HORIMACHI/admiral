package Controll;

/**
 * Created by hiroto on 2015/02/23.
 */
public class TimerUtil {
    private static TimerUtil instance = null;
    public static TimerUtil getInstance() {
        if (instance == null)
            instance = new TimerUtil();
        return instance;
    }
    public synchronized void sleep(long msec)
    {	//指定ミリ秒実行を止めるメソッド
        try
        {
            wait(msec);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
