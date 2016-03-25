package com.example.renlei.myapplication.listview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-1-26
 * Time: 下午5:03
 * Email: lei.ren@renren-inc.com
 */
public class PullRefreshActivity extends BaseActivity implements PullRefreshListView.IPullRefreshListener{
    private PullRefreshListView pullListview;
    private String[] adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        pullListview = (PullRefreshListView)findViewById(R.id.pull_listview);
        pullListview.setPullRefreshListener(this);
        adapterData = new String[] { "Afghanistan", "Albania", "Algeria",
                "American Samoa", "Andorra", "Angola", "Anguilla",
                "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
                "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
                "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
                "Benin", "Bermuda", "Bhutan", "Bolivia",
                "Bosnia and Herzegovina", "Botswana", "Bouvet Island" };
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1,
                adapterData);

        MYAdapter adapter = new MYAdapter(adapterData,this);

        pullListview.setAdapter(adapter);
//        pullListview.setAdapter(arrayAdapter);
        pullListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullRefreshActivity.this, "PullRefreshListViewonItemClick", Toast.LENGTH_SHORT).show();
                Log.d("PullRefreshListView", "onItemClick");
            }
        });
        pullListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullRefreshActivity.this, "PullRefreshListViewonItemClick", Toast.LENGTH_SHORT).show();
                Log.d("PullRefreshListView", "onItemLongClick");
                return true;//在处理长按时，注意的细节是把onItemLongClick返回设置为true，否则长按是会执行setOnItemClickListener。
            }
        });


    }
    private class MYAdapter extends BaseAdapter {
        private String []data;
        private Context mContext;

        public MYAdapter(String[] data, Context mContext) {
            this.data = data;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
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
            holder.tv.setText(data[position]);
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
           }
       },2000);
    }

    @Override
    public void onLoadMore() {

    }
}
