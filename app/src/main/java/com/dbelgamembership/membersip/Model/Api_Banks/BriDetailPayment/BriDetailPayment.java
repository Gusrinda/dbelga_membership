
package com.dbelgamembership.membersip.Model.Api_Banks.BriDetailPayment;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BriDetailPayment implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("responseDescription")
    @Expose
    private String responseDescription;
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<BriDetailPayment> CREATOR = new Creator<BriDetailPayment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BriDetailPayment createFromParcel(android.os.Parcel in) {
            return new BriDetailPayment(in);
        }

        public BriDetailPayment[] newArray(int size) {
            return (new BriDetailPayment[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5680675578968585201L;

    protected BriDetailPayment(android.os.Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.responseDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.responseCode = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public BriDetailPayment() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(responseDescription);
        dest.writeValue(responseCode);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
