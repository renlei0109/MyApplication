package com.example.renlei.myapplication.baseandroid.chapterthree;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/27
 * Time: 15:11
 */
public class VideoViewActivity extends BaseActivity {
    VideoView videoView;
    Button startBtn;
    MediaController mc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview_layout);
        videoView = (VideoView) findViewById(R.id.video_view);
        startBtn = (Button) findViewById(R.id.start_video_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVideoClick();
            }
        });
        mc = new MediaController(this);
    }
    private void onVideoClick(){
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (videoIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(videoIntent,2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2&&resultCode == Activity.RESULT_OK){
            Uri video = data.getData();
            videoView.setVideoURI(video);
            videoView.setMediaController(mc);
            videoView.start();////播放视频
            videoView.requestFocus();

        }
    }
}
