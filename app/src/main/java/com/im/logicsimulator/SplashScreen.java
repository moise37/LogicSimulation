package com.im.logicsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

//Learned Splash Screen from: https://www.youtube.com/watch?v=cyDhIovbOXc
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Menu.class);
                startActivity(i);
                finish();
            }
        }, 3100);
    }

}
