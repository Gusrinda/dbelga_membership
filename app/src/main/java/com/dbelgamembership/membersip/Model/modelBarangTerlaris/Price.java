
package com.dbelgamembership.membersip.Model.ModelBarangTerlaris;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Price implements Serializable, Parcelable
{

    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("harga_dua")
    @Expose
    private String hargaDua;
    @SerializedName("harga_tiga")
    @Expose
    private String hargaTiga;
    @SerializedName("qty_harga_1")
    @Expose
    private Integer qtyHarga1;
    @SerializedName("qty_harga_2")
    @Expose
    private Integer qtyHarga2;
    @SerializedName("qty_harga_3")
    @Expose
    private Integer qtyHarga3;
    public final static Creator<Price> CREATOR = new Creator<Price>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Price createFromParcel(android.os.Parcel in) {
            return new Price(in);
        }

        public Price[] newArray(int size) {
            return (new Price[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1319655554255255021L;

    protected Price(android.os.Parcel in) {
        this.harga = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaDua = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaTiga = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyHarga1 = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qtyHarga2 = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qtyHarga3 = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Price() {
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getHargaDua() {
        return hargaDua;
    }

    public void setHargaDua(String hargaDua) {
        this.hargaDua = hargaDua;
    }

    public String getHargaTiga() {
        return hargaTiga;
    }

    public void setHargaTiga(String hargaTiga) {
        this.hargaTiga = hargaTiga;
    }

    public Integer getQtyHarga1() {
        return qtyHarga1;
    }

    public void setQtyHarga1(Integer qtyHarga1) {
        this.qtyHarga1 = qtyHarga1;
    }

    public Integer getQtyHarga2() {
        return qtyHarga2;
    }

    public void setQtyHarga2(Integer qtyHarga2) {
        this.qtyHarga2 = qtyHarga2;
    }

    public Integer getQtyHarga3() {
        return qtyHarga3;
    }

    public void setQtyHarga3(Integer qtyHarga3) {
        this.qtyHarga3 = qtyHarga3;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(harga);
        dest.writeValue(hargaDua);
        dest.writeValue(hargaTiga);
        dest.writeValue(qtyHarga1);
        dest.writeValue(qtyHarga2);
        dest.writeValue(qtyHarga3);
    }

    public int describeContents() {
        return  0;
    }

}
