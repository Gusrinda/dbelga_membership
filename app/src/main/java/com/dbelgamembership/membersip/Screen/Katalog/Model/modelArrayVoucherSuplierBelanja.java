package com.dbelgamembership.membersip.Screen.Katalog.Model;

import java.util.Objects;

public class modelArrayVoucherSuplierBelanja {

    private String kodeVoucher;
    private String tipeVoucher;
    private String namaSuplierVoucher;
    private double potonganBelanjaSuplier;
    private double minimalVoucher;
    private double qtyMinimal;
    private double stokVoucher;


    public modelArrayVoucherSuplierBelanja() {
    }

    public modelArrayVoucherSuplierBelanja(String kodeVoucher, String tipeVoucher, String namaSuplierVoucher, double potonganBelanjaSuplier, double minimalVoucher, double qtyMinimal, double stokVoucher) {
        this.kodeVoucher = kodeVoucher;
        this.tipeVoucher = tipeVoucher;
        this.namaSuplierVoucher = namaSuplierVoucher;
        this.potonganBelanjaSuplier = potonganBelanjaSuplier;
        this.minimalVoucher = minimalVoucher;
        this.qtyMinimal = qtyMinimal;
        this.stokVoucher = stokVoucher;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        modelArrayVoucherSuplierBelanja that = (modelArrayVoucherSuplierBelanja) o;
        return Objects.equals(kodeVoucher, that.kodeVoucher);
    }


    @Override
    public int hashCode() {
        return Objects.hash(kodeVoucher);
    }

    public modelArrayVoucherSuplierBelanja merge(modelArrayVoucherSuplierBelanja other) {
        assert (this.equals(other));
        return new modelArrayVoucherSuplierBelanja(this.kodeVoucher, this.tipeVoucher , this.namaSuplierVoucher, this.potonganBelanjaSuplier,this.minimalVoucher, this.qtyMinimal, this.stokVoucher);
    }

    public String getNamaSuplierVoucher() {
        return namaSuplierVoucher;
    }

    public void setNamaSuplierVoucher(String namaSuplierVoucher) {
        this.namaSuplierVoucher = namaSuplierVoucher;
    }

    public String getKodeVoucher() {
        return kodeVoucher;
    }

    public void setKodeVoucher(String kodeVoucher) {
        this.kodeVoucher = kodeVoucher;
    }

    public String getTipeVoucher() {
        return tipeVoucher;
    }

    public void setTipeVoucher(String tipeVoucher) {
        this.tipeVoucher = tipeVoucher;
    }

    public double getMinimalVoucher() {
        return minimalVoucher;
    }

    public void setMinimalVoucher(double minimalVoucher) {
        this.minimalVoucher = minimalVoucher;
    }

    public double getQtyMinimal() {
        return qtyMinimal;
    }

    public void setQtyMinimal(double qtyMinimal) {
        this.qtyMinimal = qtyMinimal;
    }

    public double getStokVoucher() {
        return stokVoucher;
    }

    public void setStokVoucher(double stokVoucher) {
        this.stokVoucher = stokVoucher;
    }

    public double getPotonganBelanjaSuplier() {
        return potonganBelanjaSuplier;
    }

    public void setPotonganBelanjaSuplier(double potonganBelanjaSuplier) {
        this.potonganBelanjaSuplier = potonganBelanjaSuplier;
    }
}
