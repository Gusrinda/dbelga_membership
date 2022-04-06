
package com.dbelgamembership.membersip.Model.ModelRedeemVoucher;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
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
    private Integer nominal;
    @SerializedName("klaim")
    @Expose
    private Integer klaim;
    @SerializedName("expired")
    @Expose
    private Integer expired;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("stok")
    @Expose
    private Integer stok;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("tipe_member")
    @Expose
    private String tipeMember;
    @SerializedName("minimal_belanja")
    @Expose
    private String minimalBelanja;
    @SerializedName("unik_code")
    @Expose
    private String unikCode;
    @SerializedName("flag_pakai")
    @Expose
    private Boolean flagPakai;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("gudang")
    @Expose
    private List<Integer> gudang = null;
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
    private final static long serialVersionUID = -2060443486928401095L;

    protected MsgServer(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.tipe = ((String) in.readValue((String.class.getClassLoader())));
        this.nominal = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.klaim = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.expired = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
        this.stok = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.tipeMember = ((String) in.readValue((String.class.getClassLoader())));
        this.minimalBelanja = ((String) in.readValue((String.class.getClassLoader())));
        this.unikCode = ((String) in.readValue((String.class.getClassLoader())));
        this.flagPakai = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.gudang, (java.lang.Integer.class.getClassLoader()));
    }

    public MsgServer() {
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

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public Integer getKlaim() {
        return klaim;
    }

    public void setKlaim(Integer klaim) {
        this.klaim = klaim;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getTipeMember() {
        return tipeMember;
    }

    public void setTipeMember(String tipeMember) {
        this.tipeMember = tipeMember;
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

    public Boolean getFlagPakai() {
        return flagPakai;
    }

    public void setFlagPakai(Boolean flagPakai) {
        this.flagPakai = flagPakai;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getGudang() {
        return gudang;
    }

    public void setGudang(List<Integer> gudang) {
        this.gudang = gudang;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(tipe);
        dest.writeValue(nominal);
        dest.writeValue(klaim);
        dest.writeValue(expired);
        dest.writeValue(deskripsi);
        dest.writeValue(stok);
        dest.writeValue(updatedAt);
        dest.writeValue(createdAt);
        dest.writeValue(companyCode);
        dest.writeValue(tipeMember);
        dest.writeValue(minimalBelanja);
        dest.writeValue(unikCode);
        dest.writeValue(flagPakai);
        dest.writeValue(customer);
        dest.writeValue(id);
        dest.writeList(gudang);
    }

    public int describeContents() {
        return  0;
    }

}
