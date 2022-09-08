
package com.dbelgamembership.membersip.Model.ModelVoucherCustomer;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DaftarVoucher implements Serializable, Parcelable
{

    @SerializedName("id_voucher")
    @Expose
    private Integer idVoucher;
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
    private double nominal;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    @SerializedName("flag_pakai")
    @Expose
    private Boolean flagPakai;
    @SerializedName("minimal_belanja")
    @Expose
    private String minimalBelanja;
    @SerializedName("unik_code")
    @Expose
    private String unikCode;
    @SerializedName("gudang")
    @Expose
    private List<Integer> gudang = null;
    public final static Creator<DaftarVoucher> CREATOR = new Creator<DaftarVoucher>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DaftarVoucher createFromParcel(android.os.Parcel in) {
            return new DaftarVoucher(in);
        }

        public DaftarVoucher[] newArray(int size) {
            return (new DaftarVoucher[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8667837875771167650L;

    protected DaftarVoucher(android.os.Parcel in) {
        this.idVoucher = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.tipe = ((String) in.readValue((String.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
        this.nominal = ((double) in.readValue((Integer.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
        this.flagPakai = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.minimalBelanja = ((String) in.readValue((String.class.getClassLoader())));
        this.unikCode = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.gudang, (java.lang.Integer.class.getClassLoader()));
    }

    public DaftarVoucher() {
    }

    public Integer getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Integer idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getFlagPakai() {
        return flagPakai;
    }

    public void setFlagPakai(Boolean flagPakai) {
        this.flagPakai = flagPakai;
    }

    public String getMinimalBelanja() {
        return minimalBelanja;
    }

    public void setMinimalBelanja(String minimalBelanja) {
        this.minimalBelanja = minimalBelanja;
    }

    public String getUnikCode() {
        return unikCode;
    }

    public void setUnikCode(String unikCode) {
        this.unikCode = unikCode;
    }

    public List<Integer> getGudang() {
        return gudang;
    }

    public void setGudang(List<Integer> gudang) {
        this.gudang = gudang;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(idVoucher);
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(tipe);
        dest.writeValue(deskripsi);
        dest.writeValue(nominal);
        dest.writeValue(expiredDate);
        dest.writeValue(flagPakai);
        dest.writeValue(minimalBelanja);
        dest.writeValue(unikCode);
        dest.writeList(gudang);
    }

    public int describeContents() {
        return  0;
    }

}
