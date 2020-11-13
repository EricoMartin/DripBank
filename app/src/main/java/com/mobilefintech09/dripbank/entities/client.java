package com.mobilefintech09.dripbank.entities;

import com.squareup.moshi.Json;
import com.google.gson.annotations.SerializedName;

public class client {

    @Json(name ="first_name")
    private String first_name;

    @Json(name = "last_name")
    private String last_name;

    @Json(name = "account_type")
    private String account_type;

    @Json(name = "phone")
    private String phone;

    @Json(name = "bvn")
    private String bvn;

    @Json(name = "email")
    private String email;

    @Json(name = "address")
    private String address;

    @Json(name = "date_of_birth")
    private String date_of_birth;

    @Json(name = "gender")
    private String gender;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address= address;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}

//        'bvn',
//        'phone',
//        'email',
//        'password',
//        'address',
//        'date_of_birth',
//        'gender',