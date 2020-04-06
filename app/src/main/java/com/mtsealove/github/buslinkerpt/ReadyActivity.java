package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mtsealove.github.buslinkerpt.Design.ReadyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ReadyActivity extends AppCompatActivity {
    int routeID, max = 0;
    boolean exit = false;
    LinearLayout partTimeLayout;
    TextView labelTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);
        routeID = getIntent().getIntExtra("routeID", 1);
        max = getIntent().getIntExtra("max", 0);
        partTimeLayout = findViewById(R.id.partTimeLayout);
        labelTv = findViewById(R.id.labelTv);
        setSocket(routeID);
    }

    private Socket socket;

    private void setSocket(int RouteID) {
        SharedPreferences pref = getSharedPreferences("accountV", MODE_PRIVATE);
        String id = pref.getString("id", null);
        String name = pref.getString("name", null);
        String profile = pref.getString("profile", null);

        try {
            socket = IO.socket("http://172.30.1.60:3300");
            socket.connect();

            JSONObject object = new JSONObject();
            object.put("RouteID", RouteID);
            object.put("PTID", id);
            object.put("Max", max);
            object.put("Name", name);
            object.put("Profile", profile);
            socket.emit("create", object);
            socket.on("ready", onReceive);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onReceive = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONArray array = (JSONArray) args[0];
            boolean allReady = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    partTimeLayout.removeAllViews();
                }
            });

            int level=1;
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    final boolean ready = obj.getBoolean("Ready");
                    final String name = obj.getString("Name");
                    final String profile = obj.getString("Profile");
                    final String id = obj.getString("PTID");
                    level = obj.getInt("Level");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ReadyView readyView = new ReadyView(ReadyActivity.this);
                            readyView.setProfile(id, name, profile);
                            partTimeLayout.addView(readyView);
                            if (ready) {
                                readyView.setReady();
                            }
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) readyView.getLayoutParams();
                            params.setMargins(15, 0, 15, 0);
                            readyView.setLayoutParams(params);
                        }
                    });

                    Log.e("ready", String.valueOf(ready));
                    if (!ready) {
                        allReady = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    labelTv.setText(array.length() + "대의 버스가 출발 대기중입니다.");
                }
            });

            if (allReady) {
                exit = true;
                final int finalLevel = level;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReadyActivity.this, "모두 준비되었습니다.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent();
                                intent.putExtra("level", finalLevel);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }, 2000);
                    }
                });

            }
        }
    };


    @Override
    public void onBackPressed() {
        if (!exit) {
            Toast.makeText(ReadyActivity.this, "모두 준비되기 전까지 뒤로갈 수 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.close();
    }
}
