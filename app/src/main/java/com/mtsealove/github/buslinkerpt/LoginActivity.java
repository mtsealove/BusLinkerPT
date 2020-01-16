package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Restful.Response.Login;
import com.mtsealove.github.buslinkerpt.Restful.Rest;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        getAppKeyHash();
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
            onLoad();
            String id = idEt.getText().toString();
            String pw = pwEt.getText().toString();
            Rest rest = new Rest();
            Call<Login> call = rest.getRetrofitService().Login(new com.mtsealove.github.buslinkerpt.Restful.Request.Login(id, pw));
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if (response.isSuccessful()) {
                        //login success
                        if (response.body().getID() != null) {
                            // save id
                            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("UserName", response.body().getName());
                            editor.putString("ID", response.body().getID());
                            editor.putString("ProfilePath", response.body().getProfilePath());
                            editor.commit();

                            onComplete();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    moveNext();
                                }
                            }, 500);

                        } else {    //login fail
                            Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                }
            });
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

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }
}
