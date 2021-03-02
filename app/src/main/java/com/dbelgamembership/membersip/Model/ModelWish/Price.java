
package com.dbelgamembership.membersip.Model.ModelWish;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price implements Serializable, Parcelable
{

    @SerializedName("harga")
    @Expose
    private int harga;
    @SerializedName("harga_dua")
    @Expose
    private int hargaDua;
    @SerializedName("harga_tiga")
    @Expose
    private int hargaTiga;
    public final static Parcelable.Creator<Price> CREATOR = new Creator<Price>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        public Price[] newArray(int size) {
            return (new Price[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6543095353786107555L;

    protected Price(Parcel in) {
        this.harga = ((int) in.readValue((int.class.getClassLoader())));
        this.hargaDua = ((int) in.readValue((int.class.getClassLoader())));
        this.hargaTiga = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Price() {
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public Price withHarga(int harga) {
        this.harga = harga;
        return this;
    }

    public int getHargaDua() {
        return hargaDua;
    }

    public void setHargaDua(int hargaDua) {
        this.hargaDua = hargaDua;
    }

    public Price withHargaDua(int hargaDua) {
        this.hargaDua = hargaDua;
        return this;
    }

    public int getHargaTiga() {
        return hargaTiga;
    }

    public void setHargaTiga(int hargaTiga) {
        this.hargaTiga = hargaTiga;
    }

    public Price withHargaTiga(int hargaTiga) {
        this.hargaTiga = hargaTiga;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(harga);
        dest.writeValue(hargaDua);
        dest.writeValue(hargaTiga);
    }

    public int describeContents() {
        return  0;
    }

}
