
package com.dbelgamembership.membersip.Model.modelBarang;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelBarang implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ModelBarang> CREATOR = new Creator<ModelBarang>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelBarang createFromParcel(android.os.Parcel in) {
            return new ModelBarang(in);
        }

        public ModelBarang[] newArray(int size) {
            return (new ModelBarang[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6428111982478850324L;

    protected ModelBarang(android.os.Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ModelBarang() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelBarang withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public MsgServer getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
    }

    public ModelBarang withMsgServer(MsgServer msgServer) {
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
