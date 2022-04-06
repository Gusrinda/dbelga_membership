
package com.dbelgamembership.membersip.Model.ModelRedeemVoucher;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ModelRedeemVoucher implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = null;
    public final static Creator<ModelRedeemVoucher> CREATOR = new Creator<ModelRedeemVoucher>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelRedeemVoucher createFromParcel(android.os.Parcel in) {
            return new ModelRedeemVoucher(in);
        }

        public ModelRedeemVoucher[] newArray(int size) {
            return (new ModelRedeemVoucher[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8651162341576331841L;

    protected ModelRedeemVoucher(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelRedeemVoucher.MsgServer.class.getClassLoader()));
    }

    public ModelRedeemVoucher() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeList(msgServer);
    }

    public int describeContents() {
        return  0;
    }

}
