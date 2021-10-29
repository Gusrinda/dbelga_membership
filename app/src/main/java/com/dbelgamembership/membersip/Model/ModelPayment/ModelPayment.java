
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ModelPayment implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelPayment> CREATOR = new Creator<ModelPayment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelPayment createFromParcel(android.os.Parcel in) {
            return new ModelPayment(in);
        }

        public ModelPayment[] newArray(int size) {
            return (new ModelPayment[size]);
        }

    }
    ;
    private final static long serialVersionUID = -105489987943140493L;

    protected ModelPayment(android.os.Parcel in) {
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelPayment() {
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(data);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
