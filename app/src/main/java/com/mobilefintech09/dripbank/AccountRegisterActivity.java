package com.mobilefintech09.dripbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;
import com.mobilefintech09.dripbank.entities.AccessToken;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.TokenManager;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class AccountRegisterActivity extends AppCompatActivity {



    @BindView(R.id.f_name)
    TextInputLayout first_name;
    @BindView(R.id.l_name)
    TextInputLayout last_name;
    @BindView(R.id.bvn)
    TextInputLayout bvn_number;
    @BindView(R.id.account_type)
    TextInputLayout account_type;
    @BindView(R.id.user_email) TextInputLayout email;

    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager mTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        service = RetrofitBuilder.createService(ApiService.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        setupRules();


    }

    @OnClick(R.id.btn_next)
    public  void accountRegister(){
        String f_name = first_name.getEditText().getText().toString();
        String l_name = last_name.getEditText().getText().toString();
        String bvn = bvn_number.getEditText().getText().toString();
        String account = account_type.getEditText().getText().toString();
        String user_email = email.getEditText().getText().toString();

        first_name.setError(null);
        last_name.setError(null);
        bvn_number.setError(null);
        account_type.setError(null);
        email.setError(null);

        validator.clear();

        if(validator.validate()){
            Intent intent = new Intent(this, AccountRegister2Activity.class);
            intent.putExtra("first_name", f_name);
            intent.putExtra("last_name", l_name);
            intent.putExtra("bvn", bvn);
            intent.putExtra("account_type", account);
            intent.putExtra("email", user_email);
            startActivity(intent);
        }
    }

    public void setupRules(){
        validator.addValidation(this, R.id.f_name, RegexTemplate.NOT_EMPTY, R.string.err_f_name);
        validator.addValidation(this, R.id.l_name, RegexTemplate.NOT_EMPTY, R.string.err_l_name);
        validator.addValidation(this, R.id.bvn, RegexTemplate.NOT_EMPTY, R.string.err_bvn);
        validator.addValidation(this, R.id.user_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        validator.addValidation(this, R.id.account_type, RegexTemplate.NOT_EMPTY, R.string.err_acct);
    }
}
