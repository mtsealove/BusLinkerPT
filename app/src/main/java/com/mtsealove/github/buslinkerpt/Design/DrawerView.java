package com.mtsealove.github.buslinkerpt.Design;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mtsealove.github.buslinkerpt.CheckItemActivity;
import com.mtsealove.github.buslinkerpt.CommuteActivity;
import com.mtsealove.github.buslinkerpt.MainActivity;
import com.mtsealove.github.buslinkerpt.R;
import com.mtsealove.github.buslinkerpt.SendStatusActivity;
import com.mtsealove.github.buslinkerpt.SupportActivity;
import com.mtsealove.github.buslinkerpt.TutorialActivity;

public class DrawerView extends RelativeLayout {
    Context context;
    String contextName;
    LinearLayout commuteBtn, checkItemBtn, sendStatusBtn, tutorialBtn, requireBtn, logoutBtn;

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
        contextName = context.getClass().getSimpleName();
        //init views
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_drawer, this, false);
        commuteBtn = view.findViewById(R.id.commuteBtn);
        checkItemBtn = view.findViewById(R.id.checkItemBtn);
        sendStatusBtn = view.findViewById(R.id.statusSendBtn);
        tutorialBtn = view.findViewById(R.id.tutorialBtn);
        requireBtn = view.findViewById(R.id.requireBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        addView(view);

        commuteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMain();
            }
        });

        sendStatusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSendStatus();
            }
        });
        tutorialBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTutorial();
            }
        });
        checkItemBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCheckItem();
            }
        });
        requireBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSupport();
            }
        });
    }

    private void moveMain() {
        if (!contextName.equals("MainActivity")) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
        closeDrawer();
    }

    private void moveSendStatus() {
        if (!contextName.equals("SendStatusActivity")) {
            Intent intent = new Intent(context, SendStatusActivity.class);
            context.startActivity(intent);
        }
        closeDrawer();
    }

    private void moveTutorial() {
        Intent intent = new Intent(context, TutorialActivity.class);
        intent.putExtra("isFirst", false);
        context.startActivity(intent);
        closeDrawer();
    }

    private void moveCheckItem() {
        Intent intent = new Intent(context, CheckItemActivity.class);
        context.startActivity(intent);
        closeDrawer();
    }

    private void moveSupport() {
        Intent intent = new Intent(context, SupportActivity.class);
        context.startActivity(intent);
        closeDrawer();
    }


    private void closeDrawer() {
        switch (contextName) {
            case "MainActivity":
                MainActivity.closeDrawer();
                break;
            case "CommuteActivity":
                CommuteActivity.closeDrawer();
                break;
            case "SendStatusActivity":
                SendStatusActivity.closeDrawer();
                break;
            case "SupportActivity":
                SupportActivity.closeDrawer();
                break;
            case "CheckItemActivity":
                CheckItemActivity.closeDrawer();
                break;
        }
    }
}
