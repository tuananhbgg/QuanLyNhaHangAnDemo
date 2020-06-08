package com.example.thaythetn_mobile.model;

public class ThongTinNhaHang_Model {
    private String tenNH;
    private String diaChiNH;
    private String soDTNH;

    public ThongTinNhaHang_Model(String tenNH, String diaChiNH, String soDTNH) {
        this.tenNH = tenNH;
        this.diaChiNH = diaChiNH;
        this.soDTNH = soDTNH;
    }

    public String getTenNH() {
        return tenNH;
    }

    public void setTenNH(String tenNH) {
        this.tenNH = tenNH;
    }

    public String getDiaChiNH() {
        return diaChiNH;
    }

    public void setDiaChiNH(String diaChiNH) {
        this.diaChiNH = diaChiNH;
    }

    public String getSoDTNH() {
        return soDTNH;
    }

    public void setSoDTNH(String soDTNH) {
        this.soDTNH = soDTNH;
    }
}
