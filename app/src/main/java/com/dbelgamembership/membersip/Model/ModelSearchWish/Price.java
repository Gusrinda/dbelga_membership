
package com.dbelgamembership.membersip.Model.ModelSearchWish;

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
    private String harga;
    @SerializedName("harga_dua")
    @Expose
    private String hargaDua;
    @SerializedName("harga_tiga")
    @Expose
    private String hargaTiga;
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
    private final static long serialVersionUID = -224352059732339484L;

    protected Price(Parcel in) {
        this.harga = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaDua = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaTiga = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Price() {
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Price withHarga(String harga) {
        this.harga = harga;
        return this;
    }

    public String getHargaDua() {
        return hargaDua;
    }

    public void setHargaDua(String hargaDua) {
        this.hargaDua = hargaDua;
    }

    public Price withHargaDua(String hargaDua) {
        this.hargaDua = hargaDua;
        return this;
    }

    public String getHargaTiga() {
        return hargaTiga;
    }

    public void setHargaTiga(String hargaTiga) {
        this.hargaTiga = hargaTiga;
    }

    public Price withHargaTiga(String hargaTiga) {
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
