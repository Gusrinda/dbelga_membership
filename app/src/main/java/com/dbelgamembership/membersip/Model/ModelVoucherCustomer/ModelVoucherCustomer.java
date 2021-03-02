
package com.dbelgamembership.membersip.Model.ModelVoucherCustomer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelVoucherCustomer implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = new ArrayList<MsgServer>();
    public final static Parcelable.Creator<ModelVoucherCustomer> CREATOR = new Creator<ModelVoucherCustomer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelVoucherCustomer createFromParcel(Parcel in) {
            return new ModelVoucherCustomer(in);
        }

        public ModelVoucherCustomer[] newArray(int size) {
            return (new ModelVoucherCustomer[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2138506737089634035L;

    protected ModelVoucherCustomer(Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelVoucherCustomer.MsgServer.class.getClassLoader()));
    }

    public ModelVoucherCustomer() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelVoucherCustomer withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelVoucherCustomer withMsgServer(List<MsgServer> msgServer) {
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
