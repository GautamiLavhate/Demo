package com.levare.verificare.activity.login;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.levare.verificare.R;

public class SetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

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

    public void showDialog(final Context context, String msg1, String msg2) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        TextView message2 = dialog.findViewById(R.id.diMessage2);
        message2.setVisibility(View.VISIBLE);
        message2.setText(msg2);
        dialog.show();

        new CountDownTimer(2500, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //igonre
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        }.start();
    }

    public void changePassword(View view) {
        showDialog(this, null, "Password changed Successfully");
    }
}
