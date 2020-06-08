package com.example.thaythetn_mobile.model;

public class MonDaChon_Model {
    private int idDonHang;
    private String ngayTaoDH;
    private int idBanAn;
    private int idMonAn;
    private int soLuong;
    private int trangThai;
    private String donVi;
    private int nhanMon;

    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getNgayTaoDH() {
        return ngayTaoDH;
    }

    public void setNgayTaoDH(String ngayTaoDH) {
        this.ngayTaoDH = ngayTaoDH;
    }

    public int getIdBanAn() {
        return idBanAn;
    }

    public void setIdBanAn(int idBanAn) {
        this.idBanAn = idBanAn;
    }

    public int getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        this.idMonAn = idMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getNhanMon() {
        return nhanMon;
    }

    public void setNhanMon(int nhanMon) {
        this.nhanMon = nhanMon;
    }

    public MonDaChon_Model(int idDonHang, String ngayTaoDH, int idBanAn, int idMonAn, int soLuong, int trangThai, String donVi, int nhanMon) {
        this.idDonHang = idDonHang;
        this.ngayTaoDH = ngayTaoDH;
        this.idBanAn = idBanAn;
        this.idMonAn = idMonAn;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.donVi = donVi;
        this.nhanMon = nhanMon;
    }
}
