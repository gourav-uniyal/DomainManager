package com.android.testmessenger.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseDomainList {

    @SerializedName( "current_page" )
    private String page;
    @SerializedName( "last_page" )
    private String lastPage;
    @SerializedName( "data" )
    private ArrayList<Domain> domainArrayList;

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArrayList<Domain> getDomainArrayList() {
        return domainArrayList;
    }

    public void setDomainArrayList(ArrayList<Domain> domainArrayList) {
        this.domainArrayList = domainArrayList;
    }
}
