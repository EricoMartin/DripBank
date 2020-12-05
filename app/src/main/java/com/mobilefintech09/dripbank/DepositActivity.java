package com.mobilefintech09.dripbank;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DepositActivity extends AppCompatActivity {
    EditText txt_account, txt_amount, txt_pin;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        if(savedInstanceState != null){
            finish();
        }

        txt_account = findViewById(R.id.text_slideshow);
        txt_amount = findViewById(R.id.text_slideshow2);
        txt_pin = findViewById(R.id.text_slideshow3);
        mButton = findViewById(R.id.button3);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = txt_amount.getEditableText().toString();
                String account = txt_account.getEditableText().toString();
                String pin = txt_pin.getEditableText().toString();

                Intent intent = new Intent(DepositActivity.this, CardDetailsActivity.class);
                intent.putExtra("amount", amount);
                intent.putExtra("account", account);
                intent.putExtra("pin", pin);
                startActivity(intent);
            }
        });

    }
}
