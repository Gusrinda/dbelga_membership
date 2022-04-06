
package com.dbelgamembership.membersip.Model.ModelPayment;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Datum implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pembayaran_code")
    @Expose
    private String pembayaranCode;
    @SerializedName("createuser")
    @Expose
    private String createuser;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("total_payment_paid")
    @Expose
    private String totalPaymentPaid;
    @SerializedName("change")
    @Expose
    private String change;
    @SerializedName("date_transaction")
    @Expose
    private String dateTransaction;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("identitas_customer")
    @Expose
    private String identitasCustomer;
    @SerializedName("alamat_customer")
    @Expose
    private String alamatCustomer;
    @SerializedName("nomor_customer")
    @Expose
    private String nomorCustomer;
    @SerializedName("ongkos_kirim")
    @Expose
    private String ongkosKirim;
    @SerializedName("tanggal_kirim")
    @Expose
    private String tanggalKirim;
    @SerializedName("flag_dp")
    @Expose
    private Boolean flagDp;
    @SerializedName("alamat_customer_pos")
    @Expose
    private String alamatCustomerPos;
    @SerializedName("alamat_pengiriman")
    @Expose
    private String alamatPengiriman;
    @SerializedName("status_pengiriman")
    @Expose
    private String statusPengiriman;
    @SerializedName("catatan_pengiriman")
    @Expose
    private String catatanPengiriman;
    @SerializedName("nama_penerima")
    @Expose
    private String namaPenerima;
    @SerializedName("tanggal_diterima")
    @Expose
    private String tanggalDiterima;
    @SerializedName("flag_kirim")
    @Expose
    private Boolean flagKirim;
    @SerializedName("total_belanja")
    @Expose
    private String totalBelanja;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("rt_code")
    @Expose
    private String rtCode;
    @SerializedName("gudang")
    @Expose
    private Integer gudang;
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

    @SerializedName("is_ambil_uang")
    @Expose
    private Boolean isAmbilUang;

    @SerializedName("nominal_ambil_uang")
    @Expose
    private String nominalAmbilUang;


    @SerializedName("is_belanja_shopee")
    @Expose
    private Boolean isBelanjaShopee;

    @SerializedName("order_detail")
    @Expose
    private List<OrderDetail> orderDetail = null;
    @SerializedName("payment_detail")
    @Expose
    private List<PaymentDetail> paymentDetail = null;

    @SerializedName("add_item")
    @Expose
    private List<AddItem> addItem = null;


    @SerializedName("detail_barang_tebus")
    @Expose
    private List<DetailBarangTebu> detailBarangTebus = null;



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

    };
    private final static long serialVersionUID = 5915672117313461996L;

    protected Datum(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.createuser = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.totalPaymentPaid = ((String) in.readValue((Integer.class.getClassLoader())));
        this.change = ((String) in.readValue((Integer.class.getClassLoader())));
        this.dateTransaction = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.identitasCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.nomorCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.ongkosKirim = ((String) in.readValue((Integer.class.getClassLoader())));
        this.tanggalKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.flagDp = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.alamatCustomerPos = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.catatanPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.namaPenerima = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggalDiterima = ((String) in.readValue((String.class.getClassLoader())));
        this.flagKirim = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.totalBelanja = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.rtCode = ((String) in.readValue((String.class.getClassLoader())));
        this.gudang = ((Integer) in.readValue((String.class.getClassLoader())));

        this.isVoucher = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voucherCode = ((String) in.readValue((String.class.getClassLoader())));
        this.voucherId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.voucherNominal = ((String) in.readValue((String.class.getClassLoader())));

        this.isVoucherSuplier = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voucherCodeSuplier = ((String) in.readValue((String.class.getClassLoader())));
        this.voucherNominalSuplier = ((String) in.readValue((String.class.getClassLoader())));

        this.isAmbilUang = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.nominalAmbilUang = ((String) in.readValue((String.class.getClassLoader())));

        this.isBelanjaShopee= ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.orderDetail, (com.dbelgamembership.membersip.Model.ModelPayment.OrderDetail.class.getClassLoader()));
        in.readList(this.paymentDetail, (com.dbelgamembership.membersip.Model.ModelPayment.PaymentDetail.class.getClassLoader()));
        in.readList(this.addItem, (com.dbelgamembership.membersip.Model.ModelPayment.AddItem.class.getClassLoader()));
        in.readList(this.detailBarangTebus, (com.dbelgamembership.membersip.Model.ModelPayment.DetailBarangTebu.class.getClassLoader()));
    }

    public Datum() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTotalPaymentPaid() {
        return totalPaymentPaid;
    }

    public void setTotalPaymentPaid(String totalPaymentPaid) {
        this.totalPaymentPaid = totalPaymentPaid;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNomorCustomer() {
        return nomorCustomer;
    }

    public void setNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
    }

    public String getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(String ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
    }

    public String getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public Boolean getFlagDp() {
        return flagDp;
    }

    public void setFlagDp(Boolean flagDp) {
        this.flagDp = flagDp;
    }

    public String getAlamatCustomerPos() {
        return alamatCustomerPos;
    }

    public void setAlamatCustomerPos(String alamatCustomerPos) {
        this.alamatCustomerPos = alamatCustomerPos;
    }

    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
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

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getTanggalDiterima() {
        return tanggalDiterima;
    }

    public void setTanggalDiterima(String tanggalDiterima) {
        this.tanggalDiterima = tanggalDiterima;
    }

    public Boolean getFlagKirim() {
        return flagKirim;
    }

    public void setFlagKirim(Boolean flagKirim) {
        this.flagKirim = flagKirim;
    }

    public String getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(String totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRtCode() {
        return rtCode;
    }

    public void setRtCode(String rtCode) {
        this.rtCode = rtCode;
    }
    public Integer getGudang() {
        return gudang;
    }

    public void setGudang(Integer gudang) {
        this.gudang = gudang;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<PaymentDetail> getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(List<PaymentDetail> paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public List<AddItem> getAddItem() {
        return addItem;
    }

    public void setAddItem(List<AddItem> addItem) {
        this.addItem = addItem;
    }

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

    public Boolean getAmbilUang() {
        return isAmbilUang;
    }

    public void setAmbilUang(Boolean ambilUang) {
        isAmbilUang = ambilUang;
    }

    public String getNominalAmbilUang() {
        return nominalAmbilUang;
    }

    public void setNominalAmbilUang(String nominalAmbilUang) {
        this.nominalAmbilUang = nominalAmbilUang;
    }

    public Boolean getBelanjaShopee() {
        return isBelanjaShopee;
    }

    public void setBelanjaShopee(Boolean belanjaShopee) {
        isBelanjaShopee = belanjaShopee;
    }

    public List<DetailBarangTebu> getDetailBarangTebus() {
        return detailBarangTebus;
    }

    public void setDetailBarangTebus(List<DetailBarangTebu> detailBarangTebus) {
        this.detailBarangTebus = detailBarangTebus;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(pembayaranCode);
        dest.writeValue(createuser);
        dest.writeValue(customer);
        dest.writeValue(totalPaymentPaid);
        dest.writeValue(change);
        dest.writeValue(dateTransaction);
        dest.writeValue(status);
        dest.writeValue(identitasCustomer);
        dest.writeValue(alamatCustomer);
        dest.writeValue(nomorCustomer);
        dest.writeValue(ongkosKirim);
        dest.writeValue(tanggalKirim);
        dest.writeValue(flagDp);
        dest.writeValue(alamatCustomerPos);
        dest.writeValue(alamatPengiriman);
        dest.writeValue(statusPengiriman);
        dest.writeValue(catatanPengiriman);
        dest.writeValue(namaPenerima);
        dest.writeValue(tanggalDiterima);
        dest.writeValue(flagKirim);
        dest.writeValue(totalBelanja);
        dest.writeValue(updatedAt);
        dest.writeValue(rtCode);
        dest.writeValue(isVoucher);
        dest.writeValue(voucherCode);
        dest.writeValue(gudang);
        dest.writeValue(voucherId);
        dest.writeValue(voucherNominal);
        dest.writeValue(isVoucherSuplier);
        dest.writeValue(voucherCodeSuplier);
        dest.writeValue(voucherNominalSuplier);
        dest.writeValue(isAmbilUang);
        dest.writeValue(nominalAmbilUang);
        dest.writeValue(isBelanjaShopee);
        dest.writeList(orderDetail);
        dest.writeList(paymentDetail);
        dest.writeList(addItem);
        dest.writeList(detailBarangTebus);
    }

    public int describeContents() {
        return 0;
    }

}
