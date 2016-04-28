package com.example.renlei.myapplication;

import android.os.Bundle;
import android.util.Log;

import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-4-28
 * Time: 上午10:46
 * Email: lei.ren@renren-inc.com
 */
public class TestSaveInstanceStateActivity extends BaseActivity {
    private static final String TAG = "TestSaveInstanceStateActivity";
    private static final String SAVED_STRING = "saved_str";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        Log.d(TAG, "onCreate");
        if (savedInstanceState != null) {
            Log.d(TAG, "saveStri" + savedInstanceState.getString(SAVED_STRING));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        outState.putString(SAVED_STRING,"lalalala");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }
}
