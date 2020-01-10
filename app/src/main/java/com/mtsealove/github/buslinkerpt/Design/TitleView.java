package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mtsealove.github.buslinkerpt.CommuteActivity;
import com.mtsealove.github.buslinkerpt.MainActivity;
import com.mtsealove.github.buslinkerpt.R;
import com.mtsealove.github.buslinkerpt.SendStatusActivity;

public class TitleView extends RelativeLayout {
    Context context;
    TextView titleTv;
    ImageView menuIv;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_title, this, false);
        titleTv = view.findViewById(R.id.titleTv);
        menuIv = view.findViewById(R.id.menuIv);
        menuIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
        addView(view);
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    private void openDrawer() {
        String contextName=context.getClass().getSimpleName();
        switch (contextName) {
            case "MainActivity":
                MainActivity.openDrawer();
                break;
            case "CommuteActivity":
                CommuteActivity.openDrawer();
                break;
            case "SendStatusActivity":
                SendStatusActivity.openDrawer();
                break;
        }
    }
}
