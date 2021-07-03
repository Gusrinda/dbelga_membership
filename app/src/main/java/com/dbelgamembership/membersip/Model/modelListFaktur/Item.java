
package com.dbelgamembership.membersip.Model.modelListFaktur;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Item implements Serializable, Parcelable
{

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("name")
    @Expose
    private String name;
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
    private int totalSetelahDiskon;
    @SerializedName("qty_outlet")
    @Expose
    private String qtyOutlet;
    @SerializedName("qty_store")
    @Expose
    private String qtyStore;
    @SerializedName("indent")
    @Expose
    private boolean indent;
    @SerializedName("indent_value")
    @Expose
    private int indentValue;
    @SerializedName("so_code")
    @Expose
    private String soCode;
    public final static Creator<Item> CREATOR = new Creator<Item>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Item createFromParcel(android.os.Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return (new Item[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6475266154798888125L;

    protected Item(android.os.Parcel in) {
        this.orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.realPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.persentaseDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSetelahDiskon = ((int) in.readValue((int.class.getClassLoader())));
        this.qtyOutlet = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyStore = ((String) in.readValue((String.class.getClassLoader())));
        this.indent = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.indentValue = ((int) in.readValue((int.class.getClassLoader())));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Item() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Item withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public Item withCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item withName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Item withPrice(String price) {
        this.price = price;
        return this;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public Item withRealPrice(String realPrice) {
        this.realPrice = realPrice;
        return this;
    }

    public String getPersentaseDiskon() {
        return persentaseDiskon;
    }

    public void setPersentaseDiskon(String persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
    }

    public Item withPersentaseDiskon(String persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Item withTotal(String total) {
        this.total = total;
        return this;
    }

    public String getTotalDiskon() {
        return totalDiskon;
    }

    public void setTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
    }

    public Item withTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
        return this;
    }

    public int getTotalSetelahDiskon() {
        return totalSetelahDiskon;
    }

    public void setTotalSetelahDiskon(int totalSetelahDiskon) {
        this.totalSetelahDiskon = totalSetelahDiskon;
    }

    public Item withTotalSetelahDiskon(int totalSetelahDiskon) {
        this.totalSetelahDiskon = totalSetelahDiskon;
        return this;
    }

    public String getQtyOutlet() {
        return qtyOutlet;
    }

    public void setQtyOutlet(String qtyOutlet) {
        this.qtyOutlet = qtyOutlet;
    }

    public Item withQtyOutlet(String qtyOutlet) {
        this.qtyOutlet = qtyOutlet;
        return this;
    }

    public String getQtyStore() {
        return qtyStore;
    }

    public void setQtyStore(String qtyStore) {
        this.qtyStore = qtyStore;
    }

    public Item withQtyStore(String qtyStore) {
        this.qtyStore = qtyStore;
        return this;
    }

    public boolean isIndent() {
        return indent;
    }

    public void setIndent(boolean indent) {
        this.indent = indent;
    }

    public Item withIndent(boolean indent) {
        this.indent = indent;
        return this;
    }

    public int getIndentValue() {
        return indentValue;
    }

    public void setIndentValue(int indentValue) {
        this.indentValue = indentValue;
    }

    public Item withIndentValue(int indentValue) {
        this.indentValue = indentValue;
        return this;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public Item withSoCode(String soCode) {
        this.soCode = soCode;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(orderId);
        dest.writeValue(codeProduct);
        dest.writeValue(name);
        dest.writeValue(price);
        dest.writeValue(realPrice);
        dest.writeValue(persentaseDiskon);
        dest.writeValue(total);
        dest.writeValue(totalDiskon);
        dest.writeValue(totalSetelahDiskon);
        dest.writeValue(qtyOutlet);
        dest.writeValue(qtyStore);
        dest.writeValue(indent);
        dest.writeValue(indentValue);
        dest.writeValue(soCode);
    }

    public int describeContents() {
        return  0;
    }

}
