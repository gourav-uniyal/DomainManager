package com.android.testmessenger;

public class Domain {

    private String name;
    private String domainName;
    private String city;
    private String companyName;
    private String phoneNumber;
    private String whatsappMessage;
    private String message;

    public Domain(String name, String domainName, String city, String companyName, String phoneNumber, String whatsappMessage, String message) {
        this.name = name;
        this.domainName = domainName;
        this.city = city;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.whatsappMessage = whatsappMessage;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhatsappMessage() {
        return whatsappMessage;
    }

    public void setWhatsappMessage(String whatsappMessage) {
        this.whatsappMessage = whatsappMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
