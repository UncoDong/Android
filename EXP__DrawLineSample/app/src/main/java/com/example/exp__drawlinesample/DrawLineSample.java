package com.example.exp__drawlinesample;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.*;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;

public class DrawLineSample extends Activity {
    private int Pen = 1;
    private int Eraser = 2;
    /** Called when the activity is first created. */
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.main);
        TableLayout layout = new TableLayout(this);
        TableRow row = new TableRow(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        Button paint = new Button(getApplicationContext());
        paint.setText("画笔");
        row.addView(paint);
        Button eraser = new Button(getApplicationContext());
        eraser.setText("橡皮");
        row.addView(eraser);

        SeekBar color=new SeekBar(getApplicationContext());;//可拖动进度条
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-2, -2);
        color.setLayoutParams(lp);
        color.getProgressDrawable().setColorFilter(100000, PorterDuff.Mode.MULTIPLY);
        color.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),PorterDuff.Mode.SRC_ATOP);


        SeekBar.OnSeekBarChangeListener osbcl=new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                System.out.println(progress);

            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
        color.setOnSeekBarChangeListener(osbcl);
        //row.addView(color);

        layout.addView(row);
        layout.addView(color);

        final PaintView view = new PaintView(this);
        layout.addView(view);

        setContentView(layout);

        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setMode(0);
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setMode(1);
            }
        });
    }
}