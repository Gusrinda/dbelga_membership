
package com.dbelgamembership.membersip.Model.ModelTagihanUser;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ModelTagihanUser implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ModelTagihanUser> CREATOR = new Creator<ModelTagihanUser>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelTagihanUser createFromParcel(android.os.Parcel in) {
            return new ModelTagihanUser(in);
        }

        public ModelTagihanUser[] newArray(int size) {
            return (new ModelTagihanUser[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5442154803244346199L;

    protected ModelTagihanUser(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ModelTagihanUser() {
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
