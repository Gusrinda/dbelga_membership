
package com.dbelgamembership.membersip.Model.ModelListWishlist;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail implements Serializable, Parcelable
{

    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("real_price")
    @Expose
    private String realPrice;
    @SerializedName("qty_stok")
    @Expose
    private String qtyStok;
    public final static Parcelable.Creator<Detail> CREATOR = new Creator<Detail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        public Detail[] newArray(int size) {
            return (new Detail[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4640867174071358576L;

    protected Detail(Parcel in) {
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.realPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyStok = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Detail() {
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public Detail withCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Detail withName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Detail withPrice(String price) {
        this.price = price;
        return this;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public Detail withRealPrice(String realPrice) {
        this.realPrice = realPrice;
        return this;
    }

    public String getQtyStok() {
        return qtyStok;
    }

    public void setQtyStok(String qtyStok) {
        this.qtyStok = qtyStok;
    }

    public Detail withQtyStok(String qtyStok) {
        this.qtyStok = qtyStok;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(codeProduct);
        dest.writeValue(name);
        dest.writeValue(price);
        dest.writeValue(realPrice);
        dest.writeValue(qtyStok);
    }

    public int describeContents() {
        return  0;
    }

}
