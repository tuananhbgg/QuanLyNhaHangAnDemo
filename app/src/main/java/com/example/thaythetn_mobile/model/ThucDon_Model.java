package com.example.thaythetn_mobile.model;

public class ThucDon_Model {
    private int id;
    private String tenMon;
    private String theLoai;
    private int gia;
    private String tenNL;

    public ThucDon_Model(int id, String tenMon, String theLoai, int gia, String tenNL) {
        this.id = id;
        this.tenMon = tenMon;
        this.theLoai = theLoai;
        this.gia = gia;
        this.tenNL = tenNL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }
}
