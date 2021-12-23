package com.dbelgamembership.membersip.Screen.NewMainScreen.Model;

public class DummyPromo {

    private String namaPromo;
    private boolean isBaru;
    private String tanggalBerlaku;
    private String tipePromo;
    private int alamatGambar;

    public DummyPromo() {
    }

    public DummyPromo(String namaPromo, boolean isBaru, String tanggalBerlaku, String tipePromo, int alamatGambar) {
        this.namaPromo = namaPromo;
        this.isBaru = isBaru;
        this.tanggalBerlaku = tanggalBerlaku;
        this.tipePromo = tipePromo;
        this.alamatGambar = alamatGambar;
    }

    public String getNamaPromo() {
        return namaPromo;
    }

    public void setNamaPromo(String namaPromo) {
        this.namaPromo = namaPromo;
    }

    public boolean isBaru() {
        return isBaru;
    }

    public void setBaru(boolean baru) {
        isBaru = baru;
    }

    public String getTanggalBerlaku() {
        return tanggalBerlaku;
    }

    public void setTanggalBerlaku(String tanggalBerlaku) {
        this.tanggalBerlaku = tanggalBerlaku;
    }

    public String getTipePromo() {
        return tipePromo;
    }

    public void setTipePromo(String tipePromo) {
        this.tipePromo = tipePromo;
    }

    public int getAlamatGambar() {
        return alamatGambar;
    }

    public void setAlamatGambar(int alamatGambar) {
        this.alamatGambar = alamatGambar;
    }
}
