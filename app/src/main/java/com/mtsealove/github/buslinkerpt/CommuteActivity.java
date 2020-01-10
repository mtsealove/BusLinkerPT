package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Design.TitleView;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CommuteActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    boolean work;
    TitleView titleView;
    static DrawerLayout drawerLayout;

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commute);
        drawerLayout = findViewById(R.id.drawerLayout);
        titleView = findViewById(R.id.titleView);
        StatusBarManager.setStatusBarWhite(this);

        work = getIntent().getBooleanExtra("work", true);

        if (work) {
            titleView.setTitle("출근");
        } else {
            titleView.setTitle("퇴근");
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
            mScannerView.setResultHandler(CommuteActivity.this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(CommuteActivity.this, "카메라 권한을 허용하셔야 합니다", Toast.LENGTH_SHORT).show();
            finish();
        }
    };


    @Override
    public void handleResult(Result rawResult) {
        Log.e("qr", rawResult.getText()); // Prints scan results
        onComplete();
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

    public static void openDrawer() {
        if(!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else super.onBackPressed();
    }
}
