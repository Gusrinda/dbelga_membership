package com.dbelgamembership.membersip.Screen.Notifikasi.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class DataNotifikasi implements Parcelable {

    private String tipe;
    private String context;
    private String id;
    private String code;

    public DataNotifikasi() {
    }

    public DataNotifikasi(String tipe, String context, String id, String code) {
        this.tipe = tipe;
        this.context = context;
        this.id = id;
        this.code = code;
    }

    protected DataNotifikasi(Parcel in) {
        tipe = in.readString();
        context = in.readString();
        id = in.readString();
        code = in.readString();
    }

    public static final Creator<DataNotifikasi> CREATOR = new Creator<DataNotifikasi>() {
        @Override
        public DataNotifikasi createFromParcel(Parcel in) {
            return new DataNotifikasi(in);
        }

        @Override
        public DataNotifikasi[] newArray(int size) {
            return new DataNotifikasi[size];
        }
    };

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tipe);
        parcel.writeString(context);
        parcel.writeString(id);
        parcel.writeString(code);
    }
}

