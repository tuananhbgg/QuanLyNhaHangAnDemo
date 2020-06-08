package com.example.thaythetn_mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.MonDaChon_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;

import java.util.ArrayList;

public class MonDaChon_Adapter extends BaseAdapter {
    private ArrayList<MonDaChon_Model> models;
    private LayoutInflater inflater;

    public MonDaChon_Adapter(ArrayList<MonDaChon_Model> models, LayoutInflater inflater) {
        this.models = models;
        this.inflater = inflater;
    }

    public void setModels(ArrayList<MonDaChon_Model> models) {
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public MonDaChon_Model getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.item_mon_da_chon,null);
        }
        TextView tvTenMonDaChon = view.findViewById(R.id.tvTenMonDaChon);
        TextView tvSoLuongDaChon = view.findViewById(R.id.tvSoLuongDaChon);
        MonDaChon_Model model = models.get(i);
        DBController dbController = new DBController(view.getContext());
        dbController.initDB();
        ThucDon_Model thucDon_model = dbController.getThucDon(model.getIdMonAn()).get(0);
        tvTenMonDaChon.setText(thucDon_model.getTenMon());
        tvSoLuongDaChon.setText(String.valueOf(model.getSoLuong()));
        return view;
    }
}
