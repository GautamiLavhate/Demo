package com.levare.verificare.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.levare.verificare.R;
import com.levare.verificare.activity.login.LoginActivity;
import com.levare.verificare.fragment.ProfileDetailsFragment;
import com.levare.verificare.fragment.SetCurrentBatch;
import com.levare.verificare.model.User;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.levare.verificare.network.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        sessionManager=new SessionManager(ProfileActivity.this);

        launchProfileFragment();
    }

    public void setTitle(String title) {
        if (title != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void launchProfileFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_profile, new ProfileDetailsFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Dialog showExitPOP(String text, boolean isShowTwoButton) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.lay_exit_dialog);
        TextView test = dialog.findViewById(R.id.textmsg);
        test.setText(text);

        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        if (isShowTwoButton) {
            btnNo.setVisibility(View.VISIBLE);
            btnNo.setText("No");
            btnYes.setVisibility(View.VISIBLE);
            btnYes.setText("Yes");
        } else {
            btnNo.setVisibility(View.GONE);
            btnYes.setVisibility(View.VISIBLE);
            btnYes.setText("Ok");
        }
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String android_id = Settings.Secure.getString(ProfileActivity.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                logout(sessionManager.getKEY_user_id(),android_id);
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }

    public void ExitApp() {
        CurrentUser.getInstance().setCurrentUser(null);
        SetCurrentBatch.getInstance().setCurrentBatchDetails(null);
        finishAffinity();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void logout(String user_id,String device_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<User> aboutUs = serviceApi.logout(user_id,device_id);
        aboutUs.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null && user.getRESPONSESTATUS().equals("1")) {
                    Toast.makeText(ProfileActivity.this, "" +user.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                    ExitApp();

                } else {
                    Toast.makeText(ProfileActivity.this, "" + user.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
