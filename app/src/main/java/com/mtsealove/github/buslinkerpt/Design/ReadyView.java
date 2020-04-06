package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mtsealove.github.buslinkerpt.R;

public class ReadyView extends LinearLayout {
    Context context;
    ImageView profileIv;
    TextView nameTv;
    private String userID;

    public ReadyView(Context context) {
        super(context);
        init(context);
    }

    public ReadyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReadyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ReadyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_ready, null, false);
        profileIv = view.findViewById(R.id.profileIv);
        nameTv = view.findViewById(R.id.nameTv);
        addView(view);
    }

    public void setProfile(String id, String name, String img) {
        this.userID = id;
        if (name != null) {
            this.nameTv.setText(name);
        } else {
            nameTv.setText("미입장");
        }

        Glide.with(context)
                .load("http://172.30.1.60/public/uploads/" + img)
                .apply(RequestOptions.circleCropTransform())
                .error(context.getDrawable(R.drawable.user))
                .into(profileIv);
    }

    public String getUserID() {
        return userID;
    }

    public void setReady() {
        profileIv.setBackground(context.getDrawable(R.drawable.ready_true));
    }
}
