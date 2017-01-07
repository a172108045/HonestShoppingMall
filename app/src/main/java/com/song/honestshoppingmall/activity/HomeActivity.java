package com.song.honestshoppingmall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.song.honestshoppingmall.event.FirstEvent;
import com.song.honestshoppingmall.R;

import org.greenrobot.eventbus.EventBus;

public class HomeActivity extends AppCompatActivity {

    private Button btn_FirstEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_FirstEvent = (Button) findViewById(R.id.btn_first_event);

        btn_FirstEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FirstEvent("HomeActivity"));
            }
        });
    }
}
