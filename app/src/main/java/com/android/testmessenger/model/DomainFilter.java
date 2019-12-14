package com.android.testmessenger.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DomainFilter {

    @SerializedName( "status" )
    private String status;
    @SerializedName( "year" )
    private ArrayList<String> yearArray;
    @SerializedName( "month" )
    private ArrayList<String> monthArray;
    @SerializedName( "date" )
    private ArrayList<String> dateArray;
    @SerializedName( "country" )
    private ArrayList<String> countryArray;

   /* @SerializedName( "state" )
    private ArrayList<String> stateArray;
    @SerializedName( "city" )
    private ArrayList<String> cityArray;*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getYearArray() {
        return yearArray;
    }

    public void setYearArray(ArrayList<String> yearArray) {
        this.yearArray = yearArray;
    }

    public ArrayList<String> getMonthArray() {
        return monthArray;
    }

    public void setMonthArray(ArrayList<String> monthArray) {
        this.monthArray = monthArray;
    }

    public ArrayList<String> getDateArray() {
        return dateArray;
    }

    public void setDateArray(ArrayList<String> dateArray) {
        this.dateArray = dateArray;
    }

    public ArrayList<String> getCountryArray() {
        return countryArray;
    }

    public void setCountryArray(ArrayList<String> countryArray) {
        this.countryArray = countryArray;
    }

    /*
    public ArrayList<String> getStateArray() {
        return stateArray;
    }

    public void setStateArray(ArrayList<String> stateArray) {
        this.stateArray = stateArray;
    }

    public ArrayList<String> getCityArray() {
        return cityArray;
    }

    public void setCityArray(ArrayList<String> cityArray) {
        this.cityArray = cityArray;
    }*/
}