
package com.dbelgamembership.membersip.Model.ModelGetKategori;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGetKategori implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = new ArrayList<MsgServer>();
    public final static Creator<ModelGetKategori> CREATOR = new Creator<ModelGetKategori>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelGetKategori createFromParcel(android.os.Parcel in) {
            return new ModelGetKategori(in);
        }

        public ModelGetKategori[] newArray(int size) {
            return (new ModelGetKategori[size]);
        }

    }
    ;
    private final static long serialVersionUID = -874232667432596273L;

    protected ModelGetKategori(android.os.Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelGetKategori.MsgServer.class.getClassLoader()));
    }

    public ModelGetKategori() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelGetKategori withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelGetKategori withMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeList(msgServer);
    }

    public int describeContents() {
        return  0;
    }

}
