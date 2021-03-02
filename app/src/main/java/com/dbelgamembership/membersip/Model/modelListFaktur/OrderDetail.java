
package com.dbelgamembership.membersip.Model.modelListFaktur;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDetail implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("so_code")
    @Expose
    private String soCode;
    @SerializedName("payment_id")
    @Expose
    private int paymentId;
    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("alamat_pengiriman")
    @Expose
    private String alamatPengiriman;
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<Item>();
    public final static Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OrderDetail createFromParcel(Parcel in) {
            return new OrderDetail(in);
        }

        public OrderDetail[] newArray(int size) {
            return (new OrderDetail[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3361037511418794874L;

    protected OrderDetail(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentId = ((int) in.readValue((int.class.getClassLoader())));
        this.orderId = ((int) in.readValue((int.class.getClassLoader())));
        this.alamatPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.items, (com.dbelgamembership.membersip.Model.modelListFaktur.Item.class.getClassLoader()));
    }

    public OrderDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderDetail withId(int id) {
        this.id = id;
        return this;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public OrderDetail withPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
        return this;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public OrderDetail withSoCode(String soCode) {
        this.soCode = soCode;
        return this;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public OrderDetail withPaymentId(int paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderDetail withOrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
    }

    public OrderDetail withAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderDetail withItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(pembayaranCode);
        dest.writeValue(soCode);
        dest.writeValue(paymentId);
        dest.writeValue(orderId);
        dest.writeValue(alamatPengiriman);
        dest.writeList(items);
    }

    public int describeContents() {
        return  0;
    }

}
