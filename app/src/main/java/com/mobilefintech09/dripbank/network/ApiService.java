package com.mobilefintech09.dripbank.network;

import com.mobilefintech09.dripbank.entities.AccessToken;
import com.mobilefintech09.dripbank.entities.AccountResponse;
import com.mobilefintech09.dripbank.entities.Client;
import com.mobilefintech09.dripbank.entities.ClientResponse;
import com.mobilefintech09.dripbank.entities.DataResponse;
import com.mobilefintech09.dripbank.entities.ImageResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String mail, @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @POST("logout")
    Call<AccessToken> logout();

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> reAuthenticateClient(@Field("refresh_token") String refreshToken);

    @GET("client/{id}")
    Call<DataResponse> getClient(@Path("id") String  id );

    @POST("client/upload")
    @Multipart
    Call<ImageResponse> uploadAvatar(@Part MultipartBody.Part avatar);

    @POST("client")
    @FormUrlEncoded
    Call<ClientResponse> client(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("account_type") String account_type,
            @Field("bvn") String bvn,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("address") String address,
            @Field("date_of_birth") String date_of_birth,
            @Field("gender") String gender);

    @GET("account/{id}")
    Call<AccessToken> account(@Path("id") int account_number);

    @POST("account/{id}/deposit")
    @FormUrlEncoded
    Call<AccountResponse> accountDeposit(
            @Path("id") String  id,
            @Field("amount") String amount, @Field("account_number") String account, @Field("client_pin") String pin
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
