package com.mobilefintech09.dripbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;
import com.mobilefintech09.dripbank.entities.AccessToken;
import com.mobilefintech09.dripbank.entities.ApiError;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.SharedPreferenceManager;
import com.mobilefintech09.dripbank.network.TokenManager;
import com.mobilefintech09.dripbank.network.Utils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferenceManager mSharedPreferenceManager;

    private static final String TAG = "LoginActivity";
    @BindView(R.id.email)
    TextInputLayout email2;
    @BindView(R.id.password) TextInputLayout password2;

    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager mTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);
        mTokenManager = TokenManager.getInstance((getSharedPreferences("prefs", MODE_PRIVATE)));
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();

        if(mTokenManager.getToken().getAccessToken() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }



    @OnClick(R.id.btn_login)
    public void login(){
        String email = email2.getEditText().getText().toString();
        String password = password2.getEditText().getText().toString();

        email2.setError(null);
        password2.setError(null);

        validator.clear();

        if(validator.validate()) {

            call = service.login(email, password);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        mTokenManager.saveToken(response.body());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        if (response.code() == 422) {
                            handleErrors(response.errorBody());
                        }
                        if (response.code() == 401) {
                            ApiError apiError = Utils.convertErrors(response.errorBody());
                            Toast.makeText(LoginActivity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    @OnClick(R.id.go_to_register)
    public void goToRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void handleErrors(ResponseBody response){
        ApiError apiError = Utils.convertErrors(response);
        for(Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()){
            if(error.getKey().equals("username")){
                email2.setError(error.getValue().get(0));
            }
            if(error.getKey().equals("password")){
                password2.setError(error.getValue().get(0));
            }
        }
    }

    public void setupRules(){
        validator.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        validator.addValidation(this, R.id.password, RegexTemplate.NOT_EMPTY, R.string.err_password);
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
