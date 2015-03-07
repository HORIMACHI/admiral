package State;

import Controll.Click;
import Controll.Controller;
import Controll.Display;
import Image.ImageMatcher;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Constant.Constants.P_FILEPATH;
import static Constant.Constants.RSRC_FILEPATH;

/**
 * Created by hiroto on 2015/02/23.
 */
public class Ensei implements State{
    private static final Map<String,State> TR_MAP;
    static {
        Map<String,State> map = new HashMap<String,State>();
        map.put("bokou", Bokou.getInstance());
        map.put("hensei", Hensei.getInstance());
        map.put("hokyu", Hokyu.getInstance());
        map.put("kaiso", Kaiso.getInstance());
        map.put("kousyo", Kousyo.getInstance());
        map.put("nyukyo", Nyukyo.getInstance());
        TR_MAP = Collections.unmodifiableMap(map);
    }

    private Ensei(){};
    private static Ensei instance = null;
    public static Ensei getInstance() {
        if (instance == null) {
            instance = new Ensei();
        }
        return instance;
    }

    @Override
    public Map<String,State> getTRMap() {
        return TR_MAP;
    }

    @Override
    public boolean transition(Context context,State state,String dest) {
        if(!TR_MAP.containsKey(dest)) return false;//失敗
        if(dest.equals("bokou"))  {
            if(this.transitionBokou(context,state))
                return true;
            else
                return false;
        }

        System.out.println(RSRC_FILEPATH + this.getClass().getSimpleName() + "\\" + dest + ".png");
        boolean matcher = ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + this.getClass().getSimpleName() + "\\" + dest + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
        if(matcher) {
            Click.getInstance().mouseMove(ImageMatcher.getInstance().MatchX(), ImageMatcher.getInstance().MatchY());
            Click.getInstance().mouseClick();
            context.setState(TR_MAP.get(dest));
            mousePositionAdjuster(context.getState());
            return true;
        }else {
            System.out.println("sippai");
            return false;
        }
    }
}