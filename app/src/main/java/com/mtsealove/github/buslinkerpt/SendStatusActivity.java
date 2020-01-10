package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;

public class SendStatusActivity extends AppCompatActivity {
    TextView importTv, exportTv;
    LinearLayout importLayout, exportLayout;
    static DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_status);
        drawerLayout = findViewById(R.id.drawerLayout);
        importTv = findViewById(R.id.importTv);
        exportTv = findViewById(R.id.exportTv);
        importLayout = findViewById(R.id.importLayout);
        exportLayout = findViewById(R.id.exportLayout);

        StatusBarManager.setStatusBarWhite(this);
        setCount();

        importLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveGuide(true);
            }
        });
        exportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveGuide(false);
            }
        });
    }

    //todo set import/export count
    private void setCount() {
        int importCnt = 300;
        int exportCnt = 341;

        importTv.setText(importCnt + "건의 입고");
        exportTv.setText(exportCnt + "건의 출고");
    }

    public static void openDrawer() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void moveGuide(boolean isImport) {
        Intent intent = new Intent(this, GuideActivity.class);
        intent.putExtra("isImport", isImport);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else super.onBackPressed();
    }
}
