package com.levare.verificare.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.levare.verificare.R;
import com.levare.verificare.activity.CurrentUser;
import com.levare.verificare.activity.DashboardActivity;
import com.levare.verificare.model.User;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.levare.verificare.network.SessionManager;
import com.levare.verificare.util.AlertDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText usernameText;
    private EditText passwordText;
    private LinearLayout llWarning;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.edtUsername);
        passwordText = findViewById(R.id.edtPassword);
        llWarning = findViewById(R.id.llWarning);
        sessionManager=new SessionManager(LoginActivity.this);
        getSupportActionBar().hide();
    }

    public void login(View view) {
        if (!validate()) {
            return;
        }
        String android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String device_name =getDeviceName();
        //Toast.makeText(LoginActivity.this,device_name,Toast.LENGTH_LONG).show();
        signIn(usernameText.getText().toString(), passwordText.getText().toString(),android_id,device_name);//static device id "12345678999999"
    }

    /** Returns the consumer friendly device name */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }
    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (username.isEmpty()) {
            llWarning.setVisibility(View.VISIBLE);
            valid = false;
        } else {
//            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            llWarning.setVisibility(View.VISIBLE);
            valid = false;
        } else {
//            _passwordText.setError(null);
        }
        return valid;
    }

    void signIn(final String username, String Pass, String device_id,String device_name) {
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<User> aboutUs = serviceApi.UserLogin(username, Pass,device_id,device_name);
        aboutUs.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null && user.getRESPONSESTATUS().equals("1")) {
                    onLoginSuccess(user);
                    sessionManager.createLogin(user.getProfileDetails().getUserName(),
                            user.getProfileDetails().getEmail(),
                            user.getProfileDetails().getName(),
                            user.getProfileDetails().getPhoto(),
                            user.getProfileDetails().getClientId(),
                            user.getProfileDetails().getUserId(),
                            user.getProfileDetails().getLastLogin(),
                            user.getProfileDetails().getUserType());
                } else {
                    //Toast.makeText(LoginActivity.this, "" + user.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                    LoginUnSuccess(user.getRESPONSEMSG());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onLoginFailure();
            }
        });
    }

    public void onLoginSuccess(final User user) {
        AlertDialog mAlertDialog = new AlertDialog(this);
        mAlertDialog.showDialog("Logged in Successfully", "After active hours your account will auto logout");
        mAlertDialog.setListener(new AlertDialog.IAlertListener() {
            @Override
            public void onClick(Object o) {
                CallActivity(user);
                CurrentUser.getInstance().setCurrentUser(user);
            }
        });
    }

    public void LoginUnSuccess(String message){
        final AlertDialog mAlertDialog = new AlertDialog(this);
        mAlertDialog.showDialog("Login Unsuccessful!",message);
        mAlertDialog.setListener(new AlertDialog.IAlertListener() {
            @Override
            public void onClick(Object o) {

            }
        });
    }

    private void CallActivity(User user) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void onLoginFailure() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public void launchForgotPasswordActivity(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void createSnackbar(Context context, View view) {
        Snackbar snackbar = Snackbar.make(view, null, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View snackView = layoutInflater.inflate(R.layout.snackbar_success, null);
        //If the view is not covering the whole snackbar layout, add this line
        snackbarLayout.setPadding(0, 0, 0, 0);
        snackbarLayout.addView(snackView, 0);
        //Show the Snackbar
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
