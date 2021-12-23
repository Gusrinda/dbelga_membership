
package com.dbelgamembership.membersip.Model.ResponseUser;

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

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("bentuk")
    @Expose
    private String bentuk;
    @SerializedName("saldo_piutang")
    @Expose
    private Integer saldoPiutang;
    @SerializedName("main_phone_1")
    @Expose
    private String mainPhone1;
    @SerializedName("main_email")
    @Expose
    private String mainEmail;
    @SerializedName("main_address")
    @Expose
    private String mainAddress;
    @SerializedName("date_birth")
    @Expose
    private String dateBirth;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("pay_date")
    @Expose
    private String payDate;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    @SerializedName("status_member")
    @Expose
    private String statusMember;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("exp_otp")
    @Expose
    private String expOtp;
    @SerializedName("status_payment")
    @Expose
    private String statusPayment;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
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
    private final static long serialVersionUID = -5964603504753415534L;

    protected MsgServer(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.bentuk = ((String) in.readValue((String.class.getClassLoader())));
        this.saldoPiutang = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mainPhone1 = ((String) in.readValue((String.class.getClassLoader())));
        this.mainEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.mainAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.dateBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.payDate = ((String) in.readValue((String.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMember = ((String) in.readValue((String.class.getClassLoader())));
        this.otp = ((String) in.readValue((String.class.getClassLoader())));
        this.expOtp = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public Integer getSaldoPiutang() {
        return saldoPiutang;
    }

    public void setSaldoPiutang(Integer saldoPiutang) {
        this.saldoPiutang = saldoPiutang;
    }

    public String getMainPhone1() {
        return mainPhone1;
    }

    public void setMainPhone1(String mainPhone1) {
        this.mainPhone1 = mainPhone1;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getStatusMember() {
        return statusMember;
    }

    public void setStatusMember(String statusMember) {
        this.statusMember = statusMember;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getExpOtp() {
        return expOtp;
    }

    public void setExpOtp(String expOtp) {
        this.expOtp = expOtp;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(type);
        dest.writeValue(bentuk);
        dest.writeValue(saldoPiutang);
        dest.writeValue(mainPhone1);
        dest.writeValue(mainEmail);
        dest.writeValue(mainAddress);
        dest.writeValue(dateBirth);
        dest.writeValue(password);
        dest.writeValue(payDate);
        dest.writeValue(expiredDate);
        dest.writeValue(statusMember);
        dest.writeValue(otp);
        dest.writeValue(expOtp);
        dest.writeValue(statusPayment);
        dest.writeValue(updatedAt);
        dest.writeValue(createdAt);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}
