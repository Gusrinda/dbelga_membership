
package com.dbelgamembership.membersip.Model.ModelListWishlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Serializable, Parcelable
{

    @SerializedName("idMember")
    @Expose
    private int idMember;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = new ArrayList<Detail>();
    public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8742694375324486410L;

    protected Datum(Parcel in) {
        this.idMember = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.detail, (com.dbelgamembership.membersip.Model.ModelListWishlist.Detail.class.getClassLoader()));
    }

    public Datum() {
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public Datum withIdMember(int idMember) {
        this.idMember = idMember;
        return this;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public Datum withDetail(List<Detail> detail) {
        this.detail = detail;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idMember);
        dest.writeList(detail);
    }

    public int describeContents() {
        return  0;
    }

}
