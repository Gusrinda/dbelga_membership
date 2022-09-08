
package com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;

import com.dbelgamembership.membersip.Model.modelListTransaksi.DetailPaymentBni;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DaftarPelunasan implements Serializable, Parcelable
{

    @SerializedName("id_pelunasan")
    @Expose
    private Integer idPelunasan;
    @SerializedName("code_pelunasan")
    @Expose
    private String codePelunasan;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("tipe_payment")
    @Expose
    private String tipePayment;
    @SerializedName("status_pelunasan")
    @Expose
    private String statusPelunasan;
    @SerializedName("total_pelunasan")
    @Expose
    private String totalPelunasan;
    @SerializedName("daftar_transaksi")
    @Expose
    private List<DaftarTransaksi> daftarTransaksi = null;

    @SerializedName("bank_payment")
    @Expose
    private String bankPayment;
    @SerializedName("detail_payment_bni")
    @Expose
    private DetailPaymentBni detailPaymentBni;



    public final static Creator<DaftarPelunasan> CREATOR = new Creator<DaftarPelunasan>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DaftarPelunasan createFromParcel(android.os.Parcel in) {
            return new DaftarPelunasan(in);
        }

        public DaftarPelunasan[] newArray(int size) {
            return (new DaftarPelunasan[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5219951821447564200L;

    protected DaftarPelunasan(android.os.Parcel in) {
        this.idPelunasan = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.codePelunasan = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.tipePayment = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPelunasan = ((String) in.readValue((String.class.getClassLoader())));
        this.totalPelunasan = ((String) in.readValue((String.class.getClassLoader())));
        this.bankPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.detailPaymentBni = ((DetailPaymentBni) in.readValue((String.class.getClassLoader())));
        in.readList(this.daftarTransaksi, (com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan.DaftarTransaksi.class.getClassLoader()));
    }

    public DaftarPelunasan() {
    }

    public Integer getIdPelunasan() {
        return idPelunasan;
    }

    public void setIdPelunasan(Integer idPelunasan) {
        this.idPelunasan = idPelunasan;
    }

    public String getCodePelunasan() {
        return codePelunasan;
    }

    public void setCodePelunasan(String codePelunasan) {
        this.codePelunasan = codePelunasan;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatusPelunasan() {
        return statusPelunasan;
    }

    public void setStatusPelunasan(String statusPelunasan) {
        this.statusPelunasan = statusPelunasan;
    }

    public String getTotalPelunasan() {
        return totalPelunasan;
    }

    public void setTotalPelunasan(String totalPelunasan) {
        this.totalPelunasan = totalPelunasan;
    }

    public List<DaftarTransaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }

    public void setDaftarTransaksi(List<DaftarTransaksi> daftarTransaksi) {
        this.daftarTransaksi = daftarTransaksi;
    }

    public String getTipePayment() {
        return tipePayment;
    }

    public void setTipePayment(String tipePayment) {
        this.tipePayment = tipePayment;
    }

    public String getBankPayment() {
        return bankPayment;
    }

    public void setBankPayment(String bankPayment) {
        this.bankPayment = bankPayment;
    }

    public DetailPaymentBni getDetailPaymentBni() {
        return detailPaymentBni;
    }

    public void setDetailPaymentBni(DetailPaymentBni detailPaymentBni) {
        this.detailPaymentBni = detailPaymentBni;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(idPelunasan);
        dest.writeValue(codePelunasan);
        dest.writeValue(paymentDate);
        dest.writeValue(tipePayment);
        dest.writeValue(statusPelunasan);
        dest.writeValue(totalPelunasan);
        dest.writeValue(bankPayment);
        dest.writeValue(detailPaymentBni);
        dest.writeList(daftarTransaksi);
    }

    public int describeContents() {
        return  0;
    }

}
