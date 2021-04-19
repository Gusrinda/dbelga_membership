
package com.dbelgamembership.membersip.Model.modelBarangTerlaris;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBarangTerlaris implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = new ArrayList<MsgServer>();
    public final static Parcelable.Creator<ModelBarangTerlaris> CREATOR = new Creator<ModelBarangTerlaris>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelBarangTerlaris createFromParcel(Parcel in) {
            return new ModelBarangTerlaris(in);
        }

        public ModelBarangTerlaris[] newArray(int size) {
            return (new ModelBarangTerlaris[size]);
        }

    }
    ;
    private final static long serialVersionUID = 302911170328571863L;

    protected ModelBarangTerlaris(Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.modelBarangTerlaris.MsgServer.class.getClassLoader()));
    }

    public ModelBarangTerlaris() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelBarangTerlaris withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelBarangTerlaris withMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeList(msgServer);
    }

    public int describeContents() {
        return  0;
    }

}
