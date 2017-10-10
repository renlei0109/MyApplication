package com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection;

import android.graphics.Color;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection.widget.FullyLinearLayoutManager;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;

public class RecycleviewDrapWithSection extends AppCompatActivity implements RecyclerViewExpandableItemManager.OnGroupCollapseListener ,RecyclerViewExpandableItemManager.OnGroupExpandListener{
    private static final String TAG = "RecycleviewDrapWithSection";
    RecyclerView mRecycleView;
    RecycleAdapter mAdapter;
    RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    RecyclerViewDragDropManager recyclerViewDragDropManager;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;
    RecyclerView.Adapter mWrapAdapter;
    HomeSceneScrollView mScrollview;
    LinearLayout linearLayout ;
    private FullyLinearLayoutManager mLayoutManager;
    TextView mTitle;
    View mTitleBg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_drap_with_section);
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview_drag_with_section);
        mScrollview = (HomeSceneScrollView) findViewById(R.id.scroll_view);
        mLayoutManager = new FullyLinearLayoutManager(this);
        linearLayout = (LinearLayout) findViewById(R.id.ll);
        mTitle = (TextView) findViewById(R.id.module_a_3_return_title);
        mTitleBg = findViewById(R.id.title_bg);






        mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        mRecyclerViewTouchActionGuardManager.setEnabled(true);


        recyclerViewDragDropManager = new RecyclerViewDragDropManager();
        recyclerViewDragDropManager.setInitiateOnLongPress(true);
        recyclerViewDragDropManager.setDraggingItemShadowDrawable((NinePatchDrawable) ContextCompat.getDrawable(this, R.drawable.material_shadow_z3));
        recyclerViewDragDropManager.setInitiateOnMove(false);
        recyclerViewDragDropManager.setLongPressTimeout((int) (ViewConfiguration.getLongPressTimeout() * 1.5f));
        recyclerViewDragDropManager.setCheckCanDropEnabled(true);


        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(savedInstanceState);
        recyclerViewExpandableItemManager.setOnGroupCollapseListener(this);
        recyclerViewExpandableItemManager.setOnGroupExpandListener(this);

        mAdapter = new RecycleAdapter(this);
        mWrapAdapter = recyclerViewExpandableItemManager.createWrappedAdapter(mAdapter);
        mWrapAdapter = recyclerViewDragDropManager.createWrappedAdapter(mWrapAdapter);
        /*mAdapter = new RecycleAdapter(this);
        mWrapAdapter = recyclerViewDragDropManager.createWrappedAdapter(mAdapter);
        mWrapAdapter = recyclerViewExpandableItemManager.createWrappedAdapter(mWrapAdapter);*/

        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mWrapAdapter);
        mRecycleView.setHasFixedSize(true);


        mRecyclerViewTouchActionGuardManager.attachRecyclerView(mRecycleView);
        recyclerViewDragDropManager.attachRecyclerView(mRecycleView);
        recyclerViewExpandableItemManager.attachRecyclerView(mRecycleView);
//        recyclerViewExpandableItemManager.expandAll();

        mScrollview.setListener(new HomeSceneScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                /*float alpha =(1f- scrollY/300f);
                Log.d(TAG,"alpha"+alpha);*/

//                mTitle.setAlpha(getAlpha(scrollY));
                float alpha = getAlpha(scrollY);
                if (alpha>0.8f){
                    mTitle.setTextColor(Color.BLACK);
                }else {
                    mTitle.setTextColor(Color.WHITE);
                }
                mTitleBg.setAlpha(scrollY/400f);

            }
        });
    }

    private float getAlpha(int scrollY){
        /*float alpha =(1f- scrollY/300f);

        Log.d(TAG,"alpha"+alpha);
        float result = Math.abs(alpha)>0.3?Math.abs(alpha):0.3f;
        Log.d(TAG,"result"+result);*/
        return scrollY/400f;
    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {
/*        Log.d("RecycleAdapter","onGroupCollapse");
        Log.d("RecycleAdapter",""+mScrollview.getMeasuredHeight()+"recycler"+mRecycleView.getMeasuredHeight()+"   ll::::"+linearLayout.getMeasuredHeight());
        mScrollview.measure(mScrollview.getMeasuredWidth(),linearLayout.getMeasuredHeight());
        Log.d("RecycleAdapter","*******"+mScrollview.getMeasuredHeight()+"recycler"+mRecycleView.getMeasuredHeight()+"   ll:::"+linearLayout.getMeasuredHeight());
        mScrollview.invalidate();
        mScrollview.requestLayout();*/
    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {
        /*Log.d("RecycleAdapter","onGroupExpand");
        Log.d("RecycleAdapter",""+mScrollview.getMeasuredHeight()+"recycler"+mRecycleView.getMeasuredHeight()+"   ll:::"+linearLayout.getMeasuredHeight());
        mScrollview.measure(mScrollview.getMeasuredWidth(),linearLayout.getMeasuredHeight());
        Log.d("RecycleAdapter","*******"+mScrollview.getMeasuredHeight()+"recycler"+mRecycleView.getMeasuredHeight()+"   ll:::"+linearLayout.getMeasuredHeight());
        mScrollview.invalidate();
        mScrollview.requestLayout();*/
    }
}
