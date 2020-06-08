package com.example.thaythetn_mobile.view;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.LichSuThanhToan_Adapter;
import com.example.thaythetn_mobile.model.HoaDonThanhToan_Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class LichSuThanhToan_Fragment extends Fragment {
    public static DBController dbControllerThanhToan;
    public static LichSuThanhToan_Adapter adapterThanhToan;
    private GridView grdLichSuTT;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar1, calendar2;
    private TextView tvNgayDau, tvNgaySau;
    public static TextView tvTongTienThu,tvTienNguyenLieu,tvTongTienLai;
    private Button btnXemDT;
    private String date;
    public static LinearLayout LLTKDoanhThu;

    public LichSuThanhToan_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich_su_thanh_toan_, container, false);
        tvTienNguyenLieu = view.findViewById(R.id.tvTienNguyenLieu);
        tvTongTienLai = view.findViewById(R.id.tvTongTienLai);
        LLTKDoanhThu = view.findViewById(R.id.LLTKDoanhThu);
        dbControllerThanhToan = new DBController(getContext());
        dbControllerThanhToan.initDB();
        grdLichSuTT = view.findViewById(R.id.grdLichSuTT);
        tvTongTienThu = view.findViewById(R.id.tvTongTienThu);


        tvNgayDau = view.findViewById(R.id.tvNgayDau);
        tvNgaySau = view.findViewById(R.id.tvNgaySau);
        btnXemDT = view.findViewById(R.id.btnXemDT);


        adapterThanhToan = new LichSuThanhToan_Adapter(getHoaDonHomNay(), getLayoutInflater());
        tvTienNguyenLieu.setText("Tiền Nguyên Liệu: "+String.valueOf(tinhTienVon())+" VNĐ");
        tvTongTienThu.setText("Tổng Doanh Thu: " + String.valueOf(tongDoanhThu()) + " VNĐ");
        int tienLai = tongDoanhThu() - tinhTienVon();
        tvTongTienLai.setText("Tổng Tiền Lãi: " + String.valueOf(tienLai) + " VNĐ");
        grdLichSuTT.setAdapter(adapterThanhToan);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyy");
        tvNgayDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgayDau();
            }
        });
        tvNgaySau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgaySau();
            }
        });

        btnXemDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(tvNgayDau.getText())|| TextUtils.isEmpty(tvNgaySau.getText()) )
                {
                    Toast.makeText(getContext(), "Phải chọn ngày đầu và cuối để tìm kiếm", Toast.LENGTH_SHORT).show();
                }else {
                    int ngay = (int) ((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
                    //Toast.makeText(getContext(), String.valueOf(ngay), Toast.LENGTH_SHORT).show();
                    if (ngay < 0) {
                        Toast.makeText(getContext(), "Ngày Sau phải lớn hơn ngày trước", Toast.LENGTH_SHORT).show();
                    } else {

                        ArrayList<HoaDonThanhToan_Model> dsTimKiem = new ArrayList<>();
                        for (HoaDonThanhToan_Model model : getData()) {
                            try {
                                Date ngay1 = calendar1.getTime();
                                Date ngay2 = calendar2.getTime();
                                Date date = simpleDateFormat.parse(model.getNgayTaoHoaDon());
                                int ngayDau = (int) ((ngay1.getTime())/(1000 * 60 * 60 * 24));
                                int ngaySau = (int) ((ngay2.getTime())/(1000 * 60 * 60 * 24));
                                int ngayBan = (int) ((date.getTime())/(1000 * 60 * 60 * 24));
                                if(ngayDau <= ngayBan+1 && ngayBan <= ngaySau-1){
                                    dsTimKiem.add(model);
                                }
                                //Toast.makeText(getContext(), simpleDateFormat.format(ngayHD.getTime()), Toast.LENGTH_SHORT).show();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        int dtTK = 0,tvTK = 0,tlTK = 0;
                        for (HoaDonThanhToan_Model model : dsTimKiem) {
                            dtTK += model.getTongGia();
                        }
                        for (HoaDonThanhToan_Model model: dsTimKiem) {
                            ArrayList<Integer> dsID = dbControllerThanhToan.getIDMonAnDonHang(model.getNgayTaoHoaDon());
                            for(int i = 0; i < dsID.size(); i++ ){
                                int id = dsID.get(i);
                                tvTK = tvTK + ((dbControllerThanhToan.getDonHangTheoNgay(model.getNgayTaoHoaDon()).get(i).getSoLuong()) * (dbControllerThanhToan.getNguyenLieuTheoTen(dbControllerThanhToan.getThucDon(id).get(0).getTenNL()).get(0).getGiaNhap()));
                            }
                        }
                        tvTongTienThu.setText("Tổng Doanh Thu: " + String.valueOf(dtTK) + " VNĐ");
                        tvTienNguyenLieu.setText("Tiền Nguyên Liệu: " + String.valueOf(tvTK) + " VNĐ");
                        tlTK = dtTK - tvTK;
                        tvTongTienLai.setText("Tổng Tiền Lãi: " + String.valueOf(tlTK) + " VNĐ");
                        adapterThanhToan.setModels(dsTimKiem);
                        adapterThanhToan.notifyDataSetChanged();

                    }
                }

            }
        });
        return view;
    }

    public int tongDoanhThu() {
        int dt = 0;
        for (HoaDonThanhToan_Model model : adapterThanhToan.getModels()) {
            dt += model.getTongGia();
        }
        return dt;
    }
    public int tinhTienVon(){

        int tienVon = 0;
        for (HoaDonThanhToan_Model model: adapterThanhToan.getModels()) {
            ArrayList<Integer> dsID = dbControllerThanhToan.getIDMonAnDonHang(model.getNgayTaoHoaDon());
            for(int i = 0; i < dsID.size(); i++ ){
                int id = dsID.get(i);
                tienVon = tienVon + ((dbControllerThanhToan.getDonHangTheoNgay(model.getNgayTaoHoaDon()).get(i).getSoLuong()) * (dbControllerThanhToan.getNguyenLieuTheoTen(dbControllerThanhToan.getThucDon(id).get(0).getTenNL()).get(0).getGiaNhap()));
            }
        }
        return  tienVon;
    }

    public ArrayList<HoaDonThanhToan_Model> getData() {
        return dbControllerThanhToan.getHoaDonThanhToan();
    }
    public ArrayList<HoaDonThanhToan_Model> getHoaDonHomNay() {
        ArrayList<HoaDonThanhToan_Model> ds = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());
        ArrayList<HoaDonThanhToan_Model> models = dbControllerThanhToan.getHoaDonThanhToan();
        for (HoaDonThanhToan_Model model : models) {
            if (model.getNgayTaoHoaDon().substring(0,10).equals(date)) {
                ds.add(model);
            }
        }
        return ds;
    }


    private void chonNgayDau() {
        calendar1 = Calendar.getInstance();
        int ngay = calendar1.get(Calendar.DATE);
        int thang = calendar1.get(Calendar.MONTH);
        int nam = calendar1.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar1.set(i, i1, i2);
                tvNgayDau.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();

    }

    private void chonNgaySau() {
        calendar2 = Calendar.getInstance();
        int ngay = calendar2.get(Calendar.DATE);
        int thang = calendar2.get(Calendar.MONTH);
        int nam = calendar2.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar2.set(i, i1, i2);
                tvNgaySau.setText(simpleDateFormat.format(calendar2.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();

    }

}
