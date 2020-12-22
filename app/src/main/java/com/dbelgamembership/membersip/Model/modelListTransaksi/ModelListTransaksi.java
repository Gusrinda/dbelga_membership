
package com.dbelgamembership.membersip.Model.modelListTransaksi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelListTransaksi implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelListTransaksi> CREATOR = new Creator<ModelListTransaksi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelListTransaksi createFromParcel(Parcel in) {
            return new ModelListTransaksi(in);
        }

        public ModelListTransaksi[] newArray(int size) {
            return (new ModelListTransaksi[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6332972371881764159L;

    protected ModelListTransaksi(Parcel in) {
        in.readList(this.data, (com.dbelgamembership.membersip.Model.modelListTransaksi.Datum.class.getClassLoader()));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelListTransaksi() {
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public ModelListTransaksi withData(List<Datum> data) {
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
