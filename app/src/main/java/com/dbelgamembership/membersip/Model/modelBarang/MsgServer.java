
package com.dbelgamembership.membersip.Model.modelBarang;

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

    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("last_page")
    @Expose
    private Integer lastPage;
    @SerializedName("next_page_url")
    @Expose
    private String nextPageUrl;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("prev_page_url")
    @Expose
    private String prevPageUrl;
    @SerializedName("to")
    @Expose
    private Integer to;
    @SerializedName("total")
    @Expose
    private Integer total;
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
    private final static long serialVersionUID = -5228128806518690249L;

    protected MsgServer(android.os.Parcel in) {
        this.currentPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (com.dbelgamembership.membersip.Model.modelBarang.Datum.class.getClassLoader()));
        this.from = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.lastPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.nextPageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.path = ((String) in.readValue((String.class.getClassLoader())));
        this.perPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.prevPageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.to = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.total = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MsgServer() {
    }

    /**
     * 
     * @param path
     * @param total
     * @param nextPageUrl
     * @param perPage
     * @param data
     * @param lastPage
     * @param from
     * @param to
     * @param currentPage
     * @param prevPageUrl
     */
    public MsgServer(Integer currentPage, List<Datum> data, Integer from, Integer lastPage, String nextPageUrl, String path, Integer perPage, String prevPageUrl, Integer to, Integer total) {
        super();
        this.currentPage = currentPage;
        this.data = data;
        this.from = from;
        this.lastPage = lastPage;
        this.nextPageUrl = nextPageUrl;
        this.path = path;
        this.perPage = perPage;
        this.prevPageUrl = prevPageUrl;
        this.to = to;
        this.total = total;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
