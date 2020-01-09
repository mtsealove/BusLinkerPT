package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Design.TitleView;

public class MainActivity extends AppCompatActivity {
    TitleView titleView;
    LinearLayout onLayout, offLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleView = findViewById(R.id.titleView);
        offLayout = findViewById(R.id.offLayout);
        onLayout = findViewById(R.id.onLayout);
        titleView.setTitle("");

        StatusBarManager.setStatusBarWhite(this);

        offLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkOff();
            }
        });
        onLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkOn();
            }
        });
    }

    //work off QR
    private void WorkOff() {
        Intent intent = new Intent(this, CommuteActivity.class);
        intent.putExtra("work", false);
        startActivity(intent);
    }

    //work on QR
    private void WorkOn() {
        Intent intent = new Intent(this, CommuteActivity.class);
        intent.putExtra("work", true);
        startActivity(intent);
    }

}
