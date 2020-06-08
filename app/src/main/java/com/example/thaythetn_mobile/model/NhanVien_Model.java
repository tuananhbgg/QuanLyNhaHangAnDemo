package com.example.thaythetn_mobile.model;

import java.io.Serializable;

public class NhanVien_Model implements Serializable {
    private int idNhanVien;
    private byte[] anh;
    private String tenNV;
    private int namSinh;
    private String queQuan;
    private String gioiTinh;
    private String sDT;

    public NhanVien_Model(int idNhanVien, byte[] anh, String tenNV, int namSinh, String queQuan, String gioiTinh, String sDT) {
        this.idNhanVien = idNhanVien;
       this.anh = anh;
        this.tenNV = tenNV;
        this.namSinh = namSinh;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.sDT = sDT;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }
}
