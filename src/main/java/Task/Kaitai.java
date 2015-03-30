package Task;

import Controll.Click;
import Controll.Controller;
import Controll.Display;
import Controll.TimerUtil;
import Image.ImageMatcher;
import State.Context;

import java.awt.*;
import java.io.File;

import static Constant.Constants.PATH_SYMBOL;
import static Constant.Constants.RSRC_FILEPATH;

/**
 * Created by hiroto on 2015/03/30.
 */
public class Kaitai implements Task{
    public void execute(Context context) {
        System.out.println("解体開始");
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
        context.transition(context.getState(),"kousyo");

        //解体画面へ移動
        while(!img.compareImg(new File(RSRC_FILEPATH + "kousyo" + PATH_SYMBOL + "kaitai" + ".png"), robo.createScreenCapture(disp.getDesktop())));
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);

        //解体実行
        while(!img.compareImg(new File(RSRC_FILEPATH + "kousyo" + PATH_SYMBOL + "kaitai_no" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
            TimerUtil.getInstance().sleep(1000);
            if(img.compareImg(new File(RSRC_FILEPATH + "kousyo" + PATH_SYMBOL + "kanmusu" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                clk.mouseMove(img.MatchX(), img.MatchY());
                clk.mouseClick();
                clk.mouseMove(0, 0);
                if(img.compareImg(new File(RSRC_FILEPATH + "kousyo" + PATH_SYMBOL + "kaitai_exec" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                    clk.mouseMove(img.MatchX(), img.MatchY());
                    clk.mouseClick();
                    clk.mouseMove(0, 0);
                }
            }
            else break;//見つからなかったら母港に戻る.

            TimerUtil.getInstance().sleep(4000);
        }

        //最後に母港に戻る
        context.transitionBokou(context.getState());
        end();
    }
    @Override
    public int getPriority() {
        return 8;
    }

}
