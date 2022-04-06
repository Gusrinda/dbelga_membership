
package com.dbelgamembership.membersip.Model.ModelBarangTerlaris;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Datum implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("sub_kategori")
    @Expose
    private Integer subKategori;
    @SerializedName("merek")
    @Expose
    private Integer merek;
    @SerializedName("kategori")
    @Expose
    private Integer kategori;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("status_product")
    @Expose
    private String statusProduct;
    @SerializedName("satuan_terkecil")
    @Expose
    private Integer satuanTerkecil;
    @SerializedName("berat")
    @Expose
    private String berat;
    @SerializedName("terjual")
    @Expose
    private String terjual;
    @SerializedName("nama_kategori")
    @Expose
    private String namaKategori;
    @SerializedName("nama_sub_kategori")
    @Expose
    private String namaSubKategori;
    @SerializedName("satuan_kemasan")
    @Expose
    private String satuanKemasan;


    @SerializedName("stok")
    @Expose
    private Integer stok;

    @SerializedName("price")
    @Expose
    private Price price;
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
    private final static long serialVersionUID = 5917622651982982597L;

    protected Datum(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.barcode = ((String) in.readValue((String.class.getClassLoader())));
        this.subKategori = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.merek = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.kategori = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.statusProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanTerkecil = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.berat = ((String) in.readValue((String.class.getClassLoader())));
        this.terjual = ((String) in.readValue((String.class.getClassLoader())));
        this.namaKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.namaSubKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.satuanKemasan = ((String) in.readValue((String.class.getClassLoader())));
        this.stok = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.price = ((Price) in.readValue((Price.class.getClassLoader())));
    }

    public Datum() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public Integer getKategori() {
        return kategori;
    }

    public void setKategori(Integer kategori) {
        this.kategori = kategori;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

    public Integer getSatuanTerkecil() {
        return satuanTerkecil;
    }

    public void setSatuanTerkecil(Integer satuanTerkecil) {
        this.satuanTerkecil = satuanTerkecil;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getTerjual() {
        return terjual;
    }

    public void setTerjual(String terjual) {
        this.terjual = terjual;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getNamaSubKategori() {
        return namaSubKategori;
    }

    public void setNamaSubKategori(String namaSubKategori) {
        this.namaSubKategori = namaSubKategori;
    }

    public String getSatuanKemasan() {
        return satuanKemasan;
    }

    public void setSatuanKemasan(String satuanKemasan) {
        this.satuanKemasan = satuanKemasan;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(barcode);
        dest.writeValue(subKategori);
        dest.writeValue(merek);
        dest.writeValue(kategori);
        dest.writeValue(images);
        dest.writeValue(statusProduct);
        dest.writeValue(satuanTerkecil);
        dest.writeValue(berat);
        dest.writeValue(terjual);
        dest.writeValue(namaKategori);
        dest.writeValue(namaSubKategori);
        dest.writeValue(satuanKemasan);
        dest.writeValue(stok);
        dest.writeValue(price);
    }

    public int describeContents() {
        return  0;
    }

}
