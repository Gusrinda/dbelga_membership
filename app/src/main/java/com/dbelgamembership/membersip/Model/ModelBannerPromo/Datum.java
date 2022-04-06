
package com.dbelgamembership.membersip.Model.ModelBannerPromo;

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
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;
    @SerializedName("tipe")
    @Expose
    private String tipe;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("gudang")
    @Expose
    private Integer gudang;
    @SerializedName("user_input")
    @Expose
    private Integer userInput;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("supplier")
    @Expose
    private Integer supplier;
    @SerializedName("banner_promo")
    @Expose
    private String bannerPromo;
    @SerializedName("image")
    @Expose
    private String image;
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
    private final static long serialVersionUID = -3520071874694569783L;

    protected Datum(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.promoCode = ((String) in.readValue((String.class.getClassLoader())));
        this.dateStart = ((String) in.readValue((String.class.getClassLoader())));
        this.dateEnd = ((String) in.readValue((String.class.getClassLoader())));
        this.tipe = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.gudang = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userInput = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.keterangan = ((String) in.readValue((String.class.getClassLoader())));
        this.supplier = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bannerPromo = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param companyCode
     * @param bannerPromo
     * @param image
     * @param keterangan
     * @param gudang
     * @param dateEnd
     * @param createdAt
     * @param dateStart
     * @param supplier
     * @param promoCode
     * @param id
     * @param userInput
     * @param tipe
     * @param status
     * @param updatedAt
     */
    public Datum(Integer id, String companyCode, String promoCode, String dateStart, String dateEnd, String tipe, String status, String createdAt, String updatedAt, Integer gudang, Integer userInput, String keterangan, Integer supplier, String bannerPromo, String image) {
        super();
        this.id = id;
        this.companyCode = companyCode;
        this.promoCode = promoCode;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.tipe = tipe;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.gudang = gudang;
        this.userInput = userInput;
        this.keterangan = keterangan;
        this.supplier = supplier;
        this.bannerPromo = bannerPromo;
        this.image = image;
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

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getGudang() {
        return gudang;
    }

    public void setGudang(Integer gudang) {
        this.gudang = gudang;
    }

    public Integer getUserInput() {
        return userInput;
    }

    public void setUserInput(Integer userInput) {
        this.userInput = userInput;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public String getBannerPromo() {
        return bannerPromo;
    }

    public void setBannerPromo(String bannerPromo) {
        this.bannerPromo = bannerPromo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(promoCode);
        dest.writeValue(dateStart);
        dest.writeValue(dateEnd);
        dest.writeValue(tipe);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(gudang);
        dest.writeValue(userInput);
        dest.writeValue(keterangan);
        dest.writeValue(supplier);
        dest.writeValue(bannerPromo);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}
