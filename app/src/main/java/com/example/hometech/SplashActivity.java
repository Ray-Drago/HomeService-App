package com.example.hometech;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hometech.tech.TechHomeActivity;
import com.example.hometech.user.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginSession s = new LoginSession(SplashActivity.this);
                if (s.getStatus()) {
                    String user = s.getUserType();
                    if (user.equals("tech")) {
                        startActivity(new Intent(getApplicationContext(), TechHomeActivity.class));
                        finish();
                    }
                    else if (user.equals("user")) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                }
                else {
                    Intent an = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(an);
                    finish();
                }
            }
        },2000);
    }
}
