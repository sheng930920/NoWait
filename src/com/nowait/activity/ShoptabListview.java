package com.nowait.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;

public class ShoptabListview extends ListView {

	public ShoptabListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShoptabListview(Context context) {
		super(context);
	}

	public ShoptabListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// ���Զ���ؼ�ֻ����д��ListView��onMeasure������ʹ�䲻����ֹ�������ScrollViewǶ��ListViewҲ��ͬ���ĵ�������׸����
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
