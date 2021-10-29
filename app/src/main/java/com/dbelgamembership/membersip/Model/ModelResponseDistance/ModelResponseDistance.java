
package com.dbelgamembership.membersip.Model.ModelResponseDistance;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelResponseDistance implements Serializable, Parcelable
{

    @SerializedName("destination_addresses")
    @Expose
    private List<String> destinationAddresses = null;
    @SerializedName("origin_addresses")
    @Expose
    private List<String> originAddresses = null;
    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<ModelResponseDistance> CREATOR = new Creator<ModelResponseDistance>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelResponseDistance createFromParcel(android.os.Parcel in) {
            return new ModelResponseDistance(in);
        }

        public ModelResponseDistance[] newArray(int size) {
            return (new ModelResponseDistance[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2625110845860143682L;

    protected ModelResponseDistance(android.os.Parcel in) {
        in.readList(this.destinationAddresses, (java.lang.String.class.getClassLoader()));
        in.readList(this.originAddresses, (java.lang.String.class.getClassLoader()));
        in.readList(this.rows, (com.dbelgamembership.membersip.Model.ModelResponseDistance.Row.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ModelResponseDistance() {
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(destinationAddresses);
        dest.writeList(originAddresses);
        dest.writeList(rows);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
