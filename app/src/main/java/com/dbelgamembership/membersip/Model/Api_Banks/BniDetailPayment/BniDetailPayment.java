
package com.dbelgamembership.membersip.Model.Api_Banks.BniDetailPayment;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BniDetailPayment implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<BniDetailPayment> CREATOR = new Creator<BniDetailPayment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BniDetailPayment createFromParcel(android.os.Parcel in) {
            return new BniDetailPayment(in);
        }

        public BniDetailPayment[] newArray(int size) {
            return (new BniDetailPayment[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2396254267283006465L;

    protected BniDetailPayment(android.os.Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public BniDetailPayment() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
