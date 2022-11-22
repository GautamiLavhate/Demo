package com.levare.verificare.activity.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.levare.verificare.R;

public class OtpActivity extends AppCompatActivity {

    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Bundle bundle = getIntent().getExtras();
        String email = null;
        if (bundle != null) {
            email = bundle.getString("Email");
        }

        tvEmail = findViewById(R.id.tvMessage2);
        tvEmail.setPaintFlags(tvEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvEmail.setText(email);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void resendCode(View view) {
        Toast.makeText(this, "Code sent", Toast.LENGTH_SHORT).show();
    }

    public void launchSetPasswordActivity(View view) {

        Intent intent = new Intent(this, SetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
