package com.mobilefintech09.dripbank.entities;

import com.squareup.moshi.Json;

public class AccountResponse {

    @Json(name = "Account Name")
    private String accountName;
    @Json(name = "Balance")
    private Integer balance;
    @Json(name = "Account Number")
    private String accountNumber;
    @Json(name = "Message")
    private String message;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
