package com.coinsimkia.phunpapa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Config.showSplash()) {
            setup();
        } else {
            setContentView(R.layout.splash_screen);
            Handler handler = new Handler();
            handler.postDelayed(this::setup, 1500);
        }
    }

    private void setup() {
        if (!Config.isWebView())
            showOnBrowser();
        else {
            startActivity(new Intent(getApplicationContext(), AppWebView.class));
            finish();
        }
    }

    private void showOnBrowser() {
        String url = Config.getUrl();
        if (!(url.startsWith("http://") || url.startsWith("https://")))
            url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        if (intent.resolveActivity(getPackageManager()) == null) {
            intent.setPackage(null);
        }
        startActivity(intent);
        finish();
    }
}
