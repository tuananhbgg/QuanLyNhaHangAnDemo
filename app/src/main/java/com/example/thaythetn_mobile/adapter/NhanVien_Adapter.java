package com.example.thaythetn_mobile.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.NhanVien_Model;

import java.util.ArrayList;

public class NhanVien_Adapter extends BaseAdapter {
    public ArrayList<NhanVien_Model> models;
    private LayoutInflater inflater;

    public NhanVien_Adapter(ArrayList<NhanVien_Model> models, LayoutInflater inflater) {
        this.models = models;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public NhanVien_Model getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.item_nhanvien,null);
        }
        NhanVien_Model model = models.get(position);
        ImageView imgAnhNV = view.findViewById(R.id.imgAnhNV);
        TextView tvTenNV = view.findViewById(R.id.tvTenNV);
        TextView tvQueQuan = view.findViewById(R.id.tvQueQuan);

        if(model.getAnh() != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(model.getAnh(), 0, model.getAnh().length);
            imgAnhNV.setImageBitmap(bitmap);
        }
        tvTenNV.setText(model.getTenNV());
        tvQueQuan.setText(model.getQueQuan());

        return view;
    }
}
