
package com.dbelgamembership.membersip.Screen.SetupOTP.Model.ResponseSendOTP;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseSendOTP implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("waId")
    @Expose
    private String waId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("wid")
    @Expose
    private String wid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deliveryStatus")
    @Expose
    private String deliveryStatus;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("deliverAt")
    @Expose
    private String deliverAt;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("retentionPolicy")
    @Expose
    private String retentionPolicy;
    @SerializedName("retry")
    @Expose
    private Retry retry;
    @SerializedName("webhookStatus")
    @Expose
    private String webhookStatus;
    @SerializedName("device")
    @Expose
    private String device;
    public final static Creator<ResponseSendOTP> CREATOR = new Creator<ResponseSendOTP>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseSendOTP createFromParcel(android.os.Parcel in) {
            return new ResponseSendOTP(in);
        }

        public ResponseSendOTP[] newArray(int size) {
            return (new ResponseSendOTP[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4861848550802488205L;

    protected ResponseSendOTP(android.os.Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.waId = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.wid = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.deliverAt = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.priority = ((String) in.readValue((String.class.getClassLoader())));
        this.retentionPolicy = ((String) in.readValue((String.class.getClassLoader())));
        this.retry = ((Retry) in.readValue((Retry.class.getClassLoader())));
        this.webhookStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.device = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ResponseSendOTP() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaId() {
        return waId;
    }

    public void setWaId(String waId) {
        this.waId = waId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(String deliverAt) {
        this.deliverAt = deliverAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public Retry getRetry() {
        return retry;
    }

    public void setRetry(Retry retry) {
        this.retry = retry;
    }

    public String getWebhookStatus() {
        return webhookStatus;
    }

    public void setWebhookStatus(String webhookStatus) {
        this.webhookStatus = webhookStatus;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(waId);
        dest.writeValue(phone);
        dest.writeValue(wid);
        dest.writeValue(status);
        dest.writeValue(deliveryStatus);
        dest.writeValue(createdAt);
        dest.writeValue(deliverAt);
        dest.writeValue(message);
        dest.writeValue(priority);
        dest.writeValue(retentionPolicy);
        dest.writeValue(retry);
        dest.writeValue(webhookStatus);
        dest.writeValue(device);
    }

    public int describeContents() {
        return  0;
    }

}
