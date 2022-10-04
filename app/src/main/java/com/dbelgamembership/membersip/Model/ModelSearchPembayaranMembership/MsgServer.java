
package com.dbelgamembership.membersip.Model.ModelSearchPembayaranMembership;

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
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("kode_pembayaran")
    @Expose
    private String kodePembayaran;
    @SerializedName("id_customer")
    @Expose
    private Integer idCustomer;
    @SerializedName("tipe_payment")
    @Expose
    private String tipePayment;
    @SerializedName("bank_payment")
    @Expose
    private String bankPayment;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("detail_payment_bni")
    @Expose
    private DetailPaymentBni detailPaymentBni;
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
    private final static long serialVersionUID = -1570637035178736668L;

    protected MsgServer(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.kodePembayaran = ((String) in.readValue((String.class.getClassLoader())));
        this.idCustomer = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.tipePayment = ((String) in.readValue((String.class.getClassLoader())));
        this.bankPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.detailPaymentBni = ((DetailPaymentBni) in.readValue((DetailPaymentBni.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getKodePembayaran() {
        return kodePembayaran;
    }

    public void setKodePembayaran(String kodePembayaran) {
        this.kodePembayaran = kodePembayaran;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public DetailPaymentBni getDetailPaymentBni() {
        return detailPaymentBni;
    }

    public void setDetailPaymentBni(DetailPaymentBni detailPaymentBni) {
        this.detailPaymentBni = detailPaymentBni;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(kodePembayaran);
        dest.writeValue(idCustomer);
        dest.writeValue(tipePayment);
        dest.writeValue(bankPayment);
        dest.writeValue(amount);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(detailPaymentBni);
    }

    public int describeContents() {
        return  0;
    }

}
