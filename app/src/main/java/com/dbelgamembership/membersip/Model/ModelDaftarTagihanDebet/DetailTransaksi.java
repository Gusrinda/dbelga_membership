
package com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DetailTransaksi implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_bersih")
    @Expose
    private String totalBersih;
    @SerializedName("flag_lunas")
    @Expose
    private String flagLunas;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status_payment")
    @Expose
    private String statusPayment;
    @SerializedName("batas_hari")
    @Expose
    private String batasHari;
    public final static Creator<DetailTransaksi> CREATOR = new Creator<DetailTransaksi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailTransaksi createFromParcel(android.os.Parcel in) {
            return new DetailTransaksi(in);
        }

        public DetailTransaksi[] newArray(int size) {
            return (new DetailTransaksi[size]);
        }

    }
    ;
    private final static long serialVersionUID = 253156363969363687L;

    protected DetailTransaksi(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalBersih = ((String) in.readValue((String.class.getClassLoader())));
        this.flagLunas = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.batasHari = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DetailTransaksi() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalBersih() {
        return totalBersih;
    }

    public void setTotalBersih(String totalBersih) {
        this.totalBersih = totalBersih;
    }

    public String getFlagLunas() {
        return flagLunas;
    }

    public void setFlagLunas(String flagLunas) {
        this.flagLunas = flagLunas;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getBatasHari() {
        return batasHari;
    }

    public void setBatasHari(String batasHari) {
        this.batasHari = batasHari;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(pembayaranCode);
        dest.writeValue(total);
        dest.writeValue(totalBersih);
        dest.writeValue(flagLunas);
        dest.writeValue(createdAt);
        dest.writeValue(statusPayment);
        dest.writeValue(batasHari);
    }

    public int describeContents() {
        return  0;
    }

}
