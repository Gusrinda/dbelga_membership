
package com.dbelgamembership.membersip.Model.ModelListBank;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Status implements Serializable, Parcelable
{

    @SerializedName("code")
    @Expose
    private Integer code;
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
    private final static long serialVersionUID = 9135644310294163749L;

    protected Status(android.os.Parcel in) {
        this.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Status() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(description);
    }

    public int describeContents() {
        return  0;
    }

}
