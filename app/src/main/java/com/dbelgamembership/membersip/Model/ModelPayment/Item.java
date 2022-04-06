
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
    private String totalSetelahDiskon;
    @SerializedName("qty_outlet")
    @Expose
    private String qtyOutlet;
    @SerializedName("qty_store")
    @Expose
    private String qtyStore;
    @SerializedName("indent")
    @Expose
    private Boolean indent;
    @SerializedName("indent_value")
    @Expose
    private Integer indentValue;
    @SerializedName("so_code")
    @Expose
    private String soCode;
    @SerializedName("diskon")
    @Expose
    private String diskon;
    @SerializedName("qty_diskon")
    @Expose
    private String qtyDiskon;
    @SerializedName("type_diskon")
    @Expose
    private String typeDiskon;
    @SerializedName("kode_promo")
    @Expose
    private String kodePromo;
    @SerializedName("is_diskon_membership")
    @Expose
    private Boolean isDiskonMembership;
    @SerializedName("presentase_diskon_membership")
    @Expose
    private String presentaseDiskonMembership;
    @SerializedName("total_diskon_membership")
    @Expose
    private String totalDiskonMembership;
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
    private final static long serialVersionUID = 7077297467916522447L;

    protected Item(android.os.Parcel in) {
        this.orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.realPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.persentaseDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSetelahDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyOutlet = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyStore = ((String) in.readValue((String.class.getClassLoader())));
        this.indent = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.indentValue = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
        this.diskon = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.typeDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.kodePromo = ((String) in.readValue((String.class.getClassLoader())));
        this.isDiskonMembership = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.presentaseDiskonMembership = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskonMembership = ((String) in.readValue((String.class.getClassLoader())));
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

    public String getTotalSetelahDiskon() {
        return totalSetelahDiskon;
    }

    public void setTotalSetelahDiskon(String totalSetelahDiskon) {
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

    public Boolean getIndent() {
        return indent;
    }

    public void setIndent(Boolean indent) {
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

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getQtyDiskon() {
        return qtyDiskon;
    }

    public void setQtyDiskon(String qtyDiskon) {
        this.qtyDiskon = qtyDiskon;
    }

    public String getTypeDiskon() {
        return typeDiskon;
    }

    public void setTypeDiskon(String typeDiskon) {
        this.typeDiskon = typeDiskon;
    }

    public String getKodePromo() {
        return kodePromo;
    }

    public void setKodePromo(String kodePromo) {
        this.kodePromo = kodePromo;
    }

    public Boolean getIsDiskonMembership() {
        return isDiskonMembership;
    }

    public void setIsDiskonMembership(Boolean isDiskonMembership) {
        this.isDiskonMembership = isDiskonMembership;
    }

    public String getPresentaseDiskonMembership() {
        return presentaseDiskonMembership;
    }

    public void setPresentaseDiskonMembership(String presentaseDiskonMembership) {
        this.presentaseDiskonMembership = presentaseDiskonMembership;
    }

    public String getTotalDiskonMembership() {
        return totalDiskonMembership;
    }

    public void setTotalDiskonMembership(String totalDiskonMembership) {
        this.totalDiskonMembership = totalDiskonMembership;
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
        dest.writeValue(diskon);
        dest.writeValue(qtyDiskon);
        dest.writeValue(typeDiskon);
        dest.writeValue(kodePromo);
        dest.writeValue(isDiskonMembership);
        dest.writeValue(presentaseDiskonMembership);
        dest.writeValue(totalDiskonMembership);
    }

    public int describeContents() {
        return  0;
    }

}
