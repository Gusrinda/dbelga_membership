
package com.dbelgamembership.membersip.Model.ResponseLogin;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ResponseLogin implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
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
    private final static long serialVersionUID = -4684637371188664679L;

    protected ResponseLogin(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ResponseLogin() {
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
