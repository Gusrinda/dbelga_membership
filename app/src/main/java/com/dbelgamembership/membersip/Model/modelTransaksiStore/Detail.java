
package com.dbelgamembership.membersip.Model.modelTransaksiStore;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Detail implements Serializable, Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("real_price")
    @Expose
    private String realPrice;
    @SerializedName("persentase_diskon")
    @Expose
    private String persentaseDiskon;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_diskon")
    @Expose
    private String totalDiskon;
    @SerializedName("total_setelah_diskon")
    @Expose
    private String totalSetelahDiskon;
    @SerializedName("indent_value")
    @Expose
    private String indentValue;
    @SerializedName("indent")
    @Expose
    private String indent;
    @SerializedName("qty_outlet")
    @Expose
    private String qtyOutlet;
    @SerializedName("qty_store")
    @Expose
    private String qtyStore;
    public final static Creator<Detail> CREATOR = new Creator<Detail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        public Detail[] newArray(int size) {
            return (new Detail[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5281807877218165187L;

    protected Detail(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.realPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.persentaseDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSetelahDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.indentValue = ((String) in.readValue((String.class.getClassLoader())));
        this.indent = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyOutlet = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyStore = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Detail() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Detail withCode(String code) {
        this.code = code;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Detail withPrice(String price) {
        this.price = price;
        return this;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public Detail withRealPrice(String realPrice) {
        this.realPrice = realPrice;
        return this;
    }

    public String getPersentaseDiskon() {
        return persentaseDiskon;
    }

    public void setPersentaseDiskon(String persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
    }

    public Detail withPersentaseDiskon(String persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Detail withTotal(String total) {
        this.total = total;
        return this;
    }

    public String getTotalDiskon() {
        return totalDiskon;
    }

    public void setTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
    }

    public Detail withTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
        return this;
    }

    public String getTotalSetelahDiskon() {
        return totalSetelahDiskon;
    }

    public void setTotalSetelahDiskon(String totalSetelahDiskon) {
        this.totalSetelahDiskon = totalSetelahDiskon;
    }

    public Detail withTotalSetelahDiskon(String totalSetelahDiskon) {
        this.totalSetelahDiskon = totalSetelahDiskon;
        return this;
    }

    public String getIndentValue() {
        return indentValue;
    }

    public void setIndentValue(String indentValue) {
        this.indentValue = indentValue;
    }

    public Detail withIndentValue(String indentValue) {
        this.indentValue = indentValue;
        return this;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public Detail withIndent(String indent) {
        this.indent = indent;
        return this;
    }

    public String getQtyOutlet() {
        return qtyOutlet;
    }

    public void setQtyOutlet(String qtyOutlet) {
        this.qtyOutlet = qtyOutlet;
    }

    public Detail withQtyOutlet(String qtyOutlet) {
        this.qtyOutlet = qtyOutlet;
        return this;
    }

    public String getQtyStore() {
        return qtyStore;
    }

    public void setQtyStore(String qtyStore) {
        this.qtyStore = qtyStore;
    }

    public Detail withQtyStore(String qtyStore) {
        this.qtyStore = qtyStore;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(price);
        dest.writeValue(realPrice);
        dest.writeValue(persentaseDiskon);
        dest.writeValue(total);
        dest.writeValue(totalDiskon);
        dest.writeValue(totalSetelahDiskon);
        dest.writeValue(indentValue);
        dest.writeValue(indent);
        dest.writeValue(qtyOutlet);
        dest.writeValue(qtyStore);
    }

    public int describeContents() {
        return  0;
    }

}
