
package com.dbelgamembership.membersip.Model.ResponseBayarTagihan;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pd_code")
    @Expose
    private String pdCode;
    @SerializedName("customer")
    @Expose
    private Integer customer;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("user_input")
    @Expose
    private String userInput;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("akun_tujuan")
    @Expose
    private String akunTujuan;
    @SerializedName("tipe_payment")
    @Expose
    private String tipePayment;
    @SerializedName("foto_bukti")
    @Expose
    private String fotoBukti;
    @SerializedName("total_tagihan")
    @Expose
    private String totalTagihan;
    @SerializedName("total_denda")
    @Expose
    private String totalDenda;
    @SerializedName("tipe_tagihan")
    @Expose
    private String tipeTagihan;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("flag_lunas")
    @Expose
    private String flagLunas;
    @SerializedName("total_transaksi")
    @Expose
    private String totalTransaksi;
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
    private final static long serialVersionUID = -2282782792033016617L;

    protected MsgServer(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pdCode = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.paymentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.userInput = ((String) in.readValue((String.class.getClassLoader())));
        this.keterangan = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.grandTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.akunTujuan = ((String) in.readValue((String.class.getClassLoader())));
        this.tipePayment = ((String) in.readValue((String.class.getClassLoader())));
        this.fotoBukti = ((String) in.readValue((String.class.getClassLoader())));
        this.totalTagihan = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDenda = ((String) in.readValue((String.class.getClassLoader())));
        this.tipeTagihan = ((String) in.readValue((String.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.flagLunas = ((String) in.readValue((String.class.getClassLoader())));
        this.totalTransaksi = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAkunTujuan() {
        return akunTujuan;
    }

    public void setAkunTujuan(String akunTujuan) {
        this.akunTujuan = akunTujuan;
    }

    public String getTipePayment() {
        return tipePayment;
    }

    public void setTipePayment(String tipePayment) {
        this.tipePayment = tipePayment;
    }

    public String getFotoBukti() {
        return fotoBukti;
    }

    public void setFotoBukti(String fotoBukti) {
        this.fotoBukti = fotoBukti;
    }

    public String getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(String totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    public String getTotalDenda() {
        return totalDenda;
    }

    public void setTotalDenda(String totalDenda) {
        this.totalDenda = totalDenda;
    }

    public String getTipeTagihan() {
        return tipeTagihan;
    }

    public void setTipeTagihan(String tipeTagihan) {
        this.tipeTagihan = tipeTagihan;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public String getFlagLunas() {
        return flagLunas;
    }

    public void setFlagLunas(String flagLunas) {
        this.flagLunas = flagLunas;
    }

    public String getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(String totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(pdCode);
        dest.writeValue(customer);
        dest.writeValue(paymentDate);
        dest.writeValue(userInput);
        dest.writeValue(keterangan);
        dest.writeValue(status);
        dest.writeValue(grandTotal);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(akunTujuan);
        dest.writeValue(tipePayment);
        dest.writeValue(fotoBukti);
        dest.writeValue(totalTagihan);
        dest.writeValue(totalDenda);
        dest.writeValue(tipeTagihan);
        dest.writeValue(pembayaranCode);
        dest.writeValue(flagLunas);
        dest.writeValue(totalTransaksi);
    }

    public int describeContents() {
        return  0;
    }

}
