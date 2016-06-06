package com.example.renlei.myapplication.baseandroid;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/5
 * Time: 12:30
 */
public class StorageActivity extends BaseActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_layout);
        textView = (TextView) findViewById(R.id.storage_tv);
        setData();
    }

    private void setData(){
        StringBuffer sb = new StringBuffer();
        sb.append("Internal").append("\n");
        sb.append("getFilesDir(): ").append(getFilesDir().toString()).append("\n");
        sb.append("getCacheDir(): ").append(getCacheDir().toString()).append("\n");
        sb.append("getExternalFilesDir(Environment.DIRECTORY_PICTURES: ").append(getExternalFilesDir(Environment.DIRECTORY_PICTURES)).append("\n");
        sb.append("getExternalCacheDir(): ").append(getExternalCacheDir().toString()).append("\n");
        sb.append("Environment.getExternalStorageDirectory(): ").append(Environment.getExternalStorageDirectory().toString()).append("\n");
        sb.append("Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES: ").append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)).append("\n");
        sb.append("Environment.getExternalStorageDirectory().getFreeSpace(): "+Environment.getExternalStorageDirectory().getFreeSpace()).append("\n");
        sb.append("Environment.getExternalStorageDirectory().getTotalSpace(): "+Environment.getExternalStorageDirectory().getTotalSpace()).append("\n");
        textView.setText(sb);
    }
}
