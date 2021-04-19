
package com.dbelgamembership.membersip.Model.modelBarang;

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

    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("from")
    @Expose
    private int from;
    @SerializedName("last_page")
    @Expose
    private int lastPage;
    @SerializedName("next_page_url")
    @Expose
    private String nextPageUrl;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("per_page")
    @Expose
    private int perPage;
    @SerializedName("prev_page_url")
    @Expose
    private String prevPageUrl;
    @SerializedName("to")
    @Expose
    private int to;
    @SerializedName("total")
    @Expose
    private int total;
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
    private final static long serialVersionUID = 2980450578661763053L;

    protected MsgServer(android.os.Parcel in) {
        this.currentPage = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.data, (com.dbelgamembership.membersip.Model.modelBarang.Datum.class.getClassLoader()));
        this.from = ((int) in.readValue((int.class.getClassLoader())));
        this.lastPage = ((int) in.readValue((int.class.getClassLoader())));
        this.nextPageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.path = ((String) in.readValue((String.class.getClassLoader())));
        this.perPage = ((int) in.readValue((int.class.getClassLoader())));
        this.prevPageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.to = ((int) in.readValue((int.class.getClassLoader())));
        this.total = ((int) in.readValue((int.class.getClassLoader())));
    }

    public MsgServer() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public MsgServer withCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public MsgServer withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public MsgServer withFrom(int from) {
        this.from = from;
        return this;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public MsgServer withLastPage(int lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public MsgServer withNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
        return this;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MsgServer withPath(String path) {
        this.path = path;
        return this;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public MsgServer withPerPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public MsgServer withPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
        return this;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public MsgServer withTo(int to) {
        this.to = to;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public MsgServer withTotal(int total) {
        this.total = total;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(currentPage);
        dest.writeList(data);
        dest.writeValue(from);
        dest.writeValue(lastPage);
        dest.writeValue(nextPageUrl);
        dest.writeValue(path);
        dest.writeValue(perPage);
        dest.writeValue(prevPageUrl);
        dest.writeValue(to);
        dest.writeValue(total);
    }

    public int describeContents() {
        return  0;
    }

}
