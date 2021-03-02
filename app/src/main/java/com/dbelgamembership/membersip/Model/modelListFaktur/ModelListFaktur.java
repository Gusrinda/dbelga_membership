
package com.dbelgamembership.membersip.Model.modelListFaktur;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelListFaktur implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelListFaktur> CREATOR = new Creator<ModelListFaktur>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelListFaktur createFromParcel(Parcel in) {
            return new ModelListFaktur(in);
        }

        public ModelListFaktur[] newArray(int size) {
            return (new ModelListFaktur[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8232287600302264482L;

    protected ModelListFaktur(Parcel in) {
        in.readList(this.data, (com.dbelgamembership.membersip.Model.modelListFaktur.Datum.class.getClassLoader()));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelListFaktur() {
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public ModelListFaktur withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ModelListFaktur withStatus(Status status) {
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
