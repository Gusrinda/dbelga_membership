
package com.dbelgamembership.membersip.Model.ModelUser;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ModelUser implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = null;
    public final static Creator<ModelUser> CREATOR = new Creator<ModelUser>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelUser createFromParcel(android.os.Parcel in) {
            return new ModelUser(in);
        }

        public ModelUser[] newArray(int size) {
            return (new ModelUser[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6796515145435897367L;

    protected ModelUser(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelUser.MsgServer.class.getClassLoader()));
    }

    public ModelUser() {
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
