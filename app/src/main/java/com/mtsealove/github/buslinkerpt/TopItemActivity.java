package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Design.TitleView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TopItemActivity extends AppCompatActivity {
    TitleView titleView;
    TableLayout tableLayout;
    LinearLayout driver1, driver2, driver3;
    TextView driver1Tv, driver2Tv, driver3Tv;
    View driver1view, driver2view, driver3view;

    ArrayList<TextView> seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_item);
        titleView = findViewById(R.id.titleView);
        titleView.setTitle("상단 화물 상세보기");


        StatusBarManager.setStatusBarWhite(this);
        initTab();
        initDriver();
    }

    private void initDriver() {
        driver1 = findViewById(R.id.driver2layout);
        driver2 = findViewById(R.id.driver2layout);
        driver3 = findViewById(R.id.driver3layout);
        driver1Tv = findViewById(R.id.driver1Tv);
        driver2Tv = findViewById(R.id.driver2Tv);
        driver3Tv = findViewById(R.id.driver3Tv);
        driver1view = findViewById(R.id.driver1View);
        driver2view = findViewById(R.id.driver2View);
        driver3view = findViewById(R.id.driver3View);

        driver1view.setBackground(getDrawable(R.drawable.seat1));
        driver2view.setBackground(getDrawable(R.drawable.seat2));
        driver3view.setBackground(getDrawable(R.drawable.seat3));

    }

    //create tab
    private void initTab() {
        tableLayout = findViewById(R.id.tableLayout);
        seats = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            seats.add(new TextView(this));
        }
        seats.add((TextView) findViewById(R.id.seat5));
        seats.add((TextView) findViewById(R.id.seat6));
        seats.add((TextView) findViewById(R.id.seat7));
        seats.add((TextView) findViewById(R.id.seat8));
        seats.add((TextView) findViewById(R.id.seat9));
        seats.add((TextView) findViewById(R.id.seat10));
        seats.add((TextView) findViewById(R.id.seat11));
        seats.add((TextView) findViewById(R.id.seat12));
        seats.add((TextView) findViewById(R.id.seat13));
        seats.add((TextView) findViewById(R.id.seat14));
        seats.add((TextView) findViewById(R.id.seat15));
        seats.add((TextView) findViewById(R.id.seat16));
        seats.add((TextView) findViewById(R.id.seat17));
        seats.add((TextView) findViewById(R.id.seat18));
        seats.add((TextView) findViewById(R.id.seat19));
        seats.add((TextView) findViewById(R.id.seat20));
        seats.add((TextView) findViewById(R.id.seat21));
        seats.add((TextView) findViewById(R.id.seat22));
        seats.add((TextView) findViewById(R.id.seat23));
        seats.add((TextView) findViewById(R.id.seat24));
        seats.add((TextView) findViewById(R.id.seat25));
        seats.add((TextView) findViewById(R.id.seat26));
        seats.add((TextView) findViewById(R.id.seat27));
        seats.add((TextView) findViewById(R.id.seat28));
        seats.add((TextView) findViewById(R.id.seat29));
        seats.add((TextView) findViewById(R.id.seat30));
        seats.add((TextView) findViewById(R.id.seat31));
        seats.add((TextView) findViewById(R.id.seat32));
        seats.add((TextView) findViewById(R.id.seat33));
        seats.add((TextView) findViewById(R.id.seat34));
        seats.add((TextView) findViewById(R.id.seat35));
        seats.add((TextView) findViewById(R.id.seat36));
        seats.add((TextView) findViewById(R.id.seat37));
        seats.add((TextView) findViewById(R.id.seat38));
        seats.add((TextView) findViewById(R.id.seat39));
        seats.add((TextView) findViewById(R.id.seat40));
        seats.add((TextView) findViewById(R.id.seat41));
        seats.add((TextView) findViewById(R.id.seat42));
        seats.add(new TextView(this));
        seats.add((TextView) findViewById(R.id.seat44));
        seats.add((TextView) findViewById(R.id.seat45));

        for (int i = 0; i < seats.size(); i++) {
            if(i<15)
                seats.get(i).setBackground(getDrawable(R.drawable.seat1));
            else if(i<30)
                seats.get(i).setBackground(getDrawable(R.drawable.seat2));
            else
                seats.get(i).setBackground(getDrawable(R.drawable.seat3));
        }

    }
}
