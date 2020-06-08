package com.example.thaythetn_mobile.model;

public class BanAn_Model {
    private int idBanAn;
    private String viTri;
    private int sucChua;
    private int conTrong;

    public BanAn_Model(int idBanAn, String viTri, int sucChua, int conTrong) {
        this.idBanAn = idBanAn;
        this.viTri = viTri;
        this.sucChua = sucChua;
        this.conTrong = conTrong;
    }

    public int getIdBanAn() {
        return idBanAn;
    }

    public void setIdBanAn(int idBanAn) {
        this.idBanAn = idBanAn;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public int getConTrong() {
        return conTrong;
    }

    public void setConTrong(int conTrong) {
        this.conTrong = conTrong;
    }
}
