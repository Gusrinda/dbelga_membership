
package com.dbelgamembership.membersip.Model.ModelResponseCS;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelResponseCS implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = null;
    public final static Creator<ModelResponseCS> CREATOR = new Creator<ModelResponseCS>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelResponseCS createFromParcel(android.os.Parcel in) {
            return new ModelResponseCS(in);
        }

        public ModelResponseCS[] newArray(int size) {
            return (new ModelResponseCS[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2281815479640489483L;

    protected ModelResponseCS(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelResponseCS.MsgServer.class.getClassLoader()));
    }

    public ModelResponseCS() {
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
