
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentDetail implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("option_bank")
    @Expose
    private String optionBank;
    @SerializedName("options_account")
    @Expose
    private String optionsAccount;
    @SerializedName("charge")
    @Expose
    private String charge;
    @SerializedName("charge_amount")
    @Expose
    private String chargeAmount;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    public final static Creator<PaymentDetail> CREATOR = new Creator<PaymentDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PaymentDetail createFromParcel(Parcel in) {
            return new PaymentDetail(in);
        }

        public PaymentDetail[] newArray(int size) {
            return (new PaymentDetail[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4320566780612919945L;

    protected PaymentDetail(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.optionBank = ((String) in.readValue((String.class.getClassLoader())));
        this.optionsAccount = ((String) in.readValue((String.class.getClassLoader())));
        this.charge = ((String) in.readValue((String.class.getClassLoader())));
        this.chargeAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((Object.class.getClassLoader())));
    }

    public PaymentDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentDetail withId(int id) {
        this.id = id;
        return this;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public PaymentDetail withPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentDetail withPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getOptionBank() {
        return optionBank;
    }

    public void setOptionBank(String optionBank) {
        this.optionBank = optionBank;
    }

    public PaymentDetail withOptionBank(String optionBank) {
        this.optionBank = optionBank;
        return this;
    }

    public String getOptionsAccount() {
        return optionsAccount;
    }

    public void setOptionsAccount(String optionsAccount) {
        this.optionsAccount = optionsAccount;
    }

    public PaymentDetail withOptionsAccount(String optionsAccount) {
        this.optionsAccount = optionsAccount;
        return this;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public PaymentDetail withCharge(String charge) {
        this.charge = charge;
        return this;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public PaymentDetail withChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public PaymentDetail withTotal(String total) {
        this.total = total;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PaymentDetail withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PaymentDetail withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(pembayaranCode);
        dest.writeValue(paymentType);
        dest.writeValue(optionBank);
        dest.writeValue(optionsAccount);
        dest.writeValue(charge);
        dest.writeValue(chargeAmount);
        dest.writeValue(total);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}
