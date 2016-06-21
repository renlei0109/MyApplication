package com.example.renlei.myapplication.anr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renlei
 * DATE: 16-5-6
 * Time: 下午3:13
 * Email: lei.ren@renren-inc.com
 */
public class TestANRActivity extends BaseActivity {
    @Bind(R.id.start_btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        ButterKnife.bind(this);
       /* StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File file = new File(Environment.getExternalStorageDirectory()+"/test.txt");
                try {
                    FileOutputStream fos = new FileOutputStream(file,true);
                    byte [] buffer  = new byte[1024];
                    int n =0;

                    while (n<10000000){
                        n++;
                        fos.write("jfdsl".getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                for (long i = 0l; i < 9999l; i++) {
                    i++;
                }
            }
        });
    }
}
