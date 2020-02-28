package com.mtsealove.github.buslinkerpt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mtsealove.github.buslinkerpt.Design.Calendar.EventDecorator;
import com.mtsealove.github.buslinkerpt.Design.Calendar.SaturdayDecorator;
import com.mtsealove.github.buslinkerpt.Design.Calendar.SundayDecorator;
import com.mtsealove.github.buslinkerpt.Design.CalendarRouteRecyclerAdapter;
import com.mtsealove.github.buslinkerpt.Design.CalendarRouteView;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Restful.Response.Calendar;
import com.mtsealove.github.buslinkerpt.Restful.Response.Route;
import com.mtsealove.github.buslinkerpt.Restful.Response.Route_Timeline;
import com.mtsealove.github.buslinkerpt.Restful.Response.Timeline;
import com.mtsealove.github.buslinkerpt.Restful.Rest;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    TextView dateTv, nameTv, numTv;
    Rest rest;
    private String id;
    RecyclerView routeRv;
    RelativeLayout restLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        StatusBarManager.setStatusBarWhite(this);

        calendarView = findViewById(R.id.calendarView);
        dateTv = findViewById(R.id.dateTv);
        nameTv = findViewById(R.id.nameTv);
        numTv = findViewById(R.id.numTv);
        routeRv = findViewById(R.id.routeRv);
        restLayout = findViewById(R.id.restLayout);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        routeRv.setLayoutManager(lm);
        SharedPreferences pref = getSharedPreferences("accountV", MODE_PRIVATE);
        id = pref.getString("id", "null");
        rest = new Rest();
        setCalendar();
    }


    private void setCalendar() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        int year = Integer.parseInt(dateStr.split("-")[0]);
        int month = Integer.parseInt(dateStr.split("-")[1]);
        int day = Integer.parseInt(dateStr.split("-")[2]);

        dateTv.setText(year+"년 "+month+"월 "+day+"일");
        showCalendarRoute(year+"-"+month+"-"+day);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                String dateStr = date.getYear() + "년 " + date.getMonth() + "월 " + date.getDay() + "일";

                showCalendarRoute(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
                dateTv.setText(dateStr);
            }
        });

        calendarView.setDateSelected(CalendarDay.today(), true);

        Call<List<Calendar>> call = rest.getRetrofitService().getCalendar(id);
        call.enqueue(new Callback<List<Calendar>>() {
            @Override
            public void onResponse(Call<List<Calendar>> call, Response<List<Calendar>> response) {
                if (response.isSuccessful()) {
                    List<Calendar> calendars = response.body();
                    ArrayList<CalendarDay> calendarDays = new ArrayList<>();
                    for (Calendar calendar : calendars) {
                        Log.e("calendar", calendar.toString());
                        int year = Integer.parseInt(calendar.getRunDate().split("-")[0]);
                        int month = Integer.parseInt(calendar.getRunDate().split("-")[1]);
                        int day = Integer.parseInt(calendar.getRunDate().split("-")[2])+1;
                        calendarDays.add(CalendarDay.from(year, month, day));
                    }
                    calendarView.addDecorator(new EventDecorator(calendarDays));
                }
            }

            @Override
            public void onFailure(Call<List<Calendar>> call, Throwable t) {

            }
        });
        calendarView.addDecorators(new SundayDecorator(), new SaturdayDecorator());
    }


    private void showCalendarRoute(String date) {
        Call<Route_Timeline> call = rest.getRetrofitService().getRouteTimeline(id, date);
        call.enqueue(new Callback<Route_Timeline>() {
            @Override
            public void onResponse(Call<Route_Timeline> call, Response<Route_Timeline> response) {
                if (response.isSuccessful()) {
                    if (response.body().getRoute() != null) {
                        CalendarRouteRecyclerAdapter adapter = new CalendarRouteRecyclerAdapter(ScheduleActivity.this);
                        Route route = response.body().getRoute();
                        nameTv.setText(route.getName());
                        numTv.setText(route.getNum());
                        List<Timeline> timelineList = response.body().getTimeline();
                        for (Timeline timeline : timelineList) {
                            adapter.addItem(new CalendarRouteView(timeline.getLocName(), timeline.getLocAddr(), timeline.getRcTime().substring(0, 5)));
                        }
                        routeRv.setVisibility(View.VISIBLE);
                        restLayout.setVisibility(View.GONE);
                        routeRv.setAdapter(adapter);
                    } else {
                        routeRv.setAdapter(null);
                        nameTv.setText("");
                        numTv.setText("");
                        routeRv.setVisibility(View.GONE);
                        restLayout.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<Route_Timeline> call, Throwable t) {

            }
        });
    }


    public void Back(View view) {
        finish();
    }
}
