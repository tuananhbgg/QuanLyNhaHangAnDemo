package com.example.thaythetn_mobile.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.NguyenLieu_Model;
import com.example.thaythetn_mobile.view.MainActivity;
import com.example.thaythetn_mobile.view.NguyenLieu_Fragment;

import java.util.ArrayList;

public class NguyenLieu_Adapter extends RecyclerView.Adapter<NguyenLieu_Adapter.NguyenLieuViewHolder> {
    private ArrayList<NguyenLieu_Model> models;
    private Dialog dialog;

    public void setModels(ArrayList<NguyenLieu_Model> models) {
        this.models = models;
    }

    public NguyenLieu_Adapter(ArrayList<NguyenLieu_Model> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public NguyenLieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nguyen_lieu, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new NguyenLieuViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NguyenLieuViewHolder holder, int position) {
        final NguyenLieu_Model model = models.get(position);
        holder.tvTenNLNhap.setText(model.getTenNL());
        holder.tvSLNhap.setText("SL: "+String.valueOf(model.getSoLuong())+" "+model.getDonVi());
        holder.tvGiaNhap.setText("Giá : "+String.valueOf(model.getGiaNhap())+" VNĐ");
        holder.tvnoiBQ.setText("Bảo quản ở "+String.valueOf(model.getNoiBaoQuan()));

        holder.imgThemNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final View dialogThemNL = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_nhap_them_nl,null);
                final EditText edtSoLuongNhapThem;
                Button btnXacNhanNhap;
                edtSoLuongNhapThem = dialogThemNL.findViewById(R.id.edtSoLuongNhapThem);
                btnXacNhanNhap = dialogThemNL.findViewById(R.id.btnXacNhanNhap);

                btnXacNhanNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NguyenLieu_Fragment fragment = new NguyenLieu_Fragment();
                        if(edtSoLuongNhapThem.getText().toString().equals("")){
                            Toast.makeText(view.getContext(), "Nhập số lượng cần nhập thêm", Toast.LENGTH_SHORT).show();
                        }else {
                            int slSau = Integer.parseInt(edtSoLuongNhapThem.getText().toString())+model.getSoLuong();
                            NguyenLieu_Fragment.dbNguyenLieu.nhapThemNguyenLieu(model.getTenNL(),slSau);
                            Toast.makeText(view.getContext(), "Nhập thêm thành công", Toast.LENGTH_SHORT).show();
                            fragment.LamMoi();
                            dialog.dismiss();
                        }
                    }
                });

                builder.setView(dialogThemNL);

                dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class NguyenLieuViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNLNhap, tvSLNhap, tvGiaNhap,tvnoiBQ,tvNgayBan;
        ImageView imgThemNL;

        public NguyenLieuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNLNhap = itemView.findViewById(R.id.tvTenNLNhap);
            tvSLNhap = itemView.findViewById(R.id.tvSLNhap);
            tvGiaNhap = itemView.findViewById(R.id.tvGiaNhap);
            tvnoiBQ = itemView.findViewById(R.id.tvnoiBQ);
            imgThemNL = itemView.findViewById(R.id.imgThemNL);


        }
    }
}
