package com.appzgarden.payu;

import java.io.Serializable;

/**
 * Created by anshul on 22-01-2018.
 * Set the values accordingly
 */

public class PayUConfig implements Serializable{


    private String merchantKey=PayU.TEST_MERCHANTKEY;
    private String salt=PayU.TEST_SALT;
    private int mode=PayU.TEST_MODE;
    private String successURL="https://www.payumoney.com/mobileapp/payumoney/success.php";
    private String failURL="https://www.payumoney.com/mobileapp/payumoney/failure.php";
    private String amount="0.0";
    private String firstname="guest";
    private String lastname="";
    private String email="abc@example.com";
    private String phonenumber="";
    private String address1="";
    private String address2="";
    private String city="";
    private String state="";
    private String country="";
    private String zipcode="";
    private String productInfo="Recharge";

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getSuccessURL() {
        return successURL;
    }

    public void setSuccessURL(String successURL) {
        this.successURL = successURL;
    }

    public String getFailURL() {
        return failURL;
    }

    public void setFailURL(String failURL) {
        this.failURL = failURL;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }
}
