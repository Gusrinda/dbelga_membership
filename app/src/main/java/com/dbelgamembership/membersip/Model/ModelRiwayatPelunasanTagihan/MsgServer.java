
package com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan;

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
    @SerializedName("daftar_pelunasan")
    @Expose
    private List<DaftarPelunasan> daftarPelunasan = null;
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
    private final static long serialVersionUID = 1977519026080560468L;

    protected MsgServer(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.statusMember = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.daftarPelunasan, (com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan.DaftarPelunasan.class.getClassLoader()));
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

    public List<DaftarPelunasan> getDaftarPelunasan() {
        return daftarPelunasan;
    }

    public void setDaftarPelunasan(List<DaftarPelunasan> daftarPelunasan) {
        this.daftarPelunasan = daftarPelunasan;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
        dest.writeValue(statusMember);
        dest.writeList(daftarPelunasan);
    }

    public int describeContents() {
        return  0;
    }

}
