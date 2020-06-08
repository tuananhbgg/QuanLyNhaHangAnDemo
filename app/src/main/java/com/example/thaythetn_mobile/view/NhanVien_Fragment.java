package com.example.thaythetn_mobile.view;


import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.NhanVien_Adapter;
import com.example.thaythetn_mobile.model.NhanVien_Model;

import java.io.Serializable;
import java.util.ArrayList;

public class NhanVien_Fragment extends Fragment {
    public static ListView lvNhanVien;
    private NhanVien_Adapter adapter;
    private DBController dbController;
    private MainActivity mainActivity;

    public NhanVien_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nhanvien, container, false);
        dbController = new DBController(getContext());
        dbController.initDB();
        lvNhanVien = view.findViewById(R.id.lvNhanVien);
        adapter = new NhanVien_Adapter(getDSNhanVien(), getLayoutInflater());
        lvNhanVien.setAdapter(adapter);
        registerForContextMenu(lvNhanVien);
        mainActivity = new MainActivity();
        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mainActivity.toolbar.getMenu().clear();
                XemTTNhanVien_Fragment ttNhanVienFragment = new XemTTNhanVien_Fragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("infoNhanVien", adapter.models.get(position));
                ttNhanVienFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameLayout, ttNhanVienFragment, "fragmentInfoNV");
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        adapter.models = getDSNhanVien();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private void refeshData() {
        adapter.models = dbController.getNotes();
        adapter.notifyDataSetChanged();
    }

    private ArrayList<NhanVien_Model> getDSNhanVien() {
        ArrayList<NhanVien_Model> models = new ArrayList<>();
        models = dbController.getNotes();
        return models;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = new MenuInflater(getContext());
        menuInflater.inflate(R.menu.contextmenu_nhanvien, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    private AlertDialog dialog;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        final NhanVien_Model model = adapter.models.get(position);
        switch (item.getItemId()) {
            case R.id.ContextMenu_DeleteNV:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xóa nhân viên");
                builder.setMessage("Bạn có muốn xóa nhân viên này");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        dbController.deleteNhanVien(model.getIdNhanVien());
                        refeshData();
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.ContextMenu_EditNV:
                Intent intent = new Intent(getContext(), SuaThongTinNhanVien_Activity.class);
                intent.putExtra("nvUpdate", (Serializable) model);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
