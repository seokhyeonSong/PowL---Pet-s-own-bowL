package com.powl.graduation.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.powl.graduation.MainActivity;
import com.powl.graduation.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      new  Handler(getMainLooper()).postDelayed(new Runnable() {
          @Override
          public void run() {
              startActivity(new Intent(getApplicationContext(),MainActivity.class));
          }
      }, 1000);
    }
}
