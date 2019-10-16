package com.example.exp__drawlinesample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

class TestView extends View {

	public Canvas canvas;//画布
	public Paint p;//画笔
	public Paint e;//橡皮擦
	private Bitmap bitmap;//绘图
	int mode = 0;
	float x,y;
	int bgColor;

	public TestView(Context context) {
		super(context);
		initpaint();
	}

	public TestView(DrawLineSample drawLineSample,Context context, AttributeSet attrs) {
		super(context, attrs);
		initpaint();
	}


	public void setMode(int pan)
	{
		mode = pan;
	}

	public void initpaint()
	{
		bgColor = Color.WHITE;               //设置背景颜色
		bitmap = Bitmap.createBitmap(1200, 1800, Bitmap.Config.ARGB_8888);    //设置位图，线就画在位图上面，第一二个参数是位图宽和高
		canvas=new Canvas();
		canvas.setBitmap(bitmap);

		p = new Paint(Paint.DITHER_FLAG);
		p.setAntiAlias(true);                //设置抗锯齿，一般设为true
		p.setColor(Color.RED);              //设置线的颜色
		p.setStrokeCap(Paint.Cap.ROUND);     //设置线的类型
		p.setStrokeWidth(8);                //设置线的宽度

		e = new Paint();
		e.setAlpha(0);
		e.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		e.setAntiAlias(true);
		e.setDither(true);
		e.setStyle(Paint.Style.STROKE);
		e.setStrokeJoin(Paint.Join.ROUND);
		e.setStrokeWidth(8);
	}




	//触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_MOVE) {    //拖动屏幕
			if(mode==1)
				canvas.drawLine(x, y, event.getX(), event.getY(), e);
			else
				canvas.drawLine(x, y, event.getX(), event.getY(), p);   //画线，x，y是上次的坐标，event.getX(), event.getY()是当前坐标
			invalidate();
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {    //按下屏幕
			x = event.getX();
			y = event.getY();
			if(mode==1)
				canvas.drawPoint(x, y, e);                //画点
			else
				canvas.drawPoint(x, y, p);
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {    //松开屏幕

		}
		x = event.getX();   //记录坐标
		y = event.getY();
		return true;
	}

	@Override
	public void onDraw(Canvas c) {
		c.drawBitmap(bitmap, 0, 0, null);
	}
 }
