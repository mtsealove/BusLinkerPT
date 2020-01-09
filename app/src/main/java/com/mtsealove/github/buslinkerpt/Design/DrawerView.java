package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mtsealove.github.buslinkerpt.R;

public class DrawerView extends RelativeLayout {
    Context context;
    LinearLayout commuteBtn, checkItemBtn, sendStatusBtn, tutorialBtn, requireBtn, logBtn, logoutBtn;

    public DrawerView(Context context) {
        super(context);
        init(context);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        //init views
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_drawer, this, false);
        commuteBtn = view.findViewById(R.id.commuteBtn);
        checkItemBtn = view.findViewById(R.id.checkItemBtn);
        sendStatusBtn = view.findViewById(R.id.statusSendBtn);
        tutorialBtn = view.findViewById(R.id.tutorialBtn);
        requireBtn = view.findViewById(R.id.requireBtn);
        logBtn = view.findViewById(R.id.logBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        addView(view);
    }
}
