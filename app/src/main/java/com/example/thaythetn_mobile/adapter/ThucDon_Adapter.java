package com.example.thaythetn_mobile.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.TheLoai_ThucDon_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;
import com.example.thaythetn_mobile.view.ThucDon_Fragment;

import java.util.ArrayList;

public class ThucDon_Adapter extends BaseExpandableListAdapter {

    private ArrayList<TheLoai_ThucDon_Model> groupItem;
    private LayoutInflater inflater;
    private AlertDialog dialog,alertDialog;
    private ThucDon_Fragment fragment;

    public void setGroupItem(ArrayList<TheLoai_ThucDon_Model> groupItem) {
        this.groupItem = groupItem;
    }

    public ThucDon_Adapter(ArrayList<TheLoai_ThucDon_Model> groupItem, LayoutInflater inflater) {
        this.groupItem = groupItem;
        this.inflater = inflater;
    }

    @Override
    public int getGroupCount() {
        return groupItem.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupItem.get(groupPosition).getDsThucDon().size();
    }

    @Override
    public TheLoai_ThucDon_Model getGroup(int groupPosition) {
        return groupItem.get(groupPosition);
    }

    @Override
    public ThucDon_Model getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getDsThucDon().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_theloaithucdon, null);
        }
        TheLoai_ThucDon_Model model = groupItem.get(groupPosition);
        ImageView imgTheLoaiTD = view.findViewById(R.id.imgTheLoaiTD);
        TextView tvTheLoai = view.findViewById(R.id.tvTenTheLoai);
        tvTheLoai.setText(model.getTheLoai());
        if (model.getTheLoai().equals("Món Ăn")) {
            imgTheLoaiTD.setBackgroundResource(R.drawable.monan);
        } else {
            imgTheLoaiTD.setBackgroundResource(R.drawable.nuocuong);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_thucdon, null);
        }
        view.setClickable(true);
        final ThucDon_Model model = groupItem.get(groupPosition).getDsThucDon().get(childPosition);
        ImageView imgXoaThucDon = view.findViewById(R.id.imgXoaThucDon);
        ImageView imgSuaThucDon = view.findViewById(R.id.imgSuaThucDon);
        TextView tvTenMonAn = view.findViewById(R.id.tvTenMonAn);
        TextView tvGia = view.findViewById(R.id.tvGia);
        tvTenMonAn.setText(model.getTenMon());
        tvGia.setText(String.valueOf(model.getGia()));
        fragment = new ThucDon_Fragment();

        imgXoaThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn có muốn xóa món ăn này");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        boolean status = fragment.dbControllerThucDon.xoaThucDon(model.getId());
                        if (status) {
                            Toast.makeText(view.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            fragment.lamMoi();
                        } else {
                            Toast.makeText(view.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
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
            }
        });
        imgSuaThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSua = new AlertDialog.Builder(view.getContext());
                View viewDialog = inflater.inflate(R.layout.sua_thuc_don, null);
                final EditText edtSuaThucDon = viewDialog.findViewById(R.id.edtSuaThucDon);
                Button btnSuaThucDon = viewDialog.findViewById(R.id.btnSuaThucDon);
                final EditText edtSuaGia = viewDialog.findViewById(R.id.edtSuaGia);
                final Spinner spSuaTheLoai = viewDialog.findViewById(R.id.spSuaTheLoai);
                edtSuaThucDon.setText(model.getTenMon());
                edtSuaGia.setText(String.valueOf(model.getGia()));
                String[] data = new String[]{"Món ăn", "Nước uống"};
                ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item, data);
                spSuaTheLoai.setAdapter(arrayAdapter);
                if(model.getTheLoai().equals("Món ăn")){
                    spSuaTheLoai.setSelection(0);
                }else {
                    spSuaTheLoai.setSelection(1);
                }

                btnSuaThucDon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBController dbController = new DBController(view.getContext());
                        dbController.initDB();
                        ThucDon_Fragment fragment = new ThucDon_Fragment();
                        if (TextUtils.isEmpty(edtSuaThucDon.getText()) || TextUtils.isEmpty(edtSuaGia.getText())) {
                            Toast.makeText(view.getContext(), "Nhập đầy đủ thông tin vào các trường", Toast.LENGTH_SHORT).show();
                        } else {
                            model.setTenMon(edtSuaThucDon.getText().toString());
                            model.setGia(Integer.parseInt(edtSuaGia.getText().toString()));
                            model.setTheLoai(spSuaTheLoai.getSelectedItem().toString());
                            boolean status = dbController.suaThucDon(model);
                            if (status) {
                                fragment.lamMoi();
                                alertDialog.dismiss();
                                Toast.makeText(view.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builderSua.setView(viewDialog);
                alertDialog = builderSua.create();
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
