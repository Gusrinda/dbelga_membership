
package com.dbelgamembership.membersip.Model.Api_Banks.BriDetailPayment;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data implements Serializable, Parcelable
{

    @SerializedName("institutionCode")
    @Expose
    private String institutionCode;
    @SerializedName("BrivaNo")
    @Expose
    private String brivaNo;
    @SerializedName("CustCode")
    @Expose
    private String custCode;
    @SerializedName("Nama")
    @Expose
    private String nama;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Keterangan")
    @Expose
    private String keterangan;
    @SerializedName("statusBayar")
    @Expose
    private String statusBayar;
    @SerializedName("expiredDate")
    @Expose
    private String expiredDate;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3915379320892103459L;

    protected Data(android.os.Parcel in) {
        this.institutionCode = ((String) in.readValue((String.class.getClassLoader())));
        this.brivaNo = ((String) in.readValue((String.class.getClassLoader())));
        this.custCode = ((String) in.readValue((String.class.getClassLoader())));
        this.nama = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.keterangan = ((String) in.readValue((String.class.getClassLoader())));
        this.statusBayar = ((String) in.readValue((String.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
        this.lastUpdate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getBrivaNo() {
        return brivaNo;
    }

    public void setBrivaNo(String brivaNo) {
        this.brivaNo = brivaNo;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatusBayar() {
        return statusBayar;
    }

    public void setStatusBayar(String statusBayar) {
        this.statusBayar = statusBayar;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(institutionCode);
        dest.writeValue(brivaNo);
        dest.writeValue(custCode);
        dest.writeValue(nama);
        dest.writeValue(amount);
        dest.writeValue(keterangan);
        dest.writeValue(statusBayar);
        dest.writeValue(expiredDate);
        dest.writeValue(lastUpdate);
    }

    public int describeContents() {
        return  0;
    }

}
