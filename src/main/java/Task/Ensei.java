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
        //遠征idを配列として決定
        int[] ids = {2, 5, 6};
        //鎮守府海域
        if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id1_8" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
                img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id1_8_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
            clk.mouseMove(img.MatchX(), img.MatchY());
            clk.mouseClick();
            clk.mouseMove(0, 0);
            for (int i = 0; i < 3; i++) {
                System.out.println("id選択");
                //遠征のID選択
                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "eid" + String.valueOf(ids[i]) + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
                    System.out.println(String.valueOf(ids[i]) + ": マッチ!");
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
                                TimerUtil.getInstance().sleep(10000);//成功したら10秒待つ
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

    @Override
    public int getPriority() {
        return 9;
    }
    //第二艦隊から第四艦隊までのidを指定する.

//    public void execute(Context context) {
//        //遠征id配列を生成
//        final int[] ids = {5,21,38}; //id
//
//        // 母港画面まで移動させる
//        if(!context.getState().equals(Bokou.getInstance()))
//            context.transitionBokou(context.getState());
//        // 母港画面 : 処理内容
//        //インスタンスの取得
//        ImageMatcher img = ImageMatcher.getInstance();
//        Display disp = Display.getInstance();
//        Robot robo = Controller.getInstance();
//        Click clk = Click.getInstance();
//        //補給画面に移動
//        context.transition(context.getState(),"syutsugeki");
//        context.transition(context.getState(),"ensei");
//
//        //鎮守府海域
//        for (int i = 0; i < 3; i++) {
//            if(1 <= ids[i] && ids[i] <= 8) {
//                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id1_8" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
//                        img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id1_8_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                    clk.mouseMove(img.MatchX(), img.MatchY());
//                    clk.mouseClick();
//                    clk.mouseMove(0, 0);
//                }
//            }
//            else if(9 <= ids[i] && ids[i] <= 16) {
//                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id9_16" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
//                        img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id9_16_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                    clk.mouseMove(img.MatchX(), img.MatchY());
//                    clk.mouseClick();
//                    clk.mouseMove(0, 0);
//                }
//            }
//            else if(17 <= ids[i] && ids[i] <= 24) {
//                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id17_24" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
//                        img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id17_24_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                    clk.mouseMove(img.MatchX(), img.MatchY());
//                    clk.mouseClick();
//                    clk.mouseMove(0, 0);
//                }
//            }
//            else if(25 <= ids[i] && ids[i] <= 32) {
//                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id25_32" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
//                        img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id25_32_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                    clk.mouseMove(img.MatchX(), img.MatchY());
//                    clk.mouseClick();
//                    clk.mouseMove(0, 0);
//                }
//            }
//            else if(33 <= ids[i] && ids[i] <= 40) {
//                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id33_40" + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
//                        img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "id33_40_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                    clk.mouseMove(img.MatchX(), img.MatchY());
//                    clk.mouseClick();
//                    clk.mouseMove(0, 0);
//                }
//            }
//            System.out.println("id選択");
//                //遠征のID選択
//                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "eid" + String.valueOf(ids[i]) + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                    System.out.println(String.valueOf(ids[i]) + ": マッチ!");
//                    clk.mouseMove(img.MatchX(), img.MatchY());
//                    clk.mouseClick();
//                    clk.mouseMove(0, 0);
//                    //決定ボタン
//                    if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "kettei" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                        clk.mouseMove(img.MatchX(), img.MatchY());
//                        clk.mouseClick();
//                        clk.mouseMove(0, 0);
//                        //艦隊選択ボタン
//                        if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "ek" + String.valueOf(i+2) + ".png"), robo.createScreenCapture(disp.getDesktop())) ||
//                                img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "ek" + String.valueOf(i+2) + "_e" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                            clk.mouseMove(img.MatchX(), img.MatchY());
//                            clk.mouseClick();
//                            clk.mouseMove(0, 0);
//                            //遠征開始ボタン
//                            if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "e_start" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                                clk.mouseMove(img.MatchX(), img.MatchY());
//                                clk.mouseClick();
//                                clk.mouseMove(0, 0);
//                                TimerUtil.getInstance().sleep(10000);//成功したら10秒待つ
//                                //サブメニュー閉じさせる
//                                if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "e_cancel" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                                    clk.mouseMove(img.MatchX(), img.MatchY());
//                                    clk.mouseClick();
//                                    clk.mouseMove(0, 0);
//                                }
//                            }
//                            //見つからなかったらキャンセル
//                            else if (img.compareImg(new File(RSRC_FILEPATH + "ensei" + PATH_SYMBOL + "e_cancel" + ".png"), robo.createScreenCapture(disp.getDesktop()))) {
//                                clk.mouseMove(img.MatchX(), img.MatchY());
//                                clk.mouseClick();
//                                clk.mouseMove(0, 0);
//                            }
//                        }
//                    }
//                }
//            }
//        context.transitionBokou(context.getState());
//        end();//Taskを削除
//    }
}
