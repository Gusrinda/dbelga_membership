
package com.dbelgamembership.membersip.Model.ModelListTagihan;

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

    @SerializedName("tagihan")
    @Expose
    private String tagihan;
    @SerializedName("tagihan_denda")
    @Expose
    private double tagihanDenda;
    @SerializedName("tagihan_total")
    @Expose
    private double tagihanTotal;
    @SerializedName("limit_penggunaan")
    @Expose
    private double limitPenggunaan;
    @SerializedName("limit_sisa")
    @Expose
    private double limitSisa;
    @SerializedName("daftar_tagihan_periode")
    @Expose
    private List<DaftarTagihanPeriode> daftarTagihanPeriode = null;
    @SerializedName("daftar_tagihan_denda")
    @Expose
    private List<DaftarTagihanDenda> daftarTagihanDenda = null;
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
    private final static long serialVersionUID = -6330520528726507105L;

    protected MsgServer(android.os.Parcel in) {
        this.tagihan = ((String) in.readValue((String.class.getClassLoader())));
        this.tagihanDenda = ((double) in.readValue((double.class.getClassLoader())));
        this.tagihanTotal = ((double) in.readValue((double.class.getClassLoader())));
        this.limitPenggunaan = ((double) in.readValue((double.class.getClassLoader())));
        this.limitSisa = ((double) in.readValue((double.class.getClassLoader())));
        in.readList(this.daftarTagihanPeriode, (com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanPeriode.class.getClassLoader()));
        in.readList(this.daftarTagihanDenda, (com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanDenda.class.getClassLoader()));
    }

    public MsgServer() {
    }

    public String getTagihan() {
        return tagihan;
    }

    public void setTagihan(String tagihan) {
        this.tagihan = tagihan;
    }

    public double getTagihanDenda() {
        return tagihanDenda;
    }

    public void setTagihanDenda(double tagihanDenda) {
        this.tagihanDenda = tagihanDenda;
    }

    public double getTagihanTotal() {
        return tagihanTotal;
    }

    public void setTagihanTotal(double tagihanTotal) {
        this.tagihanTotal = tagihanTotal;
    }

    public double getLimitPenggunaan() {
        return limitPenggunaan;
    }

    public void setLimitPenggunaan(double limitPenggunaan) {
        this.limitPenggunaan = limitPenggunaan;
    }

    public double getLimitSisa() {
        return limitSisa;
    }

    public void setLimitSisa(double limitSisa) {
        this.limitSisa = limitSisa;
    }

    public List<DaftarTagihanPeriode> getDaftarTagihanPeriode() {
        return daftarTagihanPeriode;
    }

    public void setDaftarTagihanPeriode(List<DaftarTagihanPeriode> daftarTagihanPeriode) {
        this.daftarTagihanPeriode = daftarTagihanPeriode;
    }

    public List<DaftarTagihanDenda> getDaftarTagihanDenda() {
        return daftarTagihanDenda;
    }

    public void setDaftarTagihanDenda(List<DaftarTagihanDenda> daftarTagihanDenda) {
        this.daftarTagihanDenda = daftarTagihanDenda;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(tagihan);
        dest.writeValue(tagihanDenda);
        dest.writeValue(tagihanTotal);
        dest.writeValue(limitPenggunaan);
        dest.writeValue(limitSisa);
        dest.writeList(daftarTagihanPeriode);
        dest.writeList(daftarTagihanDenda);
    }

    public int describeContents() {
        return  0;
    }

}
