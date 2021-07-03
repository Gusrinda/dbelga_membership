
package com.dbelgamembership.membersip.Model.ResponseLogin;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseLogin implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ResponseLogin> CREATOR = new Creator<ResponseLogin>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseLogin createFromParcel(android.os.Parcel in) {
            return new ResponseLogin(in);
        }

        public ResponseLogin[] newArray(int size) {
            return (new ResponseLogin[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4462468681143114232L;

    protected ResponseLogin(android.os.Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ResponseLogin() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseLogin withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public MsgServer getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
    }

    public ResponseLogin withMsgServer(MsgServer msgServer) {
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
