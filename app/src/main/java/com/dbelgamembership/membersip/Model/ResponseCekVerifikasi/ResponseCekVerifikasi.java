
package com.dbelgamembership.membersip.Model.ResponseCekVerifikasi;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ResponseCekVerifikasi implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ResponseCekVerifikasi> CREATOR = new Creator<ResponseCekVerifikasi>() {

        @SuppressWarnings({
            "unchecked"
        })
        public ResponseCekVerifikasi createFromParcel(android.os.Parcel in) {
            return new ResponseCekVerifikasi(in);
        }

        public ResponseCekVerifikasi[] newArray(int size) {
            return (new ResponseCekVerifikasi[size]);
        }

    }
    ;
    private final static long serialVersionUID = 420780591306547221L;

    protected ResponseCekVerifikasi(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ResponseCekVerifikasi() {
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
