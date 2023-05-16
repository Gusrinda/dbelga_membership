
package com.dbelgamembership.membersip.Model.ResponseVersi;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MsgServer implements Parcelable
{

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("link")
    @Expose
    private String link;
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

    protected MsgServer(android.os.Parcel in) {
        this.version = ((String) in.readValue((String.class.getClassLoader())));
        this.note = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MsgServer() {
    }

    /**
     * 
     * @param note
     * @param link
     * @param version
     */
    public MsgServer(String version, String note, String link) {
        super();
        this.version = version;
        this.note = note;
        this.link = link;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(version);
        dest.writeValue(note);
        dest.writeValue(link);
    }

    public int describeContents() {
        return  0;
    }

}
