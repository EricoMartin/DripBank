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
import com.mobilefintech09.dripbank.network.TokenManager;
import com.mobilefintech09.dripbank.network.Utils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRegister2Activity extends AppCompatActivity {

    private static final String TAG = "AccountRegisterActivity";

    private String firstName, lastName, bv_number, acct_type, email1;


    @BindView(R.id.phone)
    TextInputLayout phone_number;
    @BindView(R.id.address)
    TextInputLayout user_address;
    @BindView(R.id.dob)
    TextInputLayout date_of_birth;
    @BindView(R.id.gender) TextInputLayout user_gender;

    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager mTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register2);

        service = RetrofitBuilder.createService(ApiService.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        setupRules();
    }

    @OnClick(R.id.btn_process)
    public void newAccountRegister(){
        Intent first_intent = getIntent();
        firstName = first_intent.getStringExtra("first_name");
        lastName = first_intent.getStringExtra("last_name");
        bv_number = first_intent.getStringExtra("bvn");
        acct_type = first_intent.getStringExtra("account_type");
        email1 = first_intent.getStringExtra("email");

        String phone = phone_number.getEditText().getText().toString();
        String address = user_address.getEditText().getText().toString();
        String dob = date_of_birth.getEditText().getText().toString();
        String gender = user_gender.getEditText().getText().toString();

        phone_number.setError(null);
        user_address.setError(null);
        date_of_birth.setError(null);
        user_gender.setError(null);


        validator.clear();

        if(validator.validate()) {

            call = service.client(firstName, lastName, bv_number, phone, email1, address, dob, gender);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(AccountRegister2Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (response.code() == 422) {
                            ApiError apiError = Utils.convertErrors(response.errorBody());
                            Toast.makeText(AccountRegister2Activity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        if (response.code() == 401) {
                            ApiError apiError = Utils.convertErrors(response.errorBody());
                            Toast.makeText(AccountRegister2Activity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(AccountRegister2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }



    public void setupRules(){
        validator.addValidation(this, R.id.phone, RegexTemplate.NOT_EMPTY, R.string.err_phone);
        validator.addValidation(this, R.id.address, RegexTemplate.NOT_EMPTY, R.string.err_address);
        validator.addValidation(this, R.id.dob, RegexTemplate.NOT_EMPTY, R.string.err_dob);
        validator.addValidation(this, R.id.gender, Patterns.EMAIL_ADDRESS, R.string.err_gender);
    }
}
