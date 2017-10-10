package com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection.widget.MyExpandableListView;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.util.List;

/**
 * Time 2017/8/31.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class ScrollViewExpandableListview extends BaseActivity {

    MyExpandableListView listview;
    ImageView placeHoldIV;
    HomeSceneScrollView mScrollView;
    private static final String TAG = "ScrollViewExpandableListview";
    LinearLayout ll;
    View line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview_expandable_listview);
        listview = (MyExpandableListView) findViewById(R.id.expand_listview);
        placeHoldIV = (ImageView) findViewById(R.id.recommend_detail_place_hold);
        mScrollView = (HomeSceneScrollView) findViewById(R.id.scroll_view);
        line = findViewById(R.id.line);
        ll = (LinearLayout) findViewById(R.id.ll);
        Expanadapter expanadapter = new Expanadapter(this);
        listview.setAdapter(expanadapter);
        listview.setGroupIndicator(null);

        mScrollView.setListener(new HomeSceneScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int[] location = new int[2];
                line.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];

                int[] location2 = new int[2];
                line.getLocationInWindow(location2);
                int xwindow = location2[0];
                int ywindow = location2[1];


                Log.d(TAG,"scrollY： "+scrollY+"  linegetScrollY  "+line.getScrollY()+"getTranslationY "+line.getTranslationY()+"   getY"+line.getY()+"  getTop"+line.getTop()+" yScreen  "+y+"  ywindow "+ywindow);
//                float alpha = scrollY/600f;
//                placeHoldIV.setAlpha(alpha);
            }
        });
        initView();
    }


    private void initView(){
        line.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.d(TAG,"line.addOnLayoutChangeListener"+"   oldTop   "+oldTop+"   top  "+top);
            }
        });
        line.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] location = new int[2];
                line.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Log.d(TAG,"getViewTreeObserver"+"y"+y);
            }
        });
//        mScrollView.
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Log.d(TAG,"  mScrollViewgetViewTreeObserver"+"mScrollView.getScrollY()"+mScrollView.getScrollY());
//                Log.d(TAG,"scrollY： "+scrollY+"  linegetScrollY  "+line.getScrollY()+"getTranslationY "+line.getTranslationY()+"   getY"+line.getY()+"  getTop"+line.getTop()+" yScreen  "+y+"  ywindow "+ywindow);
                float alpha = mScrollView.getScrollY()/600f;
                placeHoldIV.setAlpha(alpha);
            }
        });

        mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG," onGlobalLayout getViewTreeObserver"+"mScrollView.getScrollY()"+mScrollView.getScrollY());


            }
        });


    }

    class Expanadapter extends BaseExpandableListAdapter {
        Context mContext;
        LayoutInflater mInflater;

        public Expanadapter(Context mContext) {
            this.mContext = mContext;
            mInflater = LayoutInflater.from(mContext);
        }

        RecycleData recycleData = new RecycleData();
        List<Pair<RecycleData.GroupData, List<RecycleData.ChildData>>> mData = recycleData.getData();

        @Override
        public int getGroupCount() {
            return mData.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mData.get(groupPosition).second.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mData.get(groupPosition).first;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mData.get(groupPosition).second.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return mData.get(groupPosition).second.get(childPosition).name.hashCode();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.recycle_drap_with_section_group_item, null);
            ParentViewHolder holder = new ParentViewHolder(convertView);
            holder.mTV.setText(mData.get(groupPosition).first.name);

           /* holder.mLL.removeAllViews();h bg
            Log.d("RecyclerAdapter","removeAllViews");

            for (int i = 0;i<1;i++){
                Log.d("RecyclerAdapter","addView"+mData.get(groupPosition).first.name);
                holder.mLL.addView(createView());
            }*/

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.recycle_drap_with_section_child_item, null);
            ChildViewHolder holder = new ChildViewHolder(convertView);
            holder.mTV.setText(mData.get(groupPosition).second.get(childPosition).name);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
            int[] location = new int[2];
            line.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];

            int[] location2 = new int[2];
            line.getLocationInWindow(location2);
            int xwindow = location2[0];
            int ywindow = location2[1];
            Log.d(TAG,"onGroupCollapsed："+mScrollView.getMeasuredHeight()+"  top: "+mScrollView.getTop()+"  getScrollY："+mScrollView.getScrollY()+"  ll:  "+ll.getMeasuredHeight() +"  linegetScrollY  "+line.getScrollY()+"getTranslationY "+line.getTranslationY()+"   getY"+line.getY()+"  getTop"+line.getTop()+" yScreen  "+y+"  ywindow "+ywindow  );
            mScrollView.scrollBy(0,1);
            Log.d(TAG,"scrollBy  onGroupCollapsed："+mScrollView.getMeasuredHeight()+"  top: "+mScrollView.getTop()+"  getScrollY："+mScrollView.getScrollY()+"  ll:  "+ll.getMeasuredHeight() +"  linegetScrollY  "+line.getScrollY()+"getTranslationY "+line.getTranslationY()+"   getY"+line.getY()+"  getTop"+line.getTop()+" yScreen  "+y+"  ywindow "+ywindow  );


        }



        @Override
        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
            int[] location = new int[2];
            line.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];

            int[] location2 = new int[2];
            line.getLocationInWindow(location2);
            int xwindow = location2[0];
            int ywindow = location2[1];
            Log.d(TAG,"onGroupExpanded："+mScrollView.getMeasuredHeight()+"  top："+mScrollView.getTop()+"  getScrollY："+mScrollView.getScrollY()+"  ll:  "+ll.getMeasuredHeight() +"  linegetScrollY  "+line.getScrollY()+"getTranslationY "+line.getTranslationY()+"   getY"+line.getY()+"  getTop"+line.getTop()+" yScreen  "+y+"  ywindow "+ywindow );

        }
    }

    class ChildViewHolder {
        private int stateFlag;
        private View rootView;
        public TextView mTV;

        public ChildViewHolder(View rootView) {
            this.rootView = rootView;
            mTV = (TextView) rootView.findViewById(R.id.recycle_view_child_tv);
        }
    }

    class ParentViewHolder {
        private int stateFlag;
        private View rootView;
        public TextView mTV;
        LinearLayout mLL;
        LinearLayout mParentLL;

        public ParentViewHolder(View rootView) {
            this.rootView = rootView;
            mTV = (TextView) rootView.findViewById(R.id.recycle_view_group_tv);
            mLL = (LinearLayout) rootView.findViewById(R.id.lite_scene_icon_ll);
            mParentLL = (LinearLayout) rootView.findViewById(R.id.parent_view);
        }

    }
}
