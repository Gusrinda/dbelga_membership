
package com.dbelgamembership.membersip.Model.ModelResponseDistance;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Element implements Serializable, Parcelable
{

    @SerializedName("distance")
    @Expose
    private Distance distance;
    @SerializedName("duration")
    @Expose
    private Duration duration;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<Element> CREATOR = new Creator<Element>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Element createFromParcel(android.os.Parcel in) {
            return new Element(in);
        }

        public Element[] newArray(int size) {
            return (new Element[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3718430949740912415L;

    protected Element(android.os.Parcel in) {
        this.distance = ((Distance) in.readValue((Distance.class.getClassLoader())));
        this.duration = ((Duration) in.readValue((Duration.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Element() {
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(distance);
        dest.writeValue(duration);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
