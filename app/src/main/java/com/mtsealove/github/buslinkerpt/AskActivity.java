package com.mtsealove.github.buslinkerpt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;

public class AskActivity extends AppCompatActivity {
    LinearLayout phoneLayout, chatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        StatusBarManager.setStatusBarWhite(this);
        phoneLayout = findViewById(R.id.phoneLayout);
        chatLayout = findViewById(R.id.chatLayout);

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01047138131"));
                startActivity(tt);
            }
        });

        chatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://pf.kakao.com/_Vxdxlsxb";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    public void Back(View view) {
        finish();
    }
}
