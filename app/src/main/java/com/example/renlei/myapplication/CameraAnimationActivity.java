package com.example.renlei.myapplication;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-2-23
 * Time: 下午6:25
 * Email: lei.ren@renren-inc.com
 */
public class CameraAnimationActivity extends BaseActivity {
    private ImageView mImageView;
    private Button mStartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_animation);
        mImageView = (ImageView) findViewById(R.id.camera_animation_icon);
        mStartBtn = (Button) findViewById(R.id.camera_start_btn);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraAnimationActivity.this,"click",Toast.LENGTH_LONG).show();
            }
        });
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CameraAnimation animation = new CameraAnimation(mImageView.getWidth()/2f,mImageView.getHeight()/2f);
                animation.setInterpolatedTimeListener(new InterpolatedTimeListener() {
                    @Override
                    public void interpolatedTime(float interpolatedTime) {
                        if (interpolatedTime>5f){
                            mImageView.setImageDrawable(getResources().getDrawable(R.mipmap.default_head_image));
                        }
                    }
                });
                animation.setDuration(1000);
                animation.setFillAfter(true);
                mImageView.startAnimation(animation);
            }
        });
    }

    private class CameraAnimation extends Animation {
        private float centerX;
        private float centerY;
        private Camera mCamera;
        private InterpolatedTimeListener listener;

        public void setInterpolatedTimeListener(InterpolatedTimeListener listener) {
            this.listener = listener;
        }
        public CameraAnimation(float centerX, float centerY) {
            this.centerX = centerX;
            this.centerY = centerY;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (listener!=null){
                listener.interpolatedTime(interpolatedTime);
            }
            Matrix matrix = t.getMatrix();
            mCamera.save();
//            mCamera.translate(200 * interpolatedTime, -400 * interpolatedTime, 0);
//            mCamera.rotateX(100*interpolatedTime);
            mCamera.translate(0, 0, -100*interpolatedTime);
            mCamera.rotateY(80*interpolatedTime);
            mCamera.getMatrix(matrix);
            mCamera.restore();
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }



    }
    /** 动画进度监听器。 */
    public static interface InterpolatedTimeListener {
        public void interpolatedTime(float interpolatedTime);
    }

}
