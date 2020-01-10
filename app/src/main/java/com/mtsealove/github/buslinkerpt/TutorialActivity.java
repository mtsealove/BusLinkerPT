package com.mtsealove.github.buslinkerpt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mtsealove.github.buslinkerpt.Design.StatusBarManager;
import com.mtsealove.github.buslinkerpt.ui.main.SectionsPagerAdapter;

public class TutorialActivity extends AppCompatActivity {
    Button nextBtn;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        StatusBarManager.setStatusBarWhite(this);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        nextBtn = findViewById(R.id.nextBtn);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //when show last page, change text and color
                if (position == sectionsPagerAdapter.getCount() - 1) {
                    nextBtn.setBackgroundDrawable(getDrawable(R.drawable.second_btn));
                    nextBtn.setText("시작하기");
                } else {
                    nextBtn.setText("계속하기");
                    nextBtn.setBackgroundDrawable(getDrawable(R.drawable.primary_btn));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTutorial();
            }
        });
    }

    //change tutorial by button
    private void NextTutorial() {
        int current = viewPager.getCurrentItem();
        if (current < sectionsPagerAdapter.getCount() - 1) {
            viewPager.setCurrentItem(current + 1);
        } else {
            moveMain();
        }
    }

    //move to main page
    private void moveMain() {
        boolean isFirst=getIntent().getBooleanExtra("isFirst", true);
        if(isFirst) {
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("tutorial", false);
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}