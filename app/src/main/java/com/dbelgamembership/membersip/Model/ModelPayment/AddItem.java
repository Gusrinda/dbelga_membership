
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
    private String qty;
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
    private Object indentPos;
    @SerializedName("indent_value_pos")
    @Expose
    private Integer indentValuePos;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("so_code")
    @Expose
    private String soCode;
    @SerializedName("diskon")
    @Expose
    private String diskon;
    @SerializedName("total_setelah_diskon")
    @Expose
    private String totalSetelahDiskon;
    @SerializedName("total_diskon")
    @Expose
    private String totalDiskon;
    @SerializedName("real_price")
    @Expose
    private String realPrice;
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
    @SerializedName("code_product")
    @Expose
    private String codeProduct;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_sebelum_diskon")
    @Expose
    private String totalSebelumDiskon;
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

    };

    private final static long serialVersionUID = 3648740688613509579L;

    protected AddItem(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.produk = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qty = ((String) in.readValue((String.class.getClassLoader())));
        this.customerPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.diskonPotongan = ((String) in.readValue((String.class.getClassLoader())));
        this.diskonPersen = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.indentPos = ((Object) in.readValue((Object.class.getClassLoader())));
        this.indentValuePos = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
        this.diskon = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSetelahDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.realPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.typeDiskon = ((String) in.readValue((String.class.getClassLoader())));
        this.kodePromo = ((String) in.readValue((String.class.getClassLoader())));
        this.isDiskonMembership = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.presentaseDiskonMembership = ((String) in.readValue((String.class.getClassLoader())));
        this.totalDiskonMembership = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSebelumDiskon = ((String) in.readValue((String.class.getClassLoader())));
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
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

    public Object getIndentPos() {
        return indentPos;
    }

    public void setIndentPos(Object indentPos) {
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

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
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

    public String getTotalSetelahDiskon() {
        return totalSetelahDiskon;
    }

    public void setTotalSetelahDiskon(String totalSetelahDiskon) {
        this.totalSetelahDiskon = totalSetelahDiskon;
    }

    public String getTotalDiskon() {
        return totalDiskon;
    }

    public void setTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
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
        dest.writeValue(totalSetelahDiskon);
        dest.writeValue(totalDiskon);
        dest.writeValue(realPrice);
        dest.writeValue(qtyDiskon);
        dest.writeValue(typeDiskon);
        dest.writeValue(kodePromo);
        dest.writeValue(isDiskonMembership);
        dest.writeValue(presentaseDiskonMembership);
        dest.writeValue(totalDiskonMembership);
        dest.writeValue(codeProduct);
        dest.writeValue(name);
        dest.writeValue(totalSebelumDiskon);
    }

    public int describeContents() {
        return  0;
    }

}
