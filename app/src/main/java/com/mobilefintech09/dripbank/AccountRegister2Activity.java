package com.mobilefintech09.dripbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mobilefintech09.dripbank.entities.ApiError;
import com.mobilefintech09.dripbank.entities.ClientResponse;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.SharedPreferenceManager;
import com.mobilefintech09.dripbank.network.TokenManager;
import com.mobilefintech09.dripbank.network.Utils;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRegister2Activity extends AppCompatActivity {

    private static final String TAG = "AccountRegisterActivity";

    private String firstName, lastName, bv_number, acct_type, email1;

    EditText phone_number, user_address, date_of_birth, user_gender;

    SharedPreferenceManager mSharedPreferenceManager;
    ApiService service;
    Call<ClientResponse> call;
    AwesomeValidation validator;
    TokenManager mTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register2);

        phone_number = findViewById(R.id.phone);
        user_address= findViewById(R.id.address);
        date_of_birth = findViewById(R.id.dob);
        user_gender = findViewById(R.id.gender);


        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        service = RetrofitBuilder.createAuthService(ApiService.class, mTokenManager);
        //setupRules();
    }

    public void onCreateOptionsMenu(View view) {
        if (view.getId() == R.id.btn_process) {
            newAccountRegister();
            return;
        }
    }

    @OnClick(R.id.btn_process)
    public void newAccountRegister(){
        Intent first_intent = getIntent();
        firstName = first_intent.getStringExtra("first_name");
        lastName = first_intent.getStringExtra("last_name");
        email1 = first_intent.getStringExtra("email");
        bv_number = first_intent.getStringExtra("bvn");
        acct_type = first_intent.getStringExtra("account_type");


        String phone = phone_number.getEditableText().toString();
        String address = user_address.getEditableText().toString();
        String dob = date_of_birth.getEditableText().toString();
        String gender = user_gender.getEditableText().toString();


        if(validateInputs(phone, phone_number) && validateInputs(address, user_address)
                && validateInputs(dob, date_of_birth) && validateInputs(gender, user_gender) ) {

            call = service.client(firstName, lastName, acct_type, bv_number, phone, email1, address, dob, gender);
            call.enqueue(new Callback<ClientResponse>() {
                @Override
                public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {
                    Log.w(TAG, "onResponse: " + response + response.code());
                    if (response.isSuccessful()) {
                        Toast.makeText(AccountRegister2Activity.this, "Success:" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        mSharedPreferenceManager.getInstance(getSharedPreferences("dripbankuserdetails", MODE_PRIVATE), getApplicationContext()).userLogin(response.body());
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
                            mTokenManager.deleteToken();
                            Intent intent = new Intent(AccountRegister2Activity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    }
                }

                @Override
                public void onFailure(Call<ClientResponse> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(AccountRegister2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private boolean validateInputs(String value, EditText view) {
        if (value.isEmpty()) {
            view.setError(view + " Field must not be empty");
            view.requestFocus();
            return false;
        } else {
            view.setError(null);
            return true;
        }
    }



//    public void setupRules(){
//        validator.addValidation(this, R.id.phone, RegexTemplate.NOT_EMPTY, R.string.err_phone);
//        validator.addValidation(this, R.id.address, RegexTemplate.NOT_EMPTY, R.string.err_address);
//        validator.addValidation(this, R.id.dob, RegexTemplate.NOT_EMPTY, R.string.err_dob);
//        validator.addValidation(this, R.id.gender, Patterns.EMAIL_ADDRESS, R.string.err_gender);
//    }
}
