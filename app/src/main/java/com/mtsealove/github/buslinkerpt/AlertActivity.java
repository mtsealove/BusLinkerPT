package com.mtsealove.github.buslinkerpt;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mtsealove.github.buslinkerpt.Database.AlertOpenHelper;
import com.mtsealove.github.buslinkerpt.Design.AlertRecyclerAdapter;
import com.mtsealove.github.buslinkerpt.Design.AlertView;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;

public class AlertActivity extends AppCompatActivity {
    RecyclerView alertRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        alertRv = findViewById(R.id.alertRv);
        StatusBarManager.setStatusBarWhite(this);
        //setAlerts();
    }

    private void setAlerts() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        alertRv.setLayoutManager(lm);

        AlertRecyclerAdapter adapter = new AlertRecyclerAdapter(this);
        AlertOpenHelper helper = new AlertOpenHelper(this, AlertOpenHelper.Table, null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "select * from Alert";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String msg = cursor.getString(2);
                String time = cursor.getString(3);
                int status = cursor.getInt(4);
                boolean read;
                if (status == 1) {
                    read = true;
                } else {
                    read = false;
                }
                String action = cursor.getString(5);
                AlertView alertView = new AlertView(id, title, msg, time, read, action);
                adapter.addItem(alertView);
            }
        }
        alertRv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAlerts();
    }
}
