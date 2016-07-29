package com.example.renlei.myapplication.handler;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by  renlei
 * DATE: 16/6/21
 * Time: 17:45
 */
public class TestAsyncTask extends BaseActivity {
    @Bind(R.id.text1)
    TextView tv1;
    @Bind(R.id.text2)
    TextView tv2;
    @Bind(R.id.text3)
    TextView tv3;
    @Bind(R.id.start_btn)
    Button startBtn;
    MyAsyncTask1 task1 = new MyAsyncTask1();
    MyAsyncTask2 task2 = new MyAsyncTask2();
    MyAsyncTask3 task3 = new MyAsyncTask3();
    private ArrayMap<String, String> arrayMap;
    private SparseArray<String> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_asynctask);
        ButterKnife.bind(this);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new MyAsyncTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new MyAsyncTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,100);
//                new MyAsyncTask3().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

    }

    class MyAsyncTask1 extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... params) {
            for (int i = 0; i < 20; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "renlei";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tv1.setText(values[0] + "" + "");
        }


    }

    class MyAsyncTask2 extends MyAsyncTask<Integer, Integer, SparseArray<String>> {
        @Override
        protected SparseArray<String> doInBackground(Integer... params) {
            sparseArray = new SparseArray<>();


            for (int i = params[0]+0; i < 20+params[0]; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sparseArray.put(i,"sparsearray"+i);
            }
            return sparseArray;
        }

        @Override
        protected void onPostExecute(SparseArray<String> stringSparseArray) {
            tv2.setText(stringSparseArray.get(0));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv2.setText("test renlei myasyntask");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tv2.setText(values[0] + "");
        }
    }



    class MyAsyncTask3 extends AsyncTask<Void, Integer, ArrayMap<String, String>> {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected ArrayMap<String, String> doInBackground(Void... params) {
            arrayMap = new android.util.ArrayMap<>();
            for (int i = 0; i < 20; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                arrayMap.put("arraymap","renlei"+i);
            }
            return arrayMap;
        }
        @TargetApi(19)
        @Override
        protected void onPostExecute(ArrayMap<String, String> stringStringArrayMap) {
//            super.onPostExecute(stringStringArrayMap);
            tv3.setText(arrayMap.get(arrayMap.size()-1));

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tv3.setText(values[0] + "");
        }
    }

}
