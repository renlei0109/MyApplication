package com.example.renlei.myapplication.pullrefresh.listview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renlei
 * DATE: 16-1-26
 * Time: 下午5:03
 * Email: lei.ren@renren-inc.com
 */
public class TestListVIewEmptyView extends BaseActivity implements PullRefreshListView.IPullRefreshListener{
    private PullRefreshListView pullListview;
    private List<Integer>mDataList;
    private int count = 0;
    MYAdapter adapter;
    private View emptyView;
    private LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        mInflater = LayoutInflater.from(this);
        emptyView = mInflater.inflate(R.layout.activities_empty_layout,null);



        pullListview = (PullRefreshListView)findViewById(R.id.pull_listview);

        ((ViewGroup)pullListview.getParent()).addView(emptyView);
        pullListview.setEmptyView(emptyView);
//        pullListview.addFooterView(emptyView);
        pullListview.setPullRefreshListener(this);
        mDataList = new ArrayList<>();
        /*for (;count<20;count++){
            mDataList.add(count);
        }*/
        adapter = new MYAdapter(mDataList,this);

        pullListview.setAdapter(adapter);
//        pullListview.setAdapter(arrayAdapter);
        pullListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestListVIewEmptyView.this, "PullRefreshListViewonItemClick", Toast.LENGTH_SHORT).show();
                Log.d("PullRefreshListView", "onItemClick");
            }
        });
        pullListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestListVIewEmptyView.this, "PullRefreshListViewonItemClick", Toast.LENGTH_SHORT).show();
                Log.d("PullRefreshListView", "onItemLongClick");
                return true;//在处理长按时，注意的细节是把onItemLongClick返回设置为true，否则长按是会执行setOnItemClickListener。
            }
        });
    }
    private class MYAdapter extends BaseAdapter {
        private List<Integer>data;
        private Context mContext;

        public MYAdapter(List<Integer> data, Context mContext) {
            this.data = new ArrayList<>();
            this.data.addAll(data);
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            Holder holder ;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.pull_listview_item, null);
                holder = new Holder();
                holder.btn = (Button) convertView.findViewById(R.id.pull_lv_btn);
                holder.tv = (TextView) convertView.findViewById(R.id.pull_lv_tv);
                convertView.setTag(holder);
            }else {
                holder = (Holder)convertView.getTag();
            }
            holder.tv.setText(data.get(position)+"");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("PullRefreshListView", "onClick");
                }
            });
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("PullRefreshListView", "onLongClick");
                    return true;
                }
            });
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("PullRefreshListView", "btn onClick");
                }
            });
            return convertView;
        }

        public void addData(List<Integer>data){
            this.data.addAll(data);
            notifyDataSetChanged();
        }
        public void setData(List<Integer>data){
            if (this.data!=null){
                this.data.clear();
                this.data.addAll(data);
            }else {
                this.data = new ArrayList<>();
                this.data.addAll(data);
            }
            notifyDataSetChanged();
        }

        class Holder {
            TextView tv;
            Button btn;
        }
    }

    @Override
    public void onRefresh() {
        Log.d("PullRefreshListView", "onRefresh");

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               pullListview.onRefreshComplete();
               count = 0;
               mDataList = new ArrayList<Integer>();
               for (;count<20;count++){
                   mDataList.add(count);
               }
               adapter.setData(mDataList);
           }
       },2000);
    }

    @Override
    public void onLoadMore() {
        Log.d("PullRefreshListView", "onLoadMore");
        List<Integer>list = new ArrayList<>();
        for (int i = count;i<count+20;i++){
            list.add(i);
        }
        count+=20;
        if (count>=200){///模拟200以后就没有数据了
            pullListview.hideLoadMore();
        }
        adapter.addData(list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullListview.onLoadMoreCompleted();
            }
        },1000);
    }
}
