
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PaymentDetail implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("card_holder")
    @Expose
    private String cardHolder;
    public final static Creator<PaymentDetail> CREATOR = new Creator<PaymentDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PaymentDetail createFromParcel(android.os.Parcel in) {
            return new PaymentDetail(in);
        }

        public PaymentDetail[] newArray(int size) {
            return (new PaymentDetail[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4224077284257403506L;

    protected PaymentDetail(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.optionBank = ((String) in.readValue((String.class.getClassLoader())));
        this.optionsAccount = ((String) in.readValue((String.class.getClassLoader())));
        this.charge = ((String) in.readValue((String.class.getClassLoader())));
        this.chargeAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.cardHolder = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PaymentDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOptionBank() {
        return optionBank;
    }

    public void setOptionBank(String optionBank) {
        this.optionBank = optionBank;
    }

    public String getOptionsAccount() {
        return optionsAccount;
    }

    public void setOptionsAccount(String optionsAccount) {
        this.optionsAccount = optionsAccount;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
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
        dest.writeValue(cardHolder);
    }

    public int describeContents() {
        return  0;
    }

}
