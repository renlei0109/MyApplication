package com.example.renlei.myapplication.pullrefresh.basepull;

import android.app.Activity;
import android.os.Bundle;

import com.example.renlei.myapplication.R;


public class PullableTextViewActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_textview);
//		((PullToRefreshLayout) findViewById(R.id.refresh_view));
	}
}
