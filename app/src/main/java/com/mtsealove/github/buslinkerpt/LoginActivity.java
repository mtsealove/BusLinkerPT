package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mtsealove.github.buslinkerpt.Design.LogoView;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Restful.Response.Login;
import com.mtsealove.github.buslinkerpt.Restful.Rest;

import java.security.MessageDigest;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    LinearLayout loginLayout;
    LogoView logoLayout;
    ImageView loadIv;
    EditText idEt, pwEt;
    Button loginBtn;
    CheckBox keepCb;
    TextView pwTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logoLayout = findViewById(R.id.logoView);
        loginLayout = findViewById(R.id.loginLayout);
        loadIv = findViewById(R.id.loadIv);
        idEt = findViewById(R.id.idEt);
        pwEt = findViewById(R.id.pwEt);
        loginBtn = findViewById(R.id.loginBtn);
        keepCb = findViewById(R.id.keepCb);
        pwTv = findViewById(R.id.pwTv);

        StatusBarManager.setStatusBarWhite(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIpput();
            }
        });
        /*
        pwTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPw();
            }
        });

 */
        getAppKeyHash();
        CheckPermission();
    }

    private void CheckPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                getAccount();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(LoginActivity.this, "권한을 허용하셔야 프로그램을 사용하실 수 있습니다\n잠시 후 프로그램을 종료합니다.", Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                }, 20000);
            }
        };
        TedPermission.with(this)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .setPermissionListener(permissionListener)
                .check();
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
        loadIv.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.loading).into(loadIv);
    }

    //when load end
    private void onComplete() {
        Glide.with(this).load(R.drawable.check).into(loadIv);
    }

    private void onFail() {
        loadIv.setVisibility(View.INVISIBLE);
    }

    //check input
    private void checkIpput() {
        if (idEt.getText().toString().length() == 0) {
            Toast.makeText(this, "메일 주소를 입력하세요", Toast.LENGTH_SHORT).show();
        } else if (pwEt.getText().toString().length() == 0) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {    //checked
            onLoad();
            final String id = idEt.getText().toString();
            final String pw = pwEt.getText().toString();

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    Rest rest = new Rest();
                    String token = instanceIdResult.getToken();
                    Log.e("Token", token);
                    Call<Login> call = rest.getRetrofitService().Login(new com.mtsealove.github.buslinkerpt.Restful.Request.Login(id, pw, token));
                    call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if (response.isSuccessful()) {
                                //login success
                                if (response.body().getID() != null) {
                                    // save id
                                    String name = response.body().getName();
                                    String profilePath = response.body().getProfilePath();
                                    saveView(id, name, profilePath);

                                    if (keepCb.isChecked()) {
                                        saveAccount(id, pw);
                                    } else {
                                        removeAccount();
                                    }

                                    onComplete();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            moveNext();
                                        }
                                    }, 500);

                                } else {    //login fail
                                    removeAccount();
                                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                                    onFail();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                            onFail();
                        }
                    });
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

    //    save id and pw
    private void saveAccount(String id, String pw) {
        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", id);
        editor.putString("pw", pw);
        editor.commit();
    }

    private void saveView(String id, String name, String profilePath) {
        SharedPreferences pref = getSharedPreferences("accountV", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", id);
        editor.putString("name", name);
        editor.putString("profile", profilePath);
        editor.commit();
    }

    private void removeAccount() {
        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    private void getAccount() {
        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
        String id = pref.getString("id", null);
        String pw = pref.getString("pw", null);
        if (id != null && pw != null) {
            Log.e("ac", id);
            Log.e("ac", pw);
            idEt.setText(id);
            pwEt.setText(pw);
            keepCb.setChecked(true);
            checkIpput();
        } else {
            Animate();
        }
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

    /*
    private void findPw() {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

     */
}
