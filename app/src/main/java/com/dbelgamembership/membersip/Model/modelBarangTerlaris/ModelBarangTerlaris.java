
package com.dbelgamembership.membersip.Model.ModelBarangTerlaris;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ModelBarangTerlaris implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ModelBarangTerlaris> CREATOR = new Creator<ModelBarangTerlaris>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelBarangTerlaris createFromParcel(android.os.Parcel in) {
            return new ModelBarangTerlaris(in);
        }

        public ModelBarangTerlaris[] newArray(int size) {
            return (new ModelBarangTerlaris[size]);
        }

    }
    ;
    private final static long serialVersionUID = -247385238019054582L;

    protected ModelBarangTerlaris(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ModelBarangTerlaris() {
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
