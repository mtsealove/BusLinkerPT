package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Design.TitleView;

import java.util.List;

public class CommuteActivity extends AppCompatActivity {
    boolean work;
    TitleView titleView;
    private DecoratedBarcodeView barcodeView;
    private CaptureManager captureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commute);
        titleView = findViewById(R.id.titleView);
        StatusBarManager.setStatusBarWhite(this);
        barcodeView = findViewById(R.id.db_qr);

        captureManager = new CaptureManager(this, barcodeView);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();

        work = getIntent().getBooleanExtra("work", true);

        if (work) {
            titleView.setTitle("출근");
        } else {
            titleView.setTitle("퇴근");
        }

        CheckCameraPermission();
        initQrScanner();
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

        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(CommuteActivity.this, "카메라 권한을 허용하셔야 합니다", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private void initQrScanner() {
        Log.e("qr", "init");

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
        intentIntegrator.setCaptureActivity(CommuteActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
