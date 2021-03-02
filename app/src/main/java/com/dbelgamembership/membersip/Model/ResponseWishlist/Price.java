
package com.dbelgamembership.membersip.Model.ResponseWishlist;

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
    private final static long serialVersionUID = 3518822173728563278L;

    protected Price(Parcel in) {
        this.harga = ((int) in.readValue((int.class.getClassLoader())));
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(harga);
    }

    public int describeContents() {
        return  0;
    }

}
