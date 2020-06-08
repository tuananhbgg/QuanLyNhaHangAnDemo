package com.example.thaythetn_mobile.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.BanAn_Model;

import java.util.ArrayList;
public class DatBan_Adapter extends BaseAdapter {
    private ArrayList<BanAn_Model> models;
    private LayoutInflater inflater;

    public DatBan_Adapter(ArrayList<BanAn_Model> models, LayoutInflater inflater) {
        this.models = models;
        this.inflater = inflater;
    }
    public void setModels(ArrayList<BanAn_Model> models) {
        this.models = models;
    }
    @Override
    public int getCount() {
        return models.size();
    }
    @Override
    public BanAn_Model getItem(int i) {
        return models.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_banan, null);
        }
        BanAn_Model model = models.get(position);
        ImageView imgBanAn = view.findViewById(R.id.imgBanAn);
        TextView tvBanSo = view.findViewById(R.id.tvBanSo);
        LinearLayout linear = view.findViewById(R.id.linear);
        imgBanAn.setBackgroundResource(R.drawable.banantest);
        tvBanSo.setText("Bàn " + model.getIdBanAn());
        if (model.getConTrong() == 0) {
            linear.setBackgroundColor(Color.parseColor("#E7F653"));
        } else {
            linear.setBackgroundColor(Color.parseColor("#ECECEC"));
        }
        return view;
    }
}
