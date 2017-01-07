package com.song.honestshoppingmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("张业松提交代码");
        System.out.println("熊向东提交测试代码");

        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }
}
