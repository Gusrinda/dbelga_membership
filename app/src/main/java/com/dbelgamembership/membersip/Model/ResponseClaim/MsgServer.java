
package com.dbelgamembership.membersip.Model.ResponseClaim;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsgServer implements Serializable, Parcelable
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
    public final static Parcelable.Creator<MsgServer> CREATOR = new Creator<MsgServer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsgServer createFromParcel(Parcel in) {
            return new MsgServer(in);
        }

        public MsgServer[] newArray(int size) {
            return (new MsgServer[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6659547214351360175L;

    protected MsgServer(Parcel in) {
        this.idVoucher = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.tipe = ((String) in.readValue((String.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
        this.nominal = ((int) in.readValue((int.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public MsgServer withIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MsgServer withName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MsgServer withCode(String code) {
        this.code = code;
        return this;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public MsgServer withTipe(String tipe) {
        this.tipe = tipe;
        return this;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public MsgServer withDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public MsgServer withNominal(int nominal) {
        this.nominal = nominal;
        return this;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public MsgServer withExpiredDate(String expiredDate) {
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
