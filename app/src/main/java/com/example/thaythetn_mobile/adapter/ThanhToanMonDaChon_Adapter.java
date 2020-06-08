package com.example.thaythetn_mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.MonDaChon_Model;
import com.example.thaythetn_mobile.model.NguyenLieu_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;
import com.example.thaythetn_mobile.view.ThanhToan_Activity;

import java.util.ArrayList;

public class ThanhToanMonDaChon_Adapter extends BaseAdapter {
    private ArrayList<MonDaChon_Model> models;
    private LayoutInflater inflater;
    ThanhToan_Activity thanhToan_activity;

    public ThanhToanMonDaChon_Adapter(ArrayList<MonDaChon_Model> models, LayoutInflater inflater, ThanhToan_Activity thanhToan_activity) {
        this.models = models;
        this.inflater = inflater;
        this.thanhToan_activity = thanhToan_activity;
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
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_mon_da_chon, null);
        }
        final TextView tvTenMonDaChon = view.findViewById(R.id.tvTenMonDaChon);
        final TextView tvSoLuongDaChon = view.findViewById(R.id.tvSoLuongDaChon);
        ImageView imgCongMon = view.findViewById(R.id.imgCongMon);
        ImageView imgTruMon = view.findViewById(R.id.imgTruMon);

        final MonDaChon_Model model = models.get(i);
        final DBController dbController = new DBController(view.getContext());
        dbController.initDB();
        final ThucDon_Model thucDon_model = dbController.getThucDon(model.getIdMonAn()).get(0);
        if (thucDon_model.getTheLoai().equals("Nước uống")) {
            imgCongMon.setVisibility(View.VISIBLE);
            imgTruMon.setVisibility(View.VISIBLE);
        } else {
            imgCongMon.setVisibility(View.GONE);
            imgTruMon.setVisibility(View.GONE);
        }
        tvTenMonDaChon.setText(thucDon_model.getTenMon());
        tvSoLuongDaChon.setText(String.valueOf(model.getSoLuong()));

        imgTruMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getSoLuong() > 1) {
                    model.setSoLuong(model.getSoLuong() - 1);
                    dbController.capNhatSoLuongMonChon(model.getIdDonHang(), model.getSoLuong());
                    tvSoLuongDaChon.setText(String.valueOf(model.getSoLuong()));
                    NguyenLieu_Model nguyenLieu_model = dbController.getNguyenLieuTheoTen(dbController.getThucDon(model.getIdMonAn()).get(0).getTenNL()).get(0);
                    int slMoi =nguyenLieu_model.getSoLuong() +1;
                    nguyenLieu_model.setSoLuong(slMoi);
                    dbController.suaNguyenLieu(nguyenLieu_model);
                    int tongTien = thanhToan_activity.tongTienThanhToan();
                    thanhToan_activity.tvTienThanhToan.setText(tongTien+" VNĐ");
                }
            }
        });
        imgCongMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewclick) {
                NguyenLieu_Model nguyenLieu_model = dbController.getNguyenLieuTheoTen(dbController.getThucDon(model.getIdMonAn()).get(0).getTenNL()).get(0);
                if(nguyenLieu_model.getSoLuong()>0){
                    model.setSoLuong(model.getSoLuong() + 1);
                    dbController.capNhatSoLuongMonChon(model.getIdDonHang(), model.getSoLuong());
                    tvSoLuongDaChon.setText(String.valueOf(model.getSoLuong()));

                    int slMoi = nguyenLieu_model.getSoLuong()-1;
                    nguyenLieu_model.setSoLuong(slMoi);
                    dbController.suaNguyenLieu(nguyenLieu_model);
                    int tongTien = thanhToan_activity.tongTienThanhToan();
                    thanhToan_activity.tvTienThanhToan.setText(tongTien+" VNĐ");
                }else {
                    Toast.makeText(thanhToan_activity, "Sản phẩm này đã hết hàng.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

}
