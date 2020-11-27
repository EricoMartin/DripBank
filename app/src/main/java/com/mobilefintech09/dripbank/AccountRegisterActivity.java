package com.mobilefintech09.dripbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mobilefintech09.dripbank.entities.AccessToken;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.TokenManager;

import java.util.Objects;

import retrofit2.Call;

public class AccountRegisterActivity extends AppCompatActivity {


    EditText first_name, last_name, bvn_number, account_type, email;
//    @BindView(R.id.f_name)
//    TextInputLayout first_name;
//    @BindView(R.id.l_name)
//    TextInputLayout last_name;
//    @BindView(R.id.bvn)
//    TextInputLayout bvn_number;
//    @BindView(R.id.account_type)
//    TextInputLayout account_type;
//    @BindView(R.id.user_email) TextInputLayout email;

    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager mTokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        first_name = findViewById(R.id.f_name);
        last_name = findViewById(R.id.l_name);
        bvn_number = findViewById(R.id.bvn);
        account_type = findViewById(R.id.account_type);
        email = findViewById(R.id.user_email);

        service = RetrofitBuilder.createService(ApiService.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        //setupRules();
    }

    public void onCreateOptionsMenu(View view) {
        if (view.getId() == R.id.btn_next) {
            accountRegister();
            return;
        }
    }

    public void accountRegister(){
        String f_name = first_name.getEditableText().toString();
        String l_name = Objects.requireNonNull(last_name.getEditableText()).toString();
        String user_email = Objects.requireNonNull(email.getEditableText()).toString();
        String bvn = Objects.requireNonNull(bvn_number.getEditableText()).toString();
        String account = Objects.requireNonNull(account_type.getEditableText()).toString();

        if (validateInputs(f_name, first_name) && validateInputs(l_name, last_name)
                && validateInputs(user_email, email) && validateInputs(bvn, bvn_number) && validateInputs(account, account_type)){
            Intent intent = new Intent(AccountRegisterActivity.this, AccountRegister2Activity.class);
            intent.putExtra("first_name", f_name);
            intent.putExtra("last_name", l_name);
            intent.putExtra("email", user_email);
            intent.putExtra("bvn", bvn);
            intent.putExtra("account_type", account);

            startActivity(intent);
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
//        validator.addValidation(this, R.id.f_name, RegexTemplate.NOT_EMPTY, R.string.err_f_name);
//        validator.addValidation(this, R.id.l_name, RegexTemplate.NOT_EMPTY, R.string.err_l_name);
//        validator.addValidation(this, R.id.bvn, RegexTemplate.NOT_EMPTY, R.string.err_bvn);
//        validator.addValidation(this, R.id.user_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
//        validator.addValidation(this, R.id.account_type, RegexTemplate.NOT_EMPTY, R.string.err_acct);
//    }
}
