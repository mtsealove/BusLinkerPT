package com.mtsealove.github.buslinkerpt.Fragments;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;

public class MainSectionsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;
    private final Context mContext;
    ArrayList<Fragment> fragmentArrayList;

    public MainSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragmentArrayList = new ArrayList<>();
        titles=new ArrayList<>();
        titles.add("");
        titles.add("");
        titles.add("");
        fragmentArrayList.add(CommuteFragment.newInstance());
        fragmentArrayList.add(MyFragment.newInstance());
        fragmentArrayList.add(ItemFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragmentArrayList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return fragmentArrayList.size();
    }
}