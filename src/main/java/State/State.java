package State;
import Controll.Click;
import Controll.Controller;
import Controll.Display;
import Image.ImageMatcher;
import Task.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;

import static Constant.Constants.P_FILEPATH;
import static Constant.Constants.RSRC_FILEPATH;

import Task.Hokyu;
import Task.Ensei;
/**
 * Created by hiroto on 2015/02/23.
 */
public interface State {
        public abstract Map<String,State> getTRMap();//遷移先リストを返す
        public abstract boolean transition(Context context,State state,String dest);//遷移成功時にtrueを返す
        public  default void mousePositionAdjuster(State state)  {
            //画像の一致に失敗しても問題なくさせる
            Click.getInstance().mouseMove(0,0);
            if(state.getClass().getSimpleName().equals("Bokou")) {
                if(!ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "bokou" + "\\" + "hensei" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()))) {
                    System.out.println("遠征から帰ってきました");
                    Click.getInstance().mouseMove(ImageMatcher.getInstance().get_B_AdjustX(), ImageMatcher.getInstance().get_B_AdjustY());
                    Click.getInstance().mouseClick(5000);
                    while(!ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "bokou" + "\\" + "hensei" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()))) {
                        Click.getInstance().mouseMove(ImageMatcher.getInstance().get_B_AdjustX(), ImageMatcher.getInstance().get_B_AdjustY());
                        Click.getInstance().mouseClick(200);
                        System.out.println("クリック連打");
                    }
                    /*また発見したらもう一度押し続ける*/
                    if(!ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "bokou" + "\\" + "hensei" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()))){
                        mousePositionAdjuster(state);
                    }
                    System.out.println("補給タスク生成");
                    //ここで補給タスクと遠征タスクを生成する.
                    TaskQueue.queue.add(new Hokyu());
                    /* 既存の遠征タスクを全て消して後ろに追加する */
                    for(int i=0;i < TaskQueue.queue.size();i++) {
                        if(TaskQueue.queue.get(i) instanceof Ensei)
                            TaskQueue.queue.remove(i);
                    }
                    TaskQueue.queue.add(new Ensei());
                }
                //Adjuster
                boolean match = ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "bokou" + "\\" + "hensei" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
                if (match) {
                    Click.getInstance().mouseMove(ImageMatcher.getInstance().get_B_AdjustX(), ImageMatcher.getInstance().get_B_AdjustY());
                    Click.getInstance().mouseClick();
                }
            }
        }
        public  default boolean transitionBokou(Context context,State state)  {
            if(state.getClass().getSimpleName().equals("Bokou")) {
                mousePositionAdjuster(state);
                return true;
            }
            System.out.println(Bokou.getInstance());
            boolean matcher = ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "" + "\\" + "HHKNK_common" + "\\" + "bokou" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
            if(matcher) {
                Click.getInstance().mouseMove(ImageMatcher.getInstance().MatchX(), ImageMatcher.getInstance().MatchY());
                Click.getInstance().mouseClick();
                context.setState(Bokou.getInstance());
                mousePositionAdjuster(context.getState());
                return true;
            }else {
                System.out.println("sippai");
                return false;
            }
        }
        //強制的に母港に移動させる.
        public default boolean transitionAjuster(Context context,State state) {
            if(state.getClass().getSimpleName().equals("Bokou")) {
                boolean match = ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "bokou" + "\\" + "kantai" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
                if (match) {
                    Click.getInstance().mouseMove(ImageMatcher.getInstance().MatchX(), ImageMatcher.getInstance().MatchY());
                    Click.getInstance().mouseClick();
                    return true;
                }
            } else {
                boolean match = ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + "HHKNK_common" + "\\" + "bokou" + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
                if (match) {
                    Click.getInstance().mouseMove(ImageMatcher.getInstance().MatchX(), ImageMatcher.getInstance().MatchY());
                    Click.getInstance().mouseClick();
                    context.setState(Bokou.getInstance());//母港画面に強制的に移動させる
                    return true;
                }
            }
            return false;
        }
}
