package com.coinsimkia.phunpapa;

import android.app.Application;
import android.util.Log;

import com.facebook.ads.AudienceNetworkAds;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.onesignal.OneSignal;

public class App extends Application {
    private static final String ONESIGNAL_APP_ID = "########-####-####-####-############";

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
        try {
            AudienceNetworkAds.initialize(this);
            FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(15) // increase fetch interval in production ie 3600
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            mFirebaseRemoteConfig.setDefaultsAsync(R.xml.default_config);
            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("FIREBASE CONFIG ", "UPDATED : " + task.getResult());
                } else
                    Log.e("FIREBASE CONFIG", "ERROR : " + task.getException());
            });
            OneSignal.initWithContext(this);
            OneSignal.setAppId(ONESIGNAL_APP_ID);
        } catch (Exception e) {
            Log.e("Application init", e.toString());
        }
    }
}
