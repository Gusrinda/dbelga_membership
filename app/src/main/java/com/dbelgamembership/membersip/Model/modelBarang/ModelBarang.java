
package com.dbelgamembership.membersip.Model.modelBarang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
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
    private List<MsgServer> msgServer = new ArrayList<MsgServer>();
    public final static Parcelable.Creator<ModelBarang> CREATOR = new Creator<ModelBarang>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelBarang createFromParcel(Parcel in) {
            return new ModelBarang(in);
        }

        public ModelBarang[] newArray(int size) {
            return (new ModelBarang[size]);
        }

    }
    ;
    private final static long serialVersionUID = 7205872809509709101L;

    protected ModelBarang(Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.modelBarang.MsgServer.class.getClassLoader()));
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

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelBarang withMsgServer(List<MsgServer> msgServer) {
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
