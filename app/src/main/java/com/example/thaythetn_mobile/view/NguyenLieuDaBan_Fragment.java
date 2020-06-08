package com.example.thaythetn_mobile.view;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.adapter.NguyenLieudaBan_Adapter;
import com.example.thaythetn_mobile.model.NguyenLieuDaBan_Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class NguyenLieuDaBan_Fragment extends Fragment {
    public static NguyenLieudaBan_Adapter nguyenLieudaBan_adapter;
    public static ListView lvNLDaBan;
    private String date;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar1, calendar2;
    public static LinearLayout LLTimKiem;
    private TextView tvNgayDau, tvNgaySau;

    public NguyenLieuDaBan_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nguyen_lieu_da_ban, container, false);
        LLTimKiem = view.findViewById(R.id.LLTimKiem);
        tvNgayDau = view.findViewById(R.id.tvNgayDau);
        tvNgaySau = view.findViewById(R.id.tvNgaySau);
        Button btnXem = view.findViewById(R.id.btnXem);
        nguyenLieudaBan_adapter = new NguyenLieudaBan_Adapter(getDSNLDaBanHomNay(), getLayoutInflater());
        lvNLDaBan = view.findViewById(R.id.lvNLDaBan);
        lvNLDaBan.setAdapter(nguyenLieudaBan_adapter);

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

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tvNgayDau.getText()) || TextUtils.isEmpty(tvNgaySau.getText())) {
                    Toast.makeText(getContext(), "Phải chọn ngày đầu và cuối để tìm kiếm", Toast.LENGTH_SHORT).show();
                } else {
                    int ngay = (int) ((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
                    if (ngay < 0) {
                        Toast.makeText(getContext(), "Ngày Sau phải lớn hơn ngày trước", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<NguyenLieuDaBan_Model> dsTK = new ArrayList<>();
                        for (NguyenLieuDaBan_Model model : MainActivity.dbControllerM.getDSNLDaBan()) {
                            try {
                                Date ngay1 = calendar1.getTime();
                                Date ngay2 = calendar2.getTime();
                                Date date = simpleDateFormat.parse(model.getNgayBan());
                                int ngayDau = (int) ((ngay1.getTime()) / (1000 * 60 * 60 * 24));
                                int ngaySau = (int) ((ngay2.getTime()) / (1000 * 60 * 60 * 24));
                                int ngayBan = (int) ((date.getTime()) / (1000 * 60 * 60 * 24));
                                if (ngayDau <= ngayBan + 1 && ngayBan <= ngaySau - 1) {
                                    dsTK.add(model);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        nguyenLieudaBan_adapter.setModels(dsTK);
                        nguyenLieudaBan_adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        return view;

    }

    //    class NguyenLieuDB extends AsyncTask<Void, Void, ArrayList<NguyenLieuDaBan_Model>> {
//
//        @Override
//        protected ArrayList<NguyenLieuDaBan_Model> doInBackground(Void... voids) {
//            return MainActivity.dbControllerM.getDSNLDaBan();
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<NguyenLieuDaBan_Model> ds) {
//            super.onPostExecute(ds);
//
//        }
//    }
    public ArrayList<NguyenLieuDaBan_Model> getDSNLDaBan() {
        return MainActivity.dbControllerM.getDSNLDaBan();
    }

    private ArrayList<NguyenLieuDaBan_Model> dsHN;

    public ArrayList<NguyenLieuDaBan_Model> getDSNLDaBanHomNay() {
        dsHN = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                date = dateFormat.format(calendar.getTime());
                ArrayList<NguyenLieuDaBan_Model> models = MainActivity.dbControllerM.getDSNLDaBan();
                for (NguyenLieuDaBan_Model model : models) {
                    if (model.getNgayBan().equals(date)) {
                        dsHN.add(model);
                    }
                }
            }
        }).start();
        return dsHN;
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
