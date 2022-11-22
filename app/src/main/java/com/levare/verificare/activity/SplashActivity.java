package com.levare.verificare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.levare.verificare.R;
import com.levare.verificare.activity.login.LoginActivity;
import com.bumptech.glide.Glide;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(this)
                .asGif()
                .load(R.drawable.one_time)
                .into(imageView);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                loginActivity();
            }
        }, 2000);
    }

    private void loginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
