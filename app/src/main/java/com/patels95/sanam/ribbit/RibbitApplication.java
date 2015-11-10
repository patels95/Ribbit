package com.patels95.sanam.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.PushService;

public class RibbitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "dz54YtAhucArleX3cqpbyDw2YrsfaDe56xsbMwvk", "m7cle3APfeVnGvV4LjMPth33xyz6dF7FSNvxpBLD");


    }
}
