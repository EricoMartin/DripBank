package com.mobilefintech09.dripbank.entities;

import com.squareup.moshi.Json;

public class ClientResponse {

    @Json(name = "Client")
    private Client client;
    @Json(name = "Account")
    private Account account;
    @Json(name = "Message")
    private String message;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}