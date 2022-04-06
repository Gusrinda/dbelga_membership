package com.dbelgamembership.membersip.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelKatalog implements Parcelable {
    String id,
            nama_barang,
            merk_barang,
            kategori_barang,
            harga_diskon,
            harga_barang,
            kode_barang,
            stok,
            barcode,
            images,
            deskripsi,
            harga_2,
            harga_3 ,
            satuan_kemasan  ;
    String batasan1;
    String batasan2;
    String batasan3;
    int isPromo;
    String akhirPromo;
    String harga_promo;
    double stokPromo;
    int PromoMember;
    String PromoMemberKode;
    String PromoMemberAkhir;
    double PromoMemberPersenSilver;
    double PromoMemberPersenGold;
    double PromoMemberPersenPlatinum;
    double jumlahTerjual;

    public ModelKatalog() {
    }

    protected ModelKatalog(Parcel in) {
        id = in.readString();
        nama_barang = in.readString();
        merk_barang = in.readString();
        kategori_barang = in.readString();
        harga_diskon = in.readString();
        harga_barang = in.readString();
        kode_barang = in.readString();
        stok = in.readString();
        barcode = in.readString();
        images = in.readString();
        deskripsi = in.readString();
        harga_2 = in.readString();
        harga_3 = in.readString();
        satuan_kemasan = in.readString();
        batasan1 = in.readString();
        batasan2 = in.readString();
        batasan3 = in.readString();
        isPromo = in.readInt();
        akhirPromo = in.readString();
        harga_promo = in.readString();
        stokPromo = in.readDouble();
        PromoMember = in.readInt();
        PromoMemberKode = in.readString();
        PromoMemberAkhir = in.readString();
        PromoMemberPersenSilver = in.readDouble();
        PromoMemberPersenGold = in.readDouble();
        PromoMemberPersenPlatinum = in.readDouble();
        jumlahTerjual = in.readDouble();
    }

    public static final Creator<ModelKatalog> CREATOR = new Creator<ModelKatalog>() {
        @Override
        public ModelKatalog createFromParcel(Parcel in) {
            return new ModelKatalog(in);
        }

        @Override
        public ModelKatalog[] newArray(int size) {
            return new ModelKatalog[size];
        }
    };

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

    public String getKategori_barang() {
        return kategori_barang;
    }

    public void setKategori_barang(String kategori_barang) {
        this.kategori_barang = kategori_barang;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    public String getSatuan_kemasan() {
        return satuan_kemasan;
    }

    public void setSatuan_kemasan(String satuan_kemasan) {
        this.satuan_kemasan = satuan_kemasan;
    }

    public String getBatasan1() {
        return batasan1;
    }

    public void setBatasan1(String batasan1) {
        this.batasan1 = batasan1;
    }

    public String getBatasan2() {
        return batasan2;
    }

    public void setBatasan2(String batasan2) {
        this.batasan2 = batasan2;
    }

    public String getBatasan3() {
        return batasan3;
    }

    public void setBatasan3(String batasan3) {
        this.batasan3 = batasan3;
    }

    public int getIsPromo() {
        return isPromo;
    }

    public void setIsPromo(int isPromo) {
        this.isPromo = isPromo;
    }

    public String getAkhirPromo() {
        return akhirPromo;
    }

    public void setAkhirPromo(String akhirPromo) {
        this.akhirPromo = akhirPromo;
    }

    public String getHarga_promo() {
        return harga_promo;
    }

    public void setHarga_promo(String harga_promo) {
        this.harga_promo = harga_promo;
    }

    public double getStokPromo() {
        return stokPromo;
    }

    public void setStokPromo(double stokPromo) {
        this.stokPromo = stokPromo;
    }

    public int getPromoMember() {
        return PromoMember;
    }

    public void setPromoMember(int promoMember) {
        PromoMember = promoMember;
    }

    public String getPromoMemberKode() {
        return PromoMemberKode;
    }

    public void setPromoMemberKode(String promoMemberKode) {
        PromoMemberKode = promoMemberKode;
    }

    public String getPromoMemberAkhir() {
        return PromoMemberAkhir;
    }

    public void setPromoMemberAkhir(String promoMemberAkhir) {
        PromoMemberAkhir = promoMemberAkhir;
    }

    public double getPromoMemberPersenSilver() {
        return PromoMemberPersenSilver;
    }

    public void setPromoMemberPersenSilver(double promoMemberPersenSilver) {
        PromoMemberPersenSilver = promoMemberPersenSilver;
    }

    public double getPromoMemberPersenGold() {
        return PromoMemberPersenGold;
    }

    public void setPromoMemberPersenGold(double promoMemberPersenGold) {
        PromoMemberPersenGold = promoMemberPersenGold;
    }

    public double getPromoMemberPersenPlatinum() {
        return PromoMemberPersenPlatinum;
    }

    public void setPromoMemberPersenPlatinum(double promoMemberPersenPlatinum) {
        PromoMemberPersenPlatinum = promoMemberPersenPlatinum;
    }

    public double getJumlahTerjual() {
        return jumlahTerjual;
    }

    public void setJumlahTerjual(double jumlahTerjual) {
        this.jumlahTerjual = jumlahTerjual;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nama_barang);
        parcel.writeString(merk_barang);
        parcel.writeString(kategori_barang);
        parcel.writeString(harga_diskon);
        parcel.writeString(harga_barang);
        parcel.writeString(kode_barang);
        parcel.writeString(stok);
        parcel.writeString(barcode);
        parcel.writeString(images);
        parcel.writeString(deskripsi);
        parcel.writeString(harga_2);
        parcel.writeString(harga_3);
        parcel.writeString(satuan_kemasan);
        parcel.writeString(batasan1);
        parcel.writeString(batasan2);
        parcel.writeString(batasan3);
        parcel.writeInt(isPromo);
        parcel.writeString(akhirPromo);
        parcel.writeString(harga_promo);
        parcel.writeDouble(stokPromo);
        parcel.writeInt(PromoMember);
        parcel.writeString(PromoMemberKode);
        parcel.writeString(PromoMemberAkhir);
        parcel.writeDouble(PromoMemberPersenSilver);
        parcel.writeDouble(PromoMemberPersenGold);
        parcel.writeDouble(PromoMemberPersenPlatinum);
        parcel.writeDouble(jumlahTerjual);
    }
}
