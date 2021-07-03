
package com.dbelgamembership.membersip.Model.ModelSearchWish;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("id_produk")
    @Expose
    private int idProduk;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("barcode_product")
    @Expose
    private String barcodeProduct;
    @SerializedName("qty_stok")
    @Expose
    private int qtyStok;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("price")
    @Expose
    private Price price;
    public final static Creator<MsgServer> CREATOR = new Creator<MsgServer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsgServer createFromParcel(android.os.Parcel in) {
            return new MsgServer(in);
        }

        public MsgServer[] newArray(int size) {
            return (new MsgServer[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5350165025116176035L;

    protected MsgServer(android.os.Parcel in) {
        this.idProduk = ((int) in.readValue((int.class.getClassLoader())));
        this.gambar = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.barcodeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyStok = ((int) in.readValue((int.class.getClassLoader())));
        this.qty = ((int) in.readValue((int.class.getClassLoader())));
        this.price = ((Price) in.readValue((Price.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public MsgServer withIdProduk(int idProduk) {
        this.idProduk = idProduk;
        return this;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public MsgServer withGambar(String gambar) {
        this.gambar = gambar;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MsgServer withName(String name) {
        this.name = name;
        return this;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public MsgServer withCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
        return this;
    }

    public String getBarcodeProduct() {
        return barcodeProduct;
    }

    public void setBarcodeProduct(String barcodeProduct) {
        this.barcodeProduct = barcodeProduct;
    }

    public MsgServer withBarcodeProduct(String barcodeProduct) {
        this.barcodeProduct = barcodeProduct;
        return this;
    }

    public int getQtyStok() {
        return qtyStok;
    }

    public void setQtyStok(int qtyStok) {
        this.qtyStok = qtyStok;
    }

    public MsgServer withQtyStok(int qtyStok) {
        this.qtyStok = qtyStok;
        return this;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public MsgServer withQty(int qty) {
        this.qty = qty;
        return this;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public MsgServer withPrice(Price price) {
        this.price = price;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(idProduk);
        dest.writeValue(gambar);
        dest.writeValue(name);
        dest.writeValue(codeProduct);
        dest.writeValue(barcodeProduct);
        dest.writeValue(qtyStok);
        dest.writeValue(qty);
        dest.writeValue(price);
    }

    public int describeContents() {
        return  0;
    }

}
