
package com.dbelgamembership.membersip.Model.ModelTagihanUser;

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
    @SerializedName("periode")
    @Expose
    private String periode;
    @SerializedName("tagihan")
    @Expose
    private String tagihan;
    @SerializedName("tagihan_denda")
    @Expose
    private double tagihanDenda;
    @SerializedName("tagihan_total")
    @Expose
    private double tagihanTotal;
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
    private final static long serialVersionUID = -4250974222835158835L;

    protected MsgServer(android.os.Parcel in) {
        this.customerNama = ((String) in.readValue((String.class.getClassLoader())));
        this.customerId = ((String) in.readValue((String.class.getClassLoader())));
        this.customerIdentitas = ((String) in.readValue((String.class.getClassLoader())));
        this.periode = ((String) in.readValue((String.class.getClassLoader())));
        this.tagihan = ((String) in.readValue((String.class.getClassLoader())));
        this.tagihanDenda = ((double) in.readValue((Integer.class.getClassLoader())));
        this.tagihanTotal = ((double) in.readValue((Integer.class.getClassLoader())));
        this.limitPenggunaan = ((double) in.readValue((Integer.class.getClassLoader())));
        this.limitSisa = ((double) in.readValue((Integer.class.getClassLoader())));
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

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getTagihan() {
        return tagihan;
    }

    public void setTagihan(String tagihan) {
        this.tagihan = tagihan;
    }

    public double getTagihanDenda() {
        return tagihanDenda;
    }

    public void setTagihanDenda(double tagihanDenda) {
        this.tagihanDenda = tagihanDenda;
    }

    public double getTagihanTotal() {
        return tagihanTotal;
    }

    public void setTagihanTotal(double tagihanTotal) {
        this.tagihanTotal = tagihanTotal;
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
        dest.writeValue(periode);
        dest.writeValue(tagihan);
        dest.writeValue(tagihanDenda);
        dest.writeValue(tagihanTotal);
        dest.writeValue(limitPenggunaan);
        dest.writeValue(limitSisa);
    }

    public int describeContents() {
        return  0;
    }

}
