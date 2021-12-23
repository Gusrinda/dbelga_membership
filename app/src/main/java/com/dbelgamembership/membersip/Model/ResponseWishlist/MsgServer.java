
package com.dbelgamembership.membersip.Model.ResponseWishlist;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("id_produk")
    @Expose
    private Integer idProduk;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("id_gudang")
    @Expose
    private Integer idGudang;
    @SerializedName("qty_stok")
    @Expose
    private double qtyStok;
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
    private final static long serialVersionUID = 4550613377690107450L;

    protected MsgServer(android.os.Parcel in) {
        this.idProduk = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.gambar = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.qty = ((String) in.readValue((String.class.getClassLoader())));
        this.idGudang = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qtyStok = ((Double) in.readValue((Double.class.getClassLoader())));
        this.price = ((Price) in.readValue((Price.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public Integer getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(Integer idProduk) {
        this.idProduk = idProduk;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Integer getIdGudang() {
        return idGudang;
    }

    public void setIdGudang(Integer idGudang) {
        this.idGudang = idGudang;
    }

    public Double getQtyStok() {
        return qtyStok;
    }

    public void setQtyStok(Double qtyStok) {
        this.qtyStok = qtyStok;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(idProduk);
        dest.writeValue(gambar);
        dest.writeValue(name);
        dest.writeValue(codeProduct);
        dest.writeValue(qty);
        dest.writeValue(idGudang);
        dest.writeValue(qtyStok);
        dest.writeValue(price);
    }

    public int describeContents() {
        return  0;
    }

}
