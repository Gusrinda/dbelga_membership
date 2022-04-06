package com.dbelgamembership.membersip.Screen.Limit.Dummy;

public class ModelItemBayarTagihan {

    String idTransasksi;
    String kodeTransaksi;
    String createdAt;
    String totalTransaksi;
    boolean isLunas;

    public ModelItemBayarTagihan() {
    }

    public ModelItemBayarTagihan(String idTransasksi, String kodeTransaksi, String createdAt, String totalTransaksi, boolean isLunas) {
        this.idTransasksi = idTransasksi;
        this.kodeTransaksi = kodeTransaksi;
        this.createdAt = createdAt;
        this.totalTransaksi = totalTransaksi;
        this.isLunas = isLunas;
    }

    public String getIdTransasksi() {
        return idTransasksi;
    }

    public void setIdTransasksi(String idTransasksi) {
        this.idTransasksi = idTransasksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(String totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public boolean isLunas() {
        return isLunas;
    }

    public void setLunas(boolean lunas) {
        isLunas = lunas;
    }
}

