
package com.dbelgamembership.membersip.Model.ModelListWishlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListWishlist implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Parcelable.Creator<ModelListWishlist> CREATOR = new Creator<ModelListWishlist>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelListWishlist createFromParcel(Parcel in) {
            return new ModelListWishlist(in);
        }

        public ModelListWishlist[] newArray(int size) {
            return (new ModelListWishlist[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8696520392850569594L;

    protected ModelListWishlist(Parcel in) {
        in.readList(this.data, (com.dbelgamembership.membersip.Model.ModelListWishlist.Datum.class.getClassLoader()));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelListWishlist() {
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public ModelListWishlist withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ModelListWishlist withStatus(Status status) {
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
