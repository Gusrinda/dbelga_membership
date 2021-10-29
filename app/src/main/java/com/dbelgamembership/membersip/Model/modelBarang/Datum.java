
package com.dbelgamembership.membersip.Model.modelBarang;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Datum implements Serializable, Parcelable
{

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
    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("qty_harga_1")
    @Expose
    private Integer qtyHarga1;
    @SerializedName("qty_harga_2")
    @Expose
    private Integer qtyHarga2;
    @SerializedName("qty_harga_3")
    @Expose
    private Integer qtyHarga3;
    @SerializedName("stok")
    @Expose
    private Integer stok;
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
    private final static long serialVersionUID = -38210502697244851L;

    protected Datum(android.os.Parcel in) {
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
        this.satuanTengah = ((String) in.readValue((String.class.getClassLoader())));
        this.nilaiKonversiSatuanTengah = ((String) in.readValue((String.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.tesId = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.namaKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.merekProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanKemasan = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.priceDua = ((String) in.readValue((String.class.getClassLoader())));
        this.priceTiga = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyHarga1 = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qtyHarga2 = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qtyHarga3 = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stok = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Datum() {
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKategori() {
        return kategori;
    }

    public void setKategori(Integer kategori) {
        this.kategori = kategori;
    }

    public Integer getSubKategori() {
        return subKategori;
    }

    public void setSubKategori(Integer subKategori) {
        this.subKategori = subKategori;
    }

    public Integer getMerek() {
        return merek;
    }

    public void setMerek(Integer merek) {
        this.merek = merek;
    }

    public String getLebar() {
        return lebar;
    }

    public void setLebar(String lebar) {
        this.lebar = lebar;
    }

    public String getPanjang() {
        return panjang;
    }

    public void setPanjang(String panjang) {
        this.panjang = panjang;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public Integer getStokMinimal() {
        return stokMinimal;
    }

    public void setStokMinimal(Integer stokMinimal) {
        this.stokMinimal = stokMinimal;
    }

    public String getSatuanDipakai() {
        return satuanDipakai;
    }

    public void setSatuanDipakai(String satuanDipakai) {
        this.satuanDipakai = satuanDipakai;
    }

    public Integer getSatuanTerkecil() {
        return satuanTerkecil;
    }

    public void setSatuanTerkecil(Integer satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
    }

    public Integer getNilaiKonversiSatuanTerkecil() {
        return nilaiKonversiSatuanTerkecil;
    }

    public void setNilaiKonversiSatuanTerkecil(Integer nilaiKonversiSatuanTerkecil) {
        this.nilaiKonversiSatuanTerkecil = nilaiKonversiSatuanTerkecil;
    }

    public Integer getSatuanTerbesar() {
        return satuanTerbesar;
    }

    public void setSatuanTerbesar(Integer satuanTerbesar) {
        this.satuanTerbesar = satuanTerbesar;
    }

    public Integer getNilaiKonversiSatuanTerbesar() {
        return nilaiKonversiSatuanTerbesar;
    }

    public void setNilaiKonversiSatuanTerbesar(Integer nilaiKonversiSatuanTerbesar) {
        this.nilaiKonversiSatuanTerbesar = nilaiKonversiSatuanTerbesar;
    }

    public String getTypeAsset() {
        return typeAsset;
    }

    public void setTypeAsset(String typeAsset) {
        this.typeAsset = typeAsset;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public String getVarianProduct() {
        return varianProduct;
    }

    public void setVarianProduct(String varianProduct) {
        this.varianProduct = varianProduct;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

    public String getSatuanTengah() {
        return satuanTengah;
    }

    public void setSatuanTengah(String satuanTengah) {
        this.satuanTengah = satuanTengah;
    }

    public String getNilaiKonversiSatuanTengah() {
        return nilaiKonversiSatuanTengah;
    }

    public void setNilaiKonversiSatuanTengah(String nilaiKonversiSatuanTengah) {
        this.nilaiKonversiSatuanTengah = nilaiKonversiSatuanTengah;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTesId() {
        return tesId;
    }

    public void setTesId(String tesId) {
        this.tesId = tesId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getMerekProduk() {
        return merekProduk;
    }

    public void setMerekProduk(String merekProduk) {
        this.merekProduk = merekProduk;
    }

    public String getSatuanKemasan() {
        return satuanKemasan;
    }

    public void setSatuanKemasan(String satuanKemasan) {
        this.satuanKemasan = satuanKemasan;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceDua() {
        return priceDua;
    }

    public void setPriceDua(String priceDua) {
        this.priceDua = priceDua;
    }

    public String getPriceTiga() {
        return priceTiga;
    }

    public void setPriceTiga(String priceTiga) {
        this.priceTiga = priceTiga;
    }

    public Integer getQtyHarga1() {
        return qtyHarga1;
    }

    public void setQtyHarga1(Integer qtyHarga1) {
        this.qtyHarga1 = qtyHarga1;
    }

    public Integer getQtyHarga2() {
        return qtyHarga2;
    }

    public void setQtyHarga2(Integer qtyHarga2) {
        this.qtyHarga2 = qtyHarga2;
    }

    public Integer getQtyHarga3() {
        return qtyHarga3;
    }

    public void setQtyHarga3(Integer qtyHarga3) {
        this.qtyHarga3 = qtyHarga3;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
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
        dest.writeValue(id);
        dest.writeValue(namaKategori);
        dest.writeValue(merekProduk);
        dest.writeValue(satuanKemasan);
        dest.writeValue(price);
        dest.writeValue(priceDua);
        dest.writeValue(priceTiga);
        dest.writeValue(qtyHarga1);
        dest.writeValue(qtyHarga2);
        dest.writeValue(qtyHarga3);
        dest.writeValue(stok);
    }

    public int describeContents() {
        return  0;
    }

}
