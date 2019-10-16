package com.example.exp__drawlinesample;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class KeyBoardActivity extends AppCompatActivity {
    private EditText act_key_board_et;
    private CarKeyboardView keyboardView;
    private View ky_keyboard_parent;
    private CarKeyBoardUtil carKeyBoardUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);

        act_key_board_et = findViewById(R.id.act_key_board_et);
        keyboardView = findViewById(R.id.ky_keyboard);
        ky_keyboard_parent = findViewById(R.id.ky_keyboard_parent);
        carKeyBoardUtil = new CarKeyBoardUtil(ky_keyboard_parent,keyboardView,act_key_board_et);
        act_key_board_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (carKeyBoardUtil == null) {
                    carKeyBoardUtil = new CarKeyBoardUtil(ky_keyboard_parent,keyboardView,act_key_board_et);
                }
                carKeyBoardUtil.showKeyboard();
                return false;
            }
        });
    }

}
