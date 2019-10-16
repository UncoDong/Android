package com.example.exp__drawlinesample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Vector;
import java.util.concurrent.Future;

public class PaintView extends View {
    public Canvas canvas;//画布
    public Paint p;//画笔
    public Paint e;//橡皮擦
    private Bitmap bitmap;//绘图
    int mode = 1;
    //float x,y;
    int bgColor;

    //历史记录
    Vector XHistory = new Vector();
    Vector YHistory = new Vector();
    Vector THistory = new Vector();
    //未来记录
    Vector XFuture = new Vector();
    Vector YFuture = new Vector();
    Vector TFuture = new Vector();

    Vector X = new Vector();
    Vector Y = new Vector();


    public PaintView(Context context) {
        super(context);
        initpaint();
    }
    public PaintView(Context context, AttributeSet attrs)     //Constructor that is called when inflating a view from XML
    {
        super(context,attrs);
        initpaint();

    }
    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initpaint();
    }

    //改变画布
    public  void setMode(int pan)
    {
        mode = pan;
    }

    //清空屏幕
    public void clean(int pan) {
        if(pan==1)
        {XFuture.clear();
        YFuture.clear();
        TFuture.clear();
        XHistory.clear();
        YHistory.clear();
        THistory.clear();
        X.clear();
        Y.clear();}

        System.out.println("清空画布");
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }


    public void initpaint()
    {
        bgColor = Color.WHITE;               //设置背景颜色
        bitmap = Bitmap.createBitmap(1200, 1800, Bitmap.Config.ARGB_8888);    //设置位图，线就画在位图上面，第一二个参数是位图宽和高
        canvas=new Canvas();
        canvas.setBitmap(bitmap);
        //绘制背景
        p = new Paint(Paint.DITHER_FLAG);
        p.setAntiAlias(true);                //设置抗锯齿，一般设为true
        p.setColor(Color.BLUE);              //设置线的颜色

        p.setStrokeCap(Paint.Cap.ROUND);     //设置线的类型
        p.setStrokeWidth(4.0f);                //设置线的宽度

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
            System.out.println("滑动屏幕");

            DrawByArry(mode);
            invalidate();
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {    //按下屏幕
            XFuture.clear();
            YFuture.clear();
            TFuture.clear();
            X.clear();
            Y.clear();
            THistory.add(mode);
            System.out.println("摁下屏幕");
            System.out.println("摁下"+XHistory);
            invalidate();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {    //松开屏幕
            System.out.println("松开屏幕");

            XHistory.add(X.clone());
            YHistory.add(Y.clone());
            System.out.println("松开"+XHistory);
            System.out.println(XHistory.get(0).hashCode());
            System.out.println(X.hashCode());
            X.clear();
            Y.clear();

            invalidate();
        }
        X.add(event.getX());
        Y.add(event.getY());

        return true;
    }

    //后撤
    public void BackMove()
    {
        System.out.println("上一步");
        if(XHistory.size()>0)
        {
            int index  = XHistory.size()-1;
            X = (Vector)XHistory.get(index);
            Y = (Vector)YHistory.get(index);

            int a = (int)THistory.get(index);
            TFuture.add(a);
            THistory.remove(index);

            XFuture.add(X.clone());
            XHistory.remove(index);
            YFuture.add(Y.clone());
            YHistory.remove(index);

            System.out.println("后撤的"+XHistory);
            clean(0);
            DrawHistory();
            invalidate();
        }
    }

    //前撤
    public void ConMove()
    {
        System.out.println("下一步");
        if(XFuture.size()>0)
        {
            int index  = XFuture.size()-1;
            X = (Vector)XFuture.get(index);
            Y = (Vector)YFuture.get(index);

            int a = (int)TFuture.get(index);

            THistory.add((int)TFuture.get(index));
            TFuture.remove(index);

            XHistory.add(X.clone());
            XFuture.remove(index);
            YHistory.add(Y.clone());
            YFuture.remove(index);

            System.out.println("前撤的"+XHistory);

            clean(0);
            DrawHistory();
            invalidate();
        }
    }


    public void changecolor(int i)
    {
        System.out.println(0x0000ff+i*(0xff0000-0x0000ff)/100);
        p.setColor( Color.BLUE+i*( Color.RED -Color.BLUE)/100);
    }

    public Bitmap save()  {
        System.out.println(bitmap.getWidth());
        System.out.println(bitmap.getHeight());
        return bitmap;
    }

    public void SetBitMao(Bitmap b)
    {
        bitmap = b;
        System.out.println(bitmap.getWidth());
        System.out.println(bitmap.getHeight());
        canvas.setBitmap(b);
        invalidate();
    }

    public void changewidth(int i)
    {
        p.setStrokeWidth(i*0.08f);
        e.setStrokeWidth(i*0.12f);
    }

    public void DrawByArry(int m)
    {
        Paint t = null;
        if(m==0)//橡皮
            t = e;
        else
            t = p;

        for(int i = 1;i<X.size();i++)
        {
            canvas.drawLine((float)X.get(i-1),(float)Y.get(i-1),(float)X.get(i),(float)Y.get(i),t);
        }
    }


    public void DrawHistory()
    {
        for (int i = 0;i<XHistory.size();i++)
        {
            X = (Vector)XHistory.get(i);
            Y = (Vector)YHistory.get(i);

            DrawByArry((int)THistory.get(i));
        }
    }


    @Override
    public void onDraw(Canvas c) {
       // System.out.println("调用OnDraw");

        c.drawBitmap(bitmap, 0, 0, null); }
}
