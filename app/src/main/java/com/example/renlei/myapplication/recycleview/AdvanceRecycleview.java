package com.example.renlei.myapplication.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection.RecycleviewDrapWithSection;

public class AdvanceRecycleview extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_recycleview);
        findViewById(R.id.expand_with_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvanceRecycleview.this, RecycleviewDrapWithSection.class);
                startActivity(intent);
            }
        });
    }

}
