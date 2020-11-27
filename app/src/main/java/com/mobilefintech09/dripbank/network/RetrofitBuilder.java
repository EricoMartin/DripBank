package com.mobilefintech09.dripbank.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mobilefintech09.dripbank.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitBuilder {


    private static final String BASE_URL = "https://blooming-retreat-58614.herokuapp.com/api/v1/";
    private static final OkHttpClient client = buildClient();
    private static final Retrofit retrofit = buildRetrofit(client);


    private static Retrofit buildRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service){
        return retrofit.create(service);
    }

    public static <T> T createAuthService(Class<T> service, TokenManager tokenManager){
            OkHttpClient httpClient = client.newBuilder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();
                    Request.Builder builder= request.newBuilder();

                    if(tokenManager.getToken().getAccessToken() != null) {
                        builder.addHeader("Authorization", "Bearer " + tokenManager.getToken().getAccessToken());
                    }
                    request = builder.build();
                    return chain.proceed(request);
                }
            }).authenticator(RetrofitAuthenticator.getInstance(tokenManager))
                    .build();
           Retrofit newRetrofit = retrofit.newBuilder().client(httpClient).build();
           return newRetrofit.create(service);

    }


    private static OkHttpClient buildClient(){
        OkHttpClient.Builder builder= new OkHttpClient.Builder()
        .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Connection", "close");

                request = builder.build();
                return chain.proceed(request);
            }
        });
        if(BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }

    public static Retrofit getRetrofit() {
        
        return retrofit;
    }
}
