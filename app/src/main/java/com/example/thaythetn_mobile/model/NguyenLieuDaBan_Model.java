package com.example.thaythetn_mobile.model;

public class NguyenLieuDaBan_Model {
    private int IDMon;
    private String ngayBan;
    private int soLuong;
    private String donVi;

    public NguyenLieuDaBan_Model(int IDMon, String ngayBan, int soLuong, String donVi) {
        this.IDMon = IDMon;
        this.ngayBan = ngayBan;
        this.soLuong = soLuong;
        this.donVi = donVi;
    }

    public int getIDMon() {
        return IDMon;
    }

    public void setIDMon(int IDMon) {
        this.IDMon = IDMon;
    }

    public String getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(String ngayBan) {
        this.ngayBan = ngayBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }
}
