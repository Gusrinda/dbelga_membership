
package com.dbelgamembership.membersip.Model.ModelDataLimit;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("customer_nama")
    @Expose
    private String customerNama;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_identitas")
    @Expose
    private String customerIdentitas;
    @SerializedName("limit-awal")
    @Expose
    private double limitAwal;
    @SerializedName("limit_penggunaan")
    @Expose
    private double limitPenggunaan;
    @SerializedName("limit_sisa")
    @Expose
    private double limitSisa;
    public final static Creator<MsgServer> CREATOR = new Creator<MsgServer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsgServer createFromParcel(android.os.Parcel in) {
            return new MsgServer(in);
        }

        public MsgServer[] newArray(int size) {
            return (new MsgServer[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4332683260731145814L;

    protected MsgServer(android.os.Parcel in) {
        this.customerNama = ((String) in.readValue((String.class.getClassLoader())));
        this.customerId = ((String) in.readValue((String.class.getClassLoader())));
        this.customerIdentitas = ((String) in.readValue((String.class.getClassLoader())));
        this.limitAwal = ((double) in.readValue((double.class.getClassLoader())));
        this.limitPenggunaan = ((double) in.readValue((double.class.getClassLoader())));
        this.limitSisa = ((double) in.readValue((double.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public String getCustomerNama() {
        return customerNama;
    }

    public void setCustomerNama(String customerNama) {
        this.customerNama = customerNama;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerIdentitas() {
        return customerIdentitas;
    }

    public void setCustomerIdentitas(String customerIdentitas) {
        this.customerIdentitas = customerIdentitas;
    }

    public double getLimitAwal() {
        return limitAwal;
    }

    public void setLimitAwal(double limitAwal) {
        this.limitAwal = limitAwal;
    }

    public double getLimitPenggunaan() {
        return limitPenggunaan;
    }

    public void setLimitPenggunaan(double limitPenggunaan) {
        this.limitPenggunaan = limitPenggunaan;
    }

    public double getLimitSisa() {
        return limitSisa;
    }

    public void setLimitSisa(double limitSisa) {
        this.limitSisa = limitSisa;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(customerNama);
        dest.writeValue(customerId);
        dest.writeValue(customerIdentitas);
        dest.writeValue(limitAwal);
        dest.writeValue(limitPenggunaan);
        dest.writeValue(limitSisa);
    }

    public int describeContents() {
        return  0;
    }

}
