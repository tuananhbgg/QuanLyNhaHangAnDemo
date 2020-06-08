package com.example.thaythetn_mobile.view;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.ChonMon_Adapter;
import com.example.thaythetn_mobile.model.MonDaChon_Model;
import com.example.thaythetn_mobile.model.NguyenLieu_Model;
import com.example.thaythetn_mobile.model.TheLoai_ThucDon_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChonMon_Fragment extends Fragment {

    private DBController dbController;
    private ChonMon_Adapter adapter;
    private ExpandableListView elvChonMon;
    private AlertDialog dialog;
    private DonDatBan_Activity activity;

    public ChonMon_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chon_mon, container, false);
        dbController = new DBController(getContext());
        dbController.initDB();
        elvChonMon = view.findViewById(R.id.elvChonMon);
        adapter = new ChonMon_Adapter(getDataThucDon(), getLayoutInflater());
        elvChonMon.setAdapter(adapter);
        registerForContextMenu(elvChonMon);

        elvChonMon.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View viewDialog = getLayoutInflater().inflate(R.layout.so_luong_mon_chon, null);
                TextView tvTenMonChon = viewDialog.findViewById(R.id.tvTenMonChon);
                final EditText edtSoLuongChon = viewDialog.findViewById(R.id.edtSoLuongChon);
                Button btnThemSLMonChon = viewDialog.findViewById(R.id.btnThemSLMonChon);
                final ThucDon_Model modelChon = adapter.getChild(i, i1);
                tvTenMonChon.setText(modelChon.getTenMon());

                btnThemSLMonChon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Integer.parseInt(edtSoLuongChon.getText().toString()) <= 0) {
                            Toast.makeText(getContext(), "Số lượng phải > 0", Toast.LENGTH_SHORT).show();
                        } else {
//
                            activity = new DonDatBan_Activity();
                            MonDaChon_Fragment fragment = new MonDaChon_Fragment();
                            DatBan_Fragment datBan_fragment = new DatBan_Fragment();
                            NguyenLieu_Model model = dbController.getNguyenLieuTheoTen(modelChon.getTenNL()).get(0);
                            int slCu = model.getSoLuong();
                            if(slCu < Integer.parseInt(edtSoLuongChon.getText().toString())){
                                Toast.makeText(getContext(), "Hết hàng! Vui lòng chọn món khác.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                int slMoi =  slCu - Integer.parseInt(edtSoLuongChon.getText().toString());

                                model.setSoLuong(slMoi);
                                dbController.suaNguyenLieu(model);
                                dbController.themDSMonDachonTheoBan(new MonDaChon_Model(0, "", Integer.parseInt(activity.banSo), modelChon.getId(), Integer.parseInt(edtSoLuongChon.getText().toString()), 0,model.getDonVi(),0));
                                dbController.capNhatBanAn(Integer.parseInt(activity.banSo),0);
                                datBan_fragment.lamMoi();
                                fragment.lamMoi();

                            }
                            dialog.dismiss();
                        }
                    }
                });

                builder.setView(viewDialog);
                dialog = builder.create();
                dialog.show();

                return false;
            }
        });
        return view;
    }


    private ArrayList<TheLoai_ThucDon_Model> getDataThucDon() {
        ArrayList<TheLoai_ThucDon_Model> theLoai_thucDon_models = new ArrayList<>();
        ArrayList<ThucDon_Model> monan_models = new ArrayList<>();
        monan_models = dbController.getDSMonAn();
        ArrayList<ThucDon_Model> nuocUong_models = new ArrayList<>();
        nuocUong_models = dbController.getDSNuocUong();
        theLoai_thucDon_models.add(new TheLoai_ThucDon_Model("Món Ăn", monan_models));
        theLoai_thucDon_models.add(new TheLoai_ThucDon_Model("Nước Uống", nuocUong_models));
        return theLoai_thucDon_models;
    }


}
