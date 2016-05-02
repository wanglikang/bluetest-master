package com.myapp.bluetest.blue.Seekbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsSeekBar;

public class mySeekbar extends AbsSeekBar {
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public mySeekbar(Context context) {
		super(context);
	}

	public mySeekbar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

}
