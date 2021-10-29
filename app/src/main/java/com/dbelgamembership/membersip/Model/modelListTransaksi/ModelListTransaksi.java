
package com.dbelgamembership.membersip.Model.modelListTransaksi;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
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
    private final static long serialVersionUID = 1295920395915307866L;

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
