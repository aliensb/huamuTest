package win.ccav.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Created by Administrator on 2017/11/29.
 */
public class StringUtil {
    public static boolean isBlack(String str){
        if(str==null||"".equals(str)||"".equals(str.trim())){
            return true;
        }
        else return false;
    }
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isNotEmpty(String str){
        if(str!=null && !"".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }
    public static boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            img = null;
        }
    }
    public static boolean isDouble(String str)
    {
        if(str.endsWith("f")){
            return false;
        }
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException ex){}
        return false;
    }
}
