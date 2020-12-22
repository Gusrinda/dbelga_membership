
package com.dbelgamembership.membersip.Model.modelListTransaksi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Datum implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("grandtotal")
    @Expose
    private int grandtotal;
    @SerializedName("createuser")
    @Expose
    private int createuser;
    @SerializedName("identitas_customer")
    @Expose
    private String identitasCustomer;
    @SerializedName("alamat_customer")
    @Expose
    private String alamatCustomer;
    @SerializedName("alamat_pengiriman")
    @Expose
    private String alamatPengiriman;
    @SerializedName("keterangan_kirim")
    @Expose
    private String keteranganKirim;
    @SerializedName("nomor_customer")
    @Expose
    private String nomorCustomer;
    @SerializedName("ongkos_kirim")
    @Expose
    private int ongkosKirim;
    @SerializedName("tanggal_kirim")
    @Expose
    private String tanggalKirim;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = new ArrayList<Detail>();
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1158327693636236449L;

    protected Datum(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.grandtotal = ((int) in.readValue((int.class.getClassLoader())));
        this.createuser = ((int) in.readValue((int.class.getClassLoader())));
        this.identitasCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.keteranganKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.nomorCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.ongkosKirim = ((int) in.readValue((int.class.getClassLoader())));
        this.tanggalKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.detail, (com.dbelgamembership.membersip.Model.modelListTransaksi.Detail.class.getClassLoader()));
    }

    public Datum() {
    }


    public Datum(int id, String code, String customer, int grandtotal, int createuser, String identitasCustomer, String alamatCustomer, String alamatPengiriman, String nomorCustomer, int ongkosKirim, String tanggalKirim, String date, String status, String createdAt, List<Detail> detail) {
        super();
        this.id = id;
        this.code = code;
        this.customer = customer;
        this.grandtotal = grandtotal;
        this.createuser = createuser;
        this.identitasCustomer = identitasCustomer;
        this.alamatCustomer = alamatCustomer;
        this.alamatPengiriman = alamatPengiriman;
        this.keteranganKirim = keteranganKirim;
        this.nomorCustomer = nomorCustomer;
        this.ongkosKirim = ongkosKirim;
        this.tanggalKirim = tanggalKirim;
        this.date = date;
        this.status = status;
        this.createdAt = createdAt;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Datum withId(int id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Datum withCode(String code) {
        this.code = code;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Datum withCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public int getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(int grandtotal) {
        this.grandtotal = grandtotal;
    }

    public Datum withGrandtotal(int grandtotal) {
        this.grandtotal = grandtotal;
        return this;
    }

    public int getCreateuser() {
        return createuser;
    }

    public void setCreateuser(int createuser) {
        this.createuser = createuser;
    }

    public Datum withCreateuser(int createuser) {
        this.createuser = createuser;
        return this;
    }

    public String getIdentitasCustomer() {
        return identitasCustomer;
    }

    public void setIdentitasCustomer(String identitasCustomer) {
        this.identitasCustomer = identitasCustomer;
    }

    public Datum withIdentitasCustomer(String identitasCustomer) {
        this.identitasCustomer = identitasCustomer;
        return this;
    }

    public String getAlamatCustomer() {
        return alamatCustomer;
    }

    public void setAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
    }

    public Datum withAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
        return this;
    }

    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
    }

    public Datum withAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
        return this;
    }

    public String getKeteranganKirim() {
        return keteranganKirim;
    }

    public void setKeteranganKirim(String keteranganKirim) {
        this.keteranganKirim = keteranganKirim;
    }

    public Datum withKeteranganKirim(String keteranganKirim) {
        this.keteranganKirim = keteranganKirim;
        return this;
    }

    public String getNomorCustomer() {
        return nomorCustomer;
    }

    public void setNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
    }

    public Datum withNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
        return this;
    }

    public int getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(int ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
    }

    public Datum withOngkosKirim(int ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
        return this;
    }

    public String getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public Datum withTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Datum withDate(String date) {
        this.date = date;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Datum withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Datum withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public Datum withDetail(List<Detail> detail) {
        this.detail = detail;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(customer);
        dest.writeValue(grandtotal);
        dest.writeValue(createuser);
        dest.writeValue(identitasCustomer);
        dest.writeValue(alamatCustomer);
        dest.writeValue(alamatPengiriman);
        dest.writeValue(keteranganKirim);
        dest.writeValue(nomorCustomer);
        dest.writeValue(ongkosKirim);
        dest.writeValue(tanggalKirim);
        dest.writeValue(date);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeList(detail);
    }

    public int describeContents() {
        return  0;
    }

}
