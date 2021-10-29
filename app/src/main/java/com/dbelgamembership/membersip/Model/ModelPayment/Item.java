
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
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
    private Integer totalSetelahDiskon;
    @SerializedName("qty_outlet")
    @Expose
    private String qtyOutlet;
    @SerializedName("qty_store")
    @Expose
    private String qtyStore;
    @SerializedName("indent")
    @Expose
    private String indent;
    @SerializedName("indent_value")
    @Expose
    private Integer indentValue;
    @SerializedName("so_code")
    @Expose
    private String soCode;
    @SerializedName("sales")
    @Expose
    private int sales;
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
    private final static long serialVersionUID = -4311325415928016610L;

    protected Item(android.os.Parcel in) {
        this.orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.realPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.persentaseDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSetelahDiskon = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qtyOutlet = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyStore = ((String) in.readValue((String.class.getClassLoader())));
        this.indent = ((String) in.readValue((String.class.getClassLoader())));
        this.indentValue = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
        this.sales = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Item() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public String getPersentaseDiskon() {
        return persentaseDiskon;
    }

    public void setPersentaseDiskon(String persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalDiskon() {
        return totalDiskon;
    }

    public void setTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
    }

    public Integer getTotalSetelahDiskon() {
        return totalSetelahDiskon;
    }

    public void setTotalSetelahDiskon(Integer totalSetelahDiskon) {
        this.totalSetelahDiskon = totalSetelahDiskon;
    }

    public String getQtyOutlet() {
        return qtyOutlet;
    }

    public void setQtyOutlet(String qtyOutlet) {
        this.qtyOutlet = qtyOutlet;
    }

    public String getQtyStore() {
        return qtyStore;
    }

    public void setQtyStore(String qtyStore) {
        this.qtyStore = qtyStore;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public Integer getIndentValue() {
        return indentValue;
    }

    public void setIndentValue(Integer indentValue) {
        this.indentValue = indentValue;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Item withSales(int sales) {
        this.sales = sales;
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
        dest.writeValue(sales);
    }

    public int describeContents() {
        return  0;
    }

}
