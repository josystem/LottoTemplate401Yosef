package com.coinsimkia.phunpapa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
public class SplashScreen extends AppCompatActivity {
    AppCompatTextView status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        status = findViewById(R.id.status_txt);
        fetchConfiguration();
    }
    private void init() {
        if (Config.showSplash()) {
            Handler handler = new Handler();
            handler.postDelayed(this::launch, 1000);
        } else
            launch();
    }

    private void launch() {
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
        intent.setPackage("com.android.chrome");
        if (intent.resolveActivity(getPackageManager()) == null) {
            intent.setPackage(null);
        }
        startActivity(intent);
        finish();
    }

    private void fetchConfiguration() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.fetchAndActivate().addOnSuccessListener(aBoolean -> {
            init();
        }).addOnFailureListener(e -> {
            status.setText(R.string.retrying);
            fetchConfiguration();
        });
    }
}