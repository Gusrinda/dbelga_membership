
package com.dbelgamembership.membersip.Screen.User.Verifikasi.model.ResponseCreatePembayaranMembership;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ResponseCreatePembayaranMembership implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = null;
    public final static Creator<ResponseCreatePembayaranMembership> CREATOR = new Creator<ResponseCreatePembayaranMembership>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseCreatePembayaranMembership createFromParcel(android.os.Parcel in) {
            return new ResponseCreatePembayaranMembership(in);
        }

        public ResponseCreatePembayaranMembership[] newArray(int size) {
            return (new ResponseCreatePembayaranMembership[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1204707830852360218L;

    protected ResponseCreatePembayaranMembership(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Screen.User.Verifikasi.model.ResponseCreatePembayaranMembership.MsgServer.class.getClassLoader()));
    }

    public ResponseCreatePembayaranMembership() {
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
