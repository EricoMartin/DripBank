
package com.mobilefintech09.dripbank.entities;

import com.squareup.moshi.Json;

    public class Client {

        @Json(name = "username")
        private String username;
        @Json(name = "first_name")
        private String firstName;
        @Json(name = "last_name")
        private String lastName;
        @Json(name = "account_type")
        private String accountType;
        @Json(name = "bvn")
        private String bvn;
        @Json(name = "phone")
        private String phone;
        @Json(name = "email")
        private String email;
        @Json(name = "address")
        private String address;
        @Json(name = "date_of_birth")
        private String dateOfBirth;
        @Json(name = "gender")
        private String gender;
        @Json(name = "updated_at")
        private String updatedAt;
        @Json(name = "created_at")
        private String createdAt;
        @Json(name = "id")
        private Integer id;

        @Json(name = "avatar")
        private String avatar;
        @Json(name = "Account Number")
        private String accountNumber = "";

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        /**
         *  @param username
         * @param firstName
         * @param lastName
         * @param accountType
         * @param bvn
         * @param phone
         * @param email
         * @param address
         * @param dateOfBirth
         * @param gender
         * @param updatedAt
         * @param createdAt
         * @param id
         * @param avatar
         */
        public Client(String username, String firstName, String lastName, String accountType, String bvn, String phone, String email, String address, String dateOfBirth, String gender, String updatedAt, String createdAt, Integer id, String avatar) {
            super();
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.accountType = accountType;
            this.bvn = bvn;
            this.phone = phone;
            this.email = email;
            this.address = address;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.updatedAt = updatedAt;
            this.createdAt = createdAt;
            this.id = id;
            this.avatar = avatar;
        }

        public Client(int id, String firstName, String lastName, String email, String accountNumber) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.accountNumber = accountNumber;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
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
            this.address = address;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

