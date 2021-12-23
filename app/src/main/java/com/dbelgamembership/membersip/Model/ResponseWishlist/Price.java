
package com.dbelgamembership.membersip.Model.ResponseWishlist;

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
    private Integer hargaDua;
    @SerializedName("harga_tiga")
    @Expose
    private Integer hargaTiga;
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
    private final static long serialVersionUID = 423532242213819608L;

    protected Price(android.os.Parcel in) {
        this.harga = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaDua = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hargaTiga = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Price() {
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Integer getHargaDua() {
        return hargaDua;
    }

    public void setHargaDua(Integer hargaDua) {
        this.hargaDua = hargaDua;
    }

    public Integer getHargaTiga() {
        return hargaTiga;
    }

    public void setHargaTiga(Integer hargaTiga) {
        this.hargaTiga = hargaTiga;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(harga);
        dest.writeValue(hargaDua);
        dest.writeValue(hargaTiga);
    }

    public int describeContents() {
        return  0;
    }

}
