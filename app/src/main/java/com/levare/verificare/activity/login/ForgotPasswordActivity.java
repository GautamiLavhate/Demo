package com.levare.verificare.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.levare.verificare.R;
import com.levare.verificare.util.Utility;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtEmail;
    private String emailAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);

        edtEmail = findViewById(R.id.edtEmail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void launchOTPActivity(View view) {

        emailAddress = edtEmail.getText().toString().trim();

        if (Utility.isEmailValid(emailAddress)) {

            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra("Email", emailAddress);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
        }
    }
}
