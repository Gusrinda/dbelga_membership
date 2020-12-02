
package com.dbelgamembership.membersip.Model.modelBarang;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("kategori")
    @Expose
    private Integer kategori;
    @SerializedName("sub_kategori")
    @Expose
    private Integer subKategori;
    @SerializedName("merek")
    @Expose
    private Integer merek;
    @SerializedName("lebar")
    @Expose
    private String lebar;
    @SerializedName("panjang")
    @Expose
    private String panjang;
    @SerializedName("tinggi")
    @Expose
    private String tinggi;
    @SerializedName("diameter")
    @Expose
    private String diameter;
    @SerializedName("berat")
    @Expose
    private String berat;
    @SerializedName("stok_minimal")
    @Expose
    private Integer stokMinimal;
    @SerializedName("satuan_dipakai")
    @Expose
    private String satuanDipakai;
    @SerializedName("satuan_terkecil")
    @Expose
    private Integer satuanTerkecil;
    @SerializedName("nilai_konversi_satuan_terkecil")
    @Expose
    private Integer nilaiKonversiSatuanTerkecil;
    @SerializedName("satuan_terbesar")
    @Expose
    private Integer satuanTerbesar;
    @SerializedName("nilai_konversi_satuan_terbesar")
    @Expose
    private Integer nilaiKonversiSatuanTerbesar;
    @SerializedName("type_asset")
    @Expose
    private String typeAsset;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("name_short")
    @Expose
    private String nameShort;
    @SerializedName("type_product")
    @Expose
    private String typeProduct;
    @SerializedName("varian_product")
    @Expose
    private String varianProduct;
    @SerializedName("status_product")
    @Expose
    private String statusProduct;
    @SerializedName("satuan_tengah")
    @Expose
    private Integer satuanTengah;
    @SerializedName("nilai_konversi_satuan_tengah")
    @Expose
    private Integer nilaiKonversiSatuanTengah;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("nama_kategori")
    @Expose
    private String namaKategori;
    @SerializedName("price")
    @Expose
    private Price price;
    public final static Parcelable.Creator<MsgServer> CREATOR = new Creator<MsgServer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsgServer createFromParcel(Parcel in) {
            return new MsgServer(in);
        }

        public MsgServer[] newArray(int size) {
            return (new MsgServer[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3165648970883061238L;

    protected MsgServer(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.kategori = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.subKategori = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.merek = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.lebar = ((String) in.readValue((String.class.getClassLoader())));
        this.panjang = ((String) in.readValue((String.class.getClassLoader())));
        this.tinggi = ((String) in.readValue((String.class.getClassLoader())));
        this.diameter = ((String) in.readValue((String.class.getClassLoader())));
        this.berat = ((String) in.readValue((String.class.getClassLoader())));
        this.stokMinimal = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.satuanDipakai = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanTerkecil = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.nilaiKonversiSatuanTerkecil = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.satuanTerbesar = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.nilaiKonversiSatuanTerbesar = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.typeAsset = ((String) in.readValue((String.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
        this.barcode = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.deletedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.nameShort = ((String) in.readValue((String.class.getClassLoader())));
        this.typeProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.varianProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.statusProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanTengah = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.nilaiKonversiSatuanTengah = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.namaKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Price) in.readValue((Price.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MsgServer withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public MsgServer withCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MsgServer withCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MsgServer withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getKategori() {
        return kategori;
    }

    public void setKategori(Integer kategori) {
        this.kategori = kategori;
    }

    public MsgServer withKategori(Integer kategori) {
        this.kategori = kategori;
        return this;
    }

    public Integer getSubKategori() {
        return subKategori;
    }

    public void setSubKategori(Integer subKategori) {
        this.subKategori = subKategori;
    }

    public MsgServer withSubKategori(Integer subKategori) {
        this.subKategori = subKategori;
        return this;
    }

    public Integer getMerek() {
        return merek;
    }

    public void setMerek(Integer merek) {
        this.merek = merek;
    }

    public MsgServer withMerek(Integer merek) {
        this.merek = merek;
        return this;
    }

    public String getLebar() {
        return lebar;
    }

    public void setLebar(String lebar) {
        this.lebar = lebar;
    }

    public MsgServer withLebar(String lebar) {
        this.lebar = lebar;
        return this;
    }

    public String getPanjang() {
        return panjang;
    }

    public void setPanjang(String panjang) {
        this.panjang = panjang;
    }

    public MsgServer withPanjang(String panjang) {
        this.panjang = panjang;
        return this;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public MsgServer withTinggi(String tinggi) {
        this.tinggi = tinggi;
        return this;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public MsgServer withDiameter(String diameter) {
        this.diameter = diameter;
        return this;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public MsgServer withBerat(String berat) {
        this.berat = berat;
        return this;
    }

    public Integer getStokMinimal() {
        return stokMinimal;
    }

    public void setStokMinimal(Integer stokMinimal) {
        this.stokMinimal = stokMinimal;
    }

    public MsgServer withStokMinimal(Integer stokMinimal) {
        this.stokMinimal = stokMinimal;
        return this;
    }

    public String getSatuanDipakai() {
        return satuanDipakai;
    }

    public void setSatuanDipakai(String satuanDipakai) {
        this.satuanDipakai = satuanDipakai;
    }

    public MsgServer withSatuanDipakai(String satuanDipakai) {
        this.satuanDipakai = satuanDipakai;
        return this;
    }

    public Integer getSatuanTerkecil() {
        return satuanTerkecil;
    }

    public void setSatuanTerkecil(Integer satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
    }

    public MsgServer withSatuanTerkecil(Integer satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
        return this;
    }

    public Integer getNilaiKonversiSatuanTerkecil() {
        return nilaiKonversiSatuanTerkecil;
    }

    public void setNilaiKonversiSatuanTerkecil(Integer nilaiKonversiSatuanTerkecil) {
        this.nilaiKonversiSatuanTerkecil = nilaiKonversiSatuanTerkecil;
    }

    public MsgServer withNilaiKonversiSatuanTerkecil(Integer nilaiKonversiSatuanTerkecil) {
        this.nilaiKonversiSatuanTerkecil = nilaiKonversiSatuanTerkecil;
        return this;
    }

    public Integer getSatuanTerbesar() {
        return satuanTerbesar;
    }

    public void setSatuanTerbesar(Integer satuanTerbesar) {
        this.satuanTerbesar = satuanTerbesar;
    }

    public MsgServer withSatuanTerbesar(Integer satuanTerbesar) {
        this.satuanTerbesar = satuanTerbesar;
        return this;
    }

    public Integer getNilaiKonversiSatuanTerbesar() {
        return nilaiKonversiSatuanTerbesar;
    }

    public void setNilaiKonversiSatuanTerbesar(Integer nilaiKonversiSatuanTerbesar) {
        this.nilaiKonversiSatuanTerbesar = nilaiKonversiSatuanTerbesar;
    }

    public MsgServer withNilaiKonversiSatuanTerbesar(Integer nilaiKonversiSatuanTerbesar) {
        this.nilaiKonversiSatuanTerbesar = nilaiKonversiSatuanTerbesar;
        return this;
    }

    public String getTypeAsset() {
        return typeAsset;
    }

    public void setTypeAsset(String typeAsset) {
        this.typeAsset = typeAsset;
    }

    public MsgServer withTypeAsset(String typeAsset) {
        this.typeAsset = typeAsset;
        return this;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public MsgServer withDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public MsgServer withBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public MsgServer withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MsgServer withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public MsgServer withDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public MsgServer withNameShort(String nameShort) {
        this.nameShort = nameShort;
        return this;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public MsgServer withTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
        return this;
    }

    public String getVarianProduct() {
        return varianProduct;
    }

    public void setVarianProduct(String varianProduct) {
        this.varianProduct = varianProduct;
    }

    public MsgServer withVarianProduct(String varianProduct) {
        this.varianProduct = varianProduct;
        return this;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

    public MsgServer withStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
        return this;
    }

    public Integer getSatuanTengah() {
        return satuanTengah;
    }

    public void setSatuanTengah(Integer satuanTengah) {
        this.satuanTengah = satuanTengah;
    }

    public MsgServer withSatuanTengah(Integer satuanTengah) {
        this.satuanTengah = satuanTengah;
        return this;
    }

    public Integer getNilaiKonversiSatuanTengah() {
        return nilaiKonversiSatuanTengah;
    }

    public void setNilaiKonversiSatuanTengah(Integer nilaiKonversiSatuanTengah) {
        this.nilaiKonversiSatuanTengah = nilaiKonversiSatuanTengah;
    }

    public MsgServer withNilaiKonversiSatuanTengah(Integer nilaiKonversiSatuanTengah) {
        this.nilaiKonversiSatuanTengah = nilaiKonversiSatuanTengah;
        return this;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public MsgServer withImages(String images) {
        this.images = images;
        return this;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public MsgServer withNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
        return this;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public MsgServer withPrice(Price price) {
        this.price = price;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(code);
        dest.writeValue(name);
        dest.writeValue(kategori);
        dest.writeValue(subKategori);
        dest.writeValue(merek);
        dest.writeValue(lebar);
        dest.writeValue(panjang);
        dest.writeValue(tinggi);
        dest.writeValue(diameter);
        dest.writeValue(berat);
        dest.writeValue(stokMinimal);
        dest.writeValue(satuanDipakai);
        dest.writeValue(satuanTerkecil);
        dest.writeValue(nilaiKonversiSatuanTerkecil);
        dest.writeValue(satuanTerbesar);
        dest.writeValue(nilaiKonversiSatuanTerbesar);
        dest.writeValue(typeAsset);
        dest.writeValue(deskripsi);
        dest.writeValue(barcode);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(deletedAt);
        dest.writeValue(nameShort);
        dest.writeValue(typeProduct);
        dest.writeValue(varianProduct);
        dest.writeValue(statusProduct);
        dest.writeValue(satuanTengah);
        dest.writeValue(nilaiKonversiSatuanTengah);
        dest.writeValue(images);
        dest.writeValue(namaKategori);
        dest.writeValue(price);
    }

    public int describeContents() {
        return  0;
    }

}
