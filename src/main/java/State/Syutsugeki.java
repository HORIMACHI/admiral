package State;

import Controll.Click;
import Controll.Controller;
import Controll.Display;
import Image.ImageMatcher;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static Constant.Constants.P_FILEPATH;
import static Constant.Constants.RSRC_FILEPATH;
import static Constant.Constants.PATH_SYMBOL;

/**
 * Created by hiroto on 2015/02/23.
 */
public class Syutsugeki implements State{
    private static final Map<String,State> TR_MAP;
    static {
        Map<String,State> map = new HashMap<String,State>();
        map.put("bokou", Bokou.getInstance());
        map.put("hensei", Hensei.getInstance());
        map.put("hokyu", Hokyu.getInstance());
        map.put("kaiso", Kaiso.getInstance());
        map.put("nyukyo", Nyukyo.getInstance());
        map.put("ensei", Ensei.getInstance());
        TR_MAP = Collections.unmodifiableMap(map);
    }

    private Syutsugeki(){};
    private static Syutsugeki instance = null;
    public static Syutsugeki getInstance() {
        if (instance == null) {
            instance = new Syutsugeki();
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

        System.out.println(RSRC_FILEPATH + this.getClass().getSimpleName().toLowerCase() + PATH_SYMBOL + dest + ".png");
        boolean matcher = ImageMatcher.getInstance().compareImg(new File(RSRC_FILEPATH + this.getClass().getSimpleName().toLowerCase() + PATH_SYMBOL + dest + ".png"), Controller.getInstance().createScreenCapture(Display.getInstance().getDesktop()));
        if(matcher) {
            Click.getInstance().mouseMove(ImageMatcher.getInstance().MatchX(), ImageMatcher.getInstance().MatchY());
            Click.getInstance().mouseClick();
            context.setState(TR_MAP.get(dest));
            mousePositionAdjuster(context.getState());
            return true;
        }else {
            this.transition(context,state,dest);
            System.out.println("sippai");
            return false;
        }
    }
}
