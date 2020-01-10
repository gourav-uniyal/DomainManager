package com.android.testmessenger.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseFilters {
    @SerializedName( "status" )
    private String status;
    @SerializedName( "year" )
    private ArrayList<String> yearArrayList;
    @SerializedName( "date" )
    private ArrayList<String> dateArrayList;
    @SerializedName( "month" )
    private ArrayList<Month> monthArrayList;
    @SerializedName( "countries" )
    private ArrayList<String> countryArrayList;

    public ArrayList<String> getYearArrayList() {
        return yearArrayList;
    }

    public void setYearArrayList(ArrayList<String> yearArrayList) {
        this.yearArrayList = yearArrayList;
    }

    public ArrayList<String> getDateArrayList() {
        return dateArrayList;
    }

    public void setDateArrayList(ArrayList<String> dateArrayList) {
        this.dateArrayList = dateArrayList;
    }

    public ArrayList<Month> getMonthArrayList() {
        return monthArrayList;
    }

    public void setMonthArrayList(ArrayList<Month> monthArrayList) {
        this.monthArrayList = monthArrayList;
    }

    public ArrayList<String> getCountryArrayList() {
        return countryArrayList;
    }

    public void setCountryArrayList(ArrayList<String> countryArrayList) {
        this.countryArrayList = countryArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
