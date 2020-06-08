package com.example.thaythetn_mobile.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class KhoHang_Adapter extends FragmentPagerAdapter {
    private Fragment fragment[];

    public KhoHang_Adapter(@NonNull FragmentManager fm, Fragment fragment[] ) {
        super(fm);
        this.fragment = fragment;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragment[position];
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
                return "Tồn Kho";
            case 1:
                return "Đã Bán";
        }
    }
}
