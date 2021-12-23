
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AddItem implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("produk")
    @Expose
    private Integer produk;
    @SerializedName("qty")
    @Expose
    private double qty;
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
    private Integer indentValuePos;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("so_code")
    @Expose
    private String soCode;
    @SerializedName("diskon")
    @Expose
    private String diskon;
    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_sebelum_diskon")
    @Expose
    private String totalSebelumDiskon;
    @SerializedName("total_diskon")
    @Expose
    private String totalDiskon;
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
    private final static long serialVersionUID = -21352132529101564L;

    protected AddItem(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.produk = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qty = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.diskonPotongan = ((String) in.readValue((String.class.getClassLoader())));
        this.diskonPersen = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.indentPos = ((String) in.readValue((String.class.getClassLoader())));
        this.indentValuePos = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
        this.diskon = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSebelumDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskon = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AddItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public Integer getProduk() {
        return produk;
    }

    public void setProduk(Integer produk) {
        this.produk = produk;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(String customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getDiskonPotongan() {
        return diskonPotongan;
    }

    public void setDiskonPotongan(String diskonPotongan) {
        this.diskonPotongan = diskonPotongan;
    }

    public String getDiskonPersen() {
        return diskonPersen;
    }

    public void setDiskonPersen(String diskonPersen) {
        this.diskonPersen = diskonPersen;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIndentPos() {
        return indentPos;
    }

    public void setIndentPos(String indentPos) {
        this.indentPos = indentPos;
    }

    public Integer getIndentValuePos() {
        return indentValuePos;
    }

    public void setIndentValuePos(Integer indentValuePos) {
        this.indentValuePos = indentValuePos;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getTotalSebelumDiskon() {
        return totalSebelumDiskon;
    }

    public void setTotalSebelumDiskon(String totalSebelumDiskon) {
        this.totalSebelumDiskon = totalSebelumDiskon;
    }

    public String getTotalDiskon() {
        return totalDiskon;
    }

    public void setTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
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
        dest.writeValue(soCode);
        dest.writeValue(diskon);
        dest.writeValue(codeProduct);
        dest.writeValue(name);
        dest.writeValue(totalSebelumDiskon);
        dest.writeValue(totalDiskon);
    }

    public int describeContents() {
        return  0;
    }

}
