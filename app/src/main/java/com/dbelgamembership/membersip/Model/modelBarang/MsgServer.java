
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
    private int id;
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
    private int kategori;
    @SerializedName("sub_kategori")
    @Expose
    private int subKategori;
    @SerializedName("merek")
    @Expose
    private int merek;
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
    private int stokMinimal;
    @SerializedName("satuan_dipakai")
    @Expose
    private String satuanDipakai;
    @SerializedName("satuan_terkecil")
    @Expose
    private int satuanTerkecil;
    @SerializedName("nilai_konversi_satuan_terkecil")
    @Expose
    private int nilaiKonversiSatuanTerkecil;
    @SerializedName("satuan_terbesar")
    @Expose
    private int satuanTerbesar;
    @SerializedName("nilai_konversi_satuan_terbesar")
    @Expose
    private int nilaiKonversiSatuanTerbesar;
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
    private int satuanTengah;
    @SerializedName("nilai_konversi_satuan_tengah")
    @Expose
    private int nilaiKonversiSatuanTengah;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("nama_kategori")
    @Expose
    private String namaKategori;
    @SerializedName("merek_produk")
    @Expose
    private String merekProduk;
    @SerializedName("stok")
    @Expose
    private int stok;
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
    private final static long serialVersionUID = 6677352058098636840L;

    protected MsgServer(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.kategori = ((int) in.readValue((int.class.getClassLoader())));
        this.subKategori = ((int) in.readValue((int.class.getClassLoader())));
        this.merek = ((int) in.readValue((int.class.getClassLoader())));
        this.lebar = ((String) in.readValue((String.class.getClassLoader())));
        this.panjang = ((String) in.readValue((String.class.getClassLoader())));
        this.tinggi = ((String) in.readValue((String.class.getClassLoader())));
        this.diameter = ((String) in.readValue((String.class.getClassLoader())));
        this.berat = ((String) in.readValue((String.class.getClassLoader())));
        this.stokMinimal = ((int) in.readValue((int.class.getClassLoader())));
        this.satuanDipakai = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanTerkecil = ((int) in.readValue((int.class.getClassLoader())));
        this.nilaiKonversiSatuanTerkecil = ((int) in.readValue((int.class.getClassLoader())));
        this.satuanTerbesar = ((int) in.readValue((int.class.getClassLoader())));
        this.nilaiKonversiSatuanTerbesar = ((int) in.readValue((int.class.getClassLoader())));
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
        this.satuanTengah = ((int) in.readValue((int.class.getClassLoader())));
        this.nilaiKonversiSatuanTengah = ((int) in.readValue((int.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.namaKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.merekProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.stok = ((int) in.readValue((int.class.getClassLoader())));
        this.price = ((Price) in.readValue((Price.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MsgServer withId(int id) {
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

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }

    public MsgServer withKategori(int kategori) {
        this.kategori = kategori;
        return this;
    }

    public int getSubKategori() {
        return subKategori;
    }

    public void setSubKategori(int subKategori) {
        this.subKategori = subKategori;
    }

    public MsgServer withSubKategori(int subKategori) {
        this.subKategori = subKategori;
        return this;
    }

    public int getMerek() {
        return merek;
    }

    public void setMerek(int merek) {
        this.merek = merek;
    }

    public MsgServer withMerek(int merek) {
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

    public int getStokMinimal() {
        return stokMinimal;
    }

    public void setStokMinimal(int stokMinimal) {
        this.stokMinimal = stokMinimal;
    }

    public MsgServer withStokMinimal(int stokMinimal) {
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

    public int getSatuanTerkecil() {
        return satuanTerkecil;
    }

    public void setSatuanTerkecil(int satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
    }

    public MsgServer withSatuanTerkecil(int satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
        return this;
    }

    public int getNilaiKonversiSatuanTerkecil() {
        return nilaiKonversiSatuanTerkecil;
    }

    public void setNilaiKonversiSatuanTerkecil(int nilaiKonversiSatuanTerkecil) {
        this.nilaiKonversiSatuanTerkecil = nilaiKonversiSatuanTerkecil;
    }

    public MsgServer withNilaiKonversiSatuanTerkecil(int nilaiKonversiSatuanTerkecil) {
        this.nilaiKonversiSatuanTerkecil = nilaiKonversiSatuanTerkecil;
        return this;
    }

    public int getSatuanTerbesar() {
        return satuanTerbesar;
    }

    public void setSatuanTerbesar(int satuanTerbesar) {
        this.satuanTerbesar = satuanTerbesar;
    }

    public MsgServer withSatuanTerbesar(int satuanTerbesar) {
        this.satuanTerbesar = satuanTerbesar;
        return this;
    }

    public int getNilaiKonversiSatuanTerbesar() {
        return nilaiKonversiSatuanTerbesar;
    }

    public void setNilaiKonversiSatuanTerbesar(int nilaiKonversiSatuanTerbesar) {
        this.nilaiKonversiSatuanTerbesar = nilaiKonversiSatuanTerbesar;
    }

    public MsgServer withNilaiKonversiSatuanTerbesar(int nilaiKonversiSatuanTerbesar) {
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

    public int getSatuanTengah() {
        return satuanTengah;
    }

    public void setSatuanTengah(int satuanTengah) {
        this.satuanTengah = satuanTengah;
    }

    public MsgServer withSatuanTengah(int satuanTengah) {
        this.satuanTengah = satuanTengah;
        return this;
    }

    public int getNilaiKonversiSatuanTengah() {
        return nilaiKonversiSatuanTengah;
    }

    public void setNilaiKonversiSatuanTengah(int nilaiKonversiSatuanTengah) {
        this.nilaiKonversiSatuanTengah = nilaiKonversiSatuanTengah;
    }

    public MsgServer withNilaiKonversiSatuanTengah(int nilaiKonversiSatuanTengah) {
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

    public String getMerekProduk() {
        return merekProduk;
    }

    public void setMerekProduk(String merekProduk) {
        this.merekProduk = merekProduk;
    }

    public MsgServer withMerekProduk(String merekProduk) {
        this.merekProduk = merekProduk;
        return this;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public MsgServer withStok(int stok) {
        this.stok = stok;
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
        dest.writeValue(merekProduk);
        dest.writeValue(stok);
        dest.writeValue(price);
    }

    public int describeContents() {
        return  0;
    }

}
