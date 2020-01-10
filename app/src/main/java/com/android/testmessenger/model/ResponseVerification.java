package com.android.testmessenger.model;

import com.google.gson.annotations.SerializedName;

public class ResponseVerification {

    @SerializedName( "status" )
    private String status;
    @SerializedName( "user_id" )
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
