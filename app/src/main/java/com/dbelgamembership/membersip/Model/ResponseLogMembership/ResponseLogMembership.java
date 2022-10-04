
package com.dbelgamembership.membersip.Model.ResponseLogMembership;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ResponseLogMembership implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ResponseLogMembership> CREATOR = new Creator<ResponseLogMembership>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseLogMembership createFromParcel(android.os.Parcel in) {
            return new ResponseLogMembership(in);
        }

        public ResponseLogMembership[] newArray(int size) {
            return (new ResponseLogMembership[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7173058178803756512L;

    protected ResponseLogMembership(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ResponseLogMembership() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public MsgServer getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(msgServer);
    }

    public int describeContents() {
        return  0;
    }

}
