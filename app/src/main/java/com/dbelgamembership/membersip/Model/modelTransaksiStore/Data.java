
package com.dbelgamembership.membersip.Model.modelTransaksiStore;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable, Parcelable
{

    @SerializedName("createuser")
    @Expose
    private String createuser;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("alamat_customer")
    @Expose
    private String alamatCustomer;
    @SerializedName("id_spv")
    @Expose
    private String idSpv;
    @SerializedName("alamat_pengiriman")
    @Expose
    private String alamatPengiriman;
    @SerializedName("identitas_customer")
    @Expose
    private String identitasCustomer;
    @SerializedName("nomor_customer")
    @Expose
    private String nomorCustomer;
    @SerializedName("ongkos_kirim")
    @Expose
    private String ongkosKirim;
    @SerializedName("tanggal_kirim")
    @Expose
    private String tanggalKirim;
    @SerializedName("grandtotal")
    @Expose
    private String grandtotal;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = new ArrayList<Detail>();
    @SerializedName("so_code")
    @Expose
    private String soCode;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2308978745009990976L;

    protected Data(Parcel in) {
        this.createuser = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.idSpv = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.identitasCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.nomorCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.ongkosKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggalKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.grandtotal = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.detail, (com.dbelgamembership.membersip.Model.modelTransaksiStore.Detail.class.getClassLoader()));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Data withCreateuser(String createuser) {
        this.createuser = createuser;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Data withCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getAlamatCustomer() {
        return alamatCustomer;
    }

    public void setAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
    }

    public Data withAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
        return this;
    }

    public String getIdSpv() {
        return idSpv;
    }

    public void setIdSpv(String idSpv) {
        this.idSpv = idSpv;
    }

    public Data withIdSpv(String idSpv) {
        this.idSpv = idSpv;
        return this;
    }

    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
    }

    public Data withAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
        return this;
    }

    public String getIdentitasCustomer() {
        return identitasCustomer;
    }

    public void setIdentitasCustomer(String identitasCustomer) {
        this.identitasCustomer = identitasCustomer;
    }

    public Data withIdentitasCustomer(String identitasCustomer) {
        this.identitasCustomer = identitasCustomer;
        return this;
    }

    public String getNomorCustomer() {
        return nomorCustomer;
    }

    public void setNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
    }

    public Data withNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
        return this;
    }

    public String getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(String ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
    }

    public Data withOngkosKirim(String ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
        return this;
    }

    public String getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public Data withTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
        return this;
    }

    public String getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(String grandtotal) {
        this.grandtotal = grandtotal;
    }

    public Data withGrandtotal(String grandtotal) {
        this.grandtotal = grandtotal;
        return this;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public Data withDetail(List<Detail> detail) {
        this.detail = detail;
        return this;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public Data withSoCode(String soCode) {
        this.soCode = soCode;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(createuser);
        dest.writeValue(customer);
        dest.writeValue(alamatCustomer);
        dest.writeValue(idSpv);
        dest.writeValue(alamatPengiriman);
        dest.writeValue(identitasCustomer);
        dest.writeValue(nomorCustomer);
        dest.writeValue(ongkosKirim);
        dest.writeValue(tanggalKirim);
        dest.writeValue(grandtotal);
        dest.writeList(detail);
        dest.writeValue(soCode);
    }

    public int describeContents() {
        return  0;
    }

}
