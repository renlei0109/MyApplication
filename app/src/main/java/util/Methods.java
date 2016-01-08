package util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by renlei
 * DATE: 15-12-30
 * Time: 下午4:25
 * Email: lei.ren@renren-inc.com
 */
public class Methods {
    public static int dp2sp(int dip,Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int)(dip*metrics.density+0.5);
    }

}
