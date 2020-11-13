package com.mobilefintech09.dripbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }
    public void showRegisterActivity(View view){
        Intent intent = new Intent(EntryActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
