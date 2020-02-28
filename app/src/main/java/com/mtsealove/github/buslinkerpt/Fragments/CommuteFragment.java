package com.mtsealove.github.buslinkerpt.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mtsealove.github.buslinkerpt.Design.HeaderView;
import com.mtsealove.github.buslinkerpt.QrActivity;
import com.mtsealove.github.buslinkerpt.R;
import com.mtsealove.github.buslinkerpt.Restful.Response.Result;
import com.mtsealove.github.buslinkerpt.Restful.Rest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommuteFragment extends Fragment {
    Button startBtn, endBtn;
    TextView userNameTv;

    public CommuteFragment() {
        // Required empty public constructor
    }

    public static CommuteFragment newInstance() {
        CommuteFragment fragment = new CommuteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commute, container, false);
        startBtn = view.findViewById(R.id.startBtn);
        endBtn = view.findViewById(R.id.endBtn);
        userNameTv = view.findViewById(R.id.userName);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveQr(true);
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveQr(false);
            }
        });

        getCommute();
        setUserName();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void setUserName() {
        SharedPreferences pref = getContext().getSharedPreferences("accountV", Context.MODE_PRIVATE);
        String userName = pref.getString("name", "사용자");
        userNameTv.setText(userName + " 님");
    }

    private void moveQr(boolean start) {
        Intent intent = new Intent(getContext(), QrActivity.class);
        intent.putExtra("work", start);
        startActivity(intent);
    }

    private void getCommute() {
        SharedPreferences pref = getContext().getSharedPreferences("accountV", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        Rest rest = new Rest();
        Call<Result> call = rest.getRetrofitService().getCommute(id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()) {
                    if(response.body().isResult()) {
                        startBtn.setBackground(getContext().getDrawable(R.drawable.disable_btn));
                        startBtn.setClickable(false);
                        endBtn.setBackground(getContext().getDrawable(R.drawable.second_btn));
                        endBtn.setClickable(true);
                    } else {
                        endBtn.setBackground(getContext().getDrawable(R.drawable.disable_btn));
                        endBtn.setClickable(false);
                        startBtn.setBackground(getContext().getDrawable(R.drawable.second_btn));
                        startBtn.setClickable(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
     //   headerView.updateNoti();
        getCommute();
    }
}
