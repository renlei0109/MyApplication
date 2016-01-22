package com.example.renlei.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-1-21
 * Time: 下午5:10
 * Email: lei.ren@renren-inc.com
 */
public class ViewStubAndIncludeTestActivity extends BaseActivity{
    private ViewStub mViewStub;
    private TextView mTextView;
    private int mFrom;
    public static final String FROM = "from";
    private View mStub1View;
    private View mStub2View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstub_include_activty);
        Intent intent = getIntent();
        if (intent!=null){
            mFrom = intent.getIntExtra(FROM,1);
        }
        initView();
    }
    private void initView(){
        mTextView = (TextView)findViewById(R.id.view_stub_textview);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTextView.getLayoutParams();
        if (mFrom == 1){
            mViewStub = (ViewStub)findViewById(R.id.view_stub1);
            mViewStub.inflate();
            params.setMargins(0,(int)getResources().getDimension(R.dimen.stub1_height),0,0);
            mTextView.setLayoutParams(params);
            mStub1View = findViewById(R.id.stub1_view);
            mStub1View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewStubAndIncludeTestActivity.this,"click stub1",Toast.LENGTH_LONG);
                    Log.d("ViewStubAndIncludeTestActivit y","click stub1");
                }
            });
        }else if (mFrom == 2){
            mViewStub = (ViewStub)findViewById(R.id.view_stub2);
            mViewStub.inflate();
            params.setMargins(0,(int)getResources().getDimension(R.dimen.stub2_height),0,0);
            mTextView.setLayoutParams(params);
            mStub2View = findViewById(R.id.stub2_view);
            mStub2View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewStubAndIncludeTestActivity.this,"click stub2",Toast.LENGTH_LONG);
                    Log.d("ViewStubAndIncludeTestActivity", "click stub2");
                }
            });

        }
    }
}
