
package com.dbelgamembership.membersip.Model.ModelResponseCart;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ModelResponseCart implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private MsgServer msgServer;
    public final static Creator<ModelResponseCart> CREATOR = new Creator<ModelResponseCart>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelResponseCart createFromParcel(android.os.Parcel in) {
            return new ModelResponseCart(in);
        }

        public ModelResponseCart[] newArray(int size) {
            return (new ModelResponseCart[size]);
        }

    }
    ;
    private final static long serialVersionUID = 9067649130988569208L;

    protected ModelResponseCart(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.msgServer = ((MsgServer) in.readValue((MsgServer.class.getClassLoader())));
    }

    public ModelResponseCart() {
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
