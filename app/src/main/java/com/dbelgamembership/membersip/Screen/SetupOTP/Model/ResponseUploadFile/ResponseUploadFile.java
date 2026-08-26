
package com.dbelgamembership.membersip.Screen.SetupOTP.Model.ResponseUploadFile;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ResponseUploadFile implements Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<ResponseUploadFile> CREATOR = new Creator<ResponseUploadFile>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseUploadFile createFromParcel(android.os.Parcel in) {
            return new ResponseUploadFile(in);
        }

        public ResponseUploadFile[] newArray(int size) {
            return (new ResponseUploadFile[size]);
        }

    }
    ;

    protected ResponseUploadFile(android.os.Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseUploadFile() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public ResponseUploadFile(Boolean status, String message, Data data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
