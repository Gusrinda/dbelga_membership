package com.dbelgamembership.membersip.Model.ModelToko;

public class ModelGudang {

    String namaGudang;
    String alamatGudang;
    String idGudang;
    String latGudang;
    String longGudang;
    String textJarak;
    int valueJarak;

    public ModelGudang(String namaGudang, String alamatGudang, String idGudang, String latGudang, String longGudang, String textJarak, int valueJarak) {
        this.namaGudang = namaGudang;
        this.alamatGudang = alamatGudang;
        this.idGudang = idGudang;
        this.latGudang = latGudang;
        this.longGudang = longGudang;
        this.textJarak = textJarak;
        this.valueJarak = valueJarak;
    }



    public String getNamaGudang() {
        return namaGudang;
    }

    public void setNamaGudang(String namaGudang) {
        this.namaGudang = namaGudang;
    }

    public String getAlamatGudang() {
        return alamatGudang;
    }

    public void setAlamatGudang(String alamatGudang) {
        this.alamatGudang = alamatGudang;
    }

    public String getIdGudang() {
        return idGudang;
    }

    public void setIdGudang(String idGudang) {
        this.idGudang = idGudang;
    }

    public String getLatGudang() {
        return latGudang;
    }

    public void setLatGudang(String latGudang) {
        this.latGudang = latGudang;
    }

    public String getLongGudang() {
        return longGudang;
    }

    public void setLongGudang(String longGudang) {
        this.longGudang = longGudang;
    }

    public String getTextJarak() {
        return textJarak;
    }

    public void setTextJarak(String textJarak) {
        this.textJarak = textJarak;
    }

    public int getValueJarak() {
        return valueJarak;
    }

    public void setValueJarak(int valueJarak) {
        this.valueJarak = valueJarak;
    }
}
