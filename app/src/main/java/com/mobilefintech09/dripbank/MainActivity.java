package com.mobilefintech09.dripbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.mobilefintech09.dripbank.entities.AccessToken;
import com.mobilefintech09.dripbank.network.ApiService;
import com.mobilefintech09.dripbank.network.RetrofitBuilder;
import com.mobilefintech09.dripbank.network.SharedPreferenceManager;
import com.mobilefintech09.dripbank.network.TokenManager;
import com.squareup.picasso.Picasso;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiService service;
    Call<AccessToken> call;
    TokenManager mTokenManager;
    SharedPreferenceManager mSharedPreferenceManager;
    TextView mTextView;
    ImageView mImageView;

    List<Integer> iconList = new ArrayList<>();
    List<String> textList = new ArrayList<>();

    //Add a List of tags to dynamically call the different activity classes
    List<String> tagList = new ArrayList<>();

    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;



   // CardView mCardView, mCardView1, mCardView2, mCardView3, mCardView4, mCardView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        service = RetrofitBuilder.createService(ApiService.class);
        mTokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        mSharedPreferenceManager= SharedPreferenceManager.getInstance(getSharedPreferences("dripbankuserdetails", MODE_PRIVATE), getApplicationContext());

        mTextView = findViewById(R.id.text_user);
        mImageView= findViewById(R.id.imageView4);
        mTextView.setText("Welcome, " + mSharedPreferenceManager.getNewClient().getFirstName());
        String image = mSharedPreferenceManager.getNewClient().getAvatar();

        if(image != null){
            Picasso.get()
                    .load(image)
                    .centerCrop()
                    .fit().into(mImageView);
        }

        if(mTokenManager.getToken() == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        //Display the recyclerview and its content
            displayContent();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_sign_out){
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        call = service.logout();

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Log.w( TAG,"onResponse: " + response);

                    mTokenManager.deleteToken();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void displayContent(){
        final RecyclerView mRecyclerView = findViewById(R.id.recycler);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        iconList.add(R.drawable.person_icon);
        iconList.add(R.drawable.chat_icon);
        iconList.add(R.drawable.deposit_icon);
        iconList.add(R.drawable.withdraw_icon);
        iconList.add(R.drawable.transfer_icon);
        iconList.add(R.drawable.faq_icon);

        textList.add("Profile");
        textList.add("Messenger");
        textList.add("Deposit");
        textList.add("Withdraw");
        textList.add("Transfer");
        textList.add("FAQ's");

        tagList.add("profile");
        tagList.add("messenger");
        tagList.add("deposit");
        tagList.add("withdraw");
        tagList.add("transfer");
        tagList.add("faq");

        mRecyclerView.setAdapter(new IconRecyclerAdapter(this, iconList, textList, tagList));
    }

}



