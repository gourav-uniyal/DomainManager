package com.android.testmessenger.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDomain {

    @SerializedName( "status" )
    private String status;
    @SerializedName( "domains" )
    private ResponseDomainList responseDomainList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseDomainList getResponseDomainList() {
        return responseDomainList;
    }

    public void setResponseDomainList(ResponseDomainList responseDomainList) {
        this.responseDomainList = responseDomainList;
    }
}

