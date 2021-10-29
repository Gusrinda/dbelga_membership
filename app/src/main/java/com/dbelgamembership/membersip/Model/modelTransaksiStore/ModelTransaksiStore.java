
package com.dbelgamembership.membersip.Model.modelTransaksiStore;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelTransaksiStore implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelTransaksiStore> CREATOR = new Creator<ModelTransaksiStore>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelTransaksiStore createFromParcel(Parcel in) {
            return new ModelTransaksiStore(in);
        }

        public ModelTransaksiStore[] newArray(int size) {
            return (new ModelTransaksiStore[size]);
        }

    }
    ;
    private final static long serialVersionUID = 610607229361495937L;

    protected ModelTransaksiStore(Parcel in) {
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelTransaksiStore() {
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ModelTransaksiStore withData(Data data) {
        this.data = data;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ModelTransaksiStore withStatus(Status status) {
        this.status = status;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(data);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
