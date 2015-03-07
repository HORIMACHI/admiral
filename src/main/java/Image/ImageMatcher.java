package Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by hiroto on 2015/02/22.
 */
public class ImageMatcher {
    private static ImageMatcher instance = null;
    private ImageMatcher(){};
    private int matchx;//マッチした座標
    private int matchy;//マッチした座標
    private int bAdjustx;//母港のアジャストx
    private int bAdjusty;//母港のアジャストx

    public void set_B_Adjust(int x,int y){
        this.bAdjustx=x;
        this.bAdjusty=y;
    }

    public int get_B_AdjustX(){
        return bAdjustx;
    }
    public int get_B_AdjustY(){
        return bAdjusty;
    }

    public int MatchX(){
        return matchx;
    }
    public int MatchY(){
        return matchy;
    }
    public static ImageMatcher getInstance() {
        if (instance == null)
            instance = new ImageMatcher();
        return instance;
    }

    /**
     * ファイルの画像とスクリーンの画像を比較する
     * @param file ファイルパス
     * @param screen スクリーン画面
     * @return 比較結果
     */
    public boolean compareImg(File file,BufferedImage screen) {
        BufferedImage input=null;
        try {
            input = ImageIO.read(file);
        }catch(IOException io) {
            io.printStackTrace();
        }

//        //グレースケールにする
//
//        input = grayScale(input);
outputImg(input,"C:\\pixel\\input.png","png");
//        screen = grayScale(screen);
outputImg(screen,"C:\\pixel\\screen.png","png");
        for(int y =0;y < screen.getHeight();y++) {
            for(int x = 0;x < screen.getWidth();x++) {
                //左上が一致したらマッチング開始
                if(input.getRGB(0,0) == screen.getRGB(x,y)){
                    if(x + input.getWidth() >= screen.getWidth() || y + input.getHeight() >= screen.getHeight())continue;
                    if(templateMatch(input,screen,x,y) == true)return true;
                }
            }
        }
        return false;
    }
    //scx,scy
    private boolean templateMatch(BufferedImage input,BufferedImage screen,int scx,int scy) {
        for(int my = 0;my < input.getHeight() ;my++) {
            for(int mx = 0;mx < input.getWidth();mx++) {
                if(input.getRGB(mx,my) == screen.getRGB(mx+scx,my+scy));
                else return false;
            }
        }
        matchx = scx;
        matchy = scy;
        return true;
    }
    public boolean outputImg(BufferedImage image,String path,String format) {
        boolean flag = false;
        try{
            flag = ImageIO.write(image,format,new File(path));
        }catch(IOException io) {
            io.printStackTrace();
        }
        return flag;
    }
    private  BufferedImage grayScale(BufferedImage origin) {
        BufferedImage gray = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < origin.getWidth(); x++) {
            for (int y = 0; y < origin.getHeight(); y++) {
                int col = origin.getRGB(x, y);
                gray.setRGB(x, y, toGray(col));
            }
        }
        return gray;
    }
    /**
     * グレースケール変換（中間値法）
     */
    protected int toGray(int col)
    {
        Color c = new Color(col);
        int max = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
        int min = Math.min(c.getRed(), Math.min(c.getGreen(), c.getBlue()));
        int a = c.getAlpha();
        int v = (max+min)/2;

        c = new Color(v, v, v, a);
        return c.getRGB();
    }
}
