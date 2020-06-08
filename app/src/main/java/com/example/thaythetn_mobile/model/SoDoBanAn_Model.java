package com.example.thaythetn_mobile.model;

public class SoDoBanAn_Model {
    private int idSoDo;
    private byte[] anhSoDo;

    public SoDoBanAn_Model(int idSoDo, byte[] anhSoDo) {
        this.idSoDo = idSoDo;
        this.anhSoDo = anhSoDo;
    }

    public int getIdSoDo() {
        return idSoDo;
    }

    public void setIdSoDo(int idSoDo) {
        this.idSoDo = idSoDo;
    }

    public byte[] getAnhSoDo() {
        return anhSoDo;
    }

    public void setAnhSoDo(byte[] anhSoDo) {
        this.anhSoDo = anhSoDo;
    }
}
