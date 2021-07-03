
package com.dbelgamembership.membersip.Model.ModelPayment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private int totalPaymentPaid;
    @SerializedName("change")
    @Expose
    private int change;
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
    private int ongkosKirim;
    @SerializedName("tanggal_kirim")
    @Expose
    private String tanggalKirim;
    @SerializedName("flag_dp")
    @Expose
    private boolean flagDp;
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
    private boolean flagKirim;
    @SerializedName("total_belanja")
    @Expose
    private String totalBelanja;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_detail")
    @Expose
    private List<OrderDetail> orderDetail = new ArrayList<OrderDetail>();
    @SerializedName("payment_detail")
    @Expose
    private List<PaymentDetail> paymentDetail = new ArrayList<PaymentDetail>();
    @SerializedName("add_item")
    @Expose
    private List<AddItem> addItem = new ArrayList<AddItem>();
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
    private final static long serialVersionUID = -844642462847828297L;

    protected Datum(android.os.Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.pembayaranCode = ((String) in.readValue((String.class.getClassLoader())));
        this.createuser = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.totalPaymentPaid = ((int) in.readValue((int.class.getClassLoader())));
        this.change = ((int) in.readValue((int.class.getClassLoader())));
        this.dateTransaction = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.identitasCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.nomorCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.ongkosKirim = ((int) in.readValue((int.class.getClassLoader())));
        this.tanggalKirim = ((String) in.readValue((String.class.getClassLoader())));
        this.flagDp = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.alamatCustomerPos = ((String) in.readValue((String.class.getClassLoader())));
        this.alamatPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.statusPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.catatanPengiriman = ((String) in.readValue((String.class.getClassLoader())));
        this.namaPenerima = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggalDiterima = ((String) in.readValue((String.class.getClassLoader())));
        this.flagKirim = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.totalBelanja = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.orderDetail, (com.dbelgamembership.membersip.Model.ModelPayment.OrderDetail.class.getClassLoader()));
        in.readList(this.paymentDetail, (com.dbelgamembership.membersip.Model.ModelPayment.PaymentDetail.class.getClassLoader()));
        in.readList(this.addItem, (com.dbelgamembership.membersip.Model.ModelPayment.AddItem.class.getClassLoader()));
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

    public String getPembayaranCode() {
        return pembayaranCode;
    }

    public void setPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
    }

    public Datum withPembayaranCode(String pembayaranCode) {
        this.pembayaranCode = pembayaranCode;
        return this;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Datum withCreateuser(String createuser) {
        this.createuser = createuser;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Datum withCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public int getTotalPaymentPaid() {
        return totalPaymentPaid;
    }

    public void setTotalPaymentPaid(int totalPaymentPaid) {
        this.totalPaymentPaid = totalPaymentPaid;
    }

    public Datum withTotalPaymentPaid(int totalPaymentPaid) {
        this.totalPaymentPaid = totalPaymentPaid;
        return this;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public Datum withChange(int change) {
        this.change = change;
        return this;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Datum withDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Datum withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getIdentitasCustomer() {
        return identitasCustomer;
    }

    public void setIdentitasCustomer(String identitasCustomer) {
        this.identitasCustomer = identitasCustomer;
    }

    public Datum withIdentitasCustomer(String identitasCustomer) {
        this.identitasCustomer = identitasCustomer;
        return this;
    }

    public String getAlamatCustomer() {
        return alamatCustomer;
    }

    public void setAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
    }

    public Datum withAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
        return this;
    }

    public String getNomorCustomer() {
        return nomorCustomer;
    }

    public void setNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
    }

    public Datum withNomorCustomer(String nomorCustomer) {
        this.nomorCustomer = nomorCustomer;
        return this;
    }

    public int getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(int ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
    }

    public Datum withOngkosKirim(int ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
        return this;
    }

    public String getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public Datum withTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
        return this;
    }

    public boolean isFlagDp() {
        return flagDp;
    }

    public void setFlagDp(boolean flagDp) {
        this.flagDp = flagDp;
    }

    public Datum withFlagDp(boolean flagDp) {
        this.flagDp = flagDp;
        return this;
    }

    public String getAlamatCustomerPos() {
        return alamatCustomerPos;
    }

    public void setAlamatCustomerPos(String alamatCustomerPos) {
        this.alamatCustomerPos = alamatCustomerPos;
    }

    public Datum withAlamatCustomerPos(String alamatCustomerPos) {
        this.alamatCustomerPos = alamatCustomerPos;
        return this;
    }

    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
    }

    public Datum withAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
        return this;
    }

    public String getStatusPengiriman() {
        return statusPengiriman;
    }

    public void setStatusPengiriman(String statusPengiriman) {
        this.statusPengiriman = statusPengiriman;
    }

    public Datum withStatusPengiriman(String statusPengiriman) {
        this.statusPengiriman = statusPengiriman;
        return this;
    }

    public String getCatatanPengiriman() {
        return catatanPengiriman;
    }

    public void setCatatanPengiriman(String catatanPengiriman) {
        this.catatanPengiriman = catatanPengiriman;
    }

    public Datum withCatatanPengiriman(String catatanPengiriman) {
        this.catatanPengiriman = catatanPengiriman;
        return this;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public Datum withNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
        return this;
    }

    public String getTanggalDiterima() {
        return tanggalDiterima;
    }

    public void setTanggalDiterima(String tanggalDiterima) {
        this.tanggalDiterima = tanggalDiterima;
    }

    public Datum withTanggalDiterima(String tanggalDiterima) {
        this.tanggalDiterima = tanggalDiterima;
        return this;
    }

    public boolean isFlagKirim() {
        return flagKirim;
    }

    public void setFlagKirim(boolean flagKirim) {
        this.flagKirim = flagKirim;
    }

    public Datum withFlagKirim(boolean flagKirim) {
        this.flagKirim = flagKirim;
        return this;
    }

    public String getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(String totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public Datum withTotalBelanja(String totalBelanja) {
        this.totalBelanja = totalBelanja;
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

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Datum withOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
        return this;
    }

    public List<PaymentDetail> getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(List<PaymentDetail> paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public Datum withPaymentDetail(List<PaymentDetail> paymentDetail) {
        this.paymentDetail = paymentDetail;
        return this;
    }

    public List<AddItem> getAddItem() {
        return addItem;
    }

    public void setAddItem(List<AddItem> addItem) {
        this.addItem = addItem;
    }

    public Datum withAddItem(List<AddItem> addItem) {
        this.addItem = addItem;
        return this;
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
        dest.writeList(orderDetail);
        dest.writeList(paymentDetail);
        dest.writeList(addItem);
    }

    public int describeContents() {
        return  0;
    }

}
