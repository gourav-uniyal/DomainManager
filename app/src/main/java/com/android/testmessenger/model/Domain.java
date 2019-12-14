package com.android.testmessenger.model;

import com.google.gson.annotations.SerializedName;

public class Domain {

    @SerializedName( "registrant_name" )
    private String name;
    @SerializedName( "domain_name" )
    private String domainName;
    @SerializedName( "registrant_phone" )
    private String registrantNumber;
    @SerializedName( "registrant_country" )
    private String registrantCountry;
    /*@SerializedName( "city" )
    private String city;
    @SerializedName( "registrant_address" )
    private String companyName;*/

    public String getRegistrantNumber() {
        return registrantNumber;
    }

    public void setRegistrantNumber(String registrantNumber) {
        this.registrantNumber = registrantNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getRegistrantCountry() {
        return registrantCountry;
    }

    public void setRegistrantCountry(String registrantCountry) {
        this.registrantCountry = registrantCountry;
    }

    /*public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }*/
}
