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

public class ThemNhanVien_Activity extends AppCompatActivity {
    private EditText edtTenNV, edtQueQuan, edtNamSinh, edtSDT;
    private RadioButton rdbNam, rdbNu;
    private ImageView imgAvatar, imgCamera, imgFolder;
    private Button btnThemNV, btnHuy;
    private DBController dbController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nv);
        getSupportActionBar().setTitle("Thêm Nhân Viên");

        edtTenNV = findViewById(R.id.edtTenNV);
        edtQueQuan = findViewById(R.id.edtQueQuan);
        edtNamSinh = findViewById(R.id.edtNamSinh);
        edtSDT = findViewById(R.id.edtSDT);
        rdbNam = findViewById(R.id.rdbNam);
        rdbNu = findViewById(R.id.rdbNu);
        imgAvatar = findViewById(R.id.imgAvatar);
        imgCamera = findViewById(R.id.imgCamera);
        imgFolder = findViewById(R.id.imgFolder);
        btnThemNV = findViewById(R.id.btnThemNV);
        btnHuy = findViewById(R.id.btnHuy);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            }
        });
        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
            }
        });

        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtTenNV.getText()) || TextUtils.isEmpty(edtQueQuan.getText()) || TextUtils.isEmpty(edtNamSinh.getText()) || TextUtils.isEmpty(edtSDT.getText())) {
                    Toast.makeText(ThemNhanVien_Activity.this, "Nhập đầy đủ thông tin vào các trường", Toast.LENGTH_SHORT).show();
                } else if (rdbNam.isChecked() == false && rdbNu.isChecked() == false) {
                    Toast.makeText(ThemNhanVien_Activity.this, "Phải chọn giới tính", Toast.LENGTH_SHORT).show();
                } else {
                    if (rdbNam.isChecked() == false && rdbNu.isChecked() == false) {
                        Toast.makeText(ThemNhanVien_Activity.this, "Ph", Toast.LENGTH_SHORT).show();
                    }
                    String gioiTinh = "";
                    if (rdbNam.isChecked()) {
                        gioiTinh = "Nam";
                    } else {
                        gioiTinh = "Nữ";
                    }
                    NhanVien_Model model = new NhanVien_Model(0, ConverttoArrayByte(imgAvatar), edtTenNV.getText().toString(), Integer.parseInt(edtNamSinh.getText().toString()), edtQueQuan.getText().toString(), gioiTinh, edtSDT.getText().toString());
                    dbController = new DBController(ThemNhanVien_Activity.this);
                    dbController.initDB();
                    boolean status = dbController.addNhanVien(model);
                    if (status == true) {
                        Toast.makeText(ThemNhanVien_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ThemNhanVien_Activity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        }
        if (requestCode == 11 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

