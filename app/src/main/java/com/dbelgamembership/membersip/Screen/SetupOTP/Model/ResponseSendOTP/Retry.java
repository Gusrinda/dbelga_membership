
package com.dbelgamembership.membersip.Screen.SetupOTP.Model.ResponseSendOTP;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Retry implements Serializable, Parcelable
{

    @SerializedName("count")
    @Expose
    private Integer count;
    public final static Creator<Retry> CREATOR = new Creator<Retry>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Retry createFromParcel(android.os.Parcel in) {
            return new Retry(in);
        }

        public Retry[] newArray(int size) {
            return (new Retry[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6446770276179948042L;

    protected Retry(android.os.Parcel in) {
        this.count = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Retry() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(count);
    }

    public int describeContents() {
        return  0;
    }

}
