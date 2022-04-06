
package com.dbelgamembership.membersip.Model.modelListTransaksi;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DetailKekurangan implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("so_code")
    @Expose
    private String soCode;
    @SerializedName("alasan_produk")
    @Expose
    private String alasanProduk;
    @SerializedName("harga_produk")
    @Expose
    private String hargaProduk;
    @SerializedName("qty_produk")
    @Expose
    private String qtyProduk;
    @SerializedName("code_produk")
    @Expose
    private String codeProduk;
    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    public final static Creator<DetailKekurangan> CREATOR = new Creator<DetailKekurangan>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailKekurangan createFromParcel(android.os.Parcel in) {
            return new DetailKekurangan(in);
        }

        public DetailKekurangan[] newArray(int size) {
            return (new DetailKekurangan[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3638069063371930652L;

    protected DetailKekurangan(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.soCode = ((String) in.readValue((String.class.getClassLoader())));
        this.alasanProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.codeProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.namaProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DetailKekurangan() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getAlasanProduk() {
        return alasanProduk;
    }

    public void setAlasanProduk(String alasanProduk) {
        this.alasanProduk = alasanProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getQtyProduk() {
        return qtyProduk;
    }

    public void setQtyProduk(String qtyProduk) {
        this.qtyProduk = qtyProduk;
    }

    public String getCodeProduk() {
        return codeProduk;
    }

    public void setCodeProduk(String codeProduk) {
        this.codeProduk = codeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(soCode);
        dest.writeValue(alasanProduk);
        dest.writeValue(hargaProduk);
        dest.writeValue(qtyProduk);
        dest.writeValue(codeProduk);
        dest.writeValue(namaProduk);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}
