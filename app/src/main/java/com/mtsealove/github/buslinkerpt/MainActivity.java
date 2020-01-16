package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Design.TitleView;

public class MainActivity extends AppCompatActivity {
    TitleView titleView;
    LinearLayout onLayout, offLayout;
    static DrawerLayout drawerLayout;
    TextView userNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        titleView = findViewById(R.id.titleView);
        offLayout = findViewById(R.id.offLayout);
        onLayout = findViewById(R.id.onLayout);
        titleView.setTitle("");
        userNameTv = findViewById(R.id.userNameTv);

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
        setUserName();
    }

    //reed user name
    private void setUserName() {
        SharedPreferences pref=getSharedPreferences("pref", MODE_PRIVATE);
        String userName=pref.getString("UserName", "사용자");
        userNameTv.setText(userName+" 님");
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

    public static void openDrawer() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    //double back to finish
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "'뒤로' 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

