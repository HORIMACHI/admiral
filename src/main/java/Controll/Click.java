package Controll;
import java.awt.event.InputEvent;

/**
 * Created by hiroto on 2015/02/23.
 */
public class Click {
    private Click(){};
    private static Click instance = null;
    public static Click getInstance() {
        if (instance == null)
            instance = new Click();
         return instance;
    }
    public void moveAndClick(int x,int y) {
        mouseMove(x,y);
        TimerUtil.getInstance().sleep(100);//0.1s待つ
        mouseClick();
    }
    public void mouseMove(int x,int y) {
        Controller.getInstance().mouseMove(x, y);
    }
    /*押して離す動作*/
    public void mouseClick() {
        mouseClick_();
        TimerUtil.getInstance().sleep(50);//0.05s待つ
        mouseRelease_();
        TimerUtil.getInstance().sleep(4000);//4s待つ
    }
    /*押して離す動作*/
    public void mouseClick(int delay) {
        mouseClick_();
        TimerUtil.getInstance().sleep(50);//0.05s待つ
        mouseRelease_();
        TimerUtil.getInstance().sleep(delay);//0.05s待つ
    }
    /* Helperメソッド*/
    private void mouseClick_() {
        Controller.getInstance().mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }
    private void mouseRelease_() {
        Controller.getInstance().mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
