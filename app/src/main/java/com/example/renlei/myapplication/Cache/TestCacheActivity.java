package com.example.renlei.myapplication.Cache;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renlei
 * DATE: 16-5-10
 * Time: 下午5:00
 * Email: lei.ren@renren-inc.com
 */
public class TestCacheActivity extends BaseActivity {
    @Bind(R.id.save_in_jsoncache)
    Button saveJson;
    @Bind(R.id.get_from_jsoncache)
    Button getJson;
    @Bind(R.id.save_in_db)
    Button saveInDB;
    @Bind(R.id.get_from_db)
    Button getFromDB;
    @Bind(R.id.result_tv)
    TextView resultTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cache_layout);
        ButterKnife.bind(this);
        saveJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < 2000; i++) {
                            JSONObject object = new JSONObject();
                            try {
                                object.put("name", "renlei");
                                object.put("age", "18");
                                object.put("content", "lalala" + System.currentTimeMillis());
                                array.put(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        JsonCache.getInstance().saveIntoCache("ren", "lei", array.toString());
                    }
                }).start();

            }
        });
        getJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String result = JsonCache.getInstance().getFromCache("ren", "lei");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTV.setText(result);
                            }
                        });

                    }
                }).start();

            }
        });


        saveInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < 20; i++) {
                            JSONObject object = new JSONObject();
                            try {
                                object.put("name", "renlei");
                                object.put("age", "18");
                                object.put("content", "lalala" + System.currentTimeMillis());
                                array.put(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        JsonCache.getInstance().saveIntoCache("ren", "lei", array.toString());
                        SqliteCache sqliteCache = new SqliteCache();
                        sqliteCache.saveIntoSqlite(array.toString());
                    }
                }).start();
            }
        });

        getFromDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String result = new SqliteCache().getFromSqlite(1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTV.setText(result);
                            }
                        });

                    }
                }).start();
            }
        });
    }
}
