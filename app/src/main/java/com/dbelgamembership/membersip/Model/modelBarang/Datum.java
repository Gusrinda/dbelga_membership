
package com.dbelgamembership.membersip.Model.modelBarang;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum implements Serializable, Parcelable
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
    private String nilaiKonversiSatuanTerkecil;
    @SerializedName("satuan_terbesar")
    @Expose
    private String satuanTerbesar;
    @SerializedName("nilai_konversi_satuan_terbesar")
    @Expose
    private String nilaiKonversiSatuanTerbesar;
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
    private String satuanTengah;
    @SerializedName("nilai_konversi_satuan_tengah")
    @Expose
    private String nilaiKonversiSatuanTengah;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("tes_id")
    @Expose
    private String tesId;
    @SerializedName("nama_kategori")
    @Expose
    private String namaKategori;
    @SerializedName("merek_produk")
    @Expose
    private String merekProduk;
    @SerializedName("satuan_kemasan")
    @Expose
    private String satuanKemasan;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_dua")
    @Expose
    private String priceDua;
    @SerializedName("price_tiga")
    @Expose
    private String priceTiga;
    @SerializedName("stok")
    @Expose
    private int stok;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(android.os.Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1114864191204468438L;

    protected Datum(android.os.Parcel in) {
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
        this.nilaiKonversiSatuanTerkecil = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanTerbesar = ((String) in.readValue((String.class.getClassLoader())));
        this.nilaiKonversiSatuanTerbesar = ((String) in.readValue((String.class.getClassLoader())));
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
        this.satuanTengah = ((String) in.readValue((String.class.getClassLoader())));
        this.nilaiKonversiSatuanTengah = ((String) in.readValue((String.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.tesId = ((String) in.readValue((String.class.getClassLoader())));
        this.namaKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.merekProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanKemasan = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.priceDua = ((String) in.readValue((String.class.getClassLoader())));
        this.priceTiga = ((String) in.readValue((String.class.getClassLoader())));
        this.stok = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Datum() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Datum withId(int id) {
        this.id = id;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Datum withCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Datum withCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Datum withName(String name) {
        this.name = name;
        return this;
    }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }

    public Datum withKategori(int kategori) {
        this.kategori = kategori;
        return this;
    }

    public int getSubKategori() {
        return subKategori;
    }

    public void setSubKategori(int subKategori) {
        this.subKategori = subKategori;
    }

    public Datum withSubKategori(int subKategori) {
        this.subKategori = subKategori;
        return this;
    }

    public int getMerek() {
        return merek;
    }

    public void setMerek(int merek) {
        this.merek = merek;
    }

    public Datum withMerek(int merek) {
        this.merek = merek;
        return this;
    }

    public String getLebar() {
        return lebar;
    }

    public void setLebar(String lebar) {
        this.lebar = lebar;
    }

    public Datum withLebar(String lebar) {
        this.lebar = lebar;
        return this;
    }

    public String getPanjang() {
        return panjang;
    }

    public void setPanjang(String panjang) {
        this.panjang = panjang;
    }

    public Datum withPanjang(String panjang) {
        this.panjang = panjang;
        return this;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public Datum withTinggi(String tinggi) {
        this.tinggi = tinggi;
        return this;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public Datum withDiameter(String diameter) {
        this.diameter = diameter;
        return this;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public Datum withBerat(String berat) {
        this.berat = berat;
        return this;
    }

    public int getStokMinimal() {
        return stokMinimal;
    }

    public void setStokMinimal(int stokMinimal) {
        this.stokMinimal = stokMinimal;
    }

    public Datum withStokMinimal(int stokMinimal) {
        this.stokMinimal = stokMinimal;
        return this;
    }

    public String getSatuanDipakai() {
        return satuanDipakai;
    }

    public void setSatuanDipakai(String satuanDipakai) {
        this.satuanDipakai = satuanDipakai;
    }

    public Datum withSatuanDipakai(String satuanDipakai) {
        this.satuanDipakai = satuanDipakai;
        return this;
    }

    public int getSatuanTerkecil() {
        return satuanTerkecil;
    }

    public void setSatuanTerkecil(int satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
    }

    public Datum withSatuanTerkecil(int satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
        return this;
    }

    public String getNilaiKonversiSatuanTerkecil() {
        return nilaiKonversiSatuanTerkecil;
    }

    public void setNilaiKonversiSatuanTerkecil(String nilaiKonversiSatuanTerkecil) {
        this.nilaiKonversiSatuanTerkecil = nilaiKonversiSatuanTerkecil;
    }

    public Datum withNilaiKonversiSatuanTerkecil(String nilaiKonversiSatuanTerkecil) {
        this.nilaiKonversiSatuanTerkecil = nilaiKonversiSatuanTerkecil;
        return this;
    }

    public String getSatuanTerbesar() {
        return satuanTerbesar;
    }

    public void setSatuanTerbesar(String satuanTerbesar) {
        this.satuanTerbesar = satuanTerbesar;
    }

    public Datum withSatuanTerbesar(String satuanTerbesar) {
        this.satuanTerbesar = satuanTerbesar;
        return this;
    }

    public String getNilaiKonversiSatuanTerbesar() {
        return nilaiKonversiSatuanTerbesar;
    }

    public void setNilaiKonversiSatuanTerbesar(String nilaiKonversiSatuanTerbesar) {
        this.nilaiKonversiSatuanTerbesar = nilaiKonversiSatuanTerbesar;
    }

    public Datum withNilaiKonversiSatuanTerbesar(String nilaiKonversiSatuanTerbesar) {
        this.nilaiKonversiSatuanTerbesar = nilaiKonversiSatuanTerbesar;
        return this;
    }

    public String getTypeAsset() {
        return typeAsset;
    }

    public void setTypeAsset(String typeAsset) {
        this.typeAsset = typeAsset;
    }

    public Datum withTypeAsset(String typeAsset) {
        this.typeAsset = typeAsset;
        return this;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Datum withDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Datum withBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Datum withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Datum withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Datum withDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public Datum withNameShort(String nameShort) {
        this.nameShort = nameShort;
        return this;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public Datum withTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
        return this;
    }

    public String getVarianProduct() {
        return varianProduct;
    }

    public void setVarianProduct(String varianProduct) {
        this.varianProduct = varianProduct;
    }

    public Datum withVarianProduct(String varianProduct) {
        this.varianProduct = varianProduct;
        return this;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

    public Datum withStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
        return this;
    }

    public String getSatuanTengah() {
        return satuanTengah;
    }

    public void setSatuanTengah(String satuanTengah) {
        this.satuanTengah = satuanTengah;
    }

    public Datum withSatuanTengah(String satuanTengah) {
        this.satuanTengah = satuanTengah;
        return this;
    }

    public String getNilaiKonversiSatuanTengah() {
        return nilaiKonversiSatuanTengah;
    }

    public void setNilaiKonversiSatuanTengah(String nilaiKonversiSatuanTengah) {
        this.nilaiKonversiSatuanTengah = nilaiKonversiSatuanTengah;
    }

    public Datum withNilaiKonversiSatuanTengah(String nilaiKonversiSatuanTengah) {
        this.nilaiKonversiSatuanTengah = nilaiKonversiSatuanTengah;
        return this;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Datum withImages(String images) {
        this.images = images;
        return this;
    }

    public String getTesId() {
        return tesId;
    }

    public void setTesId(String tesId) {
        this.tesId = tesId;
    }

    public Datum withTesId(String tesId) {
        this.tesId = tesId;
        return this;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public Datum withNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
        return this;
    }

    public String getMerekProduk() {
        return merekProduk;
    }

    public void setMerekProduk(String merekProduk) {
        this.merekProduk = merekProduk;
    }

    public Datum withMerekProduk(String merekProduk) {
        this.merekProduk = merekProduk;
        return this;
    }

    public String getSatuanKemasan() {
        return satuanKemasan;
    }

    public void setSatuanKemasan(String satuanKemasan) {
        this.satuanKemasan = satuanKemasan;
    }

    public Datum withSatuanKemasan(String satuanKemasan) {
        this.satuanKemasan = satuanKemasan;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Datum withPrice(String price) {
        this.price = price;
        return this;
    }

    public String getPriceDua() {
        return priceDua;
    }

    public void setPriceDua(String priceDua) {
        this.priceDua = priceDua;
    }

    public Datum withPriceDua(String priceDua) {
        this.priceDua = priceDua;
        return this;
    }

    public String getPriceTiga() {
        return priceTiga;
    }

    public void setPriceTiga(String priceTiga) {
        this.priceTiga = priceTiga;
    }

    public Datum withPriceTiga(String priceTiga) {
        this.priceTiga = priceTiga;
        return this;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public Datum withStok(int stok) {
        this.stok = stok;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
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
        dest.writeValue(tesId);
        dest.writeValue(namaKategori);
        dest.writeValue(merekProduk);
        dest.writeValue(satuanKemasan);
        dest.writeValue(price);
        dest.writeValue(priceDua);
        dest.writeValue(priceTiga);
        dest.writeValue(stok);
    }

    public int describeContents() {
        return  0;
    }

}
