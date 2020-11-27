package com.mobilefintech09.dripbank.entities;

import com.squareup.moshi.Json;

public class Data {

    @Json(name = "client")
    private Client client;
    @Json(name = "account_balance")
    private String accountBalance;
    @Json(name = "account_number")
    private String accountNumber;
    @Json(name = "client_pin")
    private String clientPin;
    @Json(name = "bvn")
    private String bvn;
    @Json(name = "phone")
    private String phone;
    @Json(name = "drip_level")
    private String dripLevel;

    /**
     * No args constructor for use in serialization
     *
     */
    public Data() {
    }

    /**
     *
     * @param dripLevel
     * @param phone
     * @param client
     * @param clientPin
     * @param bvn
     * @param accountBalance
     * @param accountNumber
     */
    public Data(Client client, String accountBalance, String accountNumber, String clientPin, String bvn, String phone, String dripLevel) {
        super();
        this.client = client;
        this.accountBalance = accountBalance;
        this.accountNumber = accountNumber;
        this.clientPin = clientPin;
        this.bvn = bvn;
        this.phone = phone;
        this.dripLevel = dripLevel;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getClientPin() {
        return clientPin;
    }

    public void setClientPin(String clientPin) {
        this.clientPin = clientPin;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDripLevel() {
        return dripLevel;
    }

    public void setDripLevel(String dripLevel) {
        this.dripLevel = dripLevel;
    }

}