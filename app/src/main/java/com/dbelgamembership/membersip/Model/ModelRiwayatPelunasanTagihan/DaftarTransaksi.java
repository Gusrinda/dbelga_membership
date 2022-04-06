
package com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DaftarTransaksi implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code_pembayaran")
    @Expose
    private String codePembayaran;
    @SerializedName("total_tagihan")
    @Expose
    private String totalTagihan;
    @SerializedName("total_denda")
    @Expose
    private String totalDenda;
    @SerializedName("total_pelunasan")
    @Expose
    private String totalPelunasan;
    public final static Creator<DaftarTransaksi> CREATOR = new Creator<DaftarTransaksi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DaftarTransaksi createFromParcel(android.os.Parcel in) {
            return new DaftarTransaksi(in);
        }

        public DaftarTransaksi[] newArray(int size) {
            return (new DaftarTransaksi[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4646355226876227468L;

    protected DaftarTransaksi(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.codePembayaran = ((String) in.readValue((String.class.getClassLoader())));
        this.totalTagihan = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDenda = ((String) in.readValue((String.class.getClassLoader())));
        this.totalPelunasan = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DaftarTransaksi() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodePembayaran() {
        return codePembayaran;
    }

    public void setCodePembayaran(String codePembayaran) {
        this.codePembayaran = codePembayaran;
    }

    public String getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(String totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    public String getTotalDenda() {
        return totalDenda;
    }

    public void setTotalDenda(String totalDenda) {
        this.totalDenda = totalDenda;
    }

    public String getTotalPelunasan() {
        return totalPelunasan;
    }

    public void setTotalPelunasan(String totalPelunasan) {
        this.totalPelunasan = totalPelunasan;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(codePembayaran);
        dest.writeValue(totalTagihan);
        dest.writeValue(totalDenda);
        dest.writeValue(totalPelunasan);
    }

    public int describeContents() {
        return  0;
    }

}
