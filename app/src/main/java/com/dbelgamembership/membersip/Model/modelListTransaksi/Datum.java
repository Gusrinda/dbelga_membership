
package com.dbelgamembership.membersip.Model.modelListTransaksi;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Datum implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id_customer")
    @Expose
    private Integer idCustomer;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("grandtotal")
    @Expose
    private double grandtotal;
    @SerializedName("createuser")
    @Expose
    private Integer createuser;
    @SerializedName("createuser_name")
    @Expose
    private String createuserName;
    @SerializedName("identitas_customer")
    @Expose
    private String identitasCustomer;
    @SerializedName("alamat_customer")
    @Expose
    private String alamatCustomer;
    @SerializedName("alamat_pengiriman")
    @Expose
    private String alamatPengiriman;
    @SerializedName("keterangan_kirim")
    @Expose
    private String keteranganKirim;
    @SerializedName("status_pengiriman")
    @Expose
    private String statusPengiriman;
    @SerializedName("catatan_pengiriman")
    @Expose
    private String catatanPengiriman;
    @SerializedName("nomor_customer")
    @Expose
    private String nomorCustomer;
    @SerializedName("ongkos_kirim")
    @Expose
    private double ongkosKirim;
    @SerializedName("tanggal_kirim")
    @Expose
    private String tanggalKirim;
    @SerializedName("flagKirim")
    @Expose
    private Boolean flagKirim;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("nama_penerima")
    @Expose
    private String namaPenerima;
    @SerializedName("tipe_payment")
    @Expose
    private String tipePayment;
    @SerializedName("bukti_payment")
    @Expose
    private String buktiPayment;

    @SerializedName("bank_payment")
    @Expose
    private String bankPayment;

    @SerializedName("bukti_pengiriman")
    @Expose
    private String buktiPengiriman;
    @SerializedName("bukti_cod")
    @Expose
    private String buktiCod;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("gudang")
    @Expose
    private Integer gudang;
    @SerializedName("total_diskon_so")
    @Expose
    private String totalDiskonSo;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("catatan_payment")
    @Expose
    private String catatanPayment;

    @SerializedName("is_voucher")
    @Expose
    private Boolean isVoucher;
    @SerializedName("voucher_code")
    @Expose
    private String voucherCode;
    @SerializedName("voucher_id")
    @Expose
    private Integer voucherId;
    @SerializedName("voucher_nominal")
    @Expose
    private String voucherNominal;

    @SerializedName("is_voucher_supplier")
    @Expose
    private Boolean isVoucherSuplier;
    @SerializedName("voucher_code_supplier")
    @Expose
    private String voucherCodeSuplier;
    @SerializedName("voucher_nominal_supplier")
    @Expose
    private String voucherNominalSuplier;

//    @SerializedName("detail_payment_bni")
//    @Expose
//    private List<DetailPaymentBni> detailPaymentBni = null;

    @SerializedName("detail_payment_bni")
    @Expose
    private DetailPaymentBni detailPaymentBni;

    @SerializedName("detail")
    @Expose
    private List<Detail> detail = null;
    @SerializedName("is_kekurangan")
    @Expose
    private Boolean isKekurangan;
    @SerializedName("detail_kekurangan")
    @Expose
    private List<DetailKekurangan> detailKekurangan = null;
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
    private final static long serialVersionUID = 687273061280705201L;

    protected Datum(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.idCustomer = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.grandtotal = ((double) in.readValue((double.class.getClassLoader())));
        this.createuser = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createuserName = ((String) in.readValue((String.class.getClassLoader())));
        this.identitasCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.keteranganKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.catatanPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.nomorCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.ongkosKirim = ((double) in.readValue((double.class.getClassLoader())));
        this.tanggalKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.flagKirim = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.namaPenerima = ((String) in.readValue((String.class.getClassLoader())));
        this.tipePayment = ((String) in.readValue((String.class.getClassLoader())));
        this.buktiPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.bankPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.buktiPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.buktiCod = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.gudang = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalDiskonSo = ((String) in.readValue((String.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.catatanPayment = ((String) in.readValue((String.class.getClassLoader())));
        this.isVoucher = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voucherCode = ((String) in.readValue((String.class.getClassLoader())));
        this.voucherId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.voucherNominal = ((String) in.readValue((String.class.getClassLoader())));
        this.isVoucherSuplier = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voucherCodeSuplier = ((String) in.readValue((String.class.getClassLoader())));
        this.voucherNominalSuplier = ((String) in.readValue((String.class.getClassLoader())));
        this.detailPaymentBni = ((DetailPaymentBni) in.readValue((String.class.getClassLoader())));

//        in.readList(this.detailPaymentBni, (com.dbelgamembership.membersip.Model.modelListTransaksi.DetailPaymentBni.class.getClassLoader()));
        in.readList(this.detail, (com.dbelgamembership.membersip.Model.modelListTransaksi.Detail.class.getClassLoader()));
        this.isKekurangan = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.detailKekurangan, (com.dbelgamembership.membersip.Model.modelListTransaksi.DetailKekurangan.class.getClassLoader()));
    }

    public Datum() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(double grandtotal) {
        this.grandtotal = grandtotal;
    }

    public Integer getCreateuser() {
        return createuser;
    }

    public void setCreateuser(Integer createuser) {
        this.createuser = createuser;
    }

    public String getCreateuserName() {
        return createuserName;
    }

    public void setCreateuserName(String createuserName) {
        this.createuserName = createuserName;
    }

    public String getIdentitasCustomer() {
        return identitasCustomer;
    }

    public void setIdentitasCustomer(String identitasCustomer) {
        this.identitasCustomer = identitasCustomer;
    }

    public String getAlamatCustomer() {
        return alamatCustomer;
    }

    public void setAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
    }

    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
    }

    public String getKeteranganKirim() {
        return keteranganKirim;
    }

    public void setKeteranganKirim(String keteranganKirim) {
        this.keteranganKirim = keteranganKirim;
    }

    public String getStatusPengiriman() {
        return statusPengiriman;
    }

    public void setStatusPengiriman(String statusPengiriman) {
        this.statusPengiriman = statusPengiriman;
    }

    public String getCatatanPengiriman() {
        return catatanPengiriman;
    }

    public void setCatatanPengiriman(String catatanPengiriman) {
        this.catatanPengiriman = catatanPengiriman;
    }

    public String getNomorCustomer() {
        return nomorCustomer;
    }

    public void setNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
    }

    public double getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(double ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
    }

    public String getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public Boolean getFlagKirim() {
        return flagKirim;
    }

    public void setFlagKirim(Boolean flagKirim) {
        this.flagKirim = flagKirim;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getTipePayment() {
        return tipePayment;
    }

    public void setTipePayment(String tipePayment) {
        this.tipePayment = tipePayment;
    }

    public String getBuktiPayment() {
        return buktiPayment;
    }

    public void setBuktiPayment(String buktiPayment) {
        this.buktiPayment = buktiPayment;
    }

    public String getBuktiPengiriman() {
        return buktiPengiriman;
    }

    public void setBuktiPengiriman(String buktiPengiriman) {
        this.buktiPengiriman = buktiPengiriman;
    }

    public String getBuktiCod() {
        return buktiCod;
    }

    public void setBuktiCod(String buktiCod) {
        this.buktiCod = buktiCod;
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

    public String getTotalDiskonSo() {
        return totalDiskonSo;
    }

    public void setTotalDiskonSo(String totalDiskonSo) {
        this.totalDiskonSo = totalDiskonSo;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public String getCatatanPayment() {
        return catatanPayment;
    }

    public void setCatatanPayment(String catatanPayment) {
        this.catatanPayment = catatanPayment;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public Boolean getIsKekurangan() {
        return isKekurangan;
    }

    public void setIsKekurangan(Boolean isKekurangan) {
        this.isKekurangan = isKekurangan;
    }

    public List<DetailKekurangan> getDetailKekurangan() {
        return detailKekurangan;
    }

    public void setDetailKekurangan(List<DetailKekurangan> detailKekurangan) {
        this.detailKekurangan = detailKekurangan;
    }

    public DetailPaymentBni getDetailPaymentBni() {
        return detailPaymentBni;
    }

    public void setDetailPaymentBni(DetailPaymentBni detailPaymentBni) {
        this.detailPaymentBni = detailPaymentBni;
    }

    //    public List<DetailPaymentBni> getDetailPaymentBni() {
//        return detailPaymentBni;
//    }
//
//    public void setDetailPaymentBni(List<DetailPaymentBni> detailPaymentBni) {
//        this.detailPaymentBni = detailPaymentBni;
//    }

    public Boolean getVoucher() {
        return isVoucher;
    }

    public void setVoucher(Boolean voucher) {
        isVoucher = voucher;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherNominal() {
        return voucherNominal;
    }

    public void setVoucherNominal(String voucherNominal) {
        this.voucherNominal = voucherNominal;
    }

    public Boolean getKekurangan() {
        return isKekurangan;
    }

    public void setKekurangan(Boolean kekurangan) {
        isKekurangan = kekurangan;
    }

    public Boolean getVoucherSuplier() {
        return isVoucherSuplier;
    }

    public void setVoucherSuplier(Boolean voucherSuplier) {
        isVoucherSuplier = voucherSuplier;
    }

    public String getVoucherCodeSuplier() {
        return voucherCodeSuplier;
    }

    public void setVoucherCodeSuplier(String voucherCodeSuplier) {
        this.voucherCodeSuplier = voucherCodeSuplier;
    }

    public String getVoucherNominalSuplier() {
        return voucherNominalSuplier;
    }

    public void setVoucherNominalSuplier(String voucherNominalSuplier) {
        this.voucherNominalSuplier = voucherNominalSuplier;
    }

    public String getBankPayment() {
        return bankPayment;
    }

    public void setBankPayment(String bankPayment) {
        this.bankPayment = bankPayment;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(idCustomer);
        dest.writeValue(customer);
        dest.writeValue(grandtotal);
        dest.writeValue(createuser);
        dest.writeValue(createuserName);
        dest.writeValue(identitasCustomer);
        dest.writeValue(alamatCustomer);
        dest.writeValue(alamatPengiriman);
        dest.writeValue(keteranganKirim);
        dest.writeValue(statusPengiriman);
        dest.writeValue(catatanPengiriman);
        dest.writeValue(nomorCustomer);
        dest.writeValue(ongkosKirim);
        dest.writeValue(tanggalKirim);
        dest.writeValue(flagKirim);
        dest.writeValue(date);
        dest.writeValue(status);
        dest.writeValue(namaPenerima);
        dest.writeValue(tipePayment);
        dest.writeValue(buktiPayment);
        dest.writeValue(bankPayment);
        dest.writeValue(buktiPengiriman);
        dest.writeValue(buktiCod);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(gudang);
        dest.writeValue(totalDiskonSo);
        dest.writeValue(pembayaranCode);
        dest.writeValue(catatanPayment);
        dest.writeValue(detailPaymentBni);
//        dest.writeList(detailPaymentBni);
        dest.writeList(detail);
        dest.writeValue(isVoucher);
        dest.writeValue(voucherCode);
        dest.writeValue(voucherId);
        dest.writeValue(voucherNominal);
        dest.writeValue(isVoucherSuplier);
        dest.writeValue(voucherCodeSuplier);
        dest.writeValue(voucherNominalSuplier);
        dest.writeValue(isKekurangan);
        dest.writeList(detailKekurangan);
    }

    public int describeContents() {
        return  0;
    }

}
