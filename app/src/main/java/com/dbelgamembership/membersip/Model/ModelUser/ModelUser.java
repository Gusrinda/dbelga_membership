
package com.dbelgamembership.membersip.Model.ModelUser;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUser implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Parcelable.Creator<ModelUser> CREATOR = new Creator<ModelUser>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelUser createFromParcel(Parcel in) {
            return new ModelUser(in);
        }

        public ModelUser[] newArray(int size) {
            return (new ModelUser[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7795380076446181738L;

    protected ModelUser(Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ModelUser() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelUser withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public MsgServer getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
    }

    public ModelUser withMsgServer(MsgServer msgServer) {
        this.msgServer = msgServer;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(msgServer);
    }

    public int describeContents() {
        return  0;
    }

}
