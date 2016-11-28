package com.example.renlei.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.renlei.myapplication.Cache.TestCacheActivity;
import com.example.renlei.myapplication.animation.AnimationMainActivity;
import com.example.renlei.myapplication.anr.TestANRActivity;
import com.example.renlei.myapplication.badge.TestBadge;
import com.example.renlei.myapplication.baseandroid.BaseAndroidActivity;
import com.example.renlei.myapplication.chart.ActRotate;
import com.example.renlei.myapplication.chart.LineChartActivity;
import com.example.renlei.myapplication.chart.PieChartActivity;
import com.example.renlei.myapplication.gcmemory.GCTest;
import com.example.renlei.myapplication.handler.TestAsyncTask;
import com.example.renlei.myapplication.handler.TestHandlerActvity;
import com.example.renlei.myapplication.imageload.TestImageLoadActivity;
import com.example.renlei.myapplication.popupwindow.PopupWindowMainActivity;
import com.example.renlei.myapplication.pullrefresh.basepull.PullableListViewActivity;
import com.example.renlei.myapplication.pullrefresh.basepull.PullableTextViewActivity;
import com.example.renlei.myapplication.pullrefresh.listview.PullRefreshActivity;
import com.example.renlei.myapplication.pullrefresh.listview.PullToRereshListViewActivity;
import com.example.renlei.myapplication.pullrefresh.listview.TestListVIewEmptyView;
import com.example.renlei.myapplication.recycleview.TestRecycleViewActivty;
import com.example.renlei.myapplication.rxjava.RXBus;
import com.example.renlei.myapplication.rxjava.RxJavaBaseActivity;
import com.example.renlei.myapplication.service.MyServiceClientActivty;
import com.example.renlei.myapplication.service.aidltest.MyAIDLServiceTestActivity;
import com.example.renlei.myapplication.thread.ThreadPoolExecutorTestActivity;
import com.example.renlei.myapplication.titlebar.TestTitleBarActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import viewutil.ChooseEmailDialog;
import viewutil.RoundImageView;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    private String TAG = "StartActivity";
    Subscription sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_start);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SF_Light.otf");
        findViewById(R.id.view_num).setOnClickListener(this);
        findViewById(R.id.line_pie_chart_view).setOnClickListener(this);
        findViewById(R.id.line_chart_activty).setOnClickListener(this);
        findViewById(R.id.test_titlebar_activty).setOnClickListener(this);
        findViewById(R.id.test_viewstub1_activty).setOnClickListener(this);
        findViewById(R.id.test_viewstub2_activty).setOnClickListener(this);
        findViewById(R.id.test_surfaceview_activty).setOnClickListener(this);
        findViewById(R.id.test_round_progressbar_activty).setOnClickListener(this);
        findViewById(R.id.test_piechart_activity_activty).setOnClickListener(this);
        findViewById(R.id.test_actrotate_activty).setOnClickListener(this);
        findViewById(R.id.test_camera_animation_activty).setOnClickListener(this);
        findViewById(R.id.test_camera_myserviceclient_activty).setOnClickListener(this);
        findViewById(R.id.test_pull_listview_activty).setOnClickListener(this);
        findViewById(R.id.test_pull_listview_activty2).setOnClickListener(this);
        findViewById(R.id.test_pull_text_activty).setOnClickListener(this);
        findViewById(R.id.test_ll_pull_listview_activty).setOnClickListener(this);
        findViewById(R.id.test_aidl_service).setOnClickListener(this);
        findViewById(R.id.test_thread_pool_executor).setOnClickListener(this);
        findViewById(R.id.test_gc_activty).setOnClickListener(this);
        findViewById(R.id.test_recycleview_activity).setOnClickListener(this);
        findViewById(R.id.test_saved_instance_state).setOnClickListener(this);
        findViewById(R.id.test_animation_activity).setOnClickListener(this);
        findViewById(R.id.test_handler_activity).setOnClickListener(this);
        findViewById(R.id.test_roundimageview_activity).setOnClickListener(this);
        findViewById(R.id.test_anr_activity).setOnClickListener(this);
        findViewById(R.id.test_cache_activity).setOnClickListener(this);
        findViewById(R.id.test_listviewempty_activity).setOnClickListener(this);
        findViewById(R.id.test_popupwindow_activity).setOnClickListener(this);
        findViewById(R.id.test_base_android_activity).setOnClickListener(this);
        findViewById(R.id.test_asynctask_activity).setOnClickListener(this);
        findViewById(R.id.test_imageload_activity).setOnClickListener(this);
        findViewById(R.id.test_dialog).setOnClickListener(this);
        findViewById(R.id.test_gif).setOnClickListener(this);
        findViewById(R.id.test_badge).setOnClickListener(this);
        findViewById(R.id.test_rxjava).setOnClickListener(this);
        button = (Button) findViewById(R.id.test_round_progressbar_activty);
        button.setTypeface(typeface);
        sb =  RXBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof  Intent){
                    Intent intent = (Intent)o;
                    String key = intent.getStringExtra("key");
                    Log.d("StartActivity----",key+"------------------");
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sb.isUnsubscribed())
            sb.unsubscribe();
        Log.e(TAG, "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_num:
                startActivity(new Intent(this, NumViewActivity.class));
                break;
            case R.id.line_pie_chart_view:
                startActivity(new Intent(this, PiechartActivityOld.class));
                break;
            case R.id.line_chart_activty:
                startActivity(new Intent(this, LineChartActivity.class));
                break;
            case R.id.test_titlebar_activty:
                startActivity(new Intent(this, TestTitleBarActivity.class));
                break;
            case R.id.test_viewstub1_activty:
                Intent intent = new Intent();
                intent.putExtra("from", 1);
                intent.setClass(this, ViewStubAndIncludeTestActivity.class);
                startActivity(intent);
                break;
            case R.id.test_viewstub2_activty:
                Intent intent2 = new Intent();
                intent2.putExtra("from", 2);
                intent2.setClass(this, ViewStubAndIncludeTestActivity.class);
                startActivity(intent2);
                break;
            case R.id.test_surfaceview_activty:
                startActivity(new Intent(this, SurfaceViewTestActivity.class));
                break;
            case R.id.test_round_progressbar_activty:
                startActivity(new Intent(this, RoundProgressBarActivity.class));
                break;
            case R.id.test_piechart_activity_activty:
                startActivity(new Intent(this, PieChartActivity.class));
                break;
            case R.id.test_actrotate_activty:
                startActivity(new Intent(this, ActRotate.class));
                break;
            case R.id.test_camera_animation_activty:
                startActivity(new Intent(this, CameraAnimationActivity.class));
                break;
            case R.id.test_camera_myserviceclient_activty:
                startActivity(new Intent(this, MyServiceClientActivty.class));
                break;
            case R.id.test_pull_listview_activty:
                startActivity(new Intent(this, PullRefreshActivity.class));
                break;
            case R.id.test_pull_listview_activty2:
                startActivity(new Intent(this, PullToRereshListViewActivity.class));
                break;
            case R.id.test_pull_text_activty:
                startActivity(new Intent(this, PullableTextViewActivity.class));
                break;
            case R.id.test_ll_pull_listview_activty:
                startActivity(new Intent(this, PullableListViewActivity.class));
                break;
            case R.id.test_gc_activty:
                startActivity(new Intent(this, GCTest.class));
                break;
            case R.id.test_aidl_service:
                startActivity(new Intent(this, MyAIDLServiceTestActivity.class));
                break;
            case R.id.test_thread_pool_executor:
                startActivity(new Intent(this, ThreadPoolExecutorTestActivity.class));
                break;
            case R.id.test_recycleview_activity:
                startActivity(new Intent(this, TestRecycleViewActivty.class));
                break;
            case R.id.test_saved_instance_state:
                startActivity(new Intent(this, TestSaveInstanceStateActivity.class));
                break;
            case R.id.test_animation_activity:
                startActivity(new Intent(this, AnimationMainActivity.class));
                break;
            case R.id.test_handler_activity:
                startActivity(new Intent(this, TestHandlerActvity.class));
                break;
            case R.id.test_roundimageview_activity:
                startActivity(new Intent(this, RoumdImageActivity.class));
                break;
            case R.id.test_anr_activity:
                startActivity(new Intent(this, TestANRActivity.class));
                break;
            case R.id.test_cache_activity:
                startActivity(new Intent(this, TestCacheActivity.class));
                break;
            case R.id.test_listviewempty_activity:

                startActivity(new Intent(this, TestListVIewEmptyView.class));
                break;
            case R.id.test_popupwindow_activity:
                startActivity(new Intent(this, PopupWindowMainActivity.class));
                break;
            case R.id.test_base_android_activity:
                startActivity(new Intent(this, BaseAndroidActivity.class));
                break;
            case R.id.test_asynctask_activity:
                startActivity(new Intent(this, TestAsyncTask.class));
                break;
            case R.id.test_imageload_activity:
                Intent intent1 = new Intent(this, TestImageLoadActivity.class);
                if (intent1.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent1);
                }
                break;
            case R.id.test_dialog:
                onDialogclick();
                break;
            case R.id.test_gif:
                startActivity(new Intent(this, TestGifViewActivity.class));
                break;
            case R.id.test_badge:
                startActivity(new Intent(this, TestBadge.class));
                break;
            case R.id.test_rxjava:
                startActivity(new Intent(this, RxJavaBaseActivity.class));
                break;
        }

    }

    private void onDialogclick() {
        ChooseEmailDialog.Builder builder = new ChooseEmailDialog.Builder(this);
        List<ChooseEmailDialog.EmailDialogInfo> infos = new ArrayList<>();
        ChooseEmailDialog.EmailDialogInfo info = new ChooseEmailDialog.EmailDialogInfo();
        info.description = "Use Gmail";
        infos.add(info);
        ChooseEmailDialog.EmailDialogInfo info2 = new ChooseEmailDialog.EmailDialogInfo();
        info2.description = "Use Chime Mail";
        info2.emailAdd = "renlei@chime.com";
        infos.add(info2);
        builder.setTitle("renlei").setInfos(infos, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("renlei","pos"+position);
            }
        });
        builder.create().show();
    }

}
