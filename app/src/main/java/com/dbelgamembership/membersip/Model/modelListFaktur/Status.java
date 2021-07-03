
package com.dbelgamembership.membersip.Model.modelListFaktur;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Status implements Serializable, Parcelable
{

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("description")
    @Expose
    private String description;
    public final static Creator<Status> CREATOR = new Creator<Status>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Status createFromParcel(android.os.Parcel in) {
            return new Status(in);
        }

        public Status[] newArray(int size) {
            return (new Status[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3910591537459047316L;

    protected Status(android.os.Parcel in) {
        this.code = ((int) in.readValue((int.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Status() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Status withCode(int code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status withDescription(String description) {
        this.description = description;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(description);
    }

    public int describeContents() {
        return  0;
    }

}
