package com.mtsealove.github.buslinkerpt.Design;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

public class StatusBarManager {

    public static void setStatusBarWhite(Context context) {   //하얀 배경에 검은 아이콘
        View view=((Activity)context).getWindow().getDecorView();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if(view!=null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                ((Activity)context).getWindow().setStatusBarColor(Color.parseColor("#F4F4F4"));
            }
        }
    }

    public static void setStatusBarYellow(Context context) {   //하얀 배경에 검은 아이콘
        View view=((Activity)context).getWindow().getDecorView();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if(view!=null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                ((Activity)context).getWindow().setStatusBarColor(Color.parseColor("#ffcc00"));
            }
        }
    }

    public static void setStatusBarNaby(Context context) {   //하얀 배경에 검은 아이콘
        View view = ((Activity) context).getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                ((Activity) context).getWindow().setStatusBarColor(Color.parseColor("#001f46"));
            }
        }
    }
}
