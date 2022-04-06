package com.dbelgamembership.membersip.Model;

import java.util.Objects;

public class modelArrayDetailBarangOrder {

    private String namaBrg;
    private double qty;
    private double qtyDiskon;
    private String tipeDiskon;
    private String harga;
    private String Code;
    private String total;
    private String potongan_diskon;
    private String nominal_diskon;
    private String keterangan;

    private boolean isDiskonMembership;
    private double persenDiskonMemberhsip;
    private double totalDiskonMembership;

    private boolean isBarangTebus;


    public modelArrayDetailBarangOrder() {
    }

    public modelArrayDetailBarangOrder(String namaBrg, double qty, double qtyDiskon, String tipeDiskon, String harga, String code, String total, String potongan_diskon, String nominal_diskon, String keterangan, boolean isDiskonMembership, double persenDiskonMemberhsip, double totalDiskonMembership, boolean isBarangTebus) {
        this.namaBrg = namaBrg;
        this.qty = qty;
        this.qtyDiskon = qtyDiskon;
        this.tipeDiskon = tipeDiskon;
        this.harga = harga;
        Code = code;
        this.total = total;
        this.potongan_diskon = potongan_diskon;
        this.nominal_diskon = nominal_diskon;
        this.keterangan = keterangan;
        this.isDiskonMembership = isDiskonMembership;
        this.persenDiskonMemberhsip = persenDiskonMemberhsip;
        this.totalDiskonMembership = totalDiskonMembership;
        this.isBarangTebus = isBarangTebus;
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
        assert (this.equals(other));
        return new modelArrayDetailBarangOrder(this.namaBrg, this.qty + other.qty, this.qtyDiskon, this.tipeDiskon, this.harga, this.Code, this.total, this.potongan_diskon, this.nominal_diskon, this.keterangan, this.isDiskonMembership, this.persenDiskonMemberhsip, this.totalDiskonMembership, this.isBarangTebus);
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

    public double getQtyDiskon() {
        return qtyDiskon;
    }

    public void setQtyDiskon(double qtyDiskon) {
        this.qtyDiskon = qtyDiskon;
    }

    public String getTipeDiskon() {
        return tipeDiskon;
    }

    public void setTipeDiskon(String tipeDiskon) {
        this.tipeDiskon = tipeDiskon;
    }

    public boolean isDiskonMembership() {
        return isDiskonMembership;
    }

    public void setDiskonMembership(boolean diskonMembership) {
        isDiskonMembership = diskonMembership;
    }

    public double getPersenDiskonMemberhsip() {
        return persenDiskonMemberhsip;
    }

    public void setPersenDiskonMemberhsip(double persenDiskonMemberhsip) {
        this.persenDiskonMemberhsip = persenDiskonMemberhsip;
    }

    public double getTotalDiskonMembership() {
        return totalDiskonMembership;
    }

    public void setTotalDiskonMembership(double totalDiskonMembership) {
        this.totalDiskonMembership = totalDiskonMembership;
    }

    public boolean isBarangTebus() {
        return isBarangTebus;
    }

    public void setBarangTebus(boolean barangTebus) {
        isBarangTebus = barangTebus;
    }
}
