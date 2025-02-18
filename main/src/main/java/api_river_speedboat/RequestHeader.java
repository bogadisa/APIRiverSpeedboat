package api_river_speedboat;

import com.google.gson.annotations.SerializedName;

public class RequestHeader {
    @SerializedName("User-Agent")
    private String UserAgent;

    private String Authorization;

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }
}
