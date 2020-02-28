package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mtsealove.github.buslinkerpt.R;

import java.util.ArrayList;

public class CalendarRouteRecyclerAdapter extends RecyclerView.Adapter<CalendarRouteRecyclerAdapter.ItemViewHolder> {
    Context context;

    public CalendarRouteRecyclerAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<CalendarRouteView> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_calendar_route, parent, false);
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

    public void addItem(CalendarRouteView data) {
        listData.add(data);
    }

    public ArrayList<CalendarRouteView> getItemList() {
        return listData;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, addrTv, timeTv;

        ItemViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            addrTv = itemView.findViewById(R.id.addrTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }

        void onBind(final CalendarRouteView data) {
            nameTv.setText(data.getName());
            addrTv.setText(data.getAddr());
            timeTv.setText(data.getTime());
        }
    }
}