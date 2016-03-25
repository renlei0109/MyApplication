package com.example.renlei.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.chart.ActRotate;
import com.example.renlei.myapplication.chart.LineChartActivity;
import com.example.renlei.myapplication.chart.PieChartActivity;
import com.example.renlei.myapplication.listview.PullRefreshActivity;
import com.example.renlei.myapplication.listview.PullToRereshListViewActivity;
import com.example.renlei.myapplication.service.MyServiceClientActivty;
import com.example.renlei.myapplication.titlebar.TestTitleBarActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    private String TAG = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_start);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/SF_Light.otf");
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
        button = (Button)findViewById(R.id.test_round_progressbar_activty);
        button.setTypeface(typeface);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                startActivity(new Intent(this,PiechartActivityOld.class));
                break;
            case R.id.line_chart_activty:
                startActivity(new Intent(this, LineChartActivity.class));
                break;
            case R.id.test_titlebar_activty:
                startActivity(new Intent(this,TestTitleBarActivity.class));
                break;
            case R.id.test_viewstub1_activty:
                Intent intent = new Intent();
                intent.putExtra("from",1);
                intent.setClass(this,ViewStubAndIncludeTestActivity.class);
                startActivity(intent);
                break;
            case R.id.test_viewstub2_activty:
                Intent intent2 = new Intent();
                intent2.putExtra("from", 2);
                intent2.setClass(this, ViewStubAndIncludeTestActivity.class);
                startActivity(intent2);
                break;
            case R.id.test_surfaceview_activty:
                startActivity(new Intent(this,SurfaceViewTestActivity.class));
                break;
            case R.id.test_round_progressbar_activty:
                startActivity(new Intent(this,RoundProgressBarActivity.class));
                break;
            case R.id.test_piechart_activity_activty:
                startActivity(new Intent(this, PieChartActivity.class));
                break;
            case R.id.test_actrotate_activty:
                startActivity(new Intent(this, ActRotate.class));
                break;
            case R.id.test_camera_animation_activty:
                startActivity(new Intent(this,CameraAnimationActivity.class));
                break;
            case R.id.test_camera_myserviceclient_activty:
                startActivity(new Intent(this, MyServiceClientActivty.class));
                break;
            case R.id.test_pull_listview_activty:
                startActivity(new Intent(this, PullRefreshActivity.class));
                break;
        }

    }



}
