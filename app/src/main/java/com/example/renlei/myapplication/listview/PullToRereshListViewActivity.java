package com.example.renlei.myapplication.listview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renlei.myapplication.R;

import org.w3c.dom.Text;

public class PullToRereshListViewActivity extends AppCompatActivity {

    private RefreshListView pullListview;
    private String[] adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh2);
        pullListview = (RefreshListView)findViewById(R.id.pull_listview);
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
        pullListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullToRereshListViewActivity.this, "PullRefreshListViewonItemClick", Toast.LENGTH_SHORT).show();
                Log.d("PullRefreshListView", "onItemClick");
            }
        });
        pullListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullToRereshListViewActivity.this, "PullRefreshListViewonItemClick", Toast.LENGTH_SHORT).show();
                Log.d("PullRefreshListView", "onItemLongClick");
                return true;//在处理长按时，注意的细节是把onItemLongClick返回设置为true，否则长按是会执行setOnItemClickListener。
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pull_to_reresh_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MYAdapter extends BaseAdapter{
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
            return convertView;
        }

        class Holder {
            TextView tv;
            Button btn;
        }
    }
}
