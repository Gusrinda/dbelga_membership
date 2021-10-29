
package com.dbelgamembership.membersip.Model.ModelResponseDistance;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Row implements Serializable, Parcelable
{

    @SerializedName("elements")
    @Expose
    private List<Element> elements = null;
    public final static Creator<Row> CREATOR = new Creator<Row>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Row createFromParcel(android.os.Parcel in) {
            return new Row(in);
        }

        public Row[] newArray(int size) {
            return (new Row[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3033392714854410375L;

    protected Row(android.os.Parcel in) {
        in.readList(this.elements, (com.dbelgamembership.membersip.Model.ModelResponseDistance.Element.class.getClassLoader()));
    }

    public Row() {
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(elements);
    }

    public int describeContents() {
        return  0;
    }

}
