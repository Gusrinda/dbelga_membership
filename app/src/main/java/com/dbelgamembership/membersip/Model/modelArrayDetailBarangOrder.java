package com.dbelgamembership.membersip.Model;

import java.util.Objects;

public class modelArrayDetailBarangOrder {

    private String namaBrg;
    private double qty;
    private String harga;
    private String Code;
    private String total;
    private String potongan_diskon;
    private String nominal_diskon;
    private String keterangan;

    public modelArrayDetailBarangOrder() {
    }

    public modelArrayDetailBarangOrder(String namaBrg, double qty, String harga, String code, String total, String potongan_diskon, String nominal_diskon, String keterangan) {
        this.namaBrg = namaBrg;
        this.qty = qty;
        this.harga = harga;
        this.Code = code;
        this.total = total;
        this.potongan_diskon = potongan_diskon;
        this.nominal_diskon = nominal_diskon;
        this.keterangan = keterangan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        modelArrayDetailBarangOrder that = (modelArrayDetailBarangOrder) o;
        return Objects.equals(namaBrg, that.namaBrg) &&
                Objects.equals(Code, that.Code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namaBrg, Code);
    }

    public modelArrayDetailBarangOrder merge(modelArrayDetailBarangOrder other) {
        assert(this.equals(other));
        return new modelArrayDetailBarangOrder(this.namaBrg,this.qty + other.qty, this.harga, this.Code, this.total,this.potongan_diskon, this.nominal_diskon, this.keterangan);
    }

    public String getNamaBrg() {
        return namaBrg;
    }

    public void setNamaBrg(String namaBrg) {
        this.namaBrg = namaBrg;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNominal_diskon() {
        return nominal_diskon;
    }

    public void setNominal_diskon(String nominal_diskon) {
        this.nominal_diskon = nominal_diskon;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getPotongan_diskon() {
        return potongan_diskon;
    }

    public void setPotongan_diskon(String potongan_diskon) {
        this.potongan_diskon = potongan_diskon;
    }
}
