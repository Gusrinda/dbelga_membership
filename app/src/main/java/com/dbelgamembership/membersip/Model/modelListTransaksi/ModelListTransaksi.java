
package com.dbelgamembership.membersip.Model.modelListTransaksi;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelListTransaksi implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelListTransaksi> CREATOR = new Creator<ModelListTransaksi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelListTransaksi createFromParcel(android.os.Parcel in) {
            return new ModelListTransaksi(in);
        }

        public ModelListTransaksi[] newArray(int size) {
            return (new ModelListTransaksi[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3620182073301334665L;

    protected ModelListTransaksi(android.os.Parcel in) {
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelListTransaksi() {
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ModelListTransaksi withData(Data data) {
        this.data = data;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ModelListTransaksi withStatus(Status status) {
        this.status = status;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(data);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
