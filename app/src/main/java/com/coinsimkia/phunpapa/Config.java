package com.coinsimkia.phunpapa;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class Config {
    public static String getUrl() {
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        long app_mode = config.getLong("APP_MODE");
        if (app_mode == 0)
            return config.getString("URL_0");
        else
            return config.getString("URL_1");
    }
    public static boolean isWebView(){
        return FirebaseRemoteConfig.getInstance().getBoolean("IS_WEBVIEW");
    }
    public static boolean showSplash(){
        return FirebaseRemoteConfig.getInstance().getBoolean("SHOW_SPLASH");
    }
    public static boolean showADS(){
        return FirebaseRemoteConfig.getInstance().getBoolean("SHOW_ADS");
    }

}
