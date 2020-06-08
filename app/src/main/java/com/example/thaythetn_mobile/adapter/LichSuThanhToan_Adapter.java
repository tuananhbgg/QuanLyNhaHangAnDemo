package com.example.thaythetn_mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.HoaDonThanhToan_Model;
import com.example.thaythetn_mobile.model.MonDaChon_Model;

import java.util.ArrayList;
public class LichSuThanhToan_Adapter extends BaseAdapter {
    public ArrayList<HoaDonThanhToan_Model> models;
    private LayoutInflater inflater;

    public LichSuThanhToan_Adapter(ArrayList<HoaDonThanhToan_Model> models, LayoutInflater inflater) {
        this.models = models;
        this.inflater = inflater;
    }

    public ArrayList<HoaDonThanhToan_Model> getModels() {
        return models;
    }

    public void setModels(ArrayList<HoaDonThanhToan_Model> models) {
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public HoaDonThanhToan_Model getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.item_lichsuthanhtoan,null);
        }
        TextView tvNgayTT = view.findViewById(R.id.tvNgayTT);
        TextView tvSoBanTT = view.findViewById(R.id.tvSoBanTT);
        TextView tvTongGia = view.findViewById(R.id.tvTongGia);
        HoaDonThanhToan_Model model = models.get(i);
        tvNgayTT.setText("Ngày: "+model.getNgayTaoHoaDon());
        DBController db = new DBController(view.getContext());
        db.initDB();
        MonDaChon_Model m = db.getDonHangTheoNgay(model.getNgayTaoHoaDon()).get(0);
        tvSoBanTT.setText("Bàn: "+ String.valueOf(m.getIdBanAn()));
        tvTongGia.setText("Thành tiền: "+String.valueOf(model.getTongGia()+" VNĐ"));
        return view;
    }
}
