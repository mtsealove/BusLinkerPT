package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mtsealove.github.buslinkerpt.AlertActivity;
import com.mtsealove.github.buslinkerpt.R;

public class HeaderView extends RelativeLayout {
    Context context;
    ImageView notiIv;
    //AlertOpenHelper openHelper;
    SQLiteDatabase db;

    public HeaderView(Context context) {
        super(context);
        init(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_header, null, false);
        notiIv = view.findViewById(R.id.notiIv);
        setNoti();
        addView(view);
    }


    private void setNoti() {
        notiIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlertActivity.class);
                context.startActivity(intent);
            }
        });
    }


    /*
    public void updateNoti() {
        openHelper = new AlertOpenHelper(context, AlertOpenHelper.Table, null, 1);
        db = openHelper.getReadableDatabase();
        String query = "select count(*) from Alert where Read=1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if (count != 0) {
                notiIv.setImageDrawable(context.getDrawable(R.drawable.noti2));
            } else {
                notiIv.setImageDrawable(context.getDrawable(R.drawable.noti));
            }
        }
        cursor.close();
        db.close();
        openHelper.close();
    }*/
}
