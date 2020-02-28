package com.mtsealove.github.buslinkerpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.Fragments.MainSectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager viewPager;
    MainSectionsPagerAdapter mainSectionsPagerAdapter;
    int tabIconColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainSectionsPagerAdapter = new MainSectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mainSectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.unselected);
        setTabs();

        StatusBarManager.setStatusBarWhite(this);
    }

    //    set tab color and name
    private void setTabs() {
        tabs.getTabAt(0).setIcon(R.drawable.tab_commute);
        tabs.getTabAt(0).setText("");

        tabs.getTabAt(1).setText("");
        tabs.getTabAt(2).setIcon(R.drawable.tab_item);
        tabs.getTabAt(2).setText("");
        ImageView logoTab = new ImageView(this);
        logoTab.setImageDrawable(getDrawable(R.drawable.logo_tab));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(240, 240);
        params.setMargins(0, 0, 0, 20);
        logoTab.setLayoutParams(params);
        tabs.getTabAt(1).setCustomView(logoTab);
        tabs.selectTab(tabs.getTabAt(1));

        tabs.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabs.getTabAt(2).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tabs.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        if (tab.getIcon() != null)
                            tab.getIcon().clearColorFilter();
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        if (tab.getIcon() != null)
                            tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

    }


    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "'뒤로가기' 버튼을 한 번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
