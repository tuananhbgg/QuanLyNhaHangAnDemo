package com.example.thaythetn_mobile.model;

public class HoaDonThanhToan_Model {
    private int idHD;
    private int tongGia;
    private String ngayTaoHoaDon;

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public int getTongGia() {
        return tongGia;
    }

    public void setTongGia(int tongGia) {
        this.tongGia = tongGia;
    }

    public String getNgayTaoHoaDon() {
        return ngayTaoHoaDon;
    }

    public void setNgayTaoHoaDon(String ngayTaoHoaDon) {
        this.ngayTaoHoaDon = ngayTaoHoaDon;
    }

    public HoaDonThanhToan_Model(int idHD, int tongGia, String ngayTaoHoaDon) {
        this.idHD = idHD;
        this.tongGia = tongGia;
        this.ngayTaoHoaDon = ngayTaoHoaDon;
    }
}
