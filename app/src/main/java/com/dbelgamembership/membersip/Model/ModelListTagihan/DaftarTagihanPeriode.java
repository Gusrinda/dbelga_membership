
package com.dbelgamembership.membersip.Model.ModelListTagihan;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DaftarTagihanPeriode implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
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
    @SerializedName("total_bersih")
    @Expose
    private String totalBersih;
    @SerializedName("card_holder")
    @Expose
    private String cardHolder;
    @SerializedName("customer")
    @Expose
    private Integer customer;
    @SerializedName("flag_lunas")
    @Expose
    private String flagLunas;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;

    @SerializedName("status_payment")
    @Expose
    private String statusPayment;

    public final static Creator<DaftarTagihanPeriode> CREATOR = new Creator<DaftarTagihanPeriode>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DaftarTagihanPeriode createFromParcel(android.os.Parcel in) {
            return new DaftarTagihanPeriode(in);
        }

        public DaftarTagihanPeriode[] newArray(int size) {
            return (new DaftarTagihanPeriode[size]);
        }

    }
    ;
    private final static long serialVersionUID = 7812047347976696929L;

    protected DaftarTagihanPeriode(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.optionBank = ((String) in.readValue((String.class.getClassLoader())));
        this.optionsAccount = ((String) in.readValue((String.class.getClassLoader())));
        this.charge = ((String) in.readValue((String.class.getClassLoader())));
        this.chargeAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalBersih = ((String) in.readValue((String.class.getClassLoader())));
        this.cardHolder = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.flagLunas = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPayment = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DaftarTagihanPeriode() {
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

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
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

    public String getTotalBersih() {
        return totalBersih;
    }

    public void setTotalBersih(String totalBersih) {
        this.totalBersih = totalBersih;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public String getFlagLunas() {
        return flagLunas;
    }

    public void setFlagLunas(String flagLunas) {
        this.flagLunas = flagLunas;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(pembayaranCode);
        dest.writeValue(optionBank);
        dest.writeValue(optionsAccount);
        dest.writeValue(charge);
        dest.writeValue(chargeAmount);
        dest.writeValue(total);
        dest.writeValue(totalBersih);
        dest.writeValue(cardHolder);
        dest.writeValue(customer);
        dest.writeValue(flagLunas);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(paymentType);
        dest.writeValue(statusPayment);
    }

    public int describeContents() {
        return  0;
    }

}
