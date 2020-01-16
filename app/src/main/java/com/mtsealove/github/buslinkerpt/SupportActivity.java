package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Design.TitleView;
import com.mtsealove.github.buslinkerpt.Restful.Request.Ask;
import com.mtsealove.github.buslinkerpt.Restful.Response.Result;
import com.mtsealove.github.buslinkerpt.Restful.Rest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity {
    TitleView titleView;
    EditText contentsEt;
    Button submitBtn, backBtn;
    RelativeLayout completeLayout, formLayout;
    static DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        drawerLayout = findViewById(R.id.drawerLayout);
        titleView = findViewById(R.id.titleView);
        titleView.setTitle("문의");
        StatusBarManager.setStatusBarWhite(this);

        formLayout = findViewById(R.id.formLayout);
        completeLayout = findViewById(R.id.completeLayout);
        contentsEt = findViewById(R.id.contentsEt);
        submitBtn = findViewById(R.id.submitBtn);
        backBtn = findViewById(R.id.backBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //    check input and if correct, submit
    private void Submit() {
        if (contentsEt.getText().toString().length() == 0) {
            Toast.makeText(this, "문의 내용을 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("문의 전송")
                    .setMessage("문의사항을 전송하시겠습니까?")
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SendMsg();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void SendMsg() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String id = pref.getString("ID", "");
        Ask ask = new Ask(id, contentsEt.getText().toString());
        Rest rest = new Rest();
        Call<Result> call = rest.getRetrofitService().CreateAsk(ask);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (response.isSuccessful() && response.body().isResult()) {
                    onComplete();
                } else
                    Toast.makeText(SupportActivity.this, "오류가 발생하였습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(SupportActivity.this, "오류가 발생하였습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onComplete() {
        formLayout.setVisibility(View.GONE);
        completeLayout.setVisibility(View.VISIBLE);
    }

    public static void openDrawer() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }
}
