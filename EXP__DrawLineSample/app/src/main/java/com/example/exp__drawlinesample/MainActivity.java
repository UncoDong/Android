package com.example.exp__drawlinesample;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {

    Button pen;
    Button eraser;
    PaintView paint;
    SeekBar color;
    SeekBar width;
    Button clean;
    Button back;
    Button con;

    SoundPool sp;

    int musicid;
    String path = null;
    int mode;//0是画笔 1是橡皮擦
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        pen = (Button)findViewById(R.id.pen);
        eraser = (Button)findViewById(R.id.eraser);
        paint = (PaintView) findViewById(R.id._123);
        color = (SeekBar)findViewById(R.id.color);
        width = (SeekBar)findViewById(R.id.width);
        back= (Button)findViewById(R.id.back);
        clean = (Button)findViewById(R.id.clean);
        con = (Button)findViewById(R.id.con);

        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        musicid = sp.load(this,R.raw.music1,1);


        //画笔按钮
        pen.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View view) {
                paint.setMode(1);
                sp.play(musicid,1,1,0,0,1);
            }
        });

        //橡皮按钮
        eraser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                paint.setMode(0);
                sp.play(musicid,1,1,0,0,1);
            }
        });

        //清空画布按钮
        clean.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View view) {
            paint.clean(1);
            sp.play(musicid,1,1,0,0,1);
        }
        });


        //向后撤回
        con.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)  {
            paint.ConMove();
        }
    }
        );

        //向前撤
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                paint.BackMove();
            }
            }
        );

        //颜色监听
        color.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //System.out.println(i);
                paint.changecolor(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        //粗细监听
        width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //System.out.println(i);
                paint.changewidth(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

    }

    public void  delfile()
    {
        if(path!=null)
        {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

    public void Unused()
    {
        Bitmap b =  paint.save();
        try {
            String storage = getApplicationContext().getFilesDir().getAbsolutePath();;
            File pictureFileDir = new File(storage);
            System.out.println(storage);
            if (!pictureFileDir.exists())
            {
                System.out.println("新建文件1");
                pictureFileDir.mkdir();
            }
            File dirFile = new File(pictureFileDir.getAbsolutePath() + "/abcde");         //在storage/emulated/0目录下创建abcd文件夹
            if (!dirFile.exists())
            {
                System.out.println("新建文件2");
                dirFile .mkdirs();
            }

            File imageFile=File.createTempFile("DemoPicture",".jpg",dirFile);
            System.out.println("打开文件成功");
            FileOutputStream fileOS = null;                           //创建一个文件输出流对象
            fileOS = new FileOutputStream(imageFile);
            System.out.println("新建数据流成功");
            b.compress(Bitmap.CompressFormat.PNG,100,fileOS);             //将绘图内容压缩为PNG格式输出到输出流对象中，PNG为透明图片
            fileOS.flush();                 //将缓冲区中的数据全部写出道输出流中
            fileOS.close();
            System.out.println("保存成功");

            path = imageFile.getAbsolutePath();
            System.out.println(imageFile.getAbsolutePath());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
