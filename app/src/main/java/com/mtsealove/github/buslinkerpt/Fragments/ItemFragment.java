package com.mtsealove.github.buslinkerpt.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mtsealove.github.buslinkerpt.R;
import com.mtsealove.github.buslinkerpt.Restful.Response.Cnt;
import com.mtsealove.github.buslinkerpt.Restful.Rest;
import com.mtsealove.github.buslinkerpt.RouteActivity;
import com.mtsealove.github.buslinkerpt.ScheduleActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragment extends Fragment {
    Button routeBtn, scheduleBtn;
    TextView takeTv, sendTv, notExistTv;
    LinearLayout existLayout;
    ProgressDialog dialog;
    private int delivery = 0, take = 0;

    public ItemFragment() {
        // Required empty public constructor
    }


    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        routeBtn = view.findViewById(R.id.routeBtn);
        scheduleBtn = view.findViewById(R.id.scheduleBtn);
        sendTv = view.findViewById(R.id.sendTv);
        takeTv = view.findViewById(R.id.takeTv);
        existLayout = view.findViewById(R.id.existLayout);
        notExistTv = view.findViewById(R.id.notExistTv);
        Rest rest = new Rest();
        SharedPreferences preferences = getContext().getSharedPreferences("accountV", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "null");
        Call<Cnt> call = rest.getRetrofitService().getCnt(id);
        call.enqueue(new Callback<Cnt>() {
            @Override
            public void onResponse(Call<Cnt> call, Response<Cnt> response) {
                if (response.isSuccessful() && (response.body().getLogi() != 0 || response.body().getOwner() != 0)) {
                    int Logi = response.body().getLogi();
                    int Owner = response.body().getOwner();
                    Log.e("logi", String.valueOf(Logi));
                    Log.e("owner", String.valueOf(Owner));
                    delivery = Logi;
                    take = Owner;
                    sendTv.setText(Logi + " 건의 배송");
                    takeTv.setText(Owner + "건의 수거");
                } else {
                    routeBtn.setVisibility(View.GONE);
                    scheduleBtn.setVisibility(View.VISIBLE);
                    existLayout.setVisibility(View.GONE);
                    notExistTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Cnt> call, Throwable t) {

            }
        });

        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RouteActivity.class);
                intent.putExtra("delivery", delivery);
                intent.putExtra("take", take);
                startActivity(intent);
            }
        });
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                startActivity(intent);
            }
        });

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("잠시만 기다려주세요");
        dialog.setCancelable(false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        dialog.dismiss();
    }
}
