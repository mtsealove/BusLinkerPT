package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtsealove.github.buslinkerpt.Database.AlertOpenHelper;
import com.mtsealove.github.buslinkerpt.R;
import com.mtsealove.github.buslinkerpt.ScheduleActivity;

import java.util.ArrayList;

public class AlertRecyclerAdapter extends RecyclerView.Adapter<AlertRecyclerAdapter.ItemViewHolder> {
    Context context;

    public AlertRecyclerAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<AlertView> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_alert, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(AlertView data) {
        listData.add(data);
    }

    public ArrayList<AlertView> getItemList() {
        return listData;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout clickLayout;
        TextView titleTv, msgTv, timeTv;
        View status;
        AlertOpenHelper openHelper;
        SQLiteDatabase db;
        Intent intent;

        ItemViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.alertTitle);
            msgTv = itemView.findViewById(R.id.alertMsg);
            timeTv = itemView.findViewById(R.id.alertTime);
            status = itemView.findViewById(R.id.alertStatus);
            clickLayout = itemView.findViewById(R.id.clickLayout);
        }

        void onBind(final AlertView data) {
            titleTv.setText(data.getTitle());
            msgTv.setText(data.getMessage());
            timeTv.setText(data.getTime());
            if (!data.isStatus()) {
                status.setBackground(context.getDrawable(R.drawable.noti_prev));
            }

//            set read
            clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openHelper = new AlertOpenHelper(context, AlertOpenHelper.Table, null, 1);
                    db = openHelper.getWritableDatabase();
                    openHelper.SetRead(db, data.getID());
                    switch (data.getAction()) {
                        case "schedule":
                            intent = new Intent(context, ScheduleActivity.class);
                            context.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}