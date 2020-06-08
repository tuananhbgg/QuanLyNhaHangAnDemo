package com.example.thaythetn_mobile.model;

public class NguyenLieu_Model {
    private String tenNL;
    private int soLuong;
    private int giaNhap;
    private String donVi;
    private String noiBaoQuan;

    public NguyenLieu_Model(String tenNL, int soLuong, int giaNhap, String donVi,String noiBaoQuan) {
        this.tenNL = tenNL;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.donVi = donVi;
        this.noiBaoQuan = noiBaoQuan;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getNoiBaoQuan() {
        return noiBaoQuan;
    }

    public void setNoiBaoQuan(String noiBaoQuan) {
        this.noiBaoQuan = noiBaoQuan;
    }
}
