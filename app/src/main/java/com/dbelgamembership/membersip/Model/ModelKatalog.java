package com.dbelgamembership.membersip.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Display;

public class ModelKatalog implements Parcelable {

    public String getHarga_promo() {
        return harga_promo;
    }

    public void setHarga_promo(String harga_promo) {
        this.harga_promo = harga_promo;
    }

    String id, nama_barang, merk_barang, kategori_barang, harga_diskon, harga_barang,harga_promo, kode_barang, stok, barcode, images, deskripsi, harga_2, harga_3 , satuan_kemasan  ;

    public ModelKatalog() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getMerk_barang() {
        return merk_barang;
    }

    public void setMerk_barang(String merk_barang) {
        this.merk_barang = merk_barang;
    }

    public String getHarga_diskon() {
        return harga_diskon;
    }

    public void setHarga_diskon(String harga_diskon) {
        this.harga_diskon = harga_diskon;
    }

    public String getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(String harga_barang) {
        this.harga_barang = harga_barang;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public void setKode_barang(String kode_barang) {
        this.kode_barang = kode_barang;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getKategori_barang() {
        return kategori_barang;
    }

    public void setKategori_barang(String kategori_barang) {
        this.kategori_barang = kategori_barang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getSatuan_kemasan() {
        return satuan_kemasan;
    }

    public void setSatuan_kemasan(String satuan_kemasan) {
        this.satuan_kemasan = satuan_kemasan;
    }

    public String getHarga_2() {
        return harga_2;
    }

    public void setHarga_2(String harga_2) {
        this.harga_2 = harga_2;
    }

    public String getHarga_3() {
        return harga_3;
    }

    public void setHarga_3(String harga_3) {
        this.harga_3 = harga_3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama_barang);
        dest.writeString(this.merk_barang);
        dest.writeString(this.harga_diskon);
        dest.writeString(this.kategori_barang);
        dest.writeString(this.harga_barang);
        dest.writeString(this.harga_promo);
        dest.writeString(this.kode_barang);
        dest.writeString(this.stok);
        dest.writeString(this.barcode);
        dest.writeString(this.images);
        dest.writeString(this.deskripsi);
        dest.writeString(this.harga_2);
        dest.writeString(this.harga_3);
        dest.writeString(this.satuan_kemasan);
    }

    protected ModelKatalog(Parcel in) {
        this.id = in.readString();
        this.nama_barang = in.readString();
        this.merk_barang = in.readString();
        this.harga_diskon = in.readString();
        this.harga_barang = in.readString();
        this.kategori_barang = in.readString();
        this.harga_promo = in.readString();
        this.kode_barang = in.readString();
        this.stok = in.readString();
        this.barcode = in.readString();
        this.images = in.readString();
        this.deskripsi = in.readString();
        this.harga_2 = in.readString();
        this.harga_3 = in.readString();
        this.satuan_kemasan = in.readString();
    }

    public static final Creator<ModelKatalog> CREATOR = new Creator<ModelKatalog>() {
        @Override
        public ModelKatalog createFromParcel(Parcel source) {
            return new ModelKatalog(source);
        }

        @Override
        public ModelKatalog[] newArray(int size) {
            return new ModelKatalog[size];
        }
    };
}
