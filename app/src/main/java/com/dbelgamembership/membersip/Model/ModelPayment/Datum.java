
package com.dbelgamembership.membersip.Model.ModelPayment;

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
    private Integer totalPaymentPaid;
    @SerializedName("change")
    @Expose
    private Integer change;
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
    private Integer ongkosKirim;
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
    @SerializedName("order_detail")
    @Expose
    private List<OrderDetail> orderDetail = null;
    @SerializedName("payment_detail")
    @Expose
    private List<PaymentDetail> paymentDetail = null;
    @SerializedName("add_item")
    @Expose
    private List<AddItem> addItem = null;
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
    private final static long serialVersionUID = 5915672117313461996L;

    protected Datum(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.createuser = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.totalPaymentPaid = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.change = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dateTransaction = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.identitasCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.nomorCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.ongkosKirim = ((Integer) in.readValue((Integer.class.getClassLoader())));
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
        in.readList(this.orderDetail, (com.dbelgamembership.membersip.Model.ModelPayment.OrderDetail.class.getClassLoader()));
        in.readList(this.paymentDetail, (com.dbelgamembership.membersip.Model.ModelPayment.PaymentDetail.class.getClassLoader()));
        in.readList(this.addItem, (com.dbelgamembership.membersip.Model.ModelPayment.AddItem.class.getClassLoader()));
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

    public Integer getTotalPaymentPaid() {
        return totalPaymentPaid;
    }

    public void setTotalPaymentPaid(Integer totalPaymentPaid) {
        this.totalPaymentPaid = totalPaymentPaid;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
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

    public Integer getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(Integer ongkosKirim) {
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
        dest.writeList(orderDetail);
        dest.writeList(paymentDetail);
        dest.writeList(addItem);
    }

    public int describeContents() {
        return  0;
    }

}
