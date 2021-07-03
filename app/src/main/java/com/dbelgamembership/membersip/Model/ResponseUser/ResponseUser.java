
package com.dbelgamembership.membersip.Model.ResponseUser;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseUser implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ResponseUser> CREATOR = new Creator<ResponseUser>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseUser createFromParcel(android.os.Parcel in) {
            return new ResponseUser(in);
        }

        public ResponseUser[] newArray(int size) {
            return (new ResponseUser[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5483051537816037594L;

    protected ResponseUser(android.os.Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ResponseUser() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseUser withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public MsgServer getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
    }

    public ResponseUser withMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(msgServer);
    }

    public int describeContents() {
        return  0;
    }

}
