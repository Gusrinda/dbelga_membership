
package com.dbelgamembership.membersip.Model.ResponseUser;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
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
    private int mainKelurahan;
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
    private int saldoPiutang;
    @SerializedName("credit_limit")
    @Expose
    private String creditLimit;
    @SerializedName("credit_limit_remain")
    @Expose
    private String creditLimitRemain;
    @SerializedName("credit_limit_days")
    @Expose
    private int creditLimitDays;
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
    private int gudang;
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
    private boolean status;
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
    private int appDo;
    @SerializedName("app_sj")
    @Expose
    private int appSj;
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
    private int poin;
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
    private boolean emailVerification;
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
    private final static long serialVersionUID = 9034279053769493407L;

    protected MsgServer(android.os.Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.companyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.accurateCode = ((String) in.readValue((String.class.getClassLoader())));
        this.nik = ((String) in.readValue((String.class.getClassLoader())));
        this.ghCode = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.bentuk = ((String) in.readValue((String.class.getClassLoader())));
        this.mainAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.mainKelurahan = ((int) in.readValue((int.class.getClassLoader())));
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
        this.saldoPiutang = ((int) in.readValue((int.class.getClassLoader())));
        this.creditLimit = ((String) in.readValue((String.class.getClassLoader())));
        this.creditLimitRemain = ((String) in.readValue((String.class.getClassLoader())));
        this.creditLimitDays = ((int) in.readValue((int.class.getClassLoader())));
        this.tax = ((String) in.readValue((String.class.getClassLoader())));
        this.mainPos = ((String) in.readValue((String.class.getClassLoader())));
        this.priceVariant = ((String) in.readValue((String.class.getClassLoader())));
        this.gudang = ((int) in.readValue((int.class.getClassLoader())));
        this.headOffice = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.wilayahSales = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.alias = ((String) in.readValue((String.class.getClassLoader())));
        this.dptCode = ((String) in.readValue((String.class.getClassLoader())));
        this.appDo = ((int) in.readValue((int.class.getClassLoader())));
        this.appSj = ((int) in.readValue((int.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.dateBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMember = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.payDate = ((String) in.readValue((String.class.getClassLoader())));
        this.imagePay = ((String) in.readValue((String.class.getClassLoader())));
        this.imageCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.dateMember = ((String) in.readValue((String.class.getClassLoader())));
        this.poin = ((int) in.readValue((int.class.getClassLoader())));
        this.codeRefferal = ((String) in.readValue((String.class.getClassLoader())));
        this.expiredDate = ((String) in.readValue((String.class.getClassLoader())));
        this.otp = ((String) in.readValue((String.class.getClassLoader())));
        this.expOtp = ((String) in.readValue((String.class.getClassLoader())));
        this.emailVerification = ((boolean) in.readValue((boolean.class.getClassLoader())));
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

    public String getAccurateCode() {
        return accurateCode;
    }

    public void setAccurateCode(String accurateCode) {
        this.accurateCode = accurateCode;
    }

    public MsgServer withAccurateCode(String accurateCode) {
        this.accurateCode = accurateCode;
        return this;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public MsgServer withNik(String nik) {
        this.nik = nik;
        return this;
    }

    public String getGhCode() {
        return ghCode;
    }

    public void setGhCode(String ghCode) {
        this.ghCode = ghCode;
    }

    public MsgServer withGhCode(String ghCode) {
        this.ghCode = ghCode;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MsgServer withType(String type) {
        this.type = type;
        return this;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public MsgServer withBentuk(String bentuk) {
        this.bentuk = bentuk;
        return this;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public MsgServer withMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
        return this;
    }

    public int getMainKelurahan() {
        return mainKelurahan;
    }

    public void setMainKelurahan(int mainKelurahan) {
        this.mainKelurahan = mainKelurahan;
    }

    public MsgServer withMainKelurahan(int mainKelurahan) {
        this.mainKelurahan = mainKelurahan;
        return this;
    }

    public String getMainGeoLat() {
        return mainGeoLat;
    }

    public void setMainGeoLat(String mainGeoLat) {
        this.mainGeoLat = mainGeoLat;
    }

    public MsgServer withMainGeoLat(String mainGeoLat) {
        this.mainGeoLat = mainGeoLat;
        return this;
    }

    public String getMainGeoLng() {
        return mainGeoLng;
    }

    public void setMainGeoLng(String mainGeoLng) {
        this.mainGeoLng = mainGeoLng;
    }

    public MsgServer withMainGeoLng(String mainGeoLng) {
        this.mainGeoLng = mainGeoLng;
        return this;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public MsgServer withMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
        return this;
    }

    public String getMainOfficePhone1() {
        return mainOfficePhone1;
    }

    public void setMainOfficePhone1(String mainOfficePhone1) {
        this.mainOfficePhone1 = mainOfficePhone1;
    }

    public MsgServer withMainOfficePhone1(String mainOfficePhone1) {
        this.mainOfficePhone1 = mainOfficePhone1;
        return this;
    }

    public String getMainOfficePhone2() {
        return mainOfficePhone2;
    }

    public void setMainOfficePhone2(String mainOfficePhone2) {
        this.mainOfficePhone2 = mainOfficePhone2;
    }

    public MsgServer withMainOfficePhone2(String mainOfficePhone2) {
        this.mainOfficePhone2 = mainOfficePhone2;
        return this;
    }

    public String getMainFax1() {
        return mainFax1;
    }

    public void setMainFax1(String mainFax1) {
        this.mainFax1 = mainFax1;
    }

    public MsgServer withMainFax1(String mainFax1) {
        this.mainFax1 = mainFax1;
        return this;
    }

    public String getMainFax2() {
        return mainFax2;
    }

    public void setMainFax2(String mainFax2) {
        this.mainFax2 = mainFax2;
    }

    public MsgServer withMainFax2(String mainFax2) {
        this.mainFax2 = mainFax2;
        return this;
    }

    public String getMainPhone1() {
        return mainPhone1;
    }

    public void setMainPhone1(String mainPhone1) {
        this.mainPhone1 = mainPhone1;
    }

    public MsgServer withMainPhone1(String mainPhone1) {
        this.mainPhone1 = mainPhone1;
        return this;
    }

    public String getMainPhone2() {
        return mainPhone2;
    }

    public void setMainPhone2(String mainPhone2) {
        this.mainPhone2 = mainPhone2;
    }

    public MsgServer withMainPhone2(String mainPhone2) {
        this.mainPhone2 = mainPhone2;
        return this;
    }

    public String getMainCpName() {
        return mainCpName;
    }

    public void setMainCpName(String mainCpName) {
        this.mainCpName = mainCpName;
    }

    public MsgServer withMainCpName(String mainCpName) {
        this.mainCpName = mainCpName;
        return this;
    }

    public String getMainCpTitle() {
        return mainCpTitle;
    }

    public void setMainCpTitle(String mainCpTitle) {
        this.mainCpTitle = mainCpTitle;
    }

    public MsgServer withMainCpTitle(String mainCpTitle) {
        this.mainCpTitle = mainCpTitle;
        return this;
    }

    public String getMainCpJabatan() {
        return mainCpJabatan;
    }

    public void setMainCpJabatan(String mainCpJabatan) {
        this.mainCpJabatan = mainCpJabatan;
    }

    public MsgServer withMainCpJabatan(String mainCpJabatan) {
        this.mainCpJabatan = mainCpJabatan;
        return this;
    }

    public String getMainCpBirthdate() {
        return mainCpBirthdate;
    }

    public void setMainCpBirthdate(String mainCpBirthdate) {
        this.mainCpBirthdate = mainCpBirthdate;
    }

    public MsgServer withMainCpBirthdate(String mainCpBirthdate) {
        this.mainCpBirthdate = mainCpBirthdate;
        return this;
    }

    public int getSaldoPiutang() {
        return saldoPiutang;
    }

    public void setSaldoPiutang(int saldoPiutang) {
        this.saldoPiutang = saldoPiutang;
    }

    public MsgServer withSaldoPiutang(int saldoPiutang) {
        this.saldoPiutang = saldoPiutang;
        return this;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public MsgServer withCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public String getCreditLimitRemain() {
        return creditLimitRemain;
    }

    public void setCreditLimitRemain(String creditLimitRemain) {
        this.creditLimitRemain = creditLimitRemain;
    }

    public MsgServer withCreditLimitRemain(String creditLimitRemain) {
        this.creditLimitRemain = creditLimitRemain;
        return this;
    }

    public int getCreditLimitDays() {
        return creditLimitDays;
    }

    public void setCreditLimitDays(int creditLimitDays) {
        this.creditLimitDays = creditLimitDays;
    }

    public MsgServer withCreditLimitDays(int creditLimitDays) {
        this.creditLimitDays = creditLimitDays;
        return this;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public MsgServer withTax(String tax) {
        this.tax = tax;
        return this;
    }

    public String getMainPos() {
        return mainPos;
    }

    public void setMainPos(String mainPos) {
        this.mainPos = mainPos;
    }

    public MsgServer withMainPos(String mainPos) {
        this.mainPos = mainPos;
        return this;
    }

    public String getPriceVariant() {
        return priceVariant;
    }

    public void setPriceVariant(String priceVariant) {
        this.priceVariant = priceVariant;
    }

    public MsgServer withPriceVariant(String priceVariant) {
        this.priceVariant = priceVariant;
        return this;
    }

    public int getGudang() {
        return gudang;
    }

    public void setGudang(int gudang) {
        this.gudang = gudang;
    }

    public MsgServer withGudang(int gudang) {
        this.gudang = gudang;
        return this;
    }

    public String getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }

    public MsgServer withHeadOffice(String headOffice) {
        this.headOffice = headOffice;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MsgServer withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getWilayahSales() {
        return wilayahSales;
    }

    public void setWilayahSales(String wilayahSales) {
        this.wilayahSales = wilayahSales;
    }

    public MsgServer withWilayahSales(String wilayahSales) {
        this.wilayahSales = wilayahSales;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public MsgServer withStatus(boolean status) {
        this.status = status;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public MsgServer withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getDptCode() {
        return dptCode;
    }

    public void setDptCode(String dptCode) {
        this.dptCode = dptCode;
    }

    public MsgServer withDptCode(String dptCode) {
        this.dptCode = dptCode;
        return this;
    }

    public int getAppDo() {
        return appDo;
    }

    public void setAppDo(int appDo) {
        this.appDo = appDo;
    }

    public MsgServer withAppDo(int appDo) {
        this.appDo = appDo;
        return this;
    }

    public int getAppSj() {
        return appSj;
    }

    public void setAppSj(int appSj) {
        this.appSj = appSj;
    }

    public MsgServer withAppSj(int appSj) {
        this.appSj = appSj;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MsgServer withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public MsgServer withDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
        return this;
    }

    public String getStatusMember() {
        return statusMember;
    }

    public void setStatusMember(String statusMember) {
        this.statusMember = statusMember;
    }

    public MsgServer withStatusMember(String statusMember) {
        this.statusMember = statusMember;
        return this;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public MsgServer withStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
        return this;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public MsgServer withPayDate(String payDate) {
        this.payDate = payDate;
        return this;
    }

    public String getImagePay() {
        return imagePay;
    }

    public void setImagePay(String imagePay) {
        this.imagePay = imagePay;
    }

    public MsgServer withImagePay(String imagePay) {
        this.imagePay = imagePay;
        return this;
    }

    public String getImageCustomer() {
        return imageCustomer;
    }

    public void setImageCustomer(String imageCustomer) {
        this.imageCustomer = imageCustomer;
    }

    public MsgServer withImageCustomer(String imageCustomer) {
        this.imageCustomer = imageCustomer;
        return this;
    }

    public String getDateMember() {
        return dateMember;
    }

    public void setDateMember(String dateMember) {
        this.dateMember = dateMember;
    }

    public MsgServer withDateMember(String dateMember) {
        this.dateMember = dateMember;
        return this;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public MsgServer withPoin(int poin) {
        this.poin = poin;
        return this;
    }

    public String getCodeRefferal() {
        return codeRefferal;
    }

    public void setCodeRefferal(String codeRefferal) {
        this.codeRefferal = codeRefferal;
    }

    public MsgServer withCodeRefferal(String codeRefferal) {
        this.codeRefferal = codeRefferal;
        return this;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public MsgServer withExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public MsgServer withOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public String getExpOtp() {
        return expOtp;
    }

    public void setExpOtp(String expOtp) {
        this.expOtp = expOtp;
    }

    public MsgServer withExpOtp(String expOtp) {
        this.expOtp = expOtp;
        return this;
    }

    public boolean isEmailVerification() {
        return emailVerification;
    }

    public void setEmailVerification(boolean emailVerification) {
        this.emailVerification = emailVerification;
    }

    public MsgServer withEmailVerification(boolean emailVerification) {
        this.emailVerification = emailVerification;
        return this;
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
    }

    public int describeContents() {
        return  0;
    }

}
