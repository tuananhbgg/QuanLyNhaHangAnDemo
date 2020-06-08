package com.example.thaythetn_mobile.view;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.DatBan_Adapter;
import com.example.thaythetn_mobile.adapter.MonDaChon_Adapter;
import com.example.thaythetn_mobile.model.BanAn_Model;
import com.example.thaythetn_mobile.model.MonDaChon_Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
public class DatBan_Fragment extends Fragment {
    public static DBController dbController;
    public static DatBan_Adapter adapter;
    private DatBan_Adapter datBan_adapter;
    private GridView grdDatBan;
    private AlertDialog dialog, dialogTTBan;
    private AlertDialog dialogXuLyDH, dialogChiTiet;
    FloatingActionButton btnChoXuLy;
    public DatBan_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dat_ban, container, false);
        grdDatBan = view.findViewById(R.id.grdDatBan);
        btnChoXuLy = view.findViewById(R.id.btnChoXuLy);
        dbController = new DBController(getContext());
        dbController.initDB();
        adapter = new DatBan_Adapter(getDSBanAn(), getLayoutInflater());
        grdDatBan.setAdapter(adapter);
        final View viewXuLyDH = inflater.inflate(R.layout.da_nhan_mon, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(viewXuLyDH);
        dialogXuLyDH = builder.create();
        final View viewChiTiet = inflater.inflate(R.layout.fragment_mon_da_chon, null);
        AlertDialog.Builder builderChiTiet = new AlertDialog.Builder(getContext());
        builderChiTiet.setView(viewChiTiet);
        dialogChiTiet = builderChiTiet.create();
        btnChoXuLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogXuLyDH.isShowing() == true) {
                    dialogXuLyDH.dismiss();
                }
                GridView grdBanChuaNhanMon = viewXuLyDH.findViewById(R.id.grdBanChuaNhanMon);
                datBan_adapter = new DatBan_Adapter(getDSBanChuaNhanMon(), getLayoutInflater());
                grdBanChuaNhanMon.setAdapter(datBan_adapter);
                dialogXuLyDH.show();
                grdBanChuaNhanMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        dialogXuLyDH.dismiss();
                        if (dialogChiTiet.isShowing() == true) {
                            dialogChiTiet.dismiss();
                        }
                        final BanAn_Model model = getDSBanChuaNhanMon().get(i);
                        ListView lvMonDaChon = viewChiTiet.findViewById(R.id.lvMonDaChon);
                        Button btnXacNhanLenMon = viewChiTiet.findViewById(R.id.btnXacNhanLenMon);
                        TextView tvBanSo = viewChiTiet.findViewById(R.id.tvBanSo);
                        tvBanSo.setText("Bàn số " + model.getIdBanAn());
                        tvBanSo.setVisibility(View.VISIBLE);
                        MonDaChon_Adapter adapterMonDaChon = new MonDaChon_Adapter(dbController.getDSMonDaChonTheoBan(model.getIdBanAn()), getLayoutInflater());
                        lvMonDaChon.setAdapter(adapterMonDaChon);
                        btnXacNhanLenMon.setVisibility(View.VISIBLE);
                        btnXacNhanLenMon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boolean status = dbController.capNhatLenMon(model.getIdBanAn(), 1);
                                if (status) {
                                    dialogChiTiet.dismiss();
                                    Toast.makeText(getContext(), "Xác nhận đã lên món", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialogChiTiet.show();
                    }
                });
            }
        });
        grdDatBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                View viewTTBan = getLayoutInflater().inflate(R.layout.thong_tin_ban_an, null);
                TextView tvTTSoBan = viewTTBan.findViewById(R.id.tvTTSoBan);
                TextView tvTTViTri = viewTTBan.findViewById(R.id.tvTTViTri);
                TextView tvTTSucChua = viewTTBan.findViewById(R.id.tvTTSucChua);
                TextView tvTTTrangThai = viewTTBan.findViewById(R.id.tvTTTrangThai);
                Button btnDatBan = viewTTBan.findViewById(R.id.btnDatBan);
                if (dbController.getDSBanAnTheoID(getDSBanAn().get(i).getIdBanAn()).get(0).getConTrong() == 1) {
                    AlertDialog.Builder builderTTBan = new AlertDialog.Builder(getContext());
                    tvTTSoBan.setText("Bàn Số: " + String.valueOf(i + 1));
                    tvTTViTri.setText(dbController.getDSBanAnTheoID(getDSBanAn().get(i).getIdBanAn()).get(0).getViTri());
                    tvTTSucChua.setText("Sức Chứa Tối Đa: " + String.valueOf(dbController.getDSBanAnTheoID(getDSBanAn().get(i).getIdBanAn()).get(0).getSucChua()));
                    tvTTTrangThai.setText("Trạng Thái: Còn Trống");
                    builderTTBan.setView(viewTTBan);
                    dialogTTBan = builderTTBan.create();
                    dialogTTBan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogTTBan.show();
                    btnDatBan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogTTBan.dismiss();
                            Intent intent = new Intent(getContext(), DonDatBan_Activity.class);
                            intent.putExtra("banso", String.valueOf(i + 1));
                            startActivityForResult(intent, 10);
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View viewTB = getLayoutInflater().inflate(R.layout.dialog_xuly_donhang, null);
                    TextView tvDong = viewTB.findViewById(R.id.tvDong);
                    TextView tvThemMon = viewTB.findViewById(R.id.tvThemMon);
                    TextView tvThanhToan = viewTB.findViewById(R.id.tvThanhToan);
                    tvDong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    tvThemMon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), DonDatBan_Activity.class);
                            intent.putExtra("banso", String.valueOf(getDSBanAn().get(i).getIdBanAn()));
                            startActivityForResult(intent, 10);
                        }
                    });
                    tvThanhToan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), ThanhToan_Activity.class);
                            intent.putExtra("banso", String.valueOf(getDSBanAn().get(i).getIdBanAn()));
                            startActivityForResult(intent, 11);
                        }
                    });
                    builder.setView(viewTB);
                    dialog = builder.create();
                    dialog.show();
                }
            }
        });
        return view;
    }
    public void lamMoi() {
        adapter.setModels(getDSBanAn());
        adapter.notifyDataSetChanged();
    }
    private ArrayList<BanAn_Model> getDSBanAn() {
        ArrayList<BanAn_Model> models = dbController.getDSBanAn();
        return models;
    }
    private ArrayList<BanAn_Model> getDSBanChuaNhanMon() {
        ArrayList<BanAn_Model> models = new ArrayList<>();
        for (MonDaChon_Model con : dbController.getDSBanChuaNhanMon()) {
            boolean coBan = false;
            for (BanAn_Model mol : models) {
                if (mol.getIdBanAn() == con.getIdBanAn()) {
                    coBan = true;
                }
            }
            if (coBan == false) {
                models.add(dbController.getDSBanAnTheoID(con.getIdBanAn()).get(0));
            }
        }
        return models;
    }
}
