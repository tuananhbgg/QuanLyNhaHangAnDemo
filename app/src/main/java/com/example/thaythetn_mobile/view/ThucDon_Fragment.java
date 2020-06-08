package com.example.thaythetn_mobile.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.ThucDon_Adapter;
import com.example.thaythetn_mobile.model.TheLoai_ThucDon_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThucDon_Fragment extends Fragment {
    public static DBController dbControllerThucDon;
    public static ThucDon_Adapter adapter;
    private ExpandableListView elvThucDon;


    public ThucDon_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuc_don, container, false);
        dbControllerThucDon = new DBController(getContext());
        dbControllerThucDon.initDB();
        elvThucDon = view.findViewById(R.id.elvThucDon);
        adapter = new ThucDon_Adapter(getDataThucDon(), getLayoutInflater());
        elvThucDon.setAdapter(adapter);

        registerForContextMenu(elvThucDon);
        return view;
    }

    public void lamMoi() {
        adapter.setGroupItem(getDataThucDon());
        adapter.notifyDataSetChanged();
    }


    //private ArrayList<TheLoai_ThucDon_Model> theLoai_thucDon_models = new ArrayList<>();
    private ArrayList<ThucDon_Model> monan_models = new ArrayList<>();
    private ArrayList<ThucDon_Model> nuocUong_models = new ArrayList<>();

    private ArrayList<TheLoai_ThucDon_Model> getDataThucDon() {
        ArrayList<TheLoai_ThucDon_Model> dsTheLoai = new ArrayList<>();
        monan_models = dbControllerThucDon.getDSMonAn();
        nuocUong_models = dbControllerThucDon.getDSNuocUong();
        dsTheLoai.add(new TheLoai_ThucDon_Model("Món ăn", monan_models));
        dsTheLoai.add(new TheLoai_ThucDon_Model("Nước uống ", nuocUong_models));
        return dsTheLoai;
    }


}
