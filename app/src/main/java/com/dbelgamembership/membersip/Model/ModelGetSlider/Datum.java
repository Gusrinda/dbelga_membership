
package com.dbelgamembership.membersip.Model.ModelGetSlider;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(android.os.Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1604724410808986818L;

    protected Datum(android.os.Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Datum() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Datum withId(int id) {
        this.id = id;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Datum withImage(String image) {
        this.image = image;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}
