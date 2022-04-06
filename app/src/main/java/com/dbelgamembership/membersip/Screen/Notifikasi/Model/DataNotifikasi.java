package com.dbelgamembership.membersip.Screen.Notifikasi.Model;


public class DataNotifikasi {

    private String tipe;
    private String context;
    private String id;
    private String code;

    public DataNotifikasi() {
    }

    public DataNotifikasi(String tipe, String context, String id, String code) {
        this.tipe = tipe;
        this.context = context;
        this.id = id;
        this.code = code;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

