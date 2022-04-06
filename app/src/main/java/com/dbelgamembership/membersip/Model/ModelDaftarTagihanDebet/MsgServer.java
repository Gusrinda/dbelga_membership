
package com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status_member")
    @Expose
    private String statusMember;
    @SerializedName("detail_transaksi")
    @Expose
    private List<DetailTransaksi> detailTransaksi = null;
    public final static Creator<MsgServer> CREATOR = new Creator<MsgServer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsgServer createFromParcel(android.os.Parcel in) {
            return new MsgServer(in);
        }

        public MsgServer[] newArray(int size) {
            return (new MsgServer[size]);
        }

    }
    ;
    private final static long serialVersionUID = 146911091330549068L;

    protected MsgServer(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.statusMember = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.detailTransaksi, (com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.DetailTransaksi.class.getClassLoader()));
    }

    public MsgServer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusMember() {
        return statusMember;
    }

    public void setStatusMember(String statusMember) {
        this.statusMember = statusMember;
    }

    public List<DetailTransaksi> getDetailTransaksi() {
        return detailTransaksi;
    }

    public void setDetailTransaksi(List<DetailTransaksi> detailTransaksi) {
        this.detailTransaksi = detailTransaksi;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
        dest.writeValue(statusMember);
        dest.writeList(detailTransaksi);
    }

    public int describeContents() {
        return  0;
    }

}
