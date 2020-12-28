package com.coinsimkia.phunpapa;

import android.app.Application;
import android.util.Log;

import com.facebook.ads.AudienceNetworkAds;
import com.onesignal.OneSignal;

public class App extends Application {
    private static final String ONESIGNAL_APP_ID = "########-####-####-####-############";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // Initialize the Audience Network SDK
            AudienceNetworkAds.initialize(this);
            OneSignal.initWithContext(this);
            OneSignal.setAppId(ONESIGNAL_APP_ID);
        } catch (Exception e) {
            Log.e("Application init", e.toString());
        }
    }
}
