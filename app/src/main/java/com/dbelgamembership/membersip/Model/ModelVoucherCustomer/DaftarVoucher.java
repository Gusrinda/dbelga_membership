
package com.dbelgamembership.membersip.Model.ModelVoucherCustomer;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DaftarVoucher implements Serializable, Parcelable
{

    @SerializedName("id_voucher")
    @Expose
    private int idVoucher;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("tipe")
    @Expose
    private String tipe;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("nominal")
    @Expose
    private int nominal;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    public final static Parcelable.Creator<DaftarVoucher> CREATOR = new Creator<DaftarVoucher>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DaftarVoucher createFromParcel(Parcel in) {
            return new DaftarVoucher(in);
        }

        public DaftarVoucher[] newArray(int size) {
            return (new DaftarVoucher[size]);
        }

    }
    ;
    private final static long serialVersionUID = 930347763499258752L;

    protected DaftarVoucher(Parcel in) {
        this.idVoucher = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.tipe = ((String) in.readValue((String.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
        this.nominal = ((int) in.readValue((int.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DaftarVoucher() {
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public DaftarVoucher withIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DaftarVoucher withName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DaftarVoucher withCode(String code) {
        this.code = code;
        return this;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public DaftarVoucher withTipe(String tipe) {
        this.tipe = tipe;
        return this;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public DaftarVoucher withDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public DaftarVoucher withNominal(int nominal) {
        this.nominal = nominal;
        return this;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public DaftarVoucher withExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idVoucher);
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(tipe);
        dest.writeValue(deskripsi);
        dest.writeValue(nominal);
        dest.writeValue(expiredDate);
    }

    public int describeContents() {
        return  0;
    }

}
