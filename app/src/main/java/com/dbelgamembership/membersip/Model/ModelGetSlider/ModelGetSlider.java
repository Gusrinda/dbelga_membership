
package com.dbelgamembership.membersip.Model.ModelGetSlider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGetSlider implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelGetSlider> CREATOR = new Creator<ModelGetSlider>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelGetSlider createFromParcel(android.os.Parcel in) {
            return new ModelGetSlider(in);
        }

        public ModelGetSlider[] newArray(int size) {
            return (new ModelGetSlider[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5960965094178373553L;

    protected ModelGetSlider(android.os.Parcel in) {
        in.readList(this.data, (com.dbelgamembership.membersip.Model.ModelGetSlider.Datum.class.getClassLoader()));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    public ModelGetSlider() {
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public ModelGetSlider withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ModelGetSlider withStatus(Status status) {
        this.status = status;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
