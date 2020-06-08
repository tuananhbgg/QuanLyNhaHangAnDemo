package com.example.thaythetn_mobile.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thaythetn_mobile.DBController;
import com.example.thaythetn_mobile.R;
import com.example.thaythetn_mobile.model.NhanVien_Model;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SuaThongTinNhanVien_Activity extends AppCompatActivity {
    private EditText edtSuaTenNV, edtSuaQueQuan, edtSuaNamSinh, edtSuaSDT;
    private RadioButton rdbSuaNam, rdbSuaNu;
    private ImageView imgSuaAvatar, imgSuaCamera, imgSuaFolder;
    private Button btnSuaNV, btnHuySua;
    private DBController dbController;
    private NhanVien_Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin_nhan_vien);
        getSupportActionBar().setTitle("Sửa thông tin nhân viên");
        edtSuaTenNV = findViewById(R.id.edtSuaTenNV);
        edtSuaQueQuan = findViewById(R.id.edtSuaQueQuan);
        edtSuaNamSinh = findViewById(R.id.edtSuaNamSinh);
        edtSuaSDT = findViewById(R.id.edtSuaSDT);
        rdbSuaNam = findViewById(R.id.rdbSuaNam);
        rdbSuaNu = findViewById(R.id.rdbSuaNu);
        imgSuaAvatar = findViewById(R.id.imgSuaAvatar);
        imgSuaCamera = findViewById(R.id.imgSuaCamera);
        imgSuaFolder = findViewById(R.id.imgSuaFolder);
        btnHuySua = findViewById(R.id.btnHuySua);
        btnSuaNV = findViewById(R.id.btnSuaNV);

        if (getIntent() != null) {
            model = (NhanVien_Model) getIntent().getSerializableExtra("nvUpdate");
            if (model != null) {
                edtSuaTenNV.setText(model.getTenNV());
                edtSuaQueQuan.setText(model.getQueQuan());
                edtSuaNamSinh.setText(String.valueOf(model.getNamSinh()));
                edtSuaSDT.setText(model.getsDT());
                if (model.getGioiTinh().equals("Nam")) {
                    rdbSuaNam.setChecked(true);
                } else {
                    rdbSuaNu.setChecked(true);
                }
                byte hinhanh[] = model.getAnh();
                if(hinhanh != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
                    imgSuaAvatar.setImageBitmap(bitmap);
                }
            }

        }
        imgSuaCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
        imgSuaFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        btnSuaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtSuaTenNV.getText()) || TextUtils.isEmpty(edtSuaQueQuan.getText()) || TextUtils.isEmpty(edtSuaNamSinh.getText()) || TextUtils.isEmpty(edtSuaSDT.getText())) {
                    Toast.makeText(SuaThongTinNhanVien_Activity.this, "Nhập đầy đủ thông tin vào các trường", Toast.LENGTH_SHORT).show();
                } else if (rdbSuaNam.isChecked() == false && rdbSuaNu.isChecked() == false) {
                    Toast.makeText(SuaThongTinNhanVien_Activity.this, "Phải chọn giới tính", Toast.LENGTH_SHORT).show();
                } else {
                    if (rdbSuaNam.isChecked() == false && rdbSuaNu.isChecked() == false) {
                        Toast.makeText(SuaThongTinNhanVien_Activity.this, "Ph", Toast.LENGTH_SHORT).show();
                    }
                    String gioiTinh = "";
                    if (rdbSuaNam.isChecked()) {
                        gioiTinh = "Nam";
                    } else {
                        gioiTinh = "Nữ";
                    }
                    if(ConverttoArrayByte(imgSuaAvatar) != null){
                        model.setAnh(ConverttoArrayByte(imgSuaAvatar));
                    }
                    model.setTenNV(edtSuaTenNV.getText().toString());
                    model.setQueQuan(edtSuaQueQuan.getText().toString());
                    model.setGioiTinh(gioiTinh);
                    model.setNamSinh(Integer.parseInt(edtSuaNamSinh.getText().toString()));
                    model.setsDT(edtSuaSDT.getText().toString());
                    dbController = new DBController(SuaThongTinNhanVien_Activity.this);
                    dbController.initDB();
                    boolean status = dbController.updateNhanVien(model);
                    if (status == true) {
                        Toast.makeText(SuaThongTinNhanVien_Activity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(SuaThongTinNhanVien_Activity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgSuaAvatar.setImageBitmap(bitmap);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSuaAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
