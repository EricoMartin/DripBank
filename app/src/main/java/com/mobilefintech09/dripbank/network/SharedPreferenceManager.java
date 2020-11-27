package com.mobilefintech09.dripbank.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobilefintech09.dripbank.entities.ClientResponse;
import com.mobilefintech09.dripbank.entities.Client;

public class SharedPreferenceManager {
    private static SharedPreferenceManager mInstance;
    private  SharedPreferences.Editor mEditor;
    private Context mContext;
    private SharedPreferences prefs;

    private static final String SHARED_PREF_NAME = "dripbankuserdetails";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_FIRST_NAME = "userfirstname";
    private static final String KEY_USER_LAST_NAME = "userlastname";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_ACCOUNT_NUMBER = "keyuseraccountnumber";

    private SharedPreferenceManager(SharedPreferences prefs, Context mContext) {
        this.prefs = prefs;
        this.mEditor = prefs.edit();
        this.mContext= mContext;
    }

    public static synchronized SharedPreferenceManager getInstance(SharedPreferences prefs, Context mContext) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceManager(prefs, mContext);
        }
        return mInstance;
    }

    public boolean userLogin(ClientResponse client) {
        mEditor.putInt(KEY_USER_ID, client.getClient().getId());
        mEditor.putString(KEY_USER_FIRST_NAME, client.getClient().getFirstName());
        mEditor.putString(KEY_USER_LAST_NAME, client.getClient().getLastName());
        mEditor.putString(KEY_USER_EMAIL, client.getClient().getEmail());
        mEditor.putString(KEY_USER_ACCOUNT_NUMBER, client.getAccount().getAccountNumber());
        mEditor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }

    public Client getNewClient() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Client(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_FIRST_NAME, null),
                sharedPreferences.getString(KEY_USER_LAST_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_ACCOUNT_NUMBER, "0")
        );
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
