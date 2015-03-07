package Controll;

import java.awt.*;

/**
 * Created by hiroto on 2015/02/23.
 */
public class Controller {
    private static Robot robotInstance = null;
    private Controller(){};
    public static Robot getInstance() {
        if (robotInstance == null) {
            try {
                robotInstance = new Robot();
            } catch (AWTException awtex) {
                awtex.printStackTrace();
            }
        }
        return robotInstance;
    }
}

