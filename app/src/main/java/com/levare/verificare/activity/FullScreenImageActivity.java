package com.levare.verificare.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.levare.verificare.R;
import com.bumptech.glide.Glide;

public class FullScreenImageActivity extends AppCompatActivity {
    ImageView imgFullScreen;
    String image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        imgFullScreen=findViewById(R.id.imgFullScreen);
        Intent intent=getIntent();
        image_url=intent.getStringExtra("image_name");
        Glide.with(FullScreenImageActivity.this).load(image_url).into(imgFullScreen);
    }
}