
package com.dbelgamembership.membersip.Model.ModelSearchPembayaranMembership;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DetailPaymentBni implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("kode_transaksi")
    @Expose
    private String kodeTransaksi;
    @SerializedName("billing_type")
    @Expose
    private String billingType;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("datetime_expired")
    @Expose
    private String datetimeExpired;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("trx_amount")
    @Expose
    private String trxAmount;
    @SerializedName("trx_id")
    @Expose
    private String trxId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("virtual_account")
    @Expose
    private String virtualAccount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("tipe_transaksi")
    @Expose
    private String tipeTransaksi;
    public final static Creator<DetailPaymentBni> CREATOR = new Creator<DetailPaymentBni>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailPaymentBni createFromParcel(android.os.Parcel in) {
            return new DetailPaymentBni(in);
        }

        public DetailPaymentBni[] newArray(int size) {
            return (new DetailPaymentBni[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8165954794438355466L;

    protected DetailPaymentBni(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.kodeTransaksi = ((String) in.readValue((String.class.getClassLoader())));
        this.billingType = ((String) in.readValue((String.class.getClassLoader())));
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
        this.customerEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.customerPhone = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimeExpired = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.trxAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.trxId = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.virtualAccount = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.tipeTransaksi = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DetailPaymentBni() {
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

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDatetimeExpired() {
        return datetimeExpired;
    }

    public void setDatetimeExpired(String datetimeExpired) {
        this.datetimeExpired = datetimeExpired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(String trxAmount) {
        this.trxAmount = trxAmount;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(String virtualAccount) {
        this.virtualAccount = virtualAccount;
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

    public String getTipeTransaksi() {
        return tipeTransaksi;
    }

    public void setTipeTransaksi(String tipeTransaksi) {
        this.tipeTransaksi = tipeTransaksi;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(kodeTransaksi);
        dest.writeValue(billingType);
        dest.writeValue(clientId);
        dest.writeValue(customerEmail);
        dest.writeValue(customerName);
        dest.writeValue(customerPhone);
        dest.writeValue(datetimeExpired);
        dest.writeValue(description);
        dest.writeValue(trxAmount);
        dest.writeValue(trxId);
        dest.writeValue(type);
        dest.writeValue(virtualAccount);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(tipeTransaksi);
    }

    public int describeContents() {
        return  0;
    }

}
