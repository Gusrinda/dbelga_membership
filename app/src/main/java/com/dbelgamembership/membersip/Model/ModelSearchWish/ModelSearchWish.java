
package com.dbelgamembership.membersip.Model.ModelSearchWish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSearchWish implements Serializable, Parcelable
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
    public final static Parcelable.Creator<ModelSearchWish> CREATOR = new Creator<ModelSearchWish>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelSearchWish createFromParcel(Parcel in) {
            return new ModelSearchWish(in);
        }

        public ModelSearchWish[] newArray(int size) {
            return (new ModelSearchWish[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1782019383436851562L;

    protected ModelSearchWish(Parcel in) {
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.msgServer, ( com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer.class.getClassLoader()));
        this.code = ((int) in.readValue((int.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ModelSearchWish() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelSearchWish withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<MsgServer> getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
    }

    public ModelSearchWish withMsgServer(List<MsgServer> msgServer) {
        this.msgServer = msgServer;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ModelSearchWish withCode(int code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelSearchWish withDescription(String description) {
        this.description = description;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeList(msgServer);
        dest.writeValue(code);
        dest.writeValue(description);
    }

    public int describeContents() {
        return  0;
    }

}
