
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DetailBarangTebu implements Serializable, Parcelable
{

    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("barang_tebus_code")
    @Expose
    private String barangTebusCode;
    @SerializedName("barang_tebus_id")
    @Expose
    private Integer barangTebusId;
    @SerializedName("barang_tebus_harga")
    @Expose
    private String barangTebusHarga;
    @SerializedName("barang_tebus_stok")
    @Expose
    private String barangTebusStok;
    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;
    @SerializedName("produk_code")
    @Expose
    private String produkCode;
    @SerializedName("harga_normal")
    @Expose
    private String hargaNormal;
    public final static Creator<DetailBarangTebu> CREATOR = new Creator<DetailBarangTebu>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailBarangTebu createFromParcel(android.os.Parcel in) {
            return new DetailBarangTebu(in);
        }

        public DetailBarangTebu[] newArray(int size) {
            return (new DetailBarangTebu[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1508785099111776062L;

    protected DetailBarangTebu(android.os.Parcel in) {
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.barangTebusCode = ((String) in.readValue((String.class.getClassLoader())));
        this.barangTebusId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.barangTebusHarga = ((String) in.readValue((String.class.getClassLoader())));
        this.barangTebusStok = ((String) in.readValue((String.class.getClassLoader())));
        this.namaProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.produkCode = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaNormal = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DetailBarangTebu() {
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public String getBarangTebusCode() {
        return barangTebusCode;
    }

    public void setBarangTebusCode(String barangTebusCode) {
        this.barangTebusCode = barangTebusCode;
    }

    public Integer getBarangTebusId() {
        return barangTebusId;
    }

    public void setBarangTebusId(Integer barangTebusId) {
        this.barangTebusId = barangTebusId;
    }

    public String getBarangTebusHarga() {
        return barangTebusHarga;
    }

    public void setBarangTebusHarga(String barangTebusHarga) {
        this.barangTebusHarga = barangTebusHarga;
    }

    public String getBarangTebusStok() {
        return barangTebusStok;
    }

    public void setBarangTebusStok(String barangTebusStok) {
        this.barangTebusStok = barangTebusStok;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getProdukCode() {
        return produkCode;
    }

    public void setProdukCode(String produkCode) {
        this.produkCode = produkCode;
    }

    public String getHargaNormal() {
        return hargaNormal;
    }

    public void setHargaNormal(String hargaNormal) {
        this.hargaNormal = hargaNormal;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(pembayaranCode);
        dest.writeValue(barangTebusCode);
        dest.writeValue(barangTebusId);
        dest.writeValue(barangTebusHarga);
        dest.writeValue(barangTebusStok);
        dest.writeValue(namaProduk);
        dest.writeValue(produkCode);
        dest.writeValue(hargaNormal);
    }

    public int describeContents() {
        return  0;
    }

}
