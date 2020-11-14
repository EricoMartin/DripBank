package com.mobilefintech09.dripbank.network;

import com.mobilefintech09.dripbank.entities.AccessToken;
import com.mobilefintech09.dripbank.entities.client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String mail, @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @POST("logout")
    Call<AccessToken> logout();

    @GET("client/{id}")
    Call<AccessToken> getClient();

    @POST("client")
    @FormUrlEncoded
    Call<AccessToken> client(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("bvn") String bvn,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("address") String address,
            @Field("date_of_birth") String date_of_birth,
            @Field("gender") String gender);


    @POST("account/{id}/deposit")
    @FormUrlEncoded
    Call<AccessToken> accountDeposit(
            @Field("amount") String amount
    );


    //transfer funds
    @POST("account/{id}/donate")
    @FormUrlEncoded
    Call<AccessToken> accountTransfer(
            @Field("account_number") String account_number,
            @Field("amount") String amount
    );

    //withdraw funds
    @POST("account/{id}/withdraw")
    @FormUrlEncoded
    Call<AccessToken> accountWithdraw(
            @Field("amount") String amount
    );


    //'first_name',
    //        'last_name',
    //        'account_type',
    //        'bvn',
    //        'phone',
    //        'email',
    //        'password',
    //        'address',
    //        'date_of_birth',
    //        'gender',
    //        'avatar'

}
