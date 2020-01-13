package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.zhouyou.view.seekbar.SignSeekBar;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;


public class GuideActivity extends AppCompatActivity {
    boolean isImport;
    MapView mapView;
    SignSeekBar signSeekBar;
    TextView nameTv, carNumTv, companyTv, contactTv;
    Button actionBtn;
    ImageView backIv;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mapView = findViewById(R.id.map_view);
        signSeekBar = findViewById(R.id.seek_bar);
        nameTv = findViewById(R.id.nameTv);
        carNumTv = findViewById(R.id.carNumTv);
        companyTv = findViewById(R.id.companyTv);
        contactTv = findViewById(R.id.contactTv);
        actionBtn = findViewById(R.id.actionBtn);
        backIv = findViewById(R.id.backIv);

        isImport = getIntent().getBooleanExtra("isImport", false);
        StatusBarManager.setStatusBarWhite(this);

        initMap();
        signSeekBar.setProgress(1.5f);

        action="상차";
        actionBtn.setText(action+" 진행");

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action(action);
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //set marker point and polyline on mapView
    //todo make marker location
    private void initMap() {
        MapPoint mapPoint0 = MapPoint.mapPointWithGeoCoord(37.551751, 126.924986);
        MapPoint mapPoint1 = MapPoint.mapPointWithGeoCoord(37.539409, 126.946160);
        MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(37.543382, 126.951533);
        MapPolyline polyline = new MapPolyline();
        polyline.setLineColor(Color.parseColor("#ffcc00"));
        polyline.setTag(1000);
        polyline.addPoint(mapPoint0);
        polyline.addPoint(mapPoint1);
        polyline.addPoint(mapPoint2);

        MapPOIItem marker0 = new MapPOIItem();
        marker0.setItemName("홍대");
        marker0.setMapPoint(mapPoint0);
        marker0.setMarkerType(MapPOIItem.MarkerType.BluePin);

        MapPOIItem marker1 = new MapPOIItem();
        marker1.setItemName("재단");
        marker1.setMapPoint(mapPoint1);
        marker1.setMarkerType(MapPOIItem.MarkerType.BluePin);

        MapPOIItem marker2 = new MapPOIItem();
        marker2.setItemName("공덕");
        marker2.setMapPoint(mapPoint2);
        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin);

        mapView.addPolyline(polyline);

        mapView.addPOIItem(marker0);
        mapView.addPOIItem(marker1);
        mapView.addPOIItem(marker2);

        mapView.setMapCenterPoint(mapPoint0, true);

    }

    //todo
    private void initDriverInfo() {

    }

    private void Action(String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("확인")
                .setMessage(action+"를 진행하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}