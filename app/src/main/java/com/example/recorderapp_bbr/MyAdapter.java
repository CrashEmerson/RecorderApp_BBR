package com.example.recorderapp_bbr;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.recorderapp_bbr.RecordListFragment;
import com.example.recorderapp_bbr.RecorderFragment;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecordListFragment recordListFragment = new RecordListFragment();
                return recordListFragment;
            case 1:
                RecorderFragment recorderFragment = new RecorderFragment();
                return recorderFragment;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
