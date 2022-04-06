
package com.dbelgamembership.membersip.Model.responseCancel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseCancel implements Serializable, Parcelable
{

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("description")
    @Expose
    private String description;
    public final static Creator<ResponseCancel> CREATOR = new Creator<ResponseCancel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseCancel createFromParcel(Parcel in) {
            return new ResponseCancel(in);
        }

        public ResponseCancel[] newArray(int size) {
            return (new ResponseCancel[size]);
        }

    }
    ;
    private final static long serialVersionUID = 7185013927539746379L;

    protected ResponseCancel(Parcel in) {
        this.code = ((int) in.readValue((int.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ResponseCancel() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponseCancel withCode(int code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResponseCancel withDescription(String description) {
        this.description = description;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(description);
    }

    public int describeContents() {
        return  0;
    }

}
