
package com.dbelgamembership.membersip.Model.ModelBannerPromo;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ModelBannerPromo implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("status")
    @Expose
    private Status status;
    public final static Creator<ModelBannerPromo> CREATOR = new Creator<ModelBannerPromo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelBannerPromo createFromParcel(android.os.Parcel in) {
            return new ModelBannerPromo(in);
        }

        public ModelBannerPromo[] newArray(int size) {
            return (new ModelBannerPromo[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4592103509890284734L;

    protected ModelBannerPromo(android.os.Parcel in) {
        in.readList(this.data, (com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum.class.getClassLoader()));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelBannerPromo() {
    }

    /**
     * 
     * @param data
     * @param status
     */
    public ModelBannerPromo(List<Datum> data, Status status) {
        super();
        this.data = data;
        this.status = status;
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
