package com.example.thaythetn_mobile.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.DonDatBan_Adapter;
import com.google.android.material.tabs.TabLayout;

public class DonDatBan_Activity extends AppCompatActivity {

    private ViewPager viewPagerDatBan;
    private TabLayout tabPagerDatBan;
    public static String banSo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_dat_ban);
        viewPagerDatBan = findViewById(R.id.viewPagerDatBan);
        tabPagerDatBan = findViewById(R.id.tabPagerDatBan);
        Fragment fragments[] = new Fragment[]{new ChonMon_Fragment(), new MonDaChon_Fragment()};
        viewPagerDatBan.setAdapter(new DonDatBan_Adapter(getSupportFragmentManager(),fragments));
        tabPagerDatBan.setupWithViewPager(viewPagerDatBan);

        if(getIntent() != null){
            banSo = getIntent().getStringExtra("banso");
            //Toast.makeText(this, banSo+"", Toast.LENGTH_SHORT).show();
            if(banSo != null){
                getSupportActionBar().setTitle("Bàn Số "+banSo);
            }
        }
    }

    @Override
    protected void onDestroy() {
        DatBan_Fragment fragment = new DatBan_Fragment();
        fragment.lamMoi();
        super.onDestroy();
    }
}
