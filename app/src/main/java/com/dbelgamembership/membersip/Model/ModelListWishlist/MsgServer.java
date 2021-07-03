
package com.dbelgamembership.membersip.Model.ModelListWishlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MsgServer implements Serializable, Parcelable
{

    @SerializedName("id_customer")
    @Expose
    private int idCustomer;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("image_customer")
    @Expose
    private String imageCustomer;
    @SerializedName("status_member")
    @Expose
    private String statusMember;
    @SerializedName("wishlist_detail")
    @Expose
    private List<WishlistDetail> wishlistDetail = new ArrayList<WishlistDetail>();
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
    private final static long serialVersionUID = 3173106352440995246L;

    protected MsgServer(android.os.Parcel in) {
        this.idCustomer = ((int) in.readValue((int.class.getClassLoader())));
        this.customer = ((String) in.readValue((String.class.getClassLoader())));
        this.imageCustomer = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMember = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.wishlistDetail, (com.dbelgamembership.membersip.Model.ModelListWishlist.WishlistDetail.class.getClassLoader()));
    }

    public MsgServer() {
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public MsgServer withIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public MsgServer withCustomer(String customer) {
        this.customer = customer;
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

    public List<WishlistDetail> getWishlistDetail() {
        return wishlistDetail;
    }

    public void setWishlistDetail(List<WishlistDetail> wishlistDetail) {
        this.wishlistDetail = wishlistDetail;
    }

    public MsgServer withWishlistDetail(List<WishlistDetail> wishlistDetail) {
        this.wishlistDetail = wishlistDetail;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(idCustomer);
        dest.writeValue(customer);
        dest.writeValue(imageCustomer);
        dest.writeValue(statusMember);
        dest.writeList(wishlistDetail);
    }

    public int describeContents() {
        return  0;
    }

}
