package com.example.thaythetn_mobile.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
public class DonDatBan_Adapter extends FragmentPagerAdapter {
    private Fragment fragment[];
    public DonDatBan_Adapter(@NonNull FragmentManager fm, Fragment fragment[]) {
        super(fm);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragment[position];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default:
                return "Chọn Món";
            case 1:
                return "Món Đã Chọn";
        }
    }
}
