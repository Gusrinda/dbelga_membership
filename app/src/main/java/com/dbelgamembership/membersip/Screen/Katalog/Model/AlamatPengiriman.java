package com.dbelgamembership.membersip.Screen.Katalog.Model;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class AlamatPengiriman implements Parcelable {
    private LatLng latLng;
    private List<Address> address;
    private String alamatPengiriman;

    public AlamatPengiriman(LatLng latLng, List<Address> address, String alamatPengiriman) {
        this.latLng = latLng;
        this.address = address;
        this.alamatPengiriman = alamatPengiriman;
    }

    protected AlamatPengiriman(Parcel in) {
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        address = in.createTypedArrayList(Address.CREATOR);
        alamatPengiriman = in.readString();
    }

    public static final Creator<AlamatPengiriman> CREATOR = new Creator<AlamatPengiriman>() {
        @Override
        public AlamatPengiriman createFromParcel(Parcel in) {
            return new AlamatPengiriman(in);
        }

        @Override
        public AlamatPengiriman[] newArray(int size) {
            return new AlamatPengiriman[size];
        }
    };

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        this.alamatPengiriman = alamatPengiriman;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(latLng, i);
        parcel.writeTypedList(address);
        parcel.writeString(alamatPengiriman);
    }
}
