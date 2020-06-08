package com.example.thaythetn_mobile.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.NguyenLieu_Adapter;
import com.example.thaythetn_mobile.model.NguyenLieu_Model;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NguyenLieu_Fragment extends Fragment {

    public static DBController dbNguyenLieu;
    public static NguyenLieu_Adapter nguyen_lieu_adapter;
    private RecyclerView rcvNguyenLieu;
    private ArrayList<NguyenLieu_Model> ds;

    public NguyenLieu_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nguyen_lieu_, container, false);
        dbNguyenLieu = new DBController(getContext());
        dbNguyenLieu.initDB();
        ds = new ArrayList<>();
        nguyen_lieu_adapter = new NguyenLieu_Adapter(getDSNguyeLieu());
        rcvNguyenLieu = view.findViewById(R.id.rcvNguyenLieu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvNguyenLieu.setLayoutManager(layoutManager);
        rcvNguyenLieu.setAdapter(nguyen_lieu_adapter);
        nguyen_lieu_adapter.notifyDataSetChanged();

        return view;
    }

    private ArrayList<NguyenLieu_Model> getDSNguyeLieu() {
        ds = dbNguyenLieu.getDSNguyenLieu();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
        return ds;
    }
    public void LamMoi(){
        nguyen_lieu_adapter.setModels(getDSNguyeLieu());
        nguyen_lieu_adapter.notifyDataSetChanged();
    }

}
