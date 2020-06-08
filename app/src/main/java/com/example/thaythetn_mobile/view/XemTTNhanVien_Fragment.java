package com.example.thaythetn_mobile.view;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.NhanVien_Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class XemTTNhanVien_Fragment extends Fragment {

    private ImageView imgInfoAvatar;
    private TextView tvInfoTenNV, tvInfoNamSinh, tvInfoGT, tvInfoQQ, tvInfoSDT;

    public XemTTNhanVien_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xem_ttnhan_vien, container, false);
        imgInfoAvatar = view.findViewById(R.id.imgInfoAvatar);
        tvInfoTenNV = view.findViewById(R.id.tvInfoTenNV);
        tvInfoNamSinh = view.findViewById(R.id.tvInfoNamSinh);
        tvInfoGT = view.findViewById(R.id.tvInfoGT);
        tvInfoQQ = view.findViewById(R.id.tvInfoQQ);
        tvInfoSDT = view.findViewById(R.id.tvInfoSDT);
        Bundle bundle = getArguments();
        if (bundle != null) {
            NhanVien_Model model = (NhanVien_Model) bundle.getSerializable("infoNhanVien");
            tvInfoTenNV.setText(model.getTenNV());
            tvInfoQQ.setText(model.getQueQuan());
            tvInfoNamSinh.setText(String.valueOf(model.getNamSinh()));
            tvInfoSDT.setText(model.getsDT());
            tvInfoGT.setText(model.getGioiTinh());
            byte hinhanh[] = model.getAnh();
            if (hinhanh != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
                imgInfoAvatar.setImageBitmap(bitmap);
            }
        }
        return view;
    }

}
