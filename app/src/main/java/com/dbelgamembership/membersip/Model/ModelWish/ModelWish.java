
package com.dbelgamembership.membersip.Model.ModelWish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelWish implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = new ArrayList<MsgServer>();
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("description")
    @Expose
    private String description;
    public final static Creator<ModelWish> CREATOR = new Creator<ModelWish>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelWish createFromParcel(android.os.Parcel in) {
            return new ModelWish(in);
        }

        public ModelWish[] newArray(int size) {
            return (new ModelWish[size]);
        }

    }
    ;
    private final static long serialVersionUID = -454667642529012841L;

    protected ModelWish(android.os.Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ModelWish.MsgServer.class.getClassLoader()));
        this.code = ((int) in.readValue((int.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ModelWish() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelWish withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelWish withMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ModelWish withCode(int code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelWish withDescription(String description) {
        this.description = description;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeList(msgServer);
        dest.writeValue(code);
        dest.writeValue(description);
    }

    public int describeContents() {
        return  0;
    }

}
