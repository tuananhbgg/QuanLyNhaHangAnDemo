package com.example.thaythetn_mobile.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.KhoHang_Adapter;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class KhoHang_Fragment extends Fragment {

    private ViewPager viewPagerKhoHang;
    private TabLayout tabPagerKhoHang;
    public KhoHang_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kho_hang, container, false);
        viewPagerKhoHang = view.findViewById(R.id.viewPagerKhoHang);
        tabPagerKhoHang = view.findViewById(R.id.tabPagerKhoHang);
        final NguyenLieu_Fragment fragment = new NguyenLieu_Fragment();
        Fragment fragments[] = new Fragment[]{fragment, new NguyenLieuDaBan_Fragment()};
        viewPagerKhoHang.setOffscreenPageLimit(2);
        viewPagerKhoHang.setAdapter(new KhoHang_Adapter(getChildFragmentManager(),fragments));
        tabPagerKhoHang.setupWithViewPager(viewPagerKhoHang);
        viewPagerKhoHang.setOffscreenPageLimit(0);
        return view;
    }

}
