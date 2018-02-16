package com.example.s528802.studentregistartion;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by S528802 on 2/8/2018.
 */

public class MyFirebaseInsatnceIdService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"REG_TOKEN"+token);


    }
}
