
package com.dbelgamembership.membersip.Model.ModelListWishlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelListWishlist implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = new ArrayList<MsgServer>();
    public final static Creator<ModelListWishlist> CREATOR = new Creator<ModelListWishlist>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelListWishlist createFromParcel(android.os.Parcel in) {
            return new ModelListWishlist(in);
        }

        public ModelListWishlist[] newArray(int size) {
            return (new ModelListWishlist[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7954973648296105630L;

    protected ModelListWishlist(android.os.Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelListWishlist.MsgServer.class.getClassLoader()));
    }

    public ModelListWishlist() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelListWishlist withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelListWishlist withMsgServer(List<MsgServer> msgServer) {
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
