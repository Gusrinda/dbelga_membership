
package com.dbelgamembership.membersip.Model.ModelResponseCart;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DetailItemCart implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;
    @SerializedName("produk")
    @Expose
    private Integer produk;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("merek")
    @Expose
    private String merek;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("harga")
    @Expose
    private Harga harga;
    @SerializedName("stok")
    @Expose
    private Integer stok;
    public final static Creator<DetailItemCart> CREATOR = new Creator<DetailItemCart>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailItemCart createFromParcel(android.os.Parcel in) {
            return new DetailItemCart(in);
        }

        public DetailItemCart[] newArray(int size) {
            return (new DetailItemCart[size]);
        }

    }
    ;
    private final static long serialVersionUID = -816437064709975113L;

    protected DetailItemCart(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.namaProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.produk = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.barcode = ((String) in.readValue((String.class.getClassLoader())));
        this.qty = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.merek = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.harga = ((Harga) in.readValue((Harga.class.getClassLoader())));
        this.stok = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public DetailItemCart() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public Integer getProduk() {
        return produk;
    }

    public void setProduk(Integer produk) {
        this.produk = produk;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Harga getHarga() {
        return harga;
    }

    public void setHarga(Harga harga) {
        this.harga = harga;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(namaProduk);
        dest.writeValue(produk);
        dest.writeValue(barcode);
        dest.writeValue(qty);
        dest.writeValue(images);
        dest.writeValue(merek);
        dest.writeValue(code);
        dest.writeValue(harga);
        dest.writeValue(stok);
    }

    public int describeContents() {
        return  0;
    }

}
