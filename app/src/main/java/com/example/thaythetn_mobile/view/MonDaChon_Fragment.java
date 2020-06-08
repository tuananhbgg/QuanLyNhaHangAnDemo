package com.example.thaythetn_mobile.view;


import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.MonDaChon_Adapter;
import com.example.thaythetn_mobile.model.MonDaChon_Model;
import com.example.thaythetn_mobile.model.NguyenLieu_Model;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonDaChon_Fragment extends Fragment {
    public static DBController controllerMonDaChon;
    public static MonDaChon_Adapter adapterMonDaChon;
    private ListView lvMonDaChon;
    private DonDatBan_Activity donDatBan_activity;

    public MonDaChon_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mon_da_chon, container, false);
        lvMonDaChon = view.findViewById(R.id.lvMonDaChon);
        donDatBan_activity = new DonDatBan_Activity();
        controllerMonDaChon = new DBController(getContext());
        controllerMonDaChon.initDB();
        adapterMonDaChon = new MonDaChon_Adapter(getData(),getLayoutInflater());
        lvMonDaChon.setAdapter(adapterMonDaChon);
        registerForContextMenu(lvMonDaChon);
        return view;
    }

    public void lamMoi(){
        adapterMonDaChon.setModels(getData());
        adapterMonDaChon.notifyDataSetChanged();
    }
    private ArrayList<MonDaChon_Model> getData() {
        return controllerMonDaChon.getDSMonDaChonTheoBan(Integer.parseInt(donDatBan_activity.banSo));

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(getContext());
        inflater.inflate(R.menu.context_menu_mondachon,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.mn_xoaMonDaChon){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position =info.position;
            int idDonHangXoa = getData().get(position).getIdDonHang();
            int idMonAnXoa =  getData().get(position).getIdMonAn();

            String tenNLXoa =  controllerMonDaChon.getThucDon(idMonAnXoa).get(0).getTenNL();
            NguyenLieu_Model model = controllerMonDaChon.getNguyenLieuTheoTen(tenNLXoa).get(0);
            int sl = getData().get(position).getSoLuong();
            int slMoi = sl + model.getSoLuong();
            model.setSoLuong(slMoi);
            controllerMonDaChon.suaNguyenLieu(model);
            controllerMonDaChon.xoaMonAnDaChon(idDonHangXoa);
            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            MonDaChon_Fragment fragment = new MonDaChon_Fragment();
            fragment.lamMoi();

        }
        return super.onContextItemSelected(item);
    }
}
