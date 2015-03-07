package Constant;
import java.io.File;
/**
 * Created by horiba on 2015/02/24.
 */
public final class Constants {
    private Constants(){}
    // プロジェクトのファイルパス:定数
    public static final String P_FILEPATH = new File(".").getAbsoluteFile().getParent();
    public static final String RSRC_FILEPATH = new File(".").getAbsoluteFile().getParent() + "\\src\\main\\resources\\img\\";
    public static final long ONE_SECONDS = 1000 * 1;
    public static final long ONE_MINUTES = 1000 * 1 * 60;
    public static final long ONE_HOUR = 1000 * 1 * 60 * 60;
}
