package Task;

/**
 * Created by hiroto on 2015/02/27.
 */

import Controll.Click;
import Controll.Controller;
import Controll.Display;
import Controll.TimerUtil;
import Image.ImageMatcher;
import State.Bokou;
import State.Context;

import java.awt.*;
import java.io.File;

import static Constant.Constants.P_FILEPATH;
import static Constant.Constants.RSRC_FILEPATH;
import static Constant.Constants.PATH_SYMBOL;

/**
 * 艦隊全てを補給するタスク
 */
public class Hokyu implements Task{

    @Override
    public void execute(Context context) {
        System.out.println("補給開始");
        /* 母港画面まで移動させる */
        if(!context.getState().getClass().getSimpleName().equals("Bokou"))
            context.transitionBokou(context.getState());
        /* 母港画面 : 処理内容 */
        //インスタンスの取得
        ImageMatcher img = ImageMatcher.getInstance();
        Display disp = Display.getInstance();
        Robot robo = Controller.getInstance();
        Click clk = Click.getInstance();
        //補給画面に移動
        context.transition(context.getState(),"hokyu");
        //1,2,3,4艦隊を補給する.
        //1~4艦隊目
        for(int i=0;i < 4;i++) {
            Kantai(img,clk,robo,disp,i);
            CheckAndDecide(img,clk,robo,disp,i);
            TimerUtil.getInstance().sleep(5000);
        }
        //最後に母港に戻る
        context.transitionBokou(context.getState());
        end();
    }

        private void Kantai(ImageMatcher img, Click clk, Robot robo, Display disp, int i) {
        if (img.compareImg(new File(RSRC_FILEPATH + "hokyu" + PATH_SYMBOL + "k" + String.valueOf(i + 1) + "_e" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
                img.compareImg(new File(RSRC_FILEPATH + "hokyu" + PATH_SYMBOL + "k" + String.valueOf(i + 1) + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
        }
    }
    private boolean CheckAndDecide(ImageMatcher img, Click clk, Robot robo, Display disp, int i) {
        if (img.compareImg(new File(RSRC_FILEPATH + "hokyu" + PATH_SYMBOL + "check" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
            if(img.compareImg(new File(RSRC_FILEPATH + "hokyu" + PATH_SYMBOL + "m_hokyu" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                clk.mouseMove(img.MatchX(), img.MatchY());
                clk.mouseClick();
                clk.mouseMove(0, 0);
                return true;
            }
            else
                CheckAndDecide(img,clk,robo,disp,i);
        }
        return false;
    }
    @Override
    public int getPriority() {
        return 10;
    }

}
