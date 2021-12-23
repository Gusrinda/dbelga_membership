
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
public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("id_customer")
    @Expose
    private Integer idCustomer;
    @SerializedName("nama_customer")
    @Expose
    private String namaCustomer;
    @SerializedName("id_gudang")
    @Expose
    private Integer idGudang;
    @SerializedName("nama_gudang")
    @Expose
    private String namaGudang;
    @SerializedName("total_item")
    @Expose
    private double totalItem;
    @SerializedName("total_qty")
    @Expose
    private String totalQty;
    @SerializedName("total_cart")
    @Expose
    private String totalCart;
    @SerializedName("detail_item_cart")
    @Expose
    private List<DetailItemCart> detailItemCart = null;
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
    private final static long serialVersionUID = -8165707101030848236L;

    protected MsgServer(android.os.Parcel in) {
        this.idCustomer = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.namaCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.idGudang = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.namaGudang = ((String) in.readValue((String.class.getClassLoader())));
        this.totalItem = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalQty = ((String) in.readValue((String.class.getClassLoader())));
        this.totalCart = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.detailItemCart, (com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart.class.getClassLoader()));
    }

    public MsgServer() {
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public Integer getIdGudang() {
        return idGudang;
    }

    public void setIdGudang(Integer idGudang) {
        this.idGudang = idGudang;
    }

    public String getNamaGudang() {
        return namaGudang;
    }

    public void setNamaGudang(String namaGudang) {
        this.namaGudang = namaGudang;
    }

    public double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(double totalItem) {
        this.totalItem = totalItem;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getTotalCart() {
        return totalCart;
    }

    public void setTotalCart(String totalCart) {
        this.totalCart = totalCart;
    }

    public List<DetailItemCart> getDetailItemCart() {
        return detailItemCart;
    }

    public void setDetailItemCart(List<DetailItemCart> detailItemCart) {
        this.detailItemCart = detailItemCart;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(idCustomer);
        dest.writeValue(namaCustomer);
        dest.writeValue(idGudang);
        dest.writeValue(namaGudang);
        dest.writeValue(totalItem);
        dest.writeValue(totalQty);
        dest.writeValue(totalCart);
        dest.writeList(detailItemCart);
    }

    public int describeContents() {
        return  0;
    }

}
