package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Restful.Request.Commute;
import com.mtsealove.github.buslinkerpt.Restful.Rest;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    boolean work;
    TextView titleTv;
    String id;

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        StatusBarManager.setStatusBarWhite(this);
        titleTv = findViewById(R.id.commuteTitle);
        SharedPreferences account = getSharedPreferences("accountV", MODE_PRIVATE);
        id = account.getString("id", "");

        work = getIntent().getBooleanExtra("work", true);
        if (work) {
            titleTv.setText("출근");
        } else {
            titleTv.setText("퇴근");
        }

        CheckCameraPermission();
    }

    //permission check
    private void CheckCameraPermission() {
        TedPermission.with(this)
                .setPermissions(Manifest.permission.CAMERA)
                .setPermissionListener(permissionListener)
                .check();
    }

    //listener for status of permission
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            mScannerView = findViewById(R.id.scannerView);
            mScannerView.setResultHandler(QrActivity.this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(QrActivity.this, "카메라 권한을 허용하셔야 합니다", Toast.LENGTH_SHORT).show();
            finish();
        }
    };


    @Override
    public void handleResult(final Result rawResult) {
        Log.e("qr", rawResult.getText()); // Prints scan results
        Rest rest = new Rest();
        Commute commute = new Commute(id, rawResult.getText());
        Call<com.mtsealove.github.buslinkerpt.Restful.Response.Result> call = rest.getRetrofitService().CheckQrCode(commute);
        call.enqueue(new Callback<com.mtsealove.github.buslinkerpt.Restful.Response.Result>() {
            @Override
            public void onResponse(Call<com.mtsealove.github.buslinkerpt.Restful.Response.Result> call, Response<com.mtsealove.github.buslinkerpt.Restful.Response.Result> response) {
                if (response.isSuccessful() && response.body().isResult()) {
                    onComplete();
                } else {
                    Toast.makeText(QrActivity.this, "인증 코드가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                    mScannerView.resumeCameraPreview(QrActivity.this);
                }
            }

            @Override
            public void onFailure(Call<com.mtsealove.github.buslinkerpt.Restful.Response.Result> call, Throwable t) {
                Toast.makeText(QrActivity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //when qr read complete, show message and finish activity
    private void onComplete() {
        TextView completeMsgTv = findViewById(R.id.completeMsgTv);
        TextView commentTv = findViewById(R.id.commentTv);
        CardView completeCard = findViewById(R.id.completeCard);
        String complete, comment;

        if (work) {
            complete = "출근 완료";
            comment = "오늘도 행복한 하루 되세요.";
        } else {
            complete = "퇴근 완료";
            comment = "오늘도 고생하셨습니다.";
        }

        completeMsgTv.setText(complete);
        commentTv.setText(comment);

        completeCard.setAlpha(1f);
        completeCard.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void Back(View view) {
        finish();
    }
}
