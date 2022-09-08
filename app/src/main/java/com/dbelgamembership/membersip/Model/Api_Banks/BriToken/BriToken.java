
package com.dbelgamembership.membersip.Model.Api_Banks.BriToken;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BriToken implements Serializable, Parcelable
{

    @SerializedName("refresh_token_expires_in")
    @Expose
    private String refreshTokenExpiresIn;
    @SerializedName("api_product_list")
    @Expose
    private String apiProductList;
    @SerializedName("api_product_list_json")
    @Expose
    private List<String> apiProductListJson = null;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("developer.email")
    @Expose
    private String developerEmail;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("issued_at")
    @Expose
    private String issuedAt;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("application_name")
    @Expose
    private String applicationName;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("expires_in")
    @Expose
    private String expiresIn;
    @SerializedName("refresh_count")
    @Expose
    private String refreshCount;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<BriToken> CREATOR = new Creator<BriToken>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BriToken createFromParcel(android.os.Parcel in) {
            return new BriToken(in);
        }

        public BriToken[] newArray(int size) {
            return (new BriToken[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5475193034788228853L;

    protected BriToken(android.os.Parcel in) {
        this.refreshTokenExpiresIn = ((String) in.readValue((String.class.getClassLoader())));
        this.apiProductList = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.apiProductListJson, (java.lang.String.class.getClassLoader()));
        this.organizationName = ((String) in.readValue((String.class.getClassLoader())));
        this.developerEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.tokenType = ((String) in.readValue((String.class.getClassLoader())));
        this.issuedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
        this.accessToken = ((String) in.readValue((String.class.getClassLoader())));
        this.applicationName = ((String) in.readValue((String.class.getClassLoader())));
        this.scope = ((String) in.readValue((String.class.getClassLoader())));
        this.expiresIn = ((String) in.readValue((String.class.getClassLoader())));
        this.refreshCount = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public BriToken() {
    }

    public String getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public void setRefreshTokenExpiresIn(String refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getApiProductList() {
        return apiProductList;
    }

    public void setApiProductList(String apiProductList) {
        this.apiProductList = apiProductList;
    }

    public List<String> getApiProductListJson() {
        return apiProductListJson;
    }

    public void setApiProductListJson(List<String> apiProductListJson) {
        this.apiProductListJson = apiProductListJson;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public void setDeveloperEmail(String developerEmail) {
        this.developerEmail = developerEmail;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(String refreshCount) {
        this.refreshCount = refreshCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(refreshTokenExpiresIn);
        dest.writeValue(apiProductList);
        dest.writeList(apiProductListJson);
        dest.writeValue(organizationName);
        dest.writeValue(developerEmail);
        dest.writeValue(tokenType);
        dest.writeValue(issuedAt);
        dest.writeValue(clientId);
        dest.writeValue(accessToken);
        dest.writeValue(applicationName);
        dest.writeValue(scope);
        dest.writeValue(expiresIn);
        dest.writeValue(refreshCount);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
