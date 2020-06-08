package com.example.thaythetn_mobile.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;

import com.example.thaythetn_mobile.adapter.NhanVien_Adapter;
import com.example.thaythetn_mobile.model.BanAn_Model;
import com.example.thaythetn_mobile.model.NguyenLieu_Model;
import com.example.thaythetn_mobile.model.NhanVien_Model;
import com.example.thaythetn_mobile.model.SoDoBanAn_Model;
import com.example.thaythetn_mobile.model.ThongTinNhaHang_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Toolbar toolbar;
    private AlertDialog dialogThemMonAn, dialogThemBanAn, dialogXoaBanAn, dialogThemNL, dialogThongTin, dialogSoDoBanAn;
    private long backPressTime;
    private TextView tvTenNH, tvDiaChiNH, tvSDTNH;
    private ImageView imgSoDo;
    private Button btnDoiSoDo, btnSuaSoDo;
    public static DBController dbControllerM;
    ThongTinNhaHang_Model thongTin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbControllerM = new DBController(this);
        dbControllerM.initDB();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Đặt Bàn");
        changLayout(new DatBan_Fragment());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ban_an, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        XemTTNhanVien_Fragment xemTTNhanVienFragment = (XemTTNhanVien_Fragment) getSupportFragmentManager().findFragmentByTag("fragmentInfoNV");
        if (xemTTNhanVienFragment != null) {
            transaction.remove(xemTTNhanVienFragment);
            toolbar.getMenu().clear();
            changLayout(new NhanVien_Fragment());
            toolbar.inflateMenu(R.menu.menu_nhanvien);
            transaction.commit();
        } else {
            if (backPressTime + 3000 > System.currentTimeMillis()) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            } else {
                Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
            }
            backPressTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mn_timkiemnv) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    ArrayList<NhanVien_Model> dataSearch = new ArrayList<>();

                    NhanVien_Fragment fragment = new NhanVien_Fragment();

                    NhanVien_Adapter adapter = new NhanVien_Adapter(dbControllerM.getNotes(), getLayoutInflater());
                    ArrayList<NhanVien_Model> dataTest = adapter.models;
                    if (s.length() > 0) {
                        for (NhanVien_Model con :
                                dataTest) {
                            if (con.getTenNV().toLowerCase().contains(s.toLowerCase()))
                                dataSearch.add(con);
                        }
                        adapter.models = dataSearch;
                    } else
                        adapter.models = dbControllerM.getNotes();
                    NhanVien_Fragment.lvNhanVien.setAdapter(adapter);
                    return false;
                }
            });
        } else if (id == R.id.mn_themNV) {
            Intent intent = new Intent(MainActivity.this, ThemNhanVien_Activity.class);
            startActivity(intent);
        } else if (id == R.id.mn_them_monan) {
            final AlertDialog.Builder themMonAnBuilder = new AlertDialog.Builder(this);
            View viewThemMonAn = getLayoutInflater().inflate(R.layout.them_thuc_don, null);
            final EditText edtTenMonMoi = viewThemMonAn.findViewById(R.id.edtTenMonMoi);
            final EditText edtGiaMonMoi = viewThemMonAn.findViewById(R.id.edtGiaMonMoi);
            final Spinner spTheLoai = viewThemMonAn.findViewById(R.id.spTheLoai);
            final Spinner spNguyenLieu = viewThemMonAn.findViewById(R.id.spNguyenLieu);

            String kieuThucDon[] = new String[]{"Món ăn", "Nước uống"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, kieuThucDon);
            spTheLoai.setAdapter(arrayAdapter);


            ArrayList<String> ds = dbControllerM.getNguyenLieuChinh();
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, ds);
            spNguyenLieu.setAdapter(stringArrayAdapter);
            Button btnThemMonMoi = viewThemMonAn.findViewById(R.id.btnThemMonMoi);

            btnThemMonMoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(edtGiaMonMoi.getText()) || TextUtils.isEmpty(edtTenMonMoi.getText())) {
                        Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin món mới", Toast.LENGTH_SHORT).show();
                    } else {

                        boolean status = dbControllerM.themThucDon(new ThucDon_Model(0, edtTenMonMoi.getText().toString(), spTheLoai.getSelectedItem().toString(), Integer.parseInt(edtGiaMonMoi.getText().toString()), spNguyenLieu.getSelectedItem().toString()));
                        if (status) {
                            ThucDon_Fragment fragment = new ThucDon_Fragment();
                            fragment.lamMoi();
                            dialogThemMonAn.dismiss();
                            Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            themMonAnBuilder.setView(viewThemMonAn);
            dialogThemMonAn = themMonAnBuilder.create();
            dialogThemMonAn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogThemMonAn.show();
        } else if (id == R.id.mn_thembanan) {
            final AlertDialog.Builder themBanAnBuilder = new AlertDialog.Builder(this);
            View viewThemBanAn = getLayoutInflater().inflate(R.layout.them_ban, null);
            final EditText edtSoBanThem = viewThemBanAn.findViewById(R.id.edtSoBanThem);
            final EditText edtViTri = viewThemBanAn.findViewById(R.id.edtViTri);
            final EditText edtSucChua = viewThemBanAn.findViewById(R.id.edtSucChua);
            Button btnThemBan = viewThemBanAn.findViewById(R.id.btnThemBan);

            btnThemBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(edtViTri.getText())) {
                        Toast.makeText(MainActivity.this, "Nhập vị trí các bàn cần thêm", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(edtSucChua.getText().toString()) <= 0) {
                        Toast.makeText(MainActivity.this, "Sức chứa của bàn phải > 0", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(edtSoBanThem.getText().toString()) <= 0) {
                        Toast.makeText(MainActivity.this, "Số bàn thêm phải > 0", Toast.LENGTH_SHORT).show();
                    } else {

                        int soBan = dbControllerM.getDSBanAn().size();
                        int soBanSau = soBan + Integer.parseInt(edtSoBanThem.getText().toString());
                        for (int i = soBan + 1; i <= soBanSau; i++) {
                            dbControllerM.themBanAn(new BanAn_Model(i, edtViTri.getText().toString(), Integer.parseInt(edtSucChua.getText().toString()), 1));
                        }
                        DatBan_Fragment fragment = new DatBan_Fragment();
                        fragment.lamMoi();
                        dialogThemBanAn.dismiss();
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            themBanAnBuilder.setView(viewThemBanAn);
            dialogThemBanAn = themBanAnBuilder.create();
            dialogThemBanAn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogThemBanAn.show();

        } else if (id == R.id.mn_xoabanan) {
            final AlertDialog.Builder xoaBanAnBuilder = new AlertDialog.Builder(this);
            View viewXoaBanAn = getLayoutInflater().inflate(R.layout.xoaban, null);
            final EditText edtSoBanXoa = viewXoaBanAn.findViewById(R.id.edtSoBanXoa);
            Button btnXoaBan = viewXoaBanAn.findViewById(R.id.btnXoaBan);

            btnXoaBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtSoBanXoa.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "Phải nhập số bàn cần xóa", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(edtSoBanXoa.getText().toString()) > 0) {

                        int soBan = dbControllerM.getDSBanAn().size();
                        int soBanSau = soBan - Integer.parseInt(edtSoBanXoa.getText().toString());

                        if (soBanSau <= 0) {
                            for (int i = soBan; i > 0; i--) {
                                dbControllerM.xoaBanAn(i);
                            }
                        } else {
                            for (int i = soBan; i > soBanSau; i--) {
                                dbControllerM.xoaBanAn(i);
                            }
                        }


                        DatBan_Fragment fragment = new DatBan_Fragment();
                        fragment.lamMoi();
                        dialogXoaBanAn.dismiss();
                        Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Số bàn xóa phải > 0", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            xoaBanAnBuilder.setView(viewXoaBanAn);
            dialogXoaBanAn = xoaBanAnBuilder.create();
            dialogXoaBanAn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogXoaBanAn.show();


        } else if (id == R.id.mn_them_nl) {
            final AlertDialog.Builder themNLBuilder = new AlertDialog.Builder(this);
            View viewThemNL = getLayoutInflater().inflate(R.layout.them_nguyen_lieu, null);
            final EditText edtTenNL, edtSoLuong, edtGiaNhap, edtDonVi, edtNoiBQ;
            Button btnThemNL = viewThemNL.findViewById(R.id.btnThemNL);
            edtTenNL = viewThemNL.findViewById(R.id.edtTenNL);
            edtSoLuong = viewThemNL.findViewById(R.id.edtSoLuong);
            edtGiaNhap = viewThemNL.findViewById(R.id.edtGiaNhap);
            edtDonVi = viewThemNL.findViewById(R.id.edtDonVi);
            edtNoiBQ = viewThemNL.findViewById(R.id.edtNoiBQ);

            btnThemNL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(edtTenNL.getText()) || TextUtils.isEmpty(edtNoiBQ.getText()) || TextUtils.isEmpty(edtSoLuong.getText()) || TextUtils.isEmpty(edtGiaNhap.getText()) || TextUtils.isEmpty(edtDonVi.getText())) {
                        Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(edtSoLuong.getText().toString()) <= 0 || Integer.parseInt(edtGiaNhap.getText().toString()) <= 0) {
                        Toast.makeText(MainActivity.this, "Số lượng và giá nhập phải > 0 ", Toast.LENGTH_SHORT).show();
                    } else {
                        NguyenLieu_Fragment fragment = new NguyenLieu_Fragment();


                        ArrayList<String> ds = dbControllerM.getNguyenLieuChinh();
                        boolean coNL = false;
                        for (String ten : ds) {
                            if (edtTenNL.getText().toString().toLowerCase().equals(ten)) {
                                coNL = true;
                            }
                        }
                        if (coNL == false) {
                            dialogThemNL.dismiss();
                            boolean status = dbControllerM.themNguyenLieu(new NguyenLieu_Model(edtTenNL.getText().toString().toLowerCase(), Integer.parseInt(edtSoLuong.getText().toString()), Integer.parseInt(edtGiaNhap.getText().toString()), edtDonVi.getText().toString(), edtNoiBQ.getText().toString()));
                            if (status) {
                                fragment.LamMoi();
                                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            dialogThemNL.dismiss();

                            int slCu = dbControllerM.getNguyenLieuTheoTen(edtTenNL.getText().toString().toLowerCase()).get(0).getSoLuong();
                            int slMoi = Integer.parseInt(edtSoLuong.getText().toString()) + slCu;
                            boolean status = dbControllerM.suaNguyenLieu(new NguyenLieu_Model(edtTenNL.getText().toString(), slMoi, Integer.parseInt(edtGiaNhap.getText().toString()), "hi", "hihi"));
                            if (status) {
                                fragment.LamMoi();
                                Toast.makeText(MainActivity.this, "Nhập thêm hàng thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });


            themNLBuilder.setView(viewThemNL);
            dialogThemNL = themNLBuilder.create();
            dialogThemNL.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogThemNL.show();
        } else if (id == R.id.mn_DaBanHomNay) {
            NguyenLieuDaBan_Fragment fragment = new NguyenLieuDaBan_Fragment();
            NguyenLieuDaBan_Fragment.nguyenLieudaBan_adapter.setModels(fragment.getDSNLDaBanHomNay());
            NguyenLieuDaBan_Fragment.nguyenLieudaBan_adapter.notifyDataSetChanged();
            NguyenLieuDaBan_Fragment.LLTimKiem.setVisibility(View.GONE);
        } else if (id == R.id.mn_DaBanNhieuNgay) {
            NguyenLieuDaBan_Fragment fragment = new NguyenLieuDaBan_Fragment();
            NguyenLieuDaBan_Fragment.nguyenLieudaBan_adapter.setModels(fragment.getDSNLDaBan());
            NguyenLieuDaBan_Fragment.nguyenLieudaBan_adapter.notifyDataSetChanged();
            NguyenLieuDaBan_Fragment.LLTimKiem.setVisibility(View.VISIBLE);
        } else if (id == R.id.mn_DoanhThuHomNay) {
            LichSuThanhToan_Fragment fragment = new LichSuThanhToan_Fragment();
            LichSuThanhToan_Fragment.adapterThanhToan.setModels(fragment.getHoaDonHomNay());
            LichSuThanhToan_Fragment.adapterThanhToan.notifyDataSetChanged();
            LichSuThanhToan_Fragment.LLTKDoanhThu.setVisibility(View.GONE);
            int tienLai = fragment.tongDoanhThu() - fragment.tinhTienVon();
            LichSuThanhToan_Fragment.tvTienNguyenLieu.setText("Tiền Nguyên Liệu: " + fragment.tinhTienVon() + " VNĐ");
            LichSuThanhToan_Fragment.tvTongTienThu.setText("Tổng Doanh Thu: " + fragment.tongDoanhThu() + " VNĐ");
            LichSuThanhToan_Fragment.tvTongTienLai.setText("Tổng Tiền Lãi: " + tienLai + " VNĐ");
        } else if (id == R.id.mn_DoanhThuNhieuNgay) {
            LichSuThanhToan_Fragment fragment = new LichSuThanhToan_Fragment();
            LichSuThanhToan_Fragment.adapterThanhToan.setModels(dbControllerM.getHoaDonThanhToan());
            LichSuThanhToan_Fragment.adapterThanhToan.notifyDataSetChanged();
            LichSuThanhToan_Fragment.LLTKDoanhThu.setVisibility(View.VISIBLE);

            LichSuThanhToan_Fragment.tvTienNguyenLieu.setText("Tiền Nguyên Liệu: " + fragment.tinhTienVon() + " VNĐ");
            LichSuThanhToan_Fragment.tvTongTienThu.setText("Tổng Doanh Thu: " + fragment.tongDoanhThu() + " VNĐ");
            int tienLai = fragment.tongDoanhThu() - fragment.tinhTienVon();
            LichSuThanhToan_Fragment.tvTongTienLai.setText("Tổng Tiền Lãi: " + tienLai + " VNĐ");
        } else if (id == R.id.mn_sodobanan) {

            final SoDoBanAn_Model model = dbControllerM.getSoDo().get(0);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.so_do_ban_an, null);
            imgSoDo = view.findViewById(R.id.imgSoDo);
            if (model.getAnhSoDo() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(model.getAnhSoDo(), 0, model.getAnhSoDo().length);
                imgSoDo.setImageBitmap(bitmap);
            }
            btnDoiSoDo = view.findViewById(R.id.btnDoiSoDo);
            btnSuaSoDo = view.findViewById(R.id.btnSuaSoDo);
            btnDoiSoDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 20);
                }
            });
            btnSuaSoDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ConverttoArrayByte(imgSoDo) != null) {
                        boolean status = dbControllerM.updateSoDo(new SoDoBanAn_Model(model.getIdSoDo(), ConverttoArrayByte(imgSoDo)));
                        if (status) {
                            Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            dialogSoDoBanAn.dismiss();

                        } else {
                            Toast.makeText(MainActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                            dialogSoDoBanAn.dismiss();
                        }
                    }
                }
            });
            builder.setView(view);
            dialogSoDoBanAn = builder.create();
            dialogSoDoBanAn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogSoDoBanAn.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public byte[] ConverttoArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 20 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSoDo.setImageBitmap(bitmap);
                btnDoiSoDo.setVisibility(View.GONE);
                btnSuaSoDo.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navNhanVien) {
            toolbar.getMenu().clear();
            toolbar.setTitle("Nhân Viên");
            toolbar.inflateMenu(R.menu.menu_nhanvien);
            changLayout(new NhanVien_Fragment());
        } else if (id == R.id.navThucDon) {
            toolbar.getMenu().clear();
            toolbar.setTitle("Thực Đơn");
            toolbar.inflateMenu(R.menu.menu_thuc_don);
            changLayout(new ThucDon_Fragment());

        } else if (id == R.id.navDatBan) {
            toolbar.getMenu().clear();
            toolbar.setTitle("Đặt Bàn");
            toolbar.inflateMenu(R.menu.menu_ban_an);
            changLayout(new DatBan_Fragment());

        } else if (id == R.id.navDoanhThu) {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_doanhthu);
            toolbar.setTitle("Doanh Thu");
            changLayout(new LichSuThanhToan_Fragment());

        } else if (id == R.id.navKhoHang) {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.them_nguyen_lieu);
            toolbar.setTitle("Nguyên Liệu ");
            changLayout(new KhoHang_Fragment());
        } else if (id == R.id.nav_info) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View viewTT = getLayoutInflater().inflate(R.layout.thong_tin_nha_hang, null);
            tvTenNH = viewTT.findViewById(R.id.tvTenNH);
            tvDiaChiNH = viewTT.findViewById(R.id.tvDiaChiNH);
            tvSDTNH = viewTT.findViewById(R.id.tvSDTNH);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    thongTin = dbControllerM.getThongTinNH().get(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTenNH.setText(thongTin.getTenNH());
                            tvDiaChiNH.setText(thongTin.getDiaChiNH());
                            tvSDTNH.setText(thongTin.getSoDTNH());
                        }
                    });
                }
            }).start();
            builder.setView(viewTT);
            dialogThongTin = builder.create();
            dialogThongTin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogThongTin.show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void changLayout(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment);
        transaction.commit();
    }


}
