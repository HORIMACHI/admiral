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
import static Constant.Constants.P_FILEPATH;
import static Constant.Constants.RSRC_FILEPATH;

/**
 * Created by horiba on 2015/02/27.
 */
public class Ensei implements Task{

    //default execute
    // 第二艦隊:id2 第三艦隊:id3 第四艦隊:id5
    @Override
    public void execute(Context context) {
        /* 母港画面まで移動させる */
        if (!context.getState().equals(Bokou.getInstance()))
            context.transitionBokou(context.getState());
        /* 母港画面 : 処理内容 */
        //インスタンスの取得
        ImageMatcher img = ImageMatcher.getInstance();
        Display disp = Display.getInstance();
        Robot robo = Controller.getInstance();
        Click clk = Click.getInstance();
        //補給画面に移動
        context.transition(context.getState(), "syutsugeki");
        context.transition(context.getState(), "ensei");
        int[] ids = {2, 5, 6};
        //鎮守府海域
        if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id1_8" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
                img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id1_8_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
            for (int i = 0; i < 3; i++) {
                //遠征のID選択
                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "eid" + String.valueOf(ids[i]) + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                    clk.mouseMove(img.MatchX(), img.MatchY());
                    clk.mouseClick();
                    clk.mouseMove(0, 0);
                    //決定ボタン
                    if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "kettei" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                        clk.mouseMove(img.MatchX(), img.MatchY());
                        clk.mouseClick();
                        clk.mouseMove(0, 0);
                        //艦隊選択ボタン
                        if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "ek" + String.valueOf(i+2) + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
                                img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "ek" + String.valueOf(i+2) + "_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                            clk.mouseMove(img.MatchX(), img.MatchY());
                            clk.mouseClick();
                            clk.mouseMove(0, 0);
                            //遠征開始ボタン
                            if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "e_start" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                                clk.mouseMove(img.MatchX(), img.MatchY());
                                clk.mouseClick();
                                clk.mouseMove(0, 0);
                                TimerUtil.getInstance().sleep(5000);//成功したら5秒待つ
                                //サブメニュー閉じさせる
                                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "e_cancel" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                                    clk.mouseMove(img.MatchX(), img.MatchY());
                                    clk.mouseClick();
                                    clk.mouseMove(0, 0);
                                }
                            }
                            //見つからなかったらキャンセル
                            else if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "e_cancel" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                                clk.mouseMove(img.MatchX(), img.MatchY());
                                clk.mouseClick();
                                clk.mouseMove(0, 0);
                            }
                        }
                    }
                }
            }
        }
        context.transitionBokou(context.getState());
        end();//Taskを削除
    }
    /* 未実装 */
    //第二艦隊から第四艦隊までのidを指定する.
    public void execute(Context context,int id,int id2,int id3) {
        /* 母港画面まで移動させる */
        if(!context.getState().equals(Bokou.getInstance()))
            context.transitionBokou(context.getState());
        /* 母港画面 : 処理内容 */
        //インスタンスの取得
        ImageMatcher img = ImageMatcher.getInstance();
        Display disp = Display.getInstance();
        Robot robo = Controller.getInstance();
        Click clk = Click.getInstance();
        //補給画面に移動
        context.transition(context.getState(),"syutsugeki");
        context.transition(context.getState(),"ensei");
    }
}
