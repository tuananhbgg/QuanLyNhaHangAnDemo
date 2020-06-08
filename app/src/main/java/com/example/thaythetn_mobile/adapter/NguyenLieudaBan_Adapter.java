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
import com.example.thaythetn_mobile.model.NguyenLieuDaBan_Model;

import java.util.ArrayList;

public class NguyenLieudaBan_Adapter extends BaseAdapter {
    public static ArrayList<NguyenLieuDaBan_Model> models;
    private LayoutInflater inflater;
//    boolean flag;
//    String ngayBan;

    public static ArrayList<NguyenLieuDaBan_Model> getModels() {
        return models;
    }

    public static void setModels(ArrayList<NguyenLieuDaBan_Model> models) {
        NguyenLieudaBan_Adapter.models = models;
    }

    public NguyenLieudaBan_Adapter(ArrayList<NguyenLieuDaBan_Model> models, LayoutInflater inflater) {
        this.models = models;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public NguyenLieuDaBan_Model getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_nguyen_lieu, null);
       } //else {
//            flag = false;
//            ngayBan = "";
//        }
            ImageView imgThemNL;
            TextView tvTenNLNhap, tvSLNhap, tvGiaNhap, tvNgayBan;
            tvTenNLNhap = view.findViewById(R.id.tvTenNLNhap);
            tvSLNhap = view.findViewById(R.id.tvSLNhap);
            tvGiaNhap = view.findViewById(R.id.tvGiaNhap);
            imgThemNL = view.findViewById(R.id.imgThemNL);
            imgThemNL.setVisibility(View.GONE);
            NguyenLieuDaBan_Model model = models.get(position);
            DBController controller = new DBController(view.getContext());
            controller.initDB();
//        Toast.makeText(view.getContext(), flag + " " + ngayBan, Toast.LENGTH_SHORT).show();
//        if (flag == false) {
//            ngayBan = model.getNgayBan();
//            flag = true;
//        } else {
//            if (ngayBan == model.getNgayBan()) {
//                tvNgayBan.setText(model.getNgayBan());
//                ngayBan = model.getNgayBan();
//            } else {
//
//                tvNgayBan.setVisibility(View.GONE);
//            }
////            Toast.makeText(view.getContext(), "hihi", Toast.LENGTH_SHORT).show();
////            tvNgayBan.setVisibility(View.GONE);
//        }

            tvTenNLNhap.setText("Tên: " + controller.getThucDon(model.getIDMon()).get(0).getTenNL());
            tvGiaNhap.setText("Ngày " + model.getNgayBan());
            tvSLNhap.setText("SL " + String.valueOf(model.getSoLuong()) + " " + model.getDonVi());
            return view;
        }

}
