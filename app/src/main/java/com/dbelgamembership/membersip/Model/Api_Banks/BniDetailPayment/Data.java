
package com.dbelgamembership.membersip.Model.Api_Banks.BniDetailPayment;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data implements Serializable, Parcelable
{

    @SerializedName("trx_id")
    @Expose
    private String trxId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("virtual_account")
    @Expose
    private String virtualAccount;
    @SerializedName("trx_amount")
    @Expose
    private String trxAmount;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("datetime_created")
    @Expose
    private String datetimeCreated;
    @SerializedName("datetime_expired")
    @Expose
    private String datetimeExpired;
    @SerializedName("datetime_payment")
    @Expose
    private String datetimePayment;
    @SerializedName("datetime_last_updated")
    @Expose
    private String datetimeLastUpdated;
    @SerializedName("payment_ntb")
    @Expose
    private String paymentNtb;
    @SerializedName("payment_amount")
    @Expose
    private String paymentAmount;
    @SerializedName("va_status")
    @Expose
    private String vaStatus;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("billing_type")
    @Expose
    private String billingType;
    @SerializedName("datetime_created_iso8601")
    @Expose
    private String datetimeCreatedIso8601;
    @SerializedName("datetime_expired_iso8601")
    @Expose
    private String datetimeExpiredIso8601;
    @SerializedName("datetime_payment_iso8601")
    @Expose
    private String datetimePaymentIso8601;
    @SerializedName("datetime_last_updated_iso8601")
    @Expose
    private String datetimeLastUpdatedIso8601;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3001198393772150824L;

    protected Data(android.os.Parcel in) {
        this.trxId = ((String) in.readValue((String.class.getClassLoader())));
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
        this.virtualAccount = ((String) in.readValue((String.class.getClassLoader())));
        this.trxAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.customerEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.customerPhone = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimeCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimeExpired = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimePayment = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimeLastUpdated = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentNtb = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.vaStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.billingType = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimeCreatedIso8601 = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimeExpiredIso8601 = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimePaymentIso8601 = ((String) in.readValue((String.class.getClassLoader())));
        this.datetimeLastUpdatedIso8601 = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(String virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    public String getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(String trxAmount) {
        this.trxAmount = trxAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDatetimeCreated() {
        return datetimeCreated;
    }

    public void setDatetimeCreated(String datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }

    public String getDatetimeExpired() {
        return datetimeExpired;
    }

    public void setDatetimeExpired(String datetimeExpired) {
        this.datetimeExpired = datetimeExpired;
    }

    public String getDatetimePayment() {
        return datetimePayment;
    }

    public void setDatetimePayment(String datetimePayment) {
        this.datetimePayment = datetimePayment;
    }

    public String getDatetimeLastUpdated() {
        return datetimeLastUpdated;
    }

    public void setDatetimeLastUpdated(String datetimeLastUpdated) {
        this.datetimeLastUpdated = datetimeLastUpdated;
    }

    public String getPaymentNtb() {
        return paymentNtb;
    }

    public void setPaymentNtb(String paymentNtb) {
        this.paymentNtb = paymentNtb;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getVaStatus() {
        return vaStatus;
    }

    public void setVaStatus(String vaStatus) {
        this.vaStatus = vaStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getDatetimeCreatedIso8601() {
        return datetimeCreatedIso8601;
    }

    public void setDatetimeCreatedIso8601(String datetimeCreatedIso8601) {
        this.datetimeCreatedIso8601 = datetimeCreatedIso8601;
    }

    public String getDatetimeExpiredIso8601() {
        return datetimeExpiredIso8601;
    }

    public void setDatetimeExpiredIso8601(String datetimeExpiredIso8601) {
        this.datetimeExpiredIso8601 = datetimeExpiredIso8601;
    }

    public String getDatetimePaymentIso8601() {
        return datetimePaymentIso8601;
    }

    public void setDatetimePaymentIso8601(String datetimePaymentIso8601) {
        this.datetimePaymentIso8601 = datetimePaymentIso8601;
    }

    public String getDatetimeLastUpdatedIso8601() {
        return datetimeLastUpdatedIso8601;
    }

    public void setDatetimeLastUpdatedIso8601(String datetimeLastUpdatedIso8601) {
        this.datetimeLastUpdatedIso8601 = datetimeLastUpdatedIso8601;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(trxId);
        dest.writeValue(clientId);
        dest.writeValue(virtualAccount);
        dest.writeValue(trxAmount);
        dest.writeValue(customerName);
        dest.writeValue(customerEmail);
        dest.writeValue(customerPhone);
        dest.writeValue(datetimeCreated);
        dest.writeValue(datetimeExpired);
        dest.writeValue(datetimePayment);
        dest.writeValue(datetimeLastUpdated);
        dest.writeValue(paymentNtb);
        dest.writeValue(paymentAmount);
        dest.writeValue(vaStatus);
        dest.writeValue(description);
        dest.writeValue(billingType);
        dest.writeValue(datetimeCreatedIso8601);
        dest.writeValue(datetimeExpiredIso8601);
        dest.writeValue(datetimePaymentIso8601);
        dest.writeValue(datetimeLastUpdatedIso8601);
    }

    public int describeContents() {
        return  0;
    }

}
