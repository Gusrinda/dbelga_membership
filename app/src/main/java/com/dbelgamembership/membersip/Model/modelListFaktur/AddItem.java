
package com.dbelgamembership.membersip.Model.modelListFaktur;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddItem implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("produk")
    @Expose
    private int produk;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("customer_price")
    @Expose
    private String customerPrice;
    @SerializedName("diskon_potongan")
    @Expose
    private String diskonPotongan;
    @SerializedName("diskon_persen")
    @Expose
    private String diskonPersen;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("indent_pos")
    @Expose
    private String indentPos;
    @SerializedName("indent_value_pos")
    @Expose
    private String indentValuePos;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<AddItem> CREATOR = new Creator<AddItem>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AddItem createFromParcel(android.os.Parcel in) {
            return new AddItem(in);
        }

        public AddItem[] newArray(int size) {
            return (new AddItem[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8730860444287216885L;

    protected AddItem(android.os.Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.produk = ((int) in.readValue((int.class.getClassLoader())));
        this.qty = ((int) in.readValue((int.class.getClassLoader())));
        this.customerPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.diskonPotongan = ((String) in.readValue((String.class.getClassLoader())));
        this.diskonPersen = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.indentPos = ((String) in.readValue((String.class.getClassLoader())));
        this.indentValuePos = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AddItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AddItem withId(int id) {
        this.id = id;
        return this;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public AddItem withPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
        return this;
    }

    public int getProduk() {
        return produk;
    }

    public void setProduk(int produk) {
        this.produk = produk;
    }

    public AddItem withProduk(int produk) {
        this.produk = produk;
        return this;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public AddItem withQty(int qty) {
        this.qty = qty;
        return this;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(String customerPrice) {
        this.customerPrice = customerPrice;
    }

    public AddItem withCustomerPrice(String customerPrice) {
        this.customerPrice = customerPrice;
        return this;
    }

    public String getDiskonPotongan() {
        return diskonPotongan;
    }

    public void setDiskonPotongan(String diskonPotongan) {
        this.diskonPotongan = diskonPotongan;
    }

    public AddItem withDiskonPotongan(String diskonPotongan) {
        this.diskonPotongan = diskonPotongan;
        return this;
    }

    public String getDiskonPersen() {
        return diskonPersen;
    }

    public void setDiskonPersen(String diskonPersen) {
        this.diskonPersen = diskonPersen;
    }

    public AddItem withDiskonPersen(String diskonPersen) {
        this.diskonPersen = diskonPersen;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public AddItem withTotal(String total) {
        this.total = total;
        return this;
    }

    public String getIndentPos() {
        return indentPos;
    }

    public void setIndentPos(String indentPos) {
        this.indentPos = indentPos;
    }

    public AddItem withIndentPos(String indentPos) {
        this.indentPos = indentPos;
        return this;
    }

    public String getIndentValuePos() {
        return indentValuePos;
    }

    public void setIndentValuePos(String indentValuePos) {
        this.indentValuePos = indentValuePos;
    }

    public AddItem withIndentValuePos(String indentValuePos) {
        this.indentValuePos = indentValuePos;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public AddItem withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AddItem withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public AddItem withCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddItem withName(String name) {
        this.name = name;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(pembayaranCode);
        dest.writeValue(produk);
        dest.writeValue(qty);
        dest.writeValue(customerPrice);
        dest.writeValue(diskonPotongan);
        dest.writeValue(diskonPersen);
        dest.writeValue(total);
        dest.writeValue(indentPos);
        dest.writeValue(indentValuePos);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(codeProduct);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
