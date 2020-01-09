package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;

public class LoginActivity extends AppCompatActivity {
    LinearLayout logoLayout, loginLayout;
    ImageView loadIv;
    EditText idEt, pwEt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logoLayout = findViewById(R.id.logoLayout);
        loginLayout = findViewById(R.id.loginLayout);
        loadIv = findViewById(R.id.loadIv);
        idEt = findViewById(R.id.idEt);
        pwEt = findViewById(R.id.pwEt);
        loginBtn = findViewById(R.id.loginBtn);

        StatusBarManager.setStatusBarWhite(this);

        Animate();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIpput();
            }
        });
    }

    //animate slide up and fade in
    private void Animate() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loginLayout.setAlpha(1f);
                        logoLayout.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.move));
                        loginLayout.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fadein));
                    }
                });
            }
        }, 1200);
    }

    //when loading
    private void onLoad() {
        Glide.with(this).load(R.drawable.loading).into(loadIv);
    }

    //when load end
    private void onComplete() {
        Glide.with(this).load(R.drawable.check).into(loadIv);
    }

    //check input
    private void checkIpput() {
        if (idEt.getText().toString().length() == 0) {
            Toast.makeText(this, "메일 주소를 입력하세요", Toast.LENGTH_SHORT).show();
        } else if (pwEt.getText().toString().length() == 0) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {    //checked
            //todo
            onLoad();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //show complete img
                            onComplete();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //move to main page or tutorial page
                                    moveNext();
                                }
                            }, 500);
                        }
                    });
                }
            }, 1000);
        }
    }

    private void moveNext() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        boolean tutorial = pref.getBoolean("tutorial", true);
        Intent intent;

        if (tutorial) {  //move to tutorial page
            intent = new Intent(this, TutorialActivity.class);
        } else {    //move to main page
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
