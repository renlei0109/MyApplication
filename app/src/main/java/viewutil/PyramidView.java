package viewutil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by renlei
 * DATE: 15-12-24
 * Time: 下午7:33
 * Email: lei.ren@renren-inc.com
 * 金字塔
 */
public class PyramidView extends View{
    public PyramidView(Context context) {
//        super(context);
        this(context,null);
    }

    public PyramidView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context,null,-1);
    }

    public PyramidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
