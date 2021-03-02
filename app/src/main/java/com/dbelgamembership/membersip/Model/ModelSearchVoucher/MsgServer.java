
package com.dbelgamembership.membersip.Model.ModelSearchVoucher;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("tipe")
    @Expose
    private String tipe;
    @SerializedName("nominal")
    @Expose
    private int nominal;
    @SerializedName("klaim")
    @Expose
    private int klaim;
    @SerializedName("expired")
    @Expose
    private int expired;
    @SerializedName("stok")
    @Expose
    private int stok;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("tipe_member")
    @Expose
    private String tipeMember;
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
    private final static long serialVersionUID = -8793229313621221993L;

    protected MsgServer(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.tipe = ((String) in.readValue((String.class.getClassLoader())));
        this.nominal = ((int) in.readValue((int.class.getClassLoader())));
        this.klaim = ((int) in.readValue((int.class.getClassLoader())));
        this.expired = ((int) in.readValue((int.class.getClassLoader())));
        this.stok = ((int) in.readValue((int.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
        this.tipeMember = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MsgServer() {
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

    public int getKlaim() {
        return klaim;
    }

    public void setKlaim(int klaim) {
        this.klaim = klaim;
    }

    public MsgServer withKlaim(int klaim) {
        this.klaim = klaim;
        return this;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public MsgServer withExpired(int expired) {
        this.expired = expired;
        return this;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public MsgServer withStok(int stok) {
        this.stok = stok;
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

    public String getTipeMember() {
        return tipeMember;
    }

    public void setTipeMember(String tipeMember) {
        this.tipeMember = tipeMember;
    }

    public MsgServer withTipeMember(String tipeMember) {
        this.tipeMember = tipeMember;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(tipe);
        dest.writeValue(nominal);
        dest.writeValue(klaim);
        dest.writeValue(expired);
        dest.writeValue(stok);
        dest.writeValue(deskripsi);
        dest.writeValue(tipeMember);
    }

    public int describeContents() {
        return  0;
    }

}
