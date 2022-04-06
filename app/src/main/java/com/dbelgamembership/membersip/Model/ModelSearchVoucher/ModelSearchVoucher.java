
package com.dbelgamembership.membersip.Model.ModelSearchVoucher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSearchVoucher implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = new ArrayList<MsgServer>();
    public final static Parcelable.Creator<ModelSearchVoucher> CREATOR = new Creator<ModelSearchVoucher>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelSearchVoucher createFromParcel(Parcel in) {
            return new ModelSearchVoucher(in);
        }

        public ModelSearchVoucher[] newArray(int size) {
            return (new ModelSearchVoucher[size]);
        }

    };

    private final static long serialVersionUID = -7852337565823761641L;

    protected ModelSearchVoucher(Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer.class.getClassLoader()));
    }

    public ModelSearchVoucher() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelSearchVoucher withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelSearchVoucher withMsgServer(List<MsgServer> msgServer) {
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
