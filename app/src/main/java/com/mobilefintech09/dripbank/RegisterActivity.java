package com.mobilefintech09.dripbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;
import com.mobilefintech09.dripbank.entities.AccessToken;
import com.mobilefintech09.dripbank.entities.ApiError;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.TokenManager;
import com.mobilefintech09.dripbank.network.Utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.name)
    TextInputLayout name1;
    @BindView(R.id.email) TextInputLayout email1;
    @BindView(R.id.password) TextInputLayout password1;

    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager mTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        setupRules();

        if(mTokenManager.getToken().getAccessToken() != null){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_register)
    void register(){
        String name = name1.getEditText().getText().toString();
        String email = email1.getEditText().getText().toString();
        String password = password1.getEditText().getText().toString();

        name1.setError(null);
        email1.setError(null);
        password1.setError(null);

        validator.clear();

        if(validator.validate()) {

            call = service.register(name, email, password);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        mTokenManager.saveToken(response.body());
                        startActivity(new Intent(RegisterActivity.this, AccountRegisterActivity.class));
                        finish();
                    } else {
                        handleErrors(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });

        }
    }

    @OnClick(R.id.go_to_login)
    public void goToLogin(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

            public void handleErrors(ResponseBody response){
                ApiError apiError = Utils.convertErrors(response);
                 for(Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()){
                     if(error.getKey().equals("name")){
                         name1.setError(error.getValue().get(0));
                     }
                     if(error.getKey().equals("email")){
                         email1.setError(error.getValue().get(0));
                     }
                     if(error.getKey().equals("password")){
                         password1.setError(error.getValue().get(0));
                     }
                 }
            }

            public void setupRules(){
                validator.addValidation(this, R.id.name, RegexTemplate.NOT_EMPTY, R.string.err_name);
                validator.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.err_email);
                validator.addValidation(this, R.id.password, "[a-zA-Z0-9]{8,}", R.string.err_password);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null){
            call.cancel();
            call = null;
        }
    }
}
