package com.example.thaythetn_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thaythetn_mobile.model.BanAn_Model;
import com.example.thaythetn_mobile.model.HoaDonThanhToan_Model;
import com.example.thaythetn_mobile.model.MonDaChon_Model;
import com.example.thaythetn_mobile.model.NguyenLieuDaBan_Model;
import com.example.thaythetn_mobile.model.NguyenLieu_Model;
import com.example.thaythetn_mobile.model.NhanVien_Model;
import com.example.thaythetn_mobile.model.SoDoBanAn_Model;
import com.example.thaythetn_mobile.model.ThongTinNhaHang_Model;
import com.example.thaythetn_mobile.model.ThucDon_Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
public class DBController extends SQLiteOpenHelper {
    private static final String dbName = "quanlybanhang.db";
    private static final int version = 1;
    public Context context;
    private  SQLiteDatabase database;
    public DBController(Context context) {
        super(context, dbName, null, version);
        this.context = context;
    }
    public void initDB() {
        if (checkExistDB()) {
            database = getWritableDatabase();
        } else {
            database = getReadableDatabase();
            database.close();
            copyDB();
            database = getWritableDatabase();
        }
    }
    private void copyDB() {
        try {
            InputStream inputStream = context.getAssets().open(dbName);
            FileOutputStream outputStream = new FileOutputStream(context.getDatabasePath(dbName));
            byte[] data = new byte[1024];
            int status = inputStream.read(data);
            while (status != -1) {
                outputStream.write(data);
                status = inputStream.read(data);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkExistDB() {
        File myDB = context.getDatabasePath(dbName);
        return myDB.exists();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //TODO: Nhân viên
    public ArrayList<NhanVien_Model> getNotes() {
        ArrayList<NhanVien_Model> notes = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from NhanVien", null);
        while (cursor.moveToNext()) {
            int idNV = cursor.getInt(0);
            byte[] anh = cursor.getBlob(1);
            String tenNV = cursor.getString(2);
            int namSinh = cursor.getInt(3);
            String queQuan = cursor.getString(4);
            String gioiTinh = cursor.getString(5);
            String sDT = cursor.getString(6);
            notes.add(new NhanVien_Model(idNV, anh, tenNV, namSinh, queQuan, gioiTinh, sDT));
        }
        return notes;
    }

    public boolean addNhanVien(NhanVien_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Anh", model.getAnh());
        contentValues.put("TenNV", model.getTenNV());
        contentValues.put("NamSinh", model.getNamSinh());
        contentValues.put("QueQuan", model.getQueQuan());
        contentValues.put("GioiTinh", model.getGioiTinh());
        contentValues.put("SDT", model.getsDT());
        long id = database.insert("NhanVien", null, contentValues);
        if (id > 0) {
            return true;
        }
        return false;
    }

    public boolean updateNhanVien(NhanVien_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Anh", model.getAnh());
        contentValues.put("TenNV", model.getTenNV());
        contentValues.put("NamSinh", model.getNamSinh());
        contentValues.put("QueQuan", model.getQueQuan());
        contentValues.put("GioiTinh", model.getGioiTinh());
        contentValues.put("SDT", model.getsDT());
        int rowUpdate = database.update("NhanVien", contentValues, "IDNhanVien = " + model.getIdNhanVien(), null);
        if (rowUpdate > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteNhanVien(int id) {
        int rowDelete = database.delete("NhanVien", "IDNhanVien = " + id, null);
        if (rowDelete > 0) {
            return true;
        } else
            return false;
    }

    // TODO Thực đơn
    public ArrayList<ThucDon_Model> getThucDon(int idThucDon) {
        //Toast.makeText(context, String.valueOf(idThucDon), Toast.LENGTH_SHORT).show();
        ArrayList<ThucDon_Model> models = new ArrayList<>();
        String sql = "SELECT * from ThucDon where IDMonAn = "+ idThucDon ;
        Cursor cursor = database.rawQuery(sql,null);
        //Toast.makeText(context, String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenMon = cursor.getString(1);
            int gia = cursor.getInt(2);
            String theLoai = cursor.getString(3);
            String tenNL = cursor.getString(4);
            models.add(new ThucDon_Model(idThucDon, tenMon, theLoai, gia,tenNL));
        }
        return models;
    }

    public ArrayList<ThucDon_Model> getDSMonAn() {
        ArrayList<ThucDon_Model> models = new ArrayList<>();
        String sql = "SELECT * from ThucDon where TheLoai = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{"Món ăn"});
        while (cursor.moveToNext()) {
            int idThucDon = cursor.getInt(0);
            String tenMon = cursor.getString(1);
            int gia = cursor.getInt(2);
            String theLoai = cursor.getString(3);
            String tenNL = cursor.getString(4);
            models.add(new ThucDon_Model(idThucDon, tenMon, theLoai, gia,tenNL));
        }
        return models;
    }

    public ArrayList<ThucDon_Model> getDSNuocUong() {
        ArrayList<ThucDon_Model> models = new ArrayList<>();
        String sql = "SELECT * from ThucDon where TheLoai = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{"Nước uống"});
        while (cursor.moveToNext()) {
            int idThucDon = cursor.getInt(0);
            String tenMon = cursor.getString(1);
            int gia = cursor.getInt(2);
            String theLoai = cursor.getString(3);
            String tenNL = cursor.getString(4);
            models.add(new ThucDon_Model(idThucDon, tenMon, theLoai, gia,tenNL));
        }
        return models;
    }

    public boolean themThucDon(ThucDon_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenMon", model.getTenMon());
        contentValues.put("Gia", model.getGia());
        contentValues.put("TheLoai", model.getTheLoai());
        contentValues.put("TenNL", model.getTenNL());

        long id = database.insert("ThucDon", null, contentValues);
        if (id > 0) {
            return true;
        }
        return false;
    }

    public boolean xoaThucDon(int id) {
        int rowDelete = database.delete("ThucDon", "IDMonAn = " + id, null);
        if (rowDelete > 0) {
            return true;
        } else
            return false;
    }

    public boolean suaThucDon(ThucDon_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenMon", model.getTenMon());
        contentValues.put("Gia", model.getGia());
        contentValues.put("TheLoai", model.getTheLoai());
        long rowUpdate = database.update("ThucDon", contentValues, "IDMonAn = " + model.getId(), null);
        if (rowUpdate > 0) {
            return true;
        } else
            return false;
    }
// TODO bàn ăn
    public ArrayList<BanAn_Model> getDSBanAn() {
        ArrayList<BanAn_Model> models = new ArrayList<>();
        Cursor cursor = database.rawQuery("SeLECT * FROM BanAn", null);
        while (cursor.moveToNext()) {
            int idBanAn = cursor.getInt(0);
            String viTri = cursor.getString(1);
            int sucChua = cursor.getInt(2);
            int conTrong = cursor.getInt(3);

            models.add(new BanAn_Model(idBanAn, viTri,sucChua,conTrong));
        }
        return models;
    }
    public ArrayList<BanAn_Model> getDSBanAnTheoID(int id) {
        ArrayList<BanAn_Model> models = new ArrayList<>();
        Cursor cursor = database.rawQuery("SeLECT * FROM BanAn where IDBanAn = " + id, null);
        while (cursor.moveToNext()) {
            int idBanAn = cursor.getInt(0);
            String viTri = cursor.getString(1);
            int sucChua = cursor.getInt(2);
            int conTrong = cursor.getInt(3);

            models.add(new BanAn_Model(idBanAn, viTri,sucChua,conTrong));
        }
        return models;
    }

    public boolean themBanAn(BanAn_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDBanAn", model.getIdBanAn());
        contentValues.put("ViTri", model.getViTri());
        contentValues.put("SucChua", model.getSucChua());
        int conTrong = 1;
        contentValues.put("ConTrong", conTrong);
        long id = database.insert("BanAn", null, contentValues);
        if (id > 0) {
            return true;
        }
        return false;
    }

    public boolean xoaBanAn(int id) {
        int rowDelete = database.delete("BanAn", "IDBanAn = " + id, null);
        if (rowDelete > 0) {
            return true;
        } else
            return false;
    }

    public boolean capNhatBanAn(int id, int conTrong) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("ConTrong", conTrong);
        long rowUpdate = database.update("BanAn", contentValues, "IDBanAn = " + id, null);
        if (rowUpdate > 0) {
            return true;
        } else
            return false;
    }
    //TODO chọn món
    public ArrayList<MonDaChon_Model> getDSMonDaChonTheoBan(int idBanAn) {
        ArrayList<MonDaChon_Model> models = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * from DonHang where IDBanAn = " + idBanAn + " AND TrangThai = " + 0, null);
        while (cursor.moveToNext()) {
            int idDonHang = cursor.getInt(0);
            String ngayTaoDH = cursor.getString(1);
            int idBan = cursor.getInt(2);
            int idMonAn = cursor.getInt(3);
            int sl = cursor.getInt(4);
            int tt = cursor.getInt(5);
            String dv = cursor.getString(6);
            int nhanMon = cursor.getInt(7);

            models.add(new MonDaChon_Model(idDonHang, ngayTaoDH, idBanAn, idMonAn, sl, tt,dv,nhanMon));
        }
        if (models.size() <= 0){
            capNhatBanAn(idBanAn,1);
        }
        return models;
    }

    public boolean themDSMonDachonTheoBan(MonDaChon_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NgayTaoDH", model.getNgayTaoDH());
        contentValues.put("IDBanAn", model.getIdBanAn());
        contentValues.put("IDMonAn", model.getIdMonAn());
        contentValues.put("SoLuong", model.getSoLuong());
        contentValues.put("TrangThai", model.getTrangThai());
        contentValues.put("DonVi", model.getDonVi());
        contentValues.put("NhanMon", model.getNhanMon());
        long id = database.insert("DonHang", null, contentValues);
        if (id > 0) {
            return true;
        }
        return false;
    }
    public boolean xoaMonAnDaChon(int id){
        int rowDelete = database.delete("DonHang", "IDDonHang = " + id, null);
        if (rowDelete > 0) {
            return true;
        } else
            return false;
    }


    // todo:  hóa đơn
    public ArrayList<MonDaChon_Model> getDSBanChuaNhanMon() {
        ArrayList<MonDaChon_Model> models = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * from DonHang where TrangThai = " + 0 + " AND NhanMon = "+ 0, null);
        while (cursor.moveToNext()) {
            int idDonHang = cursor.getInt(0);
            String ngayTaoDH = cursor.getString(1);
            int idBan = cursor.getInt(2);
            int idMonAn = cursor.getInt(3);
            int sl = cursor.getInt(4);
            int tt = cursor.getInt(5);
            String dv = cursor.getString(6);
            int nhanMon = cursor.getInt(7);

            models.add(new MonDaChon_Model(idDonHang, ngayTaoDH, idBan, idMonAn, sl, tt,dv,nhanMon));
        }
        return models;
    }
    public boolean capNhatLenMon(int idBan, int nhanMon){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NhanMon", nhanMon);
        long rowUpdate = database.update("DonHang", contentValues, "IDBanAn = " + idBan, null);
        if (rowUpdate > 0) {
            return true;
        } else
            return false;
    }
    public  boolean capNhatSauThanhToan(MonDaChon_Model model){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NgayTaoDH", model.getNgayTaoDH());
        contentValues.put("IDBanAn", model.getIdBanAn());
        contentValues.put("IDMonAn", model.getIdMonAn());
        contentValues.put("SoLuong", model.getSoLuong());
        contentValues.put("TrangThai", model.getTrangThai());
        long rowUpdate = database.update("DonHang", contentValues, "IDDonHang = " + model.getIdDonHang(), null);
        if (rowUpdate > 0) {
            return true;
        } else
            return false;
    }
    public boolean capNhatSoLuongMonChon(int id, int sl){
        ContentValues contentValues = new ContentValues();
        contentValues.put("SoLuong", sl);
        long rowUpdate = database.update("DonHang", contentValues, "IDDonHang = " + id, null);
        if (rowUpdate > 0) {
            return true;
        } else
            return false;
    }
    public boolean themHoaDonThanhToan(HoaDonThanhToan_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TongGia", model.getTongGia());
        contentValues.put("NgayTaoHD", model.getNgayTaoHoaDon());
        long id = database.insert("HoaDonThanhToan", null, contentValues);
        if (id > 0) {
            return true;
        }
        return false;
    }
    public ArrayList<HoaDonThanhToan_Model> getHoaDonThanhToan(){
        ArrayList<HoaDonThanhToan_Model> models =  new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from HoaDonThanhToan ",null);
        while (cursor.moveToNext()) {
            int idHoaDon = cursor.getInt(0);
            int tongGia = cursor.getInt(1);
            String ngayTaoDH = cursor.getString(2);
            models.add(new HoaDonThanhToan_Model(idHoaDon, tongGia, ngayTaoDH));
        }
        return models;
    }
    public ArrayList<MonDaChon_Model> getDonHangTheoNgay(String ngay) {
        ArrayList<MonDaChon_Model> models = new ArrayList<>();
        String sql = "SELECT * from DonHang where NgayTaoDH = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{ngay});
        while (cursor.moveToNext()) {
            int idDonHang = cursor.getInt(0);
            String ngayTaoDH = cursor.getString(1);
            int idBan = cursor.getInt(2);
            int idMonAn = cursor.getInt(3);
            int sl = cursor.getInt(4);
            int tt = cursor.getInt(5);
            String dv = cursor.getString(6);
            int nhanMon =  cursor.getInt(7);
            models.add(new MonDaChon_Model(idDonHang, ngayTaoDH, idBan, idMonAn, sl, tt,dv,nhanMon));
        }
        return models;
    }

    public ArrayList<Integer> getIDMonAnDonHang(String ngay) {
        ArrayList<Integer> models = new ArrayList<>();
        String sql = "SELECT IDMonAn from DonHang where NgayTaoDH = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{ngay});
        while (cursor.moveToNext()) {
            int idMonAn = cursor.getInt(0);
            models.add(idMonAn);
        }
        return models;
    }

    //TODO: get danh sách nguyên liệu trong kho
    public ArrayList<NguyenLieu_Model> getDSNguyenLieu(){
        ArrayList<NguyenLieu_Model> models =  new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from NguyenLieu ",null);
        while (cursor.moveToNext()) {
            String tenNL = cursor.getString(0);
            int soLuong = cursor.getInt(1);
            int giaNhap = cursor.getInt(2);
            String donVi = cursor.getString(3);
            String noiBanQuan = cursor.getString(4);
            models.add(new NguyenLieu_Model(tenNL, soLuong, giaNhap,donVi,noiBanQuan));
        }
        return models;
    }
    public ArrayList<String> getNguyenLieuChinh(){
        ArrayList<String> models =  new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from NguyenLieu ",null);
        while (cursor.moveToNext()) {
            String tenNL = cursor.getString(0);
            models.add(tenNL);
        }
        return models;
    }
    public ArrayList<NguyenLieu_Model> getNguyenLieuTheoTen(String tenNL){
        ArrayList<NguyenLieu_Model> models =  new ArrayList<>();
        String sql = "select * from NguyenLieu where TenNL = ?";
        Cursor cursor = database.rawQuery(sql,new String[]{tenNL});
        while (cursor.moveToNext()) {
            String ten = cursor.getString(0);
            int sl = cursor.getInt(1);
            int gia = cursor.getInt(2);
            String donVi = cursor.getString(3);
            String noiBanQuan = cursor.getString(4);
            models.add(new NguyenLieu_Model(ten,sl,gia,donVi,noiBanQuan));
        }
        return models;
    }

    public boolean themNguyenLieu(NguyenLieu_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenNL", model.getTenNL());
        contentValues.put("SLCon", model.getSoLuong());
        contentValues.put("GiaNhap", model.getGiaNhap());
        contentValues.put("DonVi",model.getDonVi());
        contentValues.put("NoiBaoQuan",model.getNoiBaoQuan());
        long id = database.insert("NguyenLieu", null, contentValues);
        if (id > 0) {
            return true;
        }
        return false;
    }
    public boolean suaNguyenLieu(NguyenLieu_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SLCon", model.getSoLuong());
        contentValues.put("GiaNhap", model.getGiaNhap());
        //contentValues.put("DonVi",model.getDonVi());
        long id = database.update("NguyenLieu",contentValues,"TenNL = ?", new String[]{model.getTenNL()});
        if (id > 0) {
            return true;
        }
        return false;
    }
    public boolean nhapThemNguyenLieu(String tenNL,int slThem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SLCon", slThem);
        //contentValues.put("DonVi",model.getDonVi());
        long id = database.update("NguyenLieu",contentValues,"TenNL = ?", new String[]{tenNL});
        if (id > 0) {
            return true;
        }
        return false;
    }

    //Todo: nguyên liệu đã bán

    public ArrayList<NguyenLieuDaBan_Model> getDSNLDaBan(){
        ArrayList<NguyenLieuDaBan_Model> models =  new ArrayList<>();
        String sql = "select * from NLDaBan";
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            int idMon = cursor.getInt(0);
            String ngayBan = cursor.getString(1);
            int soLuong = cursor.getInt(2);
            String donVi = cursor.getString(3);
            models.add(new NguyenLieuDaBan_Model(idMon,ngayBan,soLuong,donVi));
        }
        return models;
    }

    public boolean themNLDaBan(NguyenLieuDaBan_Model model){
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDMon", model.getIDMon());
        contentValues.put("NgayBan", model.getNgayBan());
        contentValues.put("SoLuong", model.getSoLuong());
        contentValues.put("DonVi",model.getDonVi());
        long id = database.insert("NLDaBan", null, contentValues);
        if (id > 0) {
            return true;
        }
        return false;
    }
    public boolean suaNLDaBban(int idMonAn, int soLuong ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SoLuong",soLuong);
        long id = database.update("NLDaBan",contentValues,"IDMon = "+ idMonAn,null);
        if (id > 0) {
            return true;
        }
        return false;
    }
    public ArrayList<String> getDSDonViTheoTen(String tenNL){
        ArrayList<String> donVi =  new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        String sql = "SELECT DonVi NguyenLieu where TenNL = ?";
        Cursor cursor = database.rawQuery(sql,new String[]{tenNL});
        while (cursor.moveToNext()) {

            String dv = cursor.getString(0);
            donVi.add(dv);
        }
        return donVi;
    }
    public ArrayList<ThongTinNhaHang_Model> getThongTinNH(){
        ArrayList<ThongTinNhaHang_Model> thongTinNhaHangs = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from ThongTinNH",null);
        while (cursor.moveToNext()){
            String tenNH =  cursor.getString(0);
            String diaChi =  cursor.getString(1);
            String sDT =  cursor.getString(2);
            thongTinNhaHangs.add(new ThongTinNhaHang_Model(tenNH,diaChi,sDT));
        }
       return thongTinNhaHangs;
    }
    public boolean suaTTNhaHang(ThongTinNhaHang_Model thongTinNhaHang ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DiaChi",thongTinNhaHang.getDiaChiNH());
        contentValues.put("SDT",thongTinNhaHang.getSoDTNH());
        long id = database.update("ThongTinNH",contentValues,"TenNH = ?",new String[]{thongTinNhaHang.getTenNH()});
        if (id > 0) {
            return true;
        }
        return false;
    }
    public ArrayList<SoDoBanAn_Model> getSoDo() {
        ArrayList<SoDoBanAn_Model> notes = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from SoDoBanAn", null);
        while (cursor.moveToNext()) {
            int idNV = cursor.getInt(0);
            byte anh[] = cursor.getBlob(1);

            notes.add(new SoDoBanAn_Model(idNV, anh));
        }
        return notes;
    }
    public boolean updateSoDo(SoDoBanAn_Model model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("anhSoDo", model.getAnhSoDo());
        int rowUpdate = database.update("SoDoBanAn", contentValues, "IDSoDo = " + model.getIdSoDo(), null);
        if (rowUpdate > 0) {
            return true;
        } else
            return false;
    }
}

