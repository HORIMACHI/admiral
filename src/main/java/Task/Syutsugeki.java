package Task;

import Controll.Click;
import Controll.Controller;
import Controll.Display;
import Controll.TimerUtil;
import Image.ImageMatcher;
import State.Bokou;
import State.Context;

import java.awt.*;
import java.io.File;

import static Constant.Constants.PATH_SYMBOL;
import static Constant.Constants.RSRC_FILEPATH;

/**
 * Created by hiroto on 2015/03/30.
 */
public class Syutsugeki implements Task{

    @Override
    public void execute(Context context) {
        System.out.println("出撃タスク開始");
        /* 母港画面まで移動させる */
        if(!context.getState().getClass().getSimpleName().equals("Bokou"))
            context.transitionBokou(context.getState());
        /* 母港画面 : 処理内容 */
        //インスタンスの取得
        ImageMatcher img = ImageMatcher.getInstance();
        Display disp = Display.getInstance();
        Robot robo = Controller.getInstance();
        Click clk = Click.getInstance();
        //出撃画面へ移動
        context.transition(context.getState(),"syutsugeki");

        System.out.println("母港→出撃ボタン押す");
        System.out.println(RSRC_FILEPATH + "bokou" + PATH_SYMBOL + "syutsugeki" + ".png");
        //出撃
        while(!img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "syutsugeki" + ".png"), robo.createScreenCapture(disp.getDesktop())));
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
        System.out.println("エリア押す");
        System.out.println(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "syutsugeki" + ".png");

        //1-1を選択
        while(!img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "sid_1_1" + ".png"), robo.createScreenCapture(disp.getDesktop())));
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
        System.out.println("1-1を押す");
        //決定ボタンを押す
        while(!img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "s_kettei" + ".png"), robo.createScreenCapture(disp.getDesktop())));
            //kaitaiflagの画像が見えたら,解体タスクをキューに入れる.
            if(img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "kaitaiflag" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                TaskQueue.queue.addLast(new Kaitai());
                System.out.println("解体フラグON");
            }
            img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "s_kettei" + ".png"), robo.createScreenCapture(disp.getDesktop()));
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
        System.out.println("決定");
        //1_1への出撃ボタンを押す
        TimerUtil.getInstance().sleep(1000);


        while(!img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "s_syutsugeki" + ".png"), robo.createScreenCapture(disp.getDesktop())));
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
        System.out.println("出撃");
        //追撃せず
        while(!img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "tsuigeki" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
            if(img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "tettai" + ".png"), robo.createScreenCapture(disp.getDesktop())))
                break;
            else {
                clk.mouseMove(ImageMatcher.getInstance().get_B_AdjustX(), ImageMatcher.getInstance().get_B_AdjustY());
                clk.mouseClick();
                TimerUtil.getInstance().sleep(1000);
                System.out.println("クリック");
                continue;
            }
        }
        if(img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "tsuigeki" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
        }
        System.out.println("追撃せずor撤退");
        //撤退ボタンを押す
        while(!img.compareImg(new File(RSRC_FILEPATH + "syutsugeki" + PATH_SYMBOL + "tettai" + ".png"), robo.createScreenCapture(disp.getDesktop())));
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);

        //補給タスクを生成
        TaskQueue.queue.addLast(new Hokyu());
        //母港に戻った
        context.setState(Bokou.getInstance());
            TimerUtil.getInstance().sleep(5000);//5s wait
        context.transitionBokou(context.getState());

        end();
    }
    @Override
    public int getPriority() {
        return 1;
    }

}
