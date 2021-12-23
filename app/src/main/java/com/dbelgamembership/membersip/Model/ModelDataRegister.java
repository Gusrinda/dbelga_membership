package com.dbelgamembership.membersip.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelDataRegister implements Parcelable {
    private String namaPelanggan;
    private String jenisKelamin;
    private String tanggalLahir;
    private String nomorHandphone;
    private String emailPelanggan;
    private String password;
    private String nomorIdentitas;
    private String alamatKtp;
    private String alamatPelanggan;
    private String kota;
    private String kodePos;

    public ModelDataRegister() {
    }

    public ModelDataRegister(String namaPelanggan, String jenisKelamin, String tanggalLahir, String nomorHandphone, String emailPelanggan, String password, String nomorIdentitas, String alamatKtp, String alamatPelanggan, String kota, String kodePos) {
        this.namaPelanggan = namaPelanggan;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.nomorHandphone = nomorHandphone;
        this.emailPelanggan = emailPelanggan;
        this.password = password;
        this.nomorIdentitas = nomorIdentitas;
        this.alamatKtp = alamatKtp;
        this.alamatPelanggan = alamatPelanggan;
        this.kota = kota;
        this.kodePos = kodePos;
    }

    protected ModelDataRegister(Parcel in) {
        namaPelanggan = in.readString();
        jenisKelamin = in.readString();
        tanggalLahir = in.readString();
        nomorHandphone = in.readString();
        emailPelanggan = in.readString();
        password = in.readString();
        nomorIdentitas = in.readString();
        alamatKtp = in.readString();
        alamatPelanggan = in.readString();
        kota = in.readString();
        kodePos = in.readString();
    }

    public static final Creator<ModelDataRegister> CREATOR = new Creator<ModelDataRegister>() {
        @Override
        public ModelDataRegister createFromParcel(Parcel in) {
            return new ModelDataRegister(in);
        }

        @Override
        public ModelDataRegister[] newArray(int size) {
            return new ModelDataRegister[size];
        }
    };

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNomorHandphone() {
        return nomorHandphone;
    }

    public void setNomorHandphone(String nomorHandphone) {
        this.nomorHandphone = nomorHandphone;
    }

    public String getEmailPelanggan() {
        return emailPelanggan;
    }

    public void setEmailPelanggan(String emailPelanggan) {
        this.emailPelanggan = emailPelanggan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomorIdentitas() {
        return nomorIdentitas;
    }

    public void setNomorIdentitas(String nomorIdentitas) {
        this.nomorIdentitas = nomorIdentitas;
    }

    public String getAlamatKtp() {
        return alamatKtp;
    }

    public void setAlamatKtp(String alamatKtp) {
        this.alamatKtp = alamatKtp;
    }

    public String getAlamatPelanggan() {
        return alamatPelanggan;
    }

    public void setAlamatPelanggan(String alamatPelanggan) {
        this.alamatPelanggan = alamatPelanggan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaPelanggan);
        parcel.writeString(jenisKelamin);
        parcel.writeString(tanggalLahir);
        parcel.writeString(nomorHandphone);
        parcel.writeString(emailPelanggan);
        parcel.writeString(password);
        parcel.writeString(nomorIdentitas);
        parcel.writeString(alamatKtp);
        parcel.writeString(alamatPelanggan);
        parcel.writeString(kota);
        parcel.writeString(kodePos);
    }
}
