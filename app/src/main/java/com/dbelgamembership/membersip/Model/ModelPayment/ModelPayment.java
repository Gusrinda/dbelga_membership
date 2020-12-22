
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelPayment implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelPayment> CREATOR = new Creator<ModelPayment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelPayment createFromParcel(Parcel in) {
            return new ModelPayment(in);
        }

        public ModelPayment[] newArray(int size) {
            return (new ModelPayment[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6606358864376337644L;

    protected ModelPayment(Parcel in) {
        in.readList(this.data, (com.dbelgamembership.membersip.Model.ModelPayment.Datum.class.getClassLoader()));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelPayment() {
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public ModelPayment withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ModelPayment withStatus(Status status) {
        this.status = status;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
