package com.example.renlei.myapplication.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renlei
 * DATE: 16-4-18
 * Time: 上午11:31
 * Email: lei.ren@renren-inc.com
 */
public class TestRecycleViewActivty extends BaseActivity{
    @Bind(R.id.recycle_view)
    RecyclerView recyclerView;
    MyRecycleViewAdapter adapter;
    List<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_layout);
        ButterKnife.bind(this);
        init();
    }
    private void init(){
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
        adapter = new MyRecycleViewAdapter(TestRecycleViewActivty.this,mDatas);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View getRightView(Context context, ViewGroup parentView) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView addTV = new TextView(context);
        addTV.setText("add");
        linearLayout.addView(addTV);
        TextView delTV = new TextView(context);
        delTV.setText("remove");
        delTV.setPadding(20, 0, 20, 0);
        addTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData((int) (Math.random() * 10));
            }
        });
        delTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeData((int) (Math.random() * 10));
            }
        });
        linearLayout.addView(delTV);
        return linearLayout;
    }
}
