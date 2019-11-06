package com.android.testmessenger;

public class Domain {

    private String name;
    private String domainName;
    private String city;
    private String companyName;
    private String number;
    private String whatsapp;
    private String message;

    public Domain(String name, String domainName, String city, String companyName, String number, String whatsapp, String message) {
        this.name = name;
        this.domainName = domainName;
        this.city = city;
        this.companyName = companyName;
        this.number = number;
        this.whatsapp = whatsapp;
        this.message = message;
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

    public String getCity() {
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
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
