package com.mobilefintech09.dripbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobilefintech09.dripbank.entities.AccountResponse;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.SharedPreferenceManager;
import com.mobilefintech09.dripbank.network.TokenManager;

import java.util.Objects;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CardDetailsActivity";
    TextInputEditText txtCardNumber, txtCardCvv;
    TextInputLayout txtCardExpiry;
    Button mButton;
    Call<AccountResponse> mCall;
    ApiService mApiService;
    TokenManager mTokenManager;
    SharedPreferenceManager mSharedPreferenceManager;
    private String mAmountToPay;
    private String mAccountToPay;
    private String mPintoCard;
    private String mEmail;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        txtCardNumber = findViewById(R.id.card_number);
        txtCardCvv = findViewById(R.id.cvv);
        txtCardExpiry = findViewById(R.id.card_expiry);
        mButton = findViewById(R.id.button2);
        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        mApiService = RetrofitBuilder.createAuthService(ApiService.class, mTokenManager);
        mSharedPreferenceManager= SharedPreferenceManager.getInstance(getSharedPreferences("dripbankuserdetails", MODE_PRIVATE), getApplicationContext());

        //get extras from calling intent
        Intent previous_intent = getIntent();
        mAmountToPay = previous_intent.getStringExtra("amount");
        mAccountToPay = previous_intent.getStringExtra("account");
        mPintoCard = previous_intent.getStringExtra("pin");
        mEmail = mSharedPreferenceManager.getNewClient().getEmail();
        id = mSharedPreferenceManager.getNewClient().getAccountNumber();
        Objects.requireNonNull(txtCardExpiry.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() == 2 && !s.toString().contains("/")) {
                    s.append("/");
                }
            }
        });

        PaystackSdk.initialize(getApplicationContext());

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCardCharge();
            }
        });
        

    }

    private void performCardCharge() {

        String cardNumber = txtCardNumber.getText().toString();
        String cardExpiry = txtCardExpiry.getEditText().getText().toString();
        String cvv = txtCardCvv.getText().toString();

        String[] cardExpiryArray = cardExpiry.split("/");
        int expiryMonth = Integer.parseInt(cardExpiryArray[0]);
        int expiryYear = Integer.parseInt(cardExpiryArray[1]);



        Card card = new Card(cardNumber, expiryMonth, expiryYear, cvv);

        //Convert amount to kobo for paystack
        int koboValueForPayStack = Integer.parseInt(mAmountToPay) * 100;

        if (card.isValid()) {
            Charge charge = new Charge();
            charge.setAmount(koboValueForPayStack);
            charge.setEmail(mEmail);
            charge.setCard(card);

            PaystackSdk.chargeCard(this, charge, new Paystack.TransactionCallback() {
                @Override
                public void onSuccess(Transaction transaction) {

                    Log.d("CardDetails Activity", "onSuccess: " + transaction.getReference());
                    parseResponse(transaction.getReference());
                    finish();
                }

                @Override
                public void beforeValidate(Transaction transaction) {
                    Log.d("CardDetails Activity", "beforeValidate: " + transaction.getReference());
                }

                @Override
                public void onError(Throwable error, Transaction transaction) {
                    Log.d("CardDetails Activity", "onError: " + error.getLocalizedMessage());
                }

            });


        } else {

            return ;
        }

    }

    private void parseResponse(String transactionReference) {
        mCall = mApiService.accountDeposit(id, mAmountToPay, mAccountToPay, mPintoCard);
        mCall.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (!response.isSuccessful()) {
                    Log.w(TAG, "Unsuccessful: " + response.code() + response);

                } else {
                    Log.w(TAG, "Success: " + response.body());
                    AccountResponse message = response.body();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
        String message = "Payment Successful - " + transactionReference;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        startActivity(new Intent(CardDetailsActivity.this, ProfileActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
