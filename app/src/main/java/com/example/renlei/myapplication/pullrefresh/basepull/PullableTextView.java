package com.example.renlei.myapplication.pullrefresh.basepull;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class PullableTextView extends TextView implements Pullable
{

	public PullableTextView(Context context)
	{
		super(context);
	}

	public PullableTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullRefresh() {
		return true;
	}

	@Override
	public boolean canPullLoadMore() {
		return false;
	}
}
