package com.dbelgamembership.membersip.Screen.Voucher.Dummy;

public class DummyModelVoucher {

    String namaVoucher;
    String tipeVoucher;
    String deskripsiVoucher;
    String expVoucher;
    int poinVoicher;
    int sisaVoucher;

    public DummyModelVoucher() {
    }

    public DummyModelVoucher(String namaVoucher, String tipeVoucher, String deskripsiVoucher, String expVoucher, int poinVoicher, int sisaVoucher) {
        this.namaVoucher = namaVoucher;
        this.tipeVoucher = tipeVoucher;
        this.deskripsiVoucher = deskripsiVoucher;
        this.expVoucher = expVoucher;
        this.poinVoicher = poinVoicher;
        this.sisaVoucher = sisaVoucher;
    }

    public String getNamaVoucher() {
        return namaVoucher;
    }

    public void setNamaVoucher(String namaVoucher) {
        this.namaVoucher = namaVoucher;
    }

    public String getTipeVoucher() {
        return tipeVoucher;
    }

    public void setTipeVoucher(String tipeVoucher) {
        this.tipeVoucher = tipeVoucher;
    }

    public String getExpVoucher() {
        return expVoucher;
    }

    public void setExpVoucher(String expVoucher) {
        this.expVoucher = expVoucher;
    }

    public int getPoinVoicher() {
        return poinVoicher;
    }

    public void setPoinVoicher(int poinVoicher) {
        this.poinVoicher = poinVoicher;
    }

    public int getSisaVoucher() {
        return sisaVoucher;
    }

    public void setSisaVoucher(int sisaVoucher) {
        this.sisaVoucher = sisaVoucher;
    }

    public String getDeskripsiVoucher() {
        return deskripsiVoucher;
    }

    public void setDeskripsiVoucher(String deskripsiVoucher) {
        this.deskripsiVoucher = deskripsiVoucher;
    }
}
