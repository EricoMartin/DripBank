package com.mobilefintech09.dripbank;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobilefintech09.dripbank.entities.ClientResponse;
import com.mobilefintech09.dripbank.entities.DataResponse;
import com.mobilefintech09.dripbank.entities.ImageResponse;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.SharedPreferenceManager;
import com.mobilefintech09.dripbank.network.TokenManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    ImageView mImageView;
    Button mButton, mButtonWithdraw;
    TextView mTextView, mTextView2, mTextView3, mTextView4, mTextView5, mTextView6;
    private FloatingActionButton mFab;
    RatingBar mRatingBar;
    SharedPreferenceManager mSharedPreferenceManager;
    ApiService mApiService, apiService;
    Call<DataResponse> mCall;
    Call<ImageResponse> call;
    TokenManager mTokenManager;
    TextView mPinTextView;
    ToggleButton mToggleButton;
    private Bitmap bitmap;
    File file;
    String id;
    Uri filePath;


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
        apiService = RetrofitBuilder.createServiceAuth(ApiService.class, mTokenManager);
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
            mFab = findViewById(R.id.image_fab);
            mPinTextView = findViewById(R.id.pin_text);
            mToggleButton = findViewById(R.id.toggleButton);

         id = mSharedPreferenceManager.getNewClient().getId().toString();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

            setProfileData();
            mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        mPinTextView.setVisibility(View.VISIBLE);
                    }else{
                        mPinTextView.setVisibility(View.INVISIBLE);
                    }
                }

            });
            mButton.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, DepositActivity.class)));

    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        String[] mimeTypes = new String[] { "image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                file =  new File(Objects.requireNonNull(filePath.getPath()));

                mImageView.setImageBitmap(bitmap);

                if(bitmap != null){
                    postAvatar();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void postAvatar() throws IOException {
        mFab.setVisibility(View.INVISIBLE);
        ParcelFileDescriptor descriptor = getContentResolver().openFileDescriptor(filePath, "r", null);
        if(descriptor == null){
            return;
        }

        InputStream inputStream = new FileInputStream(descriptor.getFileDescriptor());

        File file1 =  new File(getCacheDir(), file.getName());

        OutputStream outputStream = new FileOutputStream(file1);

        copy(inputStream, outputStream);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("avatar", file.getName(),
                RequestBody.create(MediaType.parse("image/*"), file1));
        //RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), avatar);

        call = apiService.uploadAvatar(fileToUpload);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (!response.isSuccessful()) {
                    Log.w(TAG, "Unsuccessful: " + response.code() + response);
                    return;
                } else {
                    Log.w(TAG, "Success: " + response.body());
                    ImageResponse message = response.body();
                }
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {

        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1)
                break;
            out.write(buffer, 0, bytesRead);
        }
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

                                mTextView5.setText("Name: " + data.getData().getClient().getFirstName() + " " + data.getData().getClient().getLastName());

                                mTextView4.setText("Account Number: " + data.getData().getAccountNumber());

                                mTextView3.setText("Account Balance: " + data.getData().getAccountBalance());

                                mTextView2.setText("BVN: " + data.getData().getClient().getBvn());

                                mPinTextView.setText("PIN: " + data.getData().getClientPin());

                                String image = data.getData().getClient().getAvatar();

                            if(image != null){
                                Picasso.get()
                                        .load(image)
                                        .centerCrop()
                                        .fit().into(mImageView);
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

    }
}
