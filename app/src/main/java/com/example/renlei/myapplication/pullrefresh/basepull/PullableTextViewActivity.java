package com.example.renlei.myapplication.pullrefresh.basepull;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.renlei.myapplication.R;


public class PullableTextViewActivity extends Activity  implements PullRefreshListener
{

	private  PullToRefreshLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_textview);
		layout = (PullToRefreshLayout)findViewById(R.id.refresh_view);
		layout.setmPUllListener(this);
//		((PullToRefreshLayout) findViewById(R.id.refresh_view));
	}

	@Override
	public void onRefresh() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				layout.onRefreshCompleted();
			}
		},1000);
	}

	@Override
	public void onLoadMore() {

	}
}
