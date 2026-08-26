package com.dbelgamembership.membersip.Screen.SetupOTP.Model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class DataMemberSementara implements Parcelable {

    private boolean isOpeningPremium;
    private String statusMembership;
    private String idMembership;
    private String namaMembership;
    private String nomorMembership;

    public DataMemberSementara() {
    }

    public DataMemberSementara(boolean isOpeningPremium, String statusMembership, String idMembership, String namaMembership, String nomorMembership) {
        this.isOpeningPremium = isOpeningPremium;
        this.statusMembership = statusMembership;
        this.idMembership = idMembership;
        this.namaMembership = namaMembership;
        this.nomorMembership = nomorMembership;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected DataMemberSementara(Parcel in) {
        isOpeningPremium = in.readBoolean();
        statusMembership = in.readString();
        idMembership = in.readString();
        namaMembership = in.readString();
        nomorMembership = in.readString();
    }

    public static final Creator<DataMemberSementara> CREATOR = new Creator<DataMemberSementara>() {
        @Override
        public DataMemberSementara createFromParcel(Parcel in) {
            return new DataMemberSementara(in);
        }

        @Override
        public DataMemberSementara[] newArray(int size) {
            return new DataMemberSementara[size];
        }
    };

    public String getStatusMembership() {
        return statusMembership;
    }

    public void setStatusMembership(String statusMembership) {
        this.statusMembership = statusMembership;
    }

    public String getIdMembership() {
        return idMembership;
    }

    public void setIdMembership(String idMembership) {
        this.idMembership = idMembership;
    }

    public String getNamaMembership() {
        return namaMembership;
    }

    public void setNamaMembership(String namaMembership) {
        this.namaMembership = namaMembership;
    }

    public String getNomorMembership() {
        return nomorMembership;
    }

    public void setNomorMembership(String nomorMembership) {
        this.nomorMembership = nomorMembership;
    }

    public boolean isOpeningPremium() {
        return isOpeningPremium;
    }

    public void setOpeningPremium(boolean openingPremium) {
        isOpeningPremium = openingPremium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(isOpeningPremium);
        parcel.writeString(statusMembership);
        parcel.writeString(idMembership);
        parcel.writeString(namaMembership);
        parcel.writeString(nomorMembership);
    }
}
