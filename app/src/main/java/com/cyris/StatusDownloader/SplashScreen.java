package com.cyris.StatusDownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cyris.StatusDownloader.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        final Intent intent  = new Intent(this, MainActivity.class);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        handler.postDelayed(runnable,500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity();
    }
}
