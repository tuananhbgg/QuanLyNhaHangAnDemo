package com.example.thaythetn_mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.TheLoai_ThucDon_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;

import java.util.ArrayList;
public class ChonMon_Adapter extends BaseExpandableListAdapter {
    private ArrayList<TheLoai_ThucDon_Model> groupItem;
    private LayoutInflater inflater;
    public ChonMon_Adapter(ArrayList<TheLoai_ThucDon_Model> groupItem, LayoutInflater inflater) {
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
            view = inflater.inflate(R.layout.item_chon_mon, null);
        }
        final ThucDon_Model model = groupItem.get(groupPosition).getDsThucDon().get(childPosition);
        TextView tvTenMonChon = view.findViewById(R.id.tvTenMonChon);
        TextView tvGiaMonChon = view.findViewById(R.id.tvGiaMonChon);
        tvTenMonChon.setText(model.getTenMon());
        tvGiaMonChon.setText(String.valueOf(model.getGia()));

        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
