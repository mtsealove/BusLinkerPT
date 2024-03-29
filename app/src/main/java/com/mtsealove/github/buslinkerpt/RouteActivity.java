package com.mtsealove.github.buslinkerpt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mtsealove.github.buslinkerpt.Design.RouteRecyclerAdapter;
import com.mtsealove.github.buslinkerpt.Design.RouteView;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Restful.Response.Route;
import com.mtsealove.github.buslinkerpt.Restful.Response.Route_Timeline;
import com.mtsealove.github.buslinkerpt.Restful.Response.Timeline;
import com.mtsealove.github.buslinkerpt.Restful.Rest;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteActivity extends AppCompatActivity {
    SlidingUpPanelLayout slidingPaneLayout;
    RecyclerView routeRv;
    TextView corpTv, numTv, logiTv, routeNameTv, currentName, currentTime, currentAddr, currentStart, currentEnd, subTitle, dateTv, dvPhoneTV, dvNameTv, deliveryTv, takeTv;
    ImageView phoneIv, drawerIv;
    List<Timeline> timelineList;
    Button statusBtn;
    double desLat, desLng;
    private RouteRecyclerAdapter recyclerAdapter;
    LinearLayout backgroundLayout;
    ArrayList<String> actionList, locList;
    private int RouteID = 0;
    final int READY = 200;
    int level = 0;
    String date = null;
    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        StatusBarManager.setStatusBarNaby(this);
        format = new SimpleDateFormat("yyyyMMdd");

        slidingPaneLayout = findViewById(R.id.sliding_layout);
        routeRv = findViewById(R.id.routeRv);
        corpTv = findViewById(R.id.corpTv);
        numTv = findViewById(R.id.numTv);
        logiTv = findViewById(R.id.logiTv);
        dvNameTv = findViewById(R.id.DvNameTv);
        dvPhoneTV = findViewById(R.id.DvPhoneTv);
        routeNameTv = findViewById(R.id.routeNameTv);
        currentName = findViewById(R.id.name);
        currentAddr = findViewById(R.id.addr);
        currentTime = findViewById(R.id.time);
        currentStart = findViewById(R.id.currentStart);
        currentEnd = findViewById(R.id.currentEnd);
        subTitle = findViewById(R.id.subTitle);
        drawerIv = findViewById(R.id.drawerIv);
        phoneIv = findViewById(R.id.phoneIv);
        backgroundLayout = findViewById(R.id.backgroundLayout);
        dateTv = findViewById(R.id.dateTv);
        statusBtn = findViewById(R.id.statusBtn);
        deliveryTv = findViewById(R.id.deliveryTv);
        takeTv = findViewById(R.id.takeTv);
        Intent intent = getIntent();
        deliveryTv.setText(intent.getIntExtra("delivery", 0) + "박스");
        takeTv.setText(intent.getIntExtra("take", 0) + "박스");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        dateTv.setText(format.format(new Date()));
        getRouteTimeline();
        setPanel();
        level = getLevel();
    }

    private void setDate() {
        SharedPreferences pref = getSharedPreferences("date", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("date", format.format(new Date()));
        editor.commit();
    }

    private boolean otherDate() {
        String today=format.format(new Date());
        SharedPreferences pref=getSharedPreferences("date", MODE_PRIVATE);
        String date=pref.getString("date", "");
        if(date.equals(today)) {
            return  false;
        } else {
            return true;
        }
    }

    private int getLevel() {
        SharedPreferences pref = getSharedPreferences("level", MODE_PRIVATE);
        int l = pref.getInt("level", 0);
        return l;
    }

    private void setLevel(int l) {
        SharedPreferences pref = getSharedPreferences("level", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("level", l);
        editor.commit();
    }

    @SuppressLint("MissingPermission")
    private void getRouteTimeline() {
        SharedPreferences pref = getSharedPreferences("accountV", Context.MODE_PRIVATE);
        String id = pref.getString("id", null);
        Rest rest = new Rest();
        Call<Route_Timeline> call = rest.getRetrofitService().getRouteTimeline(id, null);
        call.enqueue(new Callback<Route_Timeline>() {

            @Override
            public void onResponse(Call<Route_Timeline> call, Response<Route_Timeline> response) {
                if (response.isSuccessful()) {
                    Route_Timeline route_timeline = response.body();
                    Route route = route_timeline.getRoute();
                    List<Timeline> timelines = route_timeline.getTimeline();

                    if (route != null) {
                        RouteID = route.getRouteID();
                        corpTv.setText(route.getCorp());
                        numTv.setText(route.getNum());
                        logiTv.setText(route.getLogiName());
                        dvNameTv.setText(route.getDriverName());
                        dvPhoneTV.setText(route.getDriverPhone());
                        routeNameTv.setText(route.getName());
                        timelineList = timelines;
                        setRoute(timelines);
                        setAction(timelines, RouteID);
                    }

                } else {
                    Log.e("RouteTimeline", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Route_Timeline> call, Throwable t) {
                Log.e("Err", t.toString());
            }
        });
    }

    private void setRoute(List<Timeline> timelines) {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        routeRv.setLayoutManager(lm);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dateStr = format.format(date);

        recyclerAdapter = new RouteRecyclerAdapter(this);
        for (int i = 0; i < timelines.size(); i++) {
            Timeline timeline = timelines.get(i);
            RouteView routeView = new RouteView(timeline.getLocName(), timeline.getRcTime().substring(0, 5), timeline.getLocAddr());

            Log.e("time", dateStr);
            int hour0 = Integer.parseInt(timeline.getRcTime().split(":")[0]);
            int min0 = Integer.parseInt(timeline.getRcTime().split(":")[1]);
            int hour1 = Integer.parseInt(dateStr.split(":")[0]);
            int min1 = Integer.parseInt(dateStr.split(":")[1]);
            if (hour1 > hour0 || (hour0 == hour1 && min1 > min0)) {
                routeView.setDisable(true);
            } else if (recyclerAdapter.getItemList().get(recyclerAdapter.getItemCount() - 1).isDisable()) {
                routeView.setCurrent(true);
            }

            if (i == 0) {
                routeView.setFirst(true);
            } else if (i == timelines.size() - 1) {
                routeView.setLast(true);
            }
            recyclerAdapter.addItem(routeView);
        }
        routeRv.setAdapter(recyclerAdapter);
        setCurrentTimeline();
    }

    //    set action strings
    int current = 0;

    private void setAction(List<Timeline> timelines, final int routeID) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String date = format.format(new Date());
        final SharedPreferences pref = getSharedPreferences("route", MODE_PRIVATE);
        int status = pref.getInt(date, 0);
        current = status;
        boolean logiFirst = true;
        actionList = new ArrayList<>();
        locList = new ArrayList<>();

        for (Timeline timeline : timelines) {
            switch (timeline.getLocCat()) {
                case 2:
                    if (logiFirst) {
                        actionList.add("상차 진행");
                        actionList.add("상차 완료");
                        logiFirst = false;
                    } else {
                        actionList.add("하차 진행");
                        actionList.add("하차 완료");
                    }
                    break;
                case 3:
                    actionList.add("하차 진행");
                    actionList.add("하차 완료");
                    break;
                case 4:
                    actionList.add("상차 진행");
                    actionList.add("상차 완료");
                    break;
            }
            if (timeline.getLocCat() != 1) {
                locList.add(timeline.getLocName());
                locList.add(timeline.getLocName());
            }
        }

        final int max = actionList.size();
        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RouteActivity.this, ReadyActivity.class);
                intent.putExtra("routeID", routeID);
                intent.putExtra("max", max);
                startActivityForResult(intent, READY);
            }
        });
        try {
            statusBtn.setText(actionList.get(level));
        } catch (Exception e) {
            if(otherDate()) {
                setLevel(0);
            } else {
                statusBtn.setText("일정 완료");
                statusBtn.setOnClickListener(null);
                statusBtn.setClickable(false);
            }
        }


        /*statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RouteActivity.this);
                builder.setTitle(actionList.get(current) + " 확인")
                        .setMessage(locList.get(current) + "에서 " + actionList.get(current) + "하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        current++;
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt(date, current);
                        editor.commit();
                        if (current != actionList.size()) {
                            statusBtn.setText(actionList.get(current));
                        } else {
                            statusBtn.setOnClickListener(null);
                            statusBtn.setText("완료");
                        }

                    }
                });
                builder.create().show();
            }
        });
         */
    }

    //   calculate distance and set nearest route
    private void setCurrentTimeline() {
        ArrayList<RouteView> routeViews = recyclerAdapter.getItemList();

        int current = 0;
        for (int i = 0; i < routeViews.size(); i++) {
            if (routeViews.get(i).isCurrent()) {
                current = i;
            }
        }
        Timeline currentTimeline = timelineList.get(current);
        currentName.setText(currentTimeline.getLocName());
        currentTime.setText(currentTimeline.getRcTime().substring(0, 5));
        currentAddr.setText(currentTimeline.getLocAddr());
        String start = "";

        currentEnd.setText(currentTimeline.getLocName());
        if (current != 0) {
            start = timelineList.get(current - 1).getLocName();
        } else {
            start = "출근지";
        }
        desLat = currentTimeline.getLat();
        desLng = currentTimeline.getLng();

        currentStart.setText(start);
    }

    private void setPanel() {
        slidingPaneLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                    drawerIv.setImageDrawable(getDrawable(R.drawable.slide_up));
                    backgroundLayout.setBackgroundColor(getColor(R.color.transparent));
                } else if (newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
                    drawerIv.setImageDrawable(getDrawable(R.drawable.slide_down));
                    backgroundLayout.setBackgroundColor(getColor(R.color.navy));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case READY:
                    int lv = data.getIntExtra("level", 1);
                    setLevel(lv);
                    level = lv;
                    if (actionList.size() > level) {
                        statusBtn.setText(actionList.get(level));
                    } else {
                        Toast.makeText(RouteActivity.this, "일정이 모두 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        statusBtn.setClickable(false);
                        statusBtn.setText("일정 완료");
                        setDate();
                    }
                    break;
            }
        }
    }
}
