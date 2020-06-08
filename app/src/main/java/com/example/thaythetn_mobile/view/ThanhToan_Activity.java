package com.example.thaythetn_mobile.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.ThanhToanMonDaChon_Adapter;
import com.example.thaythetn_mobile.model.HoaDonThanhToan_Model;
import com.example.thaythetn_mobile.model.MonDaChon_Model;
import com.example.thaythetn_mobile.model.NguyenLieuDaBan_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThanhToan_Activity extends AppCompatActivity {

    public static DBController dbThanhToan;
    public static ThanhToanMonDaChon_Adapter thanhToanMonDaChon_adapter;
    private ListView lvDSMonGoi;
    public TextView tvTienThanhToan;
    private Button btnThanhToan, btnHuyThanhToan;
    private String banThanhToan;
    private String dateTime,date;
    int tongTien = 0;
    ArrayList<MonDaChon_Model> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        if (getIntent() != null) {
            banThanhToan = getIntent().getStringExtra("banso");
            btnHuyThanhToan = findViewById(R.id.btnHuyThanhToan);
            tvTienThanhToan = findViewById(R.id.tvTienThanhToan);
            btnThanhToan = findViewById(R.id.btnThanhToan);
            if (banThanhToan != null) {
                getSupportActionBar().setTitle("Bàn Số " + banThanhToan);
            }
        }

        lvDSMonGoi = findViewById(R.id.lvDSMonGoi);
        dbThanhToan = new DBController(this);
        dbThanhToan.initDB();
        thanhToanMonDaChon_adapter = new ThanhToanMonDaChon_Adapter(getData(), getLayoutInflater(),this);
        lvDSMonGoi.setAdapter(thanhToanMonDaChon_adapter);


        tongTien = tongTienThanhToan();
        tvTienThanhToan.setText(tongTien + " VNĐ");
        btnHuyThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        models = dbThanhToan.getDSMonDaChonTheoBan(Integer.parseInt(banThanhToan));


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (MonDaChon_Model model: models) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
                    dateTime = dateTimeFormat.format(calendar.getTime());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    date = dateFormat.format(calendar.getTime());
                    model.setTrangThai(1);
                    model.setNgayTaoDH(dateTime);
                    dbThanhToan.capNhatSauThanhToan(model);
                    boolean status = false;
                    int slCu = 0;
                    ArrayList<NguyenLieuDaBan_Model> dsNLDaBan = dbThanhToan.getDSNLDaBan();
                    for (NguyenLieuDaBan_Model con: dsNLDaBan) {
                        if(model.getIdMonAn() == con.getIDMon() && date.equals(con.getNgayBan())){
                            status = true;
                            slCu = con.getSoLuong();
                        }
                    }
                    if (status == false){
                        dbThanhToan.themNLDaBan(new NguyenLieuDaBan_Model(model.getIdMonAn(),date,model.getSoLuong(),model.getDonVi()));
                    }else{
                        dbThanhToan.suaNLDaBban(model.getIdMonAn(),slCu+model.getSoLuong());
                    }
                }

                dbThanhToan.capNhatBanAn(Integer.parseInt(banThanhToan),1);
                dbThanhToan.themHoaDonThanhToan(new HoaDonThanhToan_Model(0,tongTien,dateTime));
                DatBan_Fragment fragment = new DatBan_Fragment();
                fragment.lamMoi();
                finish();
            }
        });
    }

    public int tongTienThanhToan() {
        int tong = 0;
        ArrayList<ThucDon_Model> models = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            tong += getData().get(i).getSoLuong() * dbThanhToan.getThucDon(getData().get(i).getIdMonAn()).get(0).getGia();
        }
        return tong;
    }

    private ArrayList<MonDaChon_Model> getData() {
        return dbThanhToan.getDSMonDaChonTheoBan(Integer.parseInt(banThanhToan));

    }


}
