
package com.dbelgamembership.membersip.Model.ResponseVersi;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ResponseVersi implements Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = null;
    public final static Creator<ResponseVersi> CREATOR = new Creator<ResponseVersi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseVersi createFromParcel(android.os.Parcel in) {
            return new ResponseVersi(in);
        }

        public ResponseVersi[] newArray(int size) {
            return (new ResponseVersi[size]);
        }

    }
    ;

    protected ResponseVersi(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ResponseVersi.MsgServer.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseVersi() {
    }

    /**
     * 
     * @param success
     * @param msgServer
     */
    public ResponseVersi(Boolean success, List<MsgServer> msgServer) {
        super();
        this.success = success;
        this.msgServer = msgServer;
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
