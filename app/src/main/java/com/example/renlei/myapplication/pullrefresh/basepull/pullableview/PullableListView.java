package com.example.renlei.myapplication.pullrefresh.basepull.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import com.example.renlei.myapplication.pullrefresh.basepull.Pullable;

/**
 * Created by renlei
 * DATE: 16-3-31
 * Time: 上午10:48
 * Email: lei.ren@renren-inc.com
 */
public class PullableListView  extends ListView implements Pullable {
    public PullableListView(Context context) {
        this(context, null);
    }

    public PullableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullRefresh() {
        if (getCount() == 0){//没有item的时候可以下拉
            return true;
        }else if (getFirstVisiblePosition()==0&&getChildAt(0).getTop()>=0){

            Log.d("PullableListView","getFirstVisiblePosition"+getFirstVisiblePosition()+"  getChildAt(0).getTop()"+getChildAt(0).getTop()+" getChildAt(0)"+getChildAt(0));
            return true;
        }
        return false;
    }

    @Override
    public boolean canPullLoadMore() {
        return false;
    }
}
