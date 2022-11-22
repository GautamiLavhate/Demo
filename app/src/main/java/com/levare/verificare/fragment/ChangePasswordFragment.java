package com.levare.verificare.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.levare.verificare.R;
import com.levare.verificare.activity.CurrentUser;
import com.levare.verificare.activity.ProfileActivity;
import com.levare.verificare.model.Profile;
import com.levare.verificare.model.User;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;

    private TextInputLayout textInputLayoutCurrentPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutNewPassword;

    private Button buttonSubmit;
    private ProfileActivity profileActivity;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileActivity = (ProfileActivity) getActivity();
        final User user = CurrentUser.getInstance().getUser();
        buttonSubmit = view.findViewById(R.id.btnChangePassword);
        editTextCurrentPassword = view.findViewById(R.id.edtCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.edtNewPassword);
        editTextConfirmPassword = view.findViewById(R.id.edtConfirmPassword);

        textInputLayoutCurrentPassword = view.findViewById(R.id.tilCurrentPassword);
        textInputLayoutNewPassword = view.findViewById(R.id.tilNewPassword);
        textInputLayoutConfirmPassword = view.findViewById(R.id.tilConfirmPassword);
        if (user == null) {
            return;
        }

        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayoutConfirmPassword.setError("");
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
                    final Call<Profile> changePassword = serviceApi.changePassword(editTextCurrentPassword.getText().toString().trim(),
                            editTextNewPassword.getText().toString().trim(), editTextConfirmPassword.getText().toString().trim(), user.getProfileDetails().getUserId());
                    changePassword.enqueue(new Callback<Profile>() {
                        @Override
                        public void onResponse(Call<Profile> call, Response<Profile> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getRESPONSESTATUS().equals("1")) {
                                    profileActivity.ExitApp();
                                    Toast.makeText(getActivity(), response.body().getRESPONSEMSG(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), response.body().getRESPONSEMSG(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Profile> call, Throwable t) {
                            Toast.makeText(getActivity(), "Error Occurred...", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private boolean isValid() {
        if (editTextCurrentPassword.getText().toString().isEmpty() || editTextCurrentPassword.getText().toString().equals("")) {
            textInputLayoutCurrentPassword.setError("Can't be empty");
            return false;
        } else if (editTextNewPassword.getText().toString().isEmpty() || editTextNewPassword.getText().toString().equals("")) {
            textInputLayoutNewPassword.setError("Can't be empty");
            return false;
        } else if (editTextConfirmPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().equals("")) {
            textInputLayoutConfirmPassword.setError("Can't be empty");
            return false;
        } else if (!editTextNewPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
            textInputLayoutConfirmPassword.setError("New password & Confirm password not match.");
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        profileActivity.setTitle("Change Password");
    }

    private void changePassword() {

    }
}
