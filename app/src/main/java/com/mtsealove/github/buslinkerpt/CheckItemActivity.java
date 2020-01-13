package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Design.TitleView;

public class CheckItemActivity extends AppCompatActivity {
    TitleView titleView;
    FrameLayout topItemBtn;
    static DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        titleView = findViewById(R.id.titleView);
        titleView.setTitle("화물 확인");
        StatusBarManager.setStatusBarWhite(this);
        topItemBtn = findViewById(R.id.top_items_layout);
        drawerLayout = findViewById(R.id.drawerLayout);

        topItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTopItem();
            }
        });
    }

    private void CheckTopItem() {
        Intent intent = new Intent(this, TopItemActivity.class);
        startActivity(intent);
    }

    public static void openDrawer() {
        if(!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }
}
