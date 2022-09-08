
package com.dbelgamembership.membersip.Model.ResponseBayarTagihan;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ResponseBayarTagihan implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msgServer")
    @Expose
    private List<MsgServer> msgServer = null;
    public final static Creator<ResponseBayarTagihan> CREATOR = new Creator<ResponseBayarTagihan>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseBayarTagihan createFromParcel(android.os.Parcel in) {
            return new ResponseBayarTagihan(in);
        }

        public ResponseBayarTagihan[] newArray(int size) {
            return (new ResponseBayarTagihan[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1842982411819353388L;

    protected ResponseBayarTagihan(android.os.Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.msgServer, (com.dbelgamembership.membersip.Model.ResponseBayarTagihan.MsgServer.class.getClassLoader()));
    }

    public ResponseBayarTagihan() {
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
