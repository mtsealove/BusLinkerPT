package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;

public class GuideActivity extends AppCompatActivity {
    boolean isImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        isImport = getIntent().getBooleanExtra("isImport", false);
        StatusBarManager.setStatusBarWhite(this);
    }
}
