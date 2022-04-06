
package com.dbelgamembership.membersip.Model.ModelListBank;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Datum implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("charge")
    @Expose
    private String charge;
    @SerializedName("charge_amount")
    @Expose
    private String chargeAmount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id_coa")
    @Expose
    private Integer idCoa;
    @SerializedName("desc")
    @Expose
    private String desc;
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
    private final static long serialVersionUID = -2062024294726266571L;

    protected Datum(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.deletedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.account = ((String) in.readValue((String.class.getClassLoader())));
        this.charge = ((String) in.readValue((String.class.getClassLoader())));
        this.chargeAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.idCoa = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.desc = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Datum() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdCoa() {
        return idCoa;
    }

    public void setIdCoa(Integer idCoa) {
        this.idCoa = idCoa;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(name);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(deletedAt);
        dest.writeValue(account);
        dest.writeValue(charge);
        dest.writeValue(chargeAmount);
        dest.writeValue(description);
        dest.writeValue(idCoa);
        dest.writeValue(desc);
    }

    public int describeContents() {
        return  0;
    }

}
