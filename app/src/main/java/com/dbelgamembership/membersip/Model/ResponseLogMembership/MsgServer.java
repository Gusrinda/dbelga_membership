
package com.dbelgamembership.membersip.Model.ResponseLogMembership;

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
    @SerializedName("customer")
    @Expose
    private Integer customer;
    @SerializedName("status_member")
    @Expose
    private String statusMember;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    @SerializedName("pay_date")
    @Expose
    private String payDate;
    @SerializedName("nominal_plafon")
    @Expose
    private String nominalPlafon;
    @SerializedName("jatuh_tempo")
    @Expose
    private String jatuhTempo;
    @SerializedName("is_data_lama")
    @Expose
    private String isDataLama;
    @SerializedName("membership_lama")
    @Expose
    private String membershipLama;
    @SerializedName("jatuh_tempo_lama")
    @Expose
    private String jatuhTempoLama;
    @SerializedName("nominal_plafon_lama")
    @Expose
    private String nominalPlafonLama;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
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
    private final static long serialVersionUID = -1450696281297522074L;

    protected MsgServer(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customer = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.statusMember = ((String) in.readValue((String.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
        this.payDate = ((String) in.readValue((String.class.getClassLoader())));
        this.nominalPlafon = ((String) in.readValue((String.class.getClassLoader())));
        this.jatuhTempo = ((String) in.readValue((String.class.getClassLoader())));
        this.isDataLama = ((String) in.readValue((String.class.getClassLoader())));
        this.membershipLama = ((String) in.readValue((String.class.getClassLoader())));
        this.jatuhTempoLama = ((String) in.readValue((String.class.getClassLoader())));
        this.nominalPlafonLama = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public String getStatusMember() {
        return statusMember;
    }

    public void setStatusMember(String statusMember) {
        this.statusMember = statusMember;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getNominalPlafon() {
        return nominalPlafon;
    }

    public void setNominalPlafon(String nominalPlafon) {
        this.nominalPlafon = nominalPlafon;
    }

    public String getJatuhTempo() {
        return jatuhTempo;
    }

    public void setJatuhTempo(String jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public String getIsDataLama() {
        return isDataLama;
    }

    public void setIsDataLama(String isDataLama) {
        this.isDataLama = isDataLama;
    }

    public String getMembershipLama() {
        return membershipLama;
    }

    public void setMembershipLama(String membershipLama) {
        this.membershipLama = membershipLama;
    }

    public String getJatuhTempoLama() {
        return jatuhTempoLama;
    }

    public void setJatuhTempoLama(String jatuhTempoLama) {
        this.jatuhTempoLama = jatuhTempoLama;
    }

    public String getNominalPlafonLama() {
        return nominalPlafonLama;
    }

    public void setNominalPlafonLama(String nominalPlafonLama) {
        this.nominalPlafonLama = nominalPlafonLama;
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(customer);
        dest.writeValue(statusMember);
        dest.writeValue(expiredDate);
        dest.writeValue(payDate);
        dest.writeValue(nominalPlafon);
        dest.writeValue(jatuhTempo);
        dest.writeValue(isDataLama);
        dest.writeValue(membershipLama);
        dest.writeValue(jatuhTempoLama);
        dest.writeValue(nominalPlafonLama);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}
