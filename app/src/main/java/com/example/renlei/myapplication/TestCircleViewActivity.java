package com.example.renlei.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.titlebar.BaseActivity;

import viewutil.AudioRecordView;
import viewutil.CircleWaveView;
import viewutil.CircleWaveView2;

/**
 * Time 2017/2/22.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class TestCircleViewActivity extends BaseActivity {
//    CircleWaveView2 mCircleWaveView;
private AudioRecordView mAudioRecordView;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_waveview);
//        mCircleWaveView = (CircleWaveView2)findViewById(R.id.circleWaveView);
        mAudioRecordView = (AudioRecordView)findViewById(R.id.wave);
        button = (Button)findViewById(R.id.receive_wave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    mCircleWaveView.addCircle();
                mAudioRecordView.recievedAudio();
            }
        });
        findViewById(R.id.stop_wave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCircleWaveView.exitRecordStatus(true);
                mAudioRecordView.exitRecordStatus(true);
            }
        });
        findViewById(R.id.start_wave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCircleWaveView.enterRecordStatus();
                mAudioRecordView.enterRecordStatus();
            }
        });
    }
}
