
package com.dbelgamembership.membersip.Model.ResponseCekVerifikasi;

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

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("membership")
    @Expose
    private String membership;
    @SerializedName("status_verifikasi")
    @Expose
    private Boolean statusVerifikasi;
    @SerializedName("veirifikasi_email")
    @Expose
    private Boolean veirifikasiEmail;
    @SerializedName("is_there_foto")
    @Expose
    private Boolean isThereFoto;
    @SerializedName("veirifikasi_foto")
    @Expose
    private Boolean veirifikasiFoto;
    @SerializedName("is_there_payment")
    @Expose
    private Boolean isTherePayment;
    @SerializedName("veirifikasi_payment")
    @Expose
    private Boolean veirifikasiPayment;
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
    private final static long serialVersionUID = -7808183052557230636L;

    protected MsgServer(android.os.Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.membership = ((String) in.readValue((String.class.getClassLoader())));
        this.statusVerifikasi = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.veirifikasiEmail = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isThereFoto = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.veirifikasiFoto = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isTherePayment = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.veirifikasiPayment = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public Boolean getStatusVerifikasi() {
        return statusVerifikasi;
    }

    public void setStatusVerifikasi(Boolean statusVerifikasi) {
        this.statusVerifikasi = statusVerifikasi;
    }

    public Boolean getVeirifikasiEmail() {
        return veirifikasiEmail;
    }

    public void setVeirifikasiEmail(Boolean veirifikasiEmail) {
        this.veirifikasiEmail = veirifikasiEmail;
    }

    public Boolean getIsThereFoto() {
        return isThereFoto;
    }

    public void setIsThereFoto(Boolean isThereFoto) {
        this.isThereFoto = isThereFoto;
    }

    public Boolean getVeirifikasiFoto() {
        return veirifikasiFoto;
    }

    public void setVeirifikasiFoto(Boolean veirifikasiFoto) {
        this.veirifikasiFoto = veirifikasiFoto;
    }

    public Boolean getIsTherePayment() {
        return isTherePayment;
    }

    public void setIsTherePayment(Boolean isTherePayment) {
        this.isTherePayment = isTherePayment;
    }

    public Boolean getVeirifikasiPayment() {
        return veirifikasiPayment;
    }

    public void setVeirifikasiPayment(Boolean veirifikasiPayment) {
        this.veirifikasiPayment = veirifikasiPayment;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(membership);
        dest.writeValue(statusVerifikasi);
        dest.writeValue(veirifikasiEmail);
        dest.writeValue(isThereFoto);
        dest.writeValue(veirifikasiFoto);
        dest.writeValue(isTherePayment);
        dest.writeValue(veirifikasiPayment);
    }

    public int describeContents() {
        return  0;
    }

}
