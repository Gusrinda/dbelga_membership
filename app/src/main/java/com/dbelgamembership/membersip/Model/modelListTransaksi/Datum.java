
package com.dbelgamembership.membersip.Model.modelListTransaksi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id_customer")
    @Expose
    private int idCustomer;
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
    @SerializedName("status_pengiriman")
    @Expose
    private String statusPengiriman;
    @SerializedName("catatan_pengiriman")
    @Expose
    private String catatanPengiriman;
    @SerializedName("nomor_customer")
    @Expose
    private String nomorCustomer;
    @SerializedName("ongkos_kirim")
    @Expose
    private int ongkosKirim;
    @SerializedName("tanggal_kirim")
    @Expose
    private String tanggalKirim;
    @SerializedName("flagKirim")
    @Expose
    private boolean flagKirim;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = new ArrayList<Detail>();
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(android.os.Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6842844691805139037L;

    protected Datum(android.os.Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.idCustomer = ((int) in.readValue((int.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.grandtotal = ((int) in.readValue((int.class.getClassLoader())));
        this.createuser = ((int) in.readValue((int.class.getClassLoader())));
        this.identitasCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.keteranganKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.catatanPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.nomorCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.ongkosKirim = ((int) in.readValue((int.class.getClassLoader())));
        this.tanggalKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.flagKirim = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.detail, (com.dbelgamembership.membersip.Model.modelListTransaksi.Detail.class.getClassLoader()));
    }

    public Datum() {
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

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Datum withIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
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

    public String getStatusPengiriman() {
        return statusPengiriman;
    }

    public void setStatusPengiriman(String statusPengiriman) {
        this.statusPengiriman = statusPengiriman;
    }

    public Datum withStatusPengiriman(String statusPengiriman) {
        this.statusPengiriman = statusPengiriman;
        return this;
    }

    public String getCatatanPengiriman() {
        return catatanPengiriman;
    }

    public void setCatatanPengiriman(String catatanPengiriman) {
        this.catatanPengiriman = catatanPengiriman;
    }

    public Datum withCatatanPengiriman(String catatanPengiriman) {
        this.catatanPengiriman = catatanPengiriman;
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

    public boolean isFlagKirim() {
        return flagKirim;
    }

    public void setFlagKirim(boolean flagKirim) {
        this.flagKirim = flagKirim;
    }

    public Datum withFlagKirim(boolean flagKirim) {
        this.flagKirim = flagKirim;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Datum withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(idCustomer);
        dest.writeValue(customer);
        dest.writeValue(grandtotal);
        dest.writeValue(createuser);
        dest.writeValue(identitasCustomer);
        dest.writeValue(alamatCustomer);
        dest.writeValue(alamatPengiriman);
        dest.writeValue(keteranganKirim);
        dest.writeValue(statusPengiriman);
        dest.writeValue(catatanPengiriman);
        dest.writeValue(nomorCustomer);
        dest.writeValue(ongkosKirim);
        dest.writeValue(tanggalKirim);
        dest.writeValue(flagKirim);
        dest.writeValue(date);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeList(detail);
    }

    public int describeContents() {
        return  0;
    }

}
