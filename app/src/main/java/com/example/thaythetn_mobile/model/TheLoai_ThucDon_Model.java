package com.example.thaythetn_mobile.model;

import java.util.ArrayList;

public class TheLoai_ThucDon_Model {
    private String theLoai;
    private ArrayList<ThucDon_Model> dsThucDon;

    public TheLoai_ThucDon_Model(String theLoai, ArrayList<ThucDon_Model> dsThucDon) {
        this.theLoai = theLoai;
        this.dsThucDon = dsThucDon;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public ArrayList<ThucDon_Model> getDsThucDon() {
        return dsThucDon;
    }

    public void setDsThucDon(ArrayList<ThucDon_Model> dsThucDon) {
        this.dsThucDon = dsThucDon;
    }
}
