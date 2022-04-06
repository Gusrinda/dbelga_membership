
package com.dbelgamembership.membersip.Model.ModelListBank;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ModelListBank implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelListBank> CREATOR = new Creator<ModelListBank>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelListBank createFromParcel(android.os.Parcel in) {
            return new ModelListBank(in);
        }

        public ModelListBank[] newArray(int size) {
            return (new ModelListBank[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2538759888104520319L;

    protected ModelListBank(android.os.Parcel in) {
        in.readList(this.data, (com.dbelgamembership.membersip.Model.ModelListBank.Datum.class.getClassLoader()));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelListBank() {
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
