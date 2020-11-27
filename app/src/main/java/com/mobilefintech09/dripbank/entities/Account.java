package com.mobilefintech09.dripbank.entities;

import com.squareup.moshi.Json;

public class Account {

    @Json(name = "account_balance")
    private Integer accountBalance;
    @Json(name = "account_type")
    private String accountType;
    @Json(name = "account_name")
    private String accountName;
    @Json(name = "bv_number")
    private String bvNumber;
    @Json(name = "client_email")
    private String clientEmail;
    @Json(name = "client_phone")
    private String clientPhone;
    @Json(name = "client_pin")
    private String clientPin;
    @Json(name = "drip_level")
    private Integer dripLevel;
    @Json(name = "account_number")
    private String accountNumber;
    @Json(name = "updated_at")
    private String updatedAt;
    @Json(name = "created_at")
    private String createdAt;
    @Json(name = "id")
    private Integer id;

    public Integer getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Integer accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBvNumber() {
        return bvNumber;
    }

    public void setBvNumber(String bvNumber) {
        this.bvNumber = bvNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientPin() {
        return clientPin;
    }

    public void setClientPin(String clientPin) {
        this.clientPin = clientPin;
    }

    public Integer getDripLevel() {
        return dripLevel;
    }

    public void setDripLevel(Integer dripLevel) {
        this.dripLevel = dripLevel;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
