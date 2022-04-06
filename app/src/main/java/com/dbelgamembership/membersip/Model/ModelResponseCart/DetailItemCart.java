
package com.dbelgamembership.membersip.Model.ModelResponseCart;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DetailItemCart implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;
    @SerializedName("produk")
    @Expose
    private Integer produk;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("merek")
    @Expose
    private String merek;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("nama_promo")
    @Expose
    private String namaPromo;
    @SerializedName("produk_promo")
    @Expose
    private Boolean produkPromo;
    @SerializedName("price_promo")
    @Expose
    private String pricePromo;
    @SerializedName("mulai_promo")
    @Expose
    private String mulaiPromo;
    @SerializedName("akhir_promo")
    @Expose
    private String akhirPromo;
    @SerializedName("qty_promo")
    @Expose
    private String qtyPromo;
    @SerializedName("kode_promo")
    @Expose
    private String kodePromo;
    @SerializedName("save_qty")
    @Expose
    private String saveQty;
    @SerializedName("produk_promo_member")
    @Expose
    private Boolean produkPromoMember;
    @SerializedName("persen_promo_member_gold")
    @Expose
    private String persenPromoMemberGold;
    @SerializedName("persen_promo_member_silver")
    @Expose
    private String persenPromoMemberSilver;
    @SerializedName("persen_promo_member_platinum")
    @Expose
    private String persenPromoMemberPlatinum;
    @SerializedName("mulai_promo_member")
    @Expose
    private String mulaiPromoMember;
    @SerializedName("akhir_promo_member")
    @Expose
    private String akhirPromoMember;
    @SerializedName("qty_promo_member")
    @Expose
    private String qtyPromoMember;
    @SerializedName("kode_promo_member")
    @Expose
    private String kodePromoMember;
    @SerializedName("tipe_promo")
    @Expose
    private String tipePromo;
    @SerializedName("tipe_promo_member")
    @Expose
    private String tipePromoMember;
    @SerializedName("nama_voucher_supplier")
    @Expose
    private String namaVoucherSupplier;
    @SerializedName("produk_voucher_supplier")
    @Expose
    private Boolean produkVoucherSupplier;
    @SerializedName("mulai_voucher_supplier")
    @Expose
    private String mulaiVoucherSupplier;
    @SerializedName("akhir_voucher_supplier")
    @Expose
    private String akhirVoucherSupplier;
    @SerializedName("kode_voucher_supplier")
    @Expose
    private String kodeVoucherSupplier;
    @SerializedName("tipe_voucher")
    @Expose
    private String tipeVoucher;
    @SerializedName("tipe_promo_voucher")
    @Expose
    private String tipePromoVoucher;
    @SerializedName("minimal_belanja")
    @Expose
    private Integer minimalBelanja;

    @SerializedName("potongan_belanja")
    @Expose
    private Integer potonganVoucherSuplier;

    @SerializedName("qty_kelipatan")
    @Expose
    private Integer qtyKelipatan;
    @SerializedName("stok_voucher")
    @Expose
    private Integer stokVoucher;
    @SerializedName("stok")
    @Expose
    private Double stok;
    @SerializedName("stok_promo")
    @Expose
    private Double stokPromo;
    @SerializedName("stok_promo_member")
    @Expose
    private Double stokPromoMember;
    @SerializedName("harga")
    @Expose
    private Harga harga;
    @SerializedName("supplier")
    @Expose
    private List<String> supplier = null;
    public final static Creator<DetailItemCart> CREATOR = new Creator<DetailItemCart>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailItemCart createFromParcel(android.os.Parcel in) {
            return new DetailItemCart(in);
        }

        public DetailItemCart[] newArray(int size) {
            return (new DetailItemCart[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5387383894416123881L;

    protected DetailItemCart(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.namaProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.produk = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.barcode = ((String) in.readValue((String.class.getClassLoader())));
        this.qty = ((String) in.readValue((String.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.merek = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.namaPromo = ((String) in.readValue((String.class.getClassLoader())));
        this.produkPromo = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pricePromo = ((String) in.readValue((String.class.getClassLoader())));
        this.mulaiPromo = ((String) in.readValue((String.class.getClassLoader())));
        this.akhirPromo = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyPromo = ((String) in.readValue((String.class.getClassLoader())));
        this.kodePromo = ((String) in.readValue((String.class.getClassLoader())));
        this.saveQty = ((String) in.readValue((String.class.getClassLoader())));
        this.produkPromoMember = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.persenPromoMemberGold = ((String) in.readValue((String.class.getClassLoader())));
        this.persenPromoMemberSilver = ((String) in.readValue((String.class.getClassLoader())));
        this.persenPromoMemberPlatinum = ((String) in.readValue((String.class.getClassLoader())));
        this.mulaiPromoMember = ((String) in.readValue((String.class.getClassLoader())));
        this.akhirPromoMember = ((String) in.readValue((String.class.getClassLoader())));
        this.qtyPromoMember = ((String) in.readValue((String.class.getClassLoader())));
        this.kodePromoMember = ((String) in.readValue((String.class.getClassLoader())));
        this.tipePromo = ((String) in.readValue((String.class.getClassLoader())));
        this.tipePromoMember = ((String) in.readValue((String.class.getClassLoader())));
        this.namaVoucherSupplier = ((String) in.readValue((String.class.getClassLoader())));
        this.produkVoucherSupplier = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.mulaiVoucherSupplier = ((String) in.readValue((String.class.getClassLoader())));
        this.akhirVoucherSupplier = ((String) in.readValue((String.class.getClassLoader())));
        this.kodeVoucherSupplier = ((String) in.readValue((String.class.getClassLoader())));
        this.tipeVoucher = ((String) in.readValue((String.class.getClassLoader())));
        this.tipePromoVoucher = ((String) in.readValue((String.class.getClassLoader())));
        this.minimalBelanja = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.qtyKelipatan = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stokVoucher = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.potonganVoucherSuplier = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stok = ((Double) in.readValue((Double.class.getClassLoader())));
        this.stokPromo = ((Double) in.readValue((Double.class.getClassLoader())));
        this.stokPromoMember = ((Double) in.readValue((Double.class.getClassLoader())));
        this.harga = ((Harga) in.readValue((Harga.class.getClassLoader())));
        in.readList(this.supplier, (java.lang.String.class.getClassLoader()));
    }

    public DetailItemCart() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public Integer getProduk() {
        return produk;
    }

    public void setProduk(Integer produk) {
        this.produk = produk;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNamaPromo() {
        return namaPromo;
    }

    public void setNamaPromo(String namaPromo) {
        this.namaPromo = namaPromo;
    }

    public Boolean getProdukPromo() {
        return produkPromo;
    }

    public void setProdukPromo(Boolean produkPromo) {
        this.produkPromo = produkPromo;
    }

    public String getPricePromo() {
        return pricePromo;
    }

    public void setPricePromo(String pricePromo) {
        this.pricePromo = pricePromo;
    }

    public String getMulaiPromo() {
        return mulaiPromo;
    }

    public void setMulaiPromo(String mulaiPromo) {
        this.mulaiPromo = mulaiPromo;
    }

    public String getAkhirPromo() {
        return akhirPromo;
    }

    public void setAkhirPromo(String akhirPromo) {
        this.akhirPromo = akhirPromo;
    }

    public String getQtyPromo() {
        return qtyPromo;
    }

    public void setQtyPromo(String qtyPromo) {
        this.qtyPromo = qtyPromo;
    }

    public String getKodePromo() {
        return kodePromo;
    }

    public void setKodePromo(String kodePromo) {
        this.kodePromo = kodePromo;
    }

    public String getSaveQty() {
        return saveQty;
    }

    public void setSaveQty(String saveQty) {
        this.saveQty = saveQty;
    }

    public Boolean getProdukPromoMember() {
        return produkPromoMember;
    }

    public void setProdukPromoMember(Boolean produkPromoMember) {
        this.produkPromoMember = produkPromoMember;
    }

    public String getPersenPromoMemberGold() {
        return persenPromoMemberGold;
    }

    public void setPersenPromoMemberGold(String persenPromoMemberGold) {
        this.persenPromoMemberGold = persenPromoMemberGold;
    }

    public String getPersenPromoMemberSilver() {
        return persenPromoMemberSilver;
    }

    public void setPersenPromoMemberSilver(String persenPromoMemberSilver) {
        this.persenPromoMemberSilver = persenPromoMemberSilver;
    }

    public String getPersenPromoMemberPlatinum() {
        return persenPromoMemberPlatinum;
    }

    public void setPersenPromoMemberPlatinum(String persenPromoMemberPlatinum) {
        this.persenPromoMemberPlatinum = persenPromoMemberPlatinum;
    }

    public String getMulaiPromoMember() {
        return mulaiPromoMember;
    }

    public void setMulaiPromoMember(String mulaiPromoMember) {
        this.mulaiPromoMember = mulaiPromoMember;
    }

    public String getAkhirPromoMember() {
        return akhirPromoMember;
    }

    public void setAkhirPromoMember(String akhirPromoMember) {
        this.akhirPromoMember = akhirPromoMember;
    }

    public String getQtyPromoMember() {
        return qtyPromoMember;
    }

    public void setQtyPromoMember(String qtyPromoMember) {
        this.qtyPromoMember = qtyPromoMember;
    }

    public String getKodePromoMember() {
        return kodePromoMember;
    }

    public void setKodePromoMember(String kodePromoMember) {
        this.kodePromoMember = kodePromoMember;
    }

    public String getTipePromo() {
        return tipePromo;
    }

    public void setTipePromo(String tipePromo) {
        this.tipePromo = tipePromo;
    }

    public String getTipePromoMember() {
        return tipePromoMember;
    }

    public void setTipePromoMember(String tipePromoMember) {
        this.tipePromoMember = tipePromoMember;
    }

    public String getNamaVoucherSupplier() {
        return namaVoucherSupplier;
    }

    public void setNamaVoucherSupplier(String namaVoucherSupplier) {
        this.namaVoucherSupplier = namaVoucherSupplier;
    }

    public Boolean getProdukVoucherSupplier() {
        return produkVoucherSupplier;
    }

    public void setProdukVoucherSupplier(Boolean produkVoucherSupplier) {
        this.produkVoucherSupplier = produkVoucherSupplier;
    }

    public String getMulaiVoucherSupplier() {
        return mulaiVoucherSupplier;
    }

    public void setMulaiVoucherSupplier(String mulaiVoucherSupplier) {
        this.mulaiVoucherSupplier = mulaiVoucherSupplier;
    }

    public String getAkhirVoucherSupplier() {
        return akhirVoucherSupplier;
    }

    public void setAkhirVoucherSupplier(String akhirVoucherSupplier) {
        this.akhirVoucherSupplier = akhirVoucherSupplier;
    }

    public String getKodeVoucherSupplier() {
        return kodeVoucherSupplier;
    }

    public void setKodeVoucherSupplier(String kodeVoucherSupplier) {
        this.kodeVoucherSupplier = kodeVoucherSupplier;
    }

    public String getTipeVoucher() {
        return tipeVoucher;
    }

    public void setTipeVoucher(String tipeVoucher) {
        this.tipeVoucher = tipeVoucher;
    }

    public String getTipePromoVoucher() {
        return tipePromoVoucher;
    }

    public void setTipePromoVoucher(String tipePromoVoucher) {
        this.tipePromoVoucher = tipePromoVoucher;
    }

    public Integer getMinimalBelanja() {
        return minimalBelanja;
    }

    public void setMinimalBelanja(Integer minimalBelanja) {
        this.minimalBelanja = minimalBelanja;
    }

    public Integer getQtyKelipatan() {
        return qtyKelipatan;
    }

    public void setQtyKelipatan(Integer qtyKelipatan) {
        this.qtyKelipatan = qtyKelipatan;
    }

    public Integer getStokVoucher() {
        return stokVoucher;
    }

    public void setStokVoucher(Integer stokVoucher) {
        this.stokVoucher = stokVoucher;
    }

    public Double getStok() {
        return stok;
    }

    public void setStok(Double stok) {
        this.stok = stok;
    }

    public Double getStokPromo() {
        return stokPromo;
    }

    public void setStokPromo(Double stokPromo) {
        this.stokPromo = stokPromo;
    }

    public Double getStokPromoMember() {
        return stokPromoMember;
    }

    public void setStokPromoMember(Double stokPromoMember) {
        this.stokPromoMember = stokPromoMember;
    }

    public Harga getHarga() {
        return harga;
    }

    public void setHarga(Harga harga) {
        this.harga = harga;
    }


    public List<String> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<String> supplier) {
        this.supplier = supplier;
    }

    public Integer getPotonganVoucherSuplier() {
        return potonganVoucherSuplier;
    }

    public void setPotonganVoucherSuplier(Integer potonganVoucherSuplier) {
        this.potonganVoucherSuplier = potonganVoucherSuplier;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(namaProduk);
        dest.writeValue(produk);
        dest.writeValue(barcode);
        dest.writeValue(qty);
        dest.writeValue(images);
        dest.writeValue(merek);
        dest.writeValue(code);
        dest.writeValue(namaPromo);
        dest.writeValue(produkPromo);
        dest.writeValue(pricePromo);
        dest.writeValue(mulaiPromo);
        dest.writeValue(akhirPromo);
        dest.writeValue(qtyPromo);
        dest.writeValue(kodePromo);
        dest.writeValue(saveQty);
        dest.writeValue(produkPromoMember);
        dest.writeValue(persenPromoMemberGold);
        dest.writeValue(persenPromoMemberSilver);
        dest.writeValue(persenPromoMemberPlatinum);
        dest.writeValue(mulaiPromoMember);
        dest.writeValue(akhirPromoMember);
        dest.writeValue(qtyPromoMember);
        dest.writeValue(kodePromoMember);
        dest.writeValue(tipePromo);
        dest.writeValue(tipePromoMember);
        dest.writeValue(namaVoucherSupplier);
        dest.writeValue(produkVoucherSupplier);
        dest.writeValue(mulaiVoucherSupplier);
        dest.writeValue(akhirVoucherSupplier);
        dest.writeValue(kodeVoucherSupplier);
        dest.writeValue(tipeVoucher);
        dest.writeValue(tipePromoVoucher);
        dest.writeValue(minimalBelanja);
        dest.writeValue(qtyKelipatan);
        dest.writeValue(stokVoucher);
        dest.writeValue(potonganVoucherSuplier);
        dest.writeValue(stok);
        dest.writeValue(stokPromo);
        dest.writeValue(stokPromoMember);
        dest.writeValue(harga);
        dest.writeList(supplier);
    }

    public int describeContents() {
        return  0;
    }

}
