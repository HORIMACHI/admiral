package Main;

import Constant.Constants;
import Controll.Controller;
import Image.ImageMatcher;
import Controll.Display;
import State.Context;
import State.StateManager;
import Task.Task;
import Task.TaskQueue;
import Task.Kaitai;
import Task.Ensei;
import Task.Syutsugeki;
import Controll.TimerUtil;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import Task.Hokyu;
import static Constant.Constants.ONE_MINUTES;
import static Constant.Constants.P_FILEPATH;
import static Constant.Constants.RSRC_FILEPATH;
import static Constant.Constants.PATH_SYMBOL;

/**
 * Created by hiroto on 2015/02/22.
 */
public class Main {
    private static final int[] e_id = {5,21,38};
    private static int timerCounter = 0;
    //メインを始める前の初期化処理
    private static void init() {
        System.out.println(P_FILEPATH);
        //画面情報の取得
        Display.getInstance().setDesktopEnvironment();
        //編成画面がどこにあるか調べる
        ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "bokou" + PATH_SYMBOL + "hensei" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
        //それよりX座標が+200のところをAdjustとする
        ImageMatcher.getInstance().set_B_Adjust(ImageMatcher.getInstance().MatchX()+200,ImageMatcher.getInstance().MatchY());
    }
    /* 第二:2,第三:5,第四:6*/
    public static void main(String[] args) {
        init();

        //編成画面がどこにあるか調べる
        boolean hensei = ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "bokou" + PATH_SYMBOL + "hensei" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
        System.out.println(hensei);
        StateManager sm = new StateManager();
        sm.transitionBokou(sm.getState());

        //ぐるぐる動かす
        while(true) {
            //画面遷移して遠征が帰ってきたかどうかを確認する
            //母港じゃなければ母港に移動
            if(sm.getState().getClass().getSimpleName().equals("Bokou")) {
                sm.transition(sm.getState(), "hensei");
                sm.transitionBokou(sm.getState());
            }else {
                sm.transitionBokou(sm.getState());
            }
            //taskqueueのsort
            TaskQueue.sort();
            //taskの実行
            queueExecuter(sm);
            TimerUtil.getInstance().sleep(ONE_MINUTES);//1分
            if(timerCounter % 5==0) {
                TaskQueue.queue.addLast(new Syutsugeki());
                timerCounter=0;
            }
            timerCounter++;
        }
    }
    static void queueExecuter(StateManager sm) {
        if (TaskQueue.queue.size() != 0) {
            System.out.println("TaskQueueがNULLじゃない!");
            while (TaskQueue.queue.size() != 0) {
                System.out.println("実行");
                TaskQueue.queue.get(0).execute(sm);
                TaskQueue.sort();
            }
            sm.transitionBokou(sm.getState());
        }
        //母港に戻った時に遠征が帰ってきた時の条件式
        if(TaskQueue.queue.size()!=0)queueExecuter(sm);
    }
}