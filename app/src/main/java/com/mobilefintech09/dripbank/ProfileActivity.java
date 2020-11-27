package com.mobilefintech09.dripbank;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilefintech09.dripbank.entities.ClientResponse;
import com.mobilefintech09.dripbank.entities.DataResponse;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.SharedPreferenceManager;
import com.mobilefintech09.dripbank.network.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    ImageView mImageView;
    Button mButton, mButtonWithdraw;
    TextView mTextView, mTextView2, mTextView3, mTextView4, mTextView5, mTextView6;
    RatingBar mRatingBar;
    SharedPreferenceManager mSharedPreferenceManager;
    ApiService mApiService;
    Call<DataResponse> mCall;
    TokenManager mTokenManager;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        mApiService = RetrofitBuilder.createAuthService(ApiService.class, mTokenManager);
        mSharedPreferenceManager= SharedPreferenceManager.getInstance(getSharedPreferences("dripbankuserdetails", MODE_PRIVATE), getApplicationContext());

            mImageView = findViewById(R.id.imageView3);
            mButton = findViewById(R.id.btn_deposit);
            mButtonWithdraw = findViewById(R.id.btn_withdraw);
            mTextView2 = findViewById(R.id.textView2);
            mTextView3= findViewById(R.id.textView3);
            mTextView4 = findViewById(R.id.textView4);
            mTextView5 = findViewById(R.id.textView5);
            mTextView6 = findViewById(R.id.textView6);
            mTextView = findViewById(R.id.textView);
            mRatingBar = findViewById(R.id.ratingBar);

         id = mSharedPreferenceManager.getNewClient().getId().toString();

            setProfileData();

    }

    private void setProfileData() {

            mCall = mApiService.getClient(id);
            mCall.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    if(!response.isSuccessful()){
                        Log.w(TAG, "Code: " + response.code() + response);
                        return;
                    }else{
                        Log.w(TAG, "Success Code: " + response.body());
                        DataResponse data = response.body();
                        //assert data != null;
                        if(data != null) {
                            //for(ClientResponse client: data){

                                mTextView5.setText("Name: " + data.getData().getClient().getFirstName());

                                mTextView4.setText("Account Number: " + data.getData().getAccountNumber());

                                mTextView3.setText("Account Balance: " + data.getData().getAccountBalance());

                                mTextView2.setText("BVN: " + data.getData().getClient().getBvn());
                            //}

                        }
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
//        String name = client.getFirst_name() + " " + client.getLast_name();
//        String image = client.getAccount_type();
//        int account_number = client.;
//        String country = learnerResponse.getCountry();
//
//        String full_text = name + '\n' + hours + " learning hours," + country;
//        holder.textView.setText(full_text );
//        if(image != null){
//            Picasso.get()
//                    .load(learnerResponse.getBadgeUrl())
//                    .centerCrop()
//                    .fit().into(holder.imageView);

    }
}
