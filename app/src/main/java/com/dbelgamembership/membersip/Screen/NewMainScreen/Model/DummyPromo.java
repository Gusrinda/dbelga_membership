package com.dbelgamembership.membersip.Screen.NewMainScreen.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class DummyPromo implements Parcelable {

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

    protected DummyPromo(Parcel in) {
        namaPromo = in.readString();
        isBaru = in.readByte() != 0;
        tanggalBerlaku = in.readString();
        tipePromo = in.readString();
        alamatGambar = in.readInt();
    }

    public static final Creator<DummyPromo> CREATOR = new Creator<DummyPromo>() {
        @Override
        public DummyPromo createFromParcel(Parcel in) {
            return new DummyPromo(in);
        }

        @Override
        public DummyPromo[] newArray(int size) {
            return new DummyPromo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namaPromo);
        dest.writeByte((byte) (isBaru ? 1 : 0));
        dest.writeString(tanggalBerlaku);
        dest.writeString(tipePromo);
        dest.writeInt(alamatGambar);
    }
}
