package com.example.firebaseauth;

import android.app.Application;

import com.facebook.FacebookSdk;

public class FirebaseAuth extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
