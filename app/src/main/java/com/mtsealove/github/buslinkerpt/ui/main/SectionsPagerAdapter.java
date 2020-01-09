package com.mtsealove.github.buslinkerpt.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mtsealove.github.buslinkerpt.Fragments.TutorialFragment;
import com.mtsealove.github.buslinkerpt.R;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentArrayList;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragmentArrayList=new ArrayList<>();
        fragmentArrayList.add(TutorialFragment.newInstance("출근 및 퇴근", "출근 및 퇴근시에는\n" +
                "기사님이 생성하는 QR코드를\n" +
                "스캔하세요.", "test"));
        fragmentArrayList.add(TutorialFragment.newInstance("화물 확인", "하단 화물과 \n" +
                "상단 화물의 현황을\n" +
                "알아보세요.", "test"));
        fragmentArrayList.add(TutorialFragment.newInstance("상태 전송", "화물의 상태가 변경될 경우에는\n" +
                "상태를 실시간으로\n" +
                "전송해주세요.", "test"));
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}