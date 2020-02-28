package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtsealove.github.buslinkerpt.R;

import java.util.ArrayList;

public class RouteRecyclerAdapter extends RecyclerView.Adapter<RouteRecyclerAdapter.ItemViewHolder> {
    Context context;

    public RouteRecyclerAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<RouteView> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_route, parent, false);
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

    public void addItem(RouteView data) {
        listData.add(data);
    }

    public ArrayList<RouteView> getItemList() {
        return listData;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, timeTv, addrTv;
        View topLine, bottomLine;
        ImageView indicator, currentIndicator;
        RelativeLayout clickLayout;

        ItemViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name);
            timeTv = itemView.findViewById(R.id.time);
            addrTv = itemView.findViewById(R.id.addr);
            topLine = itemView.findViewById(R.id.topLine);
            bottomLine = itemView.findViewById(R.id.bottomLine);
            indicator = itemView.findViewById(R.id.indicator);
            currentIndicator = itemView.findViewById(R.id.currentIndicator);
            clickLayout = itemView.findViewById(R.id.clickLayout);
        }

        void onBind(final RouteView data) {
            if (data.isDisable()) {
                timeTv.setAlpha(0.4f);
                nameTv.setAlpha(0.4f);
                addrTv.setAlpha(0.4f);
                topLine.setBackgroundColor(context.getColor(R.color.navy));
                bottomLine.setBackgroundColor(context.getColor(R.color.navy));
                indicator.setImageDrawable(context.getDrawable(R.drawable.route_start));
            } else {
                topLine.setBackgroundColor(context.getColor(R.color.yellow));
                bottomLine.setBackgroundColor(context.getColor(R.color.yellow));
            }
            if (data.isCurrent()) {
                topLine.setBackgroundColor(context.getColor(R.color.navy));
                indicator.setVisibility(View.GONE);
                currentIndicator.setVisibility(View.VISIBLE);
            }

            if (data.isFirst()) {
                topLine.setAlpha(0.0f);
            } else if (data.isLast()) {
                bottomLine.setAlpha(0.0f);
            }
            timeTv.setText(data.getTime());
            nameTv.setText(data.getName());
            addrTv.setText(data.getAddr());
        }
    }
}