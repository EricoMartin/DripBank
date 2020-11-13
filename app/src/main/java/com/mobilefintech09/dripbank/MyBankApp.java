package com.mobilefintech09.dripbank;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

public class MyBankApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
}
