
package com.dbelgamembership.membersip.Model.ModelWish;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishlistDetail implements Serializable, Parcelable
{

    @SerializedName("id_product")
    @Expose
    private int idProduct;
    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("qty_stok")
    @Expose
    private int qtyStok;
    @SerializedName("price")
    @Expose
    private Price price;
    public final static Parcelable.Creator<WishlistDetail> CREATOR = new Creator<WishlistDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WishlistDetail createFromParcel(Parcel in) {
            return new WishlistDetail(in);
        }

        public WishlistDetail[] newArray(int size) {
            return (new WishlistDetail[size]);
        }

    }
    ;
    private final static long serialVersionUID = 9135666169962624873L;

    protected WishlistDetail(Parcel in) {
        this.idProduct = ((int) in.readValue((int.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.gambar = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyStok = ((int) in.readValue((int.class.getClassLoader())));
        this.price = ((Price) in.readValue((Price.class.getClassLoader())));
    }

    public WishlistDetail() {
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public WishlistDetail withIdProduct(int idProduct) {
        this.idProduct = idProduct;
        return this;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public WishlistDetail withCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WishlistDetail withName(String name) {
        this.name = name;
        return this;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public WishlistDetail withGambar(String gambar) {
        this.gambar = gambar;
        return this;
    }

    public int getQtyStok() {
        return qtyStok;
    }

    public void setQtyStok(int qtyStok) {
        this.qtyStok = qtyStok;
    }

    public WishlistDetail withQtyStok(int qtyStok) {
        this.qtyStok = qtyStok;
        return this;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public WishlistDetail withPrice(Price price) {
        this.price = price;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idProduct);
        dest.writeValue(codeProduct);
        dest.writeValue(name);
        dest.writeValue(gambar);
        dest.writeValue(qtyStok);
        dest.writeValue(price);
    }

    public int describeContents() {
        return  0;
    }

}
