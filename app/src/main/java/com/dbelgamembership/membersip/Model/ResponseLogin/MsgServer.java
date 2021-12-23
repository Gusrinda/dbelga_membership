
package com.dbelgamembership.membersip.Model.ResponseLogin;

import java.io.Serializable;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
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
    @SerializedName("accurate_code")
    @Expose
    private String accurateCode;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("gh_code")
    @Expose
    private String ghCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("bentuk")
    @Expose
    private String bentuk;
    @SerializedName("main_address")
    @Expose
    private String mainAddress;
    @SerializedName("main_kelurahan")
    @Expose
    private String mainKelurahan;
    @SerializedName("main_geo_lat")
    @Expose
    private String mainGeoLat;
    @SerializedName("main_geo_lng")
    @Expose
    private String mainGeoLng;
    @SerializedName("main_email")
    @Expose
    private String mainEmail;
    @SerializedName("main_office_phone_1")
    @Expose
    private String mainOfficePhone1;
    @SerializedName("main_office_phone_2")
    @Expose
    private String mainOfficePhone2;
    @SerializedName("main_fax_1")
    @Expose
    private String mainFax1;
    @SerializedName("main_fax_2")
    @Expose
    private String mainFax2;
    @SerializedName("main_phone_1")
    @Expose
    private String mainPhone1;
    @SerializedName("main_phone_2")
    @Expose
    private String mainPhone2;
    @SerializedName("main_cp_name")
    @Expose
    private String mainCpName;
    @SerializedName("main_cp_title")
    @Expose
    private String mainCpTitle;
    @SerializedName("main_cp_jabatan")
    @Expose
    private String mainCpJabatan;
    @SerializedName("main_cp_birthdate")
    @Expose
    private String mainCpBirthdate;
    @SerializedName("saldo_piutang")
    @Expose
    private Integer saldoPiutang;
    @SerializedName("credit_limit")
    @Expose
    private String creditLimit;
    @SerializedName("credit_limit_remain")
    @Expose
    private String creditLimitRemain;
    @SerializedName("credit_limit_days")
    @Expose
    private String creditLimitDays;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("main_pos")
    @Expose
    private String mainPos;
    @SerializedName("price_variant")
    @Expose
    private String priceVariant;
    @SerializedName("gudang")
    @Expose
    private String gudang;
    @SerializedName("head_office")
    @Expose
    private String headOffice;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("wilayah_sales")
    @Expose
    private String wilayahSales;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("dpt_code")
    @Expose
    private String dptCode;
    @SerializedName("app_do")
    @Expose
    private Integer appDo;
    @SerializedName("app_sj")
    @Expose
    private Integer appSj;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("date_birth")
    @Expose
    private String dateBirth;
    @SerializedName("status_member")
    @Expose
    private String statusMember;
    @SerializedName("status_payment")
    @Expose
    private String statusPayment;
    @SerializedName("pay_date")
    @Expose
    private String payDate;
    @SerializedName("image_pay")
    @Expose
    private String imagePay;
    @SerializedName("image_customer")
    @Expose
    private String imageCustomer;
    @SerializedName("date_member")
    @Expose
    private String dateMember;
    @SerializedName("poin")
    @Expose
    private String poin;
    @SerializedName("code_refferal")
    @Expose
    private String codeRefferal;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("exp_otp")
    @Expose
    private String expOtp;
    @SerializedName("email_verification")
    @Expose
    private Boolean emailVerification;
    @SerializedName("img_identitas")
    @Expose
    private String imgIdentitas;
    @SerializedName("img_wajah")
    @Expose
    private String imgWajah;
    @SerializedName("img_full")
    @Expose
    private String imgFull;
    @SerializedName("img_rumah")
    @Expose
    private String imgRumah;
    @SerializedName("verifikasi_foto")
    @Expose
    private Boolean verifikasiFoto;
    @SerializedName("identitas")
    @Expose
    private String identitas;
    @SerializedName("flag_member")
    @Expose
    private Boolean flagMember;
    public final static Creator<MsgServer> CREATOR = new Creator<MsgServer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsgServer createFromParcel(android.os.Parcel in) {
            return new MsgServer(in);
        }

        public MsgServer[] newArray(int size) {
            return (new MsgServer[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5957261109981523062L;

    protected MsgServer(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.accurateCode = ((String) in.readValue((String.class.getClassLoader())));
        this.nik = ((String) in.readValue((String.class.getClassLoader())));
        this.ghCode = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.bentuk = ((String) in.readValue((String.class.getClassLoader())));
        this.mainAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.mainKelurahan = ((String) in.readValue((String.class.getClassLoader())));
        this.mainGeoLat = ((String) in.readValue((String.class.getClassLoader())));
        this.mainGeoLng = ((String) in.readValue((String.class.getClassLoader())));
        this.mainEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.mainOfficePhone1 = ((String) in.readValue((String.class.getClassLoader())));
        this.mainOfficePhone2 = ((String) in.readValue((String.class.getClassLoader())));
        this.mainFax1 = ((String) in.readValue((String.class.getClassLoader())));
        this.mainFax2 = ((String) in.readValue((String.class.getClassLoader())));
        this.mainPhone1 = ((String) in.readValue((String.class.getClassLoader())));
        this.mainPhone2 = ((String) in.readValue((String.class.getClassLoader())));
        this.mainCpName = ((String) in.readValue((String.class.getClassLoader())));
        this.mainCpTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.mainCpJabatan = ((String) in.readValue((String.class.getClassLoader())));
        this.mainCpBirthdate = ((String) in.readValue((String.class.getClassLoader())));
        this.saldoPiutang = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.creditLimit = ((String) in.readValue((String.class.getClassLoader())));
        this.creditLimitRemain = ((String) in.readValue((String.class.getClassLoader())));
        this.creditLimitDays = ((String) in.readValue((String.class.getClassLoader())));
        this.tax = ((String) in.readValue((String.class.getClassLoader())));
        this.mainPos = ((String) in.readValue((String.class.getClassLoader())));
        this.priceVariant = ((String) in.readValue((String.class.getClassLoader())));
        this.gudang = ((String) in.readValue((String.class.getClassLoader())));
        this.headOffice = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.wilayahSales = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.alias = ((String) in.readValue((String.class.getClassLoader())));
        this.dptCode = ((String) in.readValue((String.class.getClassLoader())));
        this.appDo = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.appSj = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.dateBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMember = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.payDate = ((String) in.readValue((String.class.getClassLoader())));
        this.imagePay = ((String) in.readValue((String.class.getClassLoader())));
        this.imageCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.dateMember = ((String) in.readValue((String.class.getClassLoader())));
        this.poin = ((String) in.readValue((String.class.getClassLoader())));
        this.codeRefferal = ((String) in.readValue((String.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
        this.otp = ((String) in.readValue((String.class.getClassLoader())));
        this.expOtp = ((String) in.readValue((String.class.getClassLoader())));
        this.emailVerification = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.imgIdentitas = ((String) in.readValue((String.class.getClassLoader())));
        this.imgWajah = ((String) in.readValue((String.class.getClassLoader())));
        this.imgFull = ((String) in.readValue((String.class.getClassLoader())));
        this.imgRumah = ((String) in.readValue((String.class.getClassLoader())));
        this.verifikasiFoto = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.identitas = ((String) in.readValue((String.class.getClassLoader())));
        this.flagMember = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public MsgServer() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccurateCode() {
        return accurateCode;
    }

    public void setAccurateCode(String accurateCode) {
        this.accurateCode = accurateCode;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getGhCode() {
        return ghCode;
    }

    public void setGhCode(String ghCode) {
        this.ghCode = ghCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getMainKelurahan() {
        return mainKelurahan;
    }

    public void setMainKelurahan(String mainKelurahan) {
        this.mainKelurahan = mainKelurahan;
    }

    public String getMainGeoLat() {
        return mainGeoLat;
    }

    public void setMainGeoLat(String mainGeoLat) {
        this.mainGeoLat = mainGeoLat;
    }

    public String getMainGeoLng() {
        return mainGeoLng;
    }

    public void setMainGeoLng(String mainGeoLng) {
        this.mainGeoLng = mainGeoLng;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getMainOfficePhone1() {
        return mainOfficePhone1;
    }

    public void setMainOfficePhone1(String mainOfficePhone1) {
        this.mainOfficePhone1 = mainOfficePhone1;
    }

    public String getMainOfficePhone2() {
        return mainOfficePhone2;
    }

    public void setMainOfficePhone2(String mainOfficePhone2) {
        this.mainOfficePhone2 = mainOfficePhone2;
    }

    public String getMainFax1() {
        return mainFax1;
    }

    public void setMainFax1(String mainFax1) {
        this.mainFax1 = mainFax1;
    }

    public String getMainFax2() {
        return mainFax2;
    }

    public void setMainFax2(String mainFax2) {
        this.mainFax2 = mainFax2;
    }

    public String getMainPhone1() {
        return mainPhone1;
    }

    public void setMainPhone1(String mainPhone1) {
        this.mainPhone1 = mainPhone1;
    }

    public String getMainPhone2() {
        return mainPhone2;
    }

    public void setMainPhone2(String mainPhone2) {
        this.mainPhone2 = mainPhone2;
    }

    public String getMainCpName() {
        return mainCpName;
    }

    public void setMainCpName(String mainCpName) {
        this.mainCpName = mainCpName;
    }

    public String getMainCpTitle() {
        return mainCpTitle;
    }

    public void setMainCpTitle(String mainCpTitle) {
        this.mainCpTitle = mainCpTitle;
    }

    public String getMainCpJabatan() {
        return mainCpJabatan;
    }

    public void setMainCpJabatan(String mainCpJabatan) {
        this.mainCpJabatan = mainCpJabatan;
    }

    public String getMainCpBirthdate() {
        return mainCpBirthdate;
    }

    public void setMainCpBirthdate(String mainCpBirthdate) {
        this.mainCpBirthdate = mainCpBirthdate;
    }

    public Integer getSaldoPiutang() {
        return saldoPiutang;
    }

    public void setSaldoPiutang(Integer saldoPiutang) {
        this.saldoPiutang = saldoPiutang;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCreditLimitRemain() {
        return creditLimitRemain;
    }

    public void setCreditLimitRemain(String creditLimitRemain) {
        this.creditLimitRemain = creditLimitRemain;
    }

    public String getCreditLimitDays() {
        return creditLimitDays;
    }

    public void setCreditLimitDays(String creditLimitDays) {
        this.creditLimitDays = creditLimitDays;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getMainPos() {
        return mainPos;
    }

    public void setMainPos(String mainPos) {
        this.mainPos = mainPos;
    }

    public String getPriceVariant() {
        return priceVariant;
    }

    public void setPriceVariant(String priceVariant) {
        this.priceVariant = priceVariant;
    }

    public String getGudang() {
        return gudang;
    }

    public void setGudang(String gudang) {
        this.gudang = gudang;
    }

    public String getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWilayahSales() {
        return wilayahSales;
    }

    public void setWilayahSales(String wilayahSales) {
        this.wilayahSales = wilayahSales;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDptCode() {
        return dptCode;
    }

    public void setDptCode(String dptCode) {
        this.dptCode = dptCode;
    }

    public Integer getAppDo() {
        return appDo;
    }

    public void setAppDo(Integer appDo) {
        this.appDo = appDo;
    }

    public Integer getAppSj() {
        return appSj;
    }

    public void setAppSj(Integer appSj) {
        this.appSj = appSj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getStatusMember() {
        return statusMember;
    }

    public void setStatusMember(String statusMember) {
        this.statusMember = statusMember;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getImagePay() {
        return imagePay;
    }

    public void setImagePay(String imagePay) {
        this.imagePay = imagePay;
    }

    public String getImageCustomer() {
        return imageCustomer;
    }

    public void setImageCustomer(String imageCustomer) {
        this.imageCustomer = imageCustomer;
    }

    public String getDateMember() {
        return dateMember;
    }

    public void setDateMember(String dateMember) {
        this.dateMember = dateMember;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getCodeRefferal() {
        return codeRefferal;
    }

    public void setCodeRefferal(String codeRefferal) {
        this.codeRefferal = codeRefferal;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getExpOtp() {
        return expOtp;
    }

    public void setExpOtp(String expOtp) {
        this.expOtp = expOtp;
    }

    public Boolean getEmailVerification() {
        return emailVerification;
    }

    public void setEmailVerification(Boolean emailVerification) {
        this.emailVerification = emailVerification;
    }

    public String getImgIdentitas() {
        return imgIdentitas;
    }

    public void setImgIdentitas(String imgIdentitas) {
        this.imgIdentitas = imgIdentitas;
    }

    public String getImgWajah() {
        return imgWajah;
    }

    public void setImgWajah(String imgWajah) {
        this.imgWajah = imgWajah;
    }

    public String getImgFull() {
        return imgFull;
    }

    public void setImgFull(String imgFull) {
        this.imgFull = imgFull;
    }

    public String getImgRumah() {
        return imgRumah;
    }

    public void setImgRumah(String imgRumah) {
        this.imgRumah = imgRumah;
    }

    public Boolean getVerifikasiFoto() {
        return verifikasiFoto;
    }

    public void setVerifikasiFoto(Boolean verifikasiFoto) {
        this.verifikasiFoto = verifikasiFoto;
    }

    public String getIdentitas() {
        return identitas;
    }

    public void setIdentitas(String identitas) {
        this.identitas = identitas;
    }

    public Boolean getFlagMember() {
        return flagMember;
    }

    public void setFlagMember(Boolean flagMember) {
        this.flagMember = flagMember;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyCode);
        dest.writeValue(code);
        dest.writeValue(accurateCode);
        dest.writeValue(nik);
        dest.writeValue(ghCode);
        dest.writeValue(name);
        dest.writeValue(type);
        dest.writeValue(bentuk);
        dest.writeValue(mainAddress);
        dest.writeValue(mainKelurahan);
        dest.writeValue(mainGeoLat);
        dest.writeValue(mainGeoLng);
        dest.writeValue(mainEmail);
        dest.writeValue(mainOfficePhone1);
        dest.writeValue(mainOfficePhone2);
        dest.writeValue(mainFax1);
        dest.writeValue(mainFax2);
        dest.writeValue(mainPhone1);
        dest.writeValue(mainPhone2);
        dest.writeValue(mainCpName);
        dest.writeValue(mainCpTitle);
        dest.writeValue(mainCpJabatan);
        dest.writeValue(mainCpBirthdate);
        dest.writeValue(saldoPiutang);
        dest.writeValue(creditLimit);
        dest.writeValue(creditLimitRemain);
        dest.writeValue(creditLimitDays);
        dest.writeValue(tax);
        dest.writeValue(mainPos);
        dest.writeValue(priceVariant);
        dest.writeValue(gudang);
        dest.writeValue(headOffice);
        dest.writeValue(description);
        dest.writeValue(wilayahSales);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(alias);
        dest.writeValue(dptCode);
        dest.writeValue(appDo);
        dest.writeValue(appSj);
        dest.writeValue(password);
        dest.writeValue(dateBirth);
        dest.writeValue(statusMember);
        dest.writeValue(statusPayment);
        dest.writeValue(payDate);
        dest.writeValue(imagePay);
        dest.writeValue(imageCustomer);
        dest.writeValue(dateMember);
        dest.writeValue(poin);
        dest.writeValue(codeRefferal);
        dest.writeValue(expiredDate);
        dest.writeValue(otp);
        dest.writeValue(expOtp);
        dest.writeValue(emailVerification);
        dest.writeValue(imgIdentitas);
        dest.writeValue(imgWajah);
        dest.writeValue(imgFull);
        dest.writeValue(imgRumah);
        dest.writeValue(verifikasiFoto);
        dest.writeValue(identitas);
        dest.writeValue(flagMember);
    }

    public int describeContents() {
        return  0;
    }

}
