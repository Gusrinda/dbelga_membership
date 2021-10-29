
package com.dbelgamembership.membersip.Model.ModelToko;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelToko implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = null;
    public final static Creator<ModelToko> CREATOR = new Creator<ModelToko>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelToko createFromParcel(android.os.Parcel in) {
            return new ModelToko(in);
        }

        public ModelToko[] newArray(int size) {
            return (new ModelToko[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2514241120640822203L;

    protected ModelToko(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelToko.MsgServer.class.getClassLoader()));
    }

    public ModelToko() {
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
