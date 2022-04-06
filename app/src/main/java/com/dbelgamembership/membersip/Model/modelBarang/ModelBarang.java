
package com.dbelgamembership.membersip.Model.modelBarang;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ModelBarang implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
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
    private final static long serialVersionUID = -6730123934433014167L;

    protected ModelBarang(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelBarang() {
    }

    /**
     * 
     * @param success
     * @param msgServer
     */
    public ModelBarang(Boolean success, MsgServer msgServer) {
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
