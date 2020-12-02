
package com.dbelgamembership.membersip.Model.modelBarang;

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
    private Integer harga;
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
    private final static long serialVersionUID = -5759932736221394038L;

    protected Price(Parcel in) {
        this.harga = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Price() {
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Price withHarga(Integer harga) {
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
