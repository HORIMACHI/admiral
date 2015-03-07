package Controll;

import java.awt.*;

/**
 * Created by hiroto on 2015/02/23.
 */
public class Display {
    private static Display instance = null;
    private int width;
    private int height;
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    private Display(){};
    public static Display getInstance() {
        if (instance == null)
            instance = new Display();
        return instance;
    }
    //Rectangelを返す
    public Rectangle getDesktop() {
        return new Rectangle(width,height);
    }
    //デスクトップを取得する。
    public void setDesktopEnvironment() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();
        width = displayMode.getWidth();
        height = displayMode.getHeight();
    }
}
