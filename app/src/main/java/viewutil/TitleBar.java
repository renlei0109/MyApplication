package viewutil;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renlei.myapplication.R;
import util.Methods;

/**
 * Created by renlei
 * DATE: 15-12-30
 * Time: 下午4:24
 * Email: lei.ren@renren-inc.com
 */
public class TitleBar extends ViewGroup{
    private View mLeftView;
    private View mMiddleView;
    private View mRightView;
    private View mRootView;
    LayoutInflater mInflater;
    private Context mContext;
    private int titleHeight;
    DisplayMetrics metrics;
    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        metrics = context.getResources().getDisplayMetrics();

    }


    private void initTitlebar(){
        mRootView = mInflater.inflate(R.layout.title_bar, null);
        mLeftView = mRootView.findViewById(R.id.title_left);
        mMiddleView = mRootView.findViewById(R.id.title_middle);
        mRightView = mRootView.findViewById(R.id.title_right);
        titleHeight = Methods.dp2sp(50,mContext);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLeftView.layout(Methods.dp2sp(10,mContext),0,mLeftView.getMeasuredWidth(),titleHeight);
        mRightView.layout(metrics.widthPixels-mRightView.getMeasuredWidth(),0,metrics.widthPixels-Methods.dp2sp(10,mContext),titleHeight);
    }
}
