package com.example.exp__drawlinesample;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by hao on 2018/9/6.
 * 车牌号专用键盘
 */
public class CarKeyboardView extends KeyboardView {

    public CarKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CarKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Keyboard keyboard = getKeyboard();
        if (keyboard == null) return;
        List<Keyboard.Key> keys = keyboard.getKeys();
        if (keys != null && keys.size() > 0) {
            @SuppressLint("DrawAllocation")
            Paint paint = new Paint();
            paint.setTextAlign(Paint.Align.CENTER);
            Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
            paint.setTypeface(font);
            paint.setAntiAlias(true);
            for (Keyboard.Key key : keys) {
                if (key.codes[0] == -4) {
                    Drawable dr = getContext().getResources().getDrawable(R.drawable.white);
                    dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    dr.draw(canvas);
                    if (key.label != null) {
                        paint.setTextSize(18);
                        //设置字体颜色
                        paint.setColor(getContext().getResources().getColor(R.color.white));
                        // 注意： 这个方法不支持硬件加速，所以我们要测试时必须先关闭硬件加速。加上这一句
//                        setLayerType(LAYER_TYPE_SOFTWARE, null);
//                        paint.setShadowLayer(0.1f, 0, 0, getContext().getResources().getColor(R.color.color_white));
                        @SuppressLint("DrawAllocation")
                        Rect rect = new Rect(key.x, key.y, key.x + key.width, key.y + key.height);
                        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                        int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
                        paint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText(key.label.toString(), rect.centerX(), baseline, paint);
//                        paint.setShadowLayer(0, 0, 0, 0);
                    }
                } else if (key.codes[0] == -5) {
                    Drawable dr = getContext().getResources().getDrawable(R.drawable.back);
                    dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    dr.draw(canvas);
                } else if (key.codes[0] == 73||key.codes[0] == 79) {  // notice 字母 I 和 O 设置特殊背景
                    Drawable dr = getContext().getResources().getDrawable(R.drawable.hui);
                    dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    dr.draw(canvas);
                    if (key.label != null) {
                        paint.setTextSize(18);
                        paint.setColor(getContext().getResources().getColor(R.color.white));
                        // 注意： 这个方法不支持硬件加速，所以我们要测试时必须先关闭硬件加速。加上这一句
//                        setLayerType(LAYER_TYPE_SOFTWARE, null);
//                        paint.setShadowLayer(0.1f, 0, 0, getContext().getResources().getColor(R.color.color_white_999999));
                        @SuppressLint("DrawAllocation")
                        Rect rect = new Rect(key.x, key.y, key.x + key.width, key.y + key.height);
                        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                        int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                        paint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText(key.label.toString(), rect.centerX(), baseline, paint);
//                        paint.setShadowLayer(0, 0, 0, 0);
                    }
                }
            }
        }
    }
}