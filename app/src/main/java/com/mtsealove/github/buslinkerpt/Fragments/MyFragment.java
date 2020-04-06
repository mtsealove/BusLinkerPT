package com.mtsealove.github.buslinkerpt.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mtsealove.github.buslinkerpt.AskActivity;
import com.mtsealove.github.buslinkerpt.Design.HeaderView;
import com.mtsealove.github.buslinkerpt.LoginActivity;
import com.mtsealove.github.buslinkerpt.R;
import com.mtsealove.github.buslinkerpt.Restful.Response.My;
import com.mtsealove.github.buslinkerpt.Restful.Rest;
import com.mtsealove.github.buslinkerpt.ScheduleActivity;
import com.mtsealove.github.buslinkerpt.TutorialActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFragment extends Fragment {
    TextView routeTv, itemCntTv, userNameTv;
    ImageView userIv;
    LinearLayout scheduleLayout, tutorialLayout, askLayout, logoutLayout;
    ProgressDialog progressDialog;

    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.setCancelable(false);
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        routeTv = view.findViewById(R.id.routeTv);
        itemCntTv = view.findViewById(R.id.itemCntTv);
        userNameTv = view.findViewById(R.id.userName);
        userIv = view.findViewById(R.id.userIv);
        userIv.setBackground(new ShapeDrawable(new OvalShape()));
        userIv.setClipToOutline(true);
        scheduleLayout = view.findViewById(R.id.scheduleLayout);
        tutorialLayout = view.findViewById(R.id.tutorialLayout);
        askLayout = view.findViewById(R.id.askLayout);
        logoutLayout = view.findViewById(R.id.logoutLayout);

        scheduleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSchedule();
            }
        });
        tutorialLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToTutorial();
            }
        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogout();
            }
        });
        askLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAsk();
            }
        });
        getMy();
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getMy() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("잠시만 기다려주세요");
        dialog.setCancelable(false);
        dialog.show();

        SharedPreferences preferences = getContext().getSharedPreferences("accountV", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "null");
        String name = preferences.getString("name", "null");
        userNameTv.setText(name + "님, ");
        Rest rest = new Rest();
        Call<My> call = rest.getRetrofitService().getMy(id);
        call.enqueue(new Callback<My>() {
            @Override
            public void onResponse(Call<My> call, Response<My> response) {
                if (response.isSuccessful()) {
                    if(response.body().getRouteName()!=null) {
                        routeTv.setText(response.body().getRouteName());
                    }

                    itemCntTv.setText(response.body().getItemCnt()+"개의 화물");
                    Glide.with(getContext())
                            .load("http://172.30.1.57:3200/public/uploads/"+response.body().getProfilePath())
                            .into(userIv);
                } else {
                    Toast.makeText(getContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<My> call, Throwable t) {
                Toast.makeText(getContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.dismiss();
    }

    private void moveToSchedule() {
        progressDialog.show();
        Intent intent = new Intent(getContext(), ScheduleActivity.class);
        startActivity(intent);
    }

    private void moveToTutorial() {
        Intent intent = new Intent(getContext(), TutorialActivity.class);
        intent.putExtra("first", false);
        startActivity(intent);
    }

    private void moveToAsk() {
        Intent intent = new Intent(getContext(), AskActivity.class);
        startActivity(intent);
    }

    private void checkLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences account = getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = account.edit();
                editor.clear();
                editor.commit();
                account = getContext().getSharedPreferences("accountV", Context.MODE_PRIVATE);
                editor = account.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                ((Activity) getContext()).finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
//        headerView.updateNoti();
        progressDialog.dismiss();
    }
}
