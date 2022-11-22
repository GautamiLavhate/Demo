package com.levare.verificare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.levare.verificare.R;
import com.levare.verificare.activity.CurrentUser;
import com.levare.verificare.activity.ProfileActivity;
import com.levare.verificare.model.Profile;
import com.levare.verificare.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProfileDetailsFragment extends Fragment {

    private ProfileDetailsViewModel mViewModel;
    private View mView;
    private ImageView photo;
    private Button buttonLogout;
    private ProfileActivity profileActivity;
    public static ProfileDetailsFragment newInstance() {
        return new ProfileDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.profile_detail_fragment, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileActivity = (ProfileActivity) getActivity();
        mViewModel = ViewModelProviders.of(this).get(ProfileDetailsViewModel.class);
        User user = CurrentUser.getInstance().getUser();

        photo = mView.findViewById(R.id.ivProfilePicture);
        buttonLogout = mView.findViewById(R.id.mbtnLogout);
        final TextView name = mView.findViewById(R.id.tvProfileName);
        final TextView email = mView.findViewById(R.id.tvProfileEmail);
        final TextView mobile = mView.findViewById(R.id.tvProfileMobile);
        final TextView tvProfileAadhar = mView.findViewById(R.id.tvProfileAadhar);
        final TextView tvProfileEmployeeType = mView.findViewById(R.id.tvProfileEmployeeType);
        final TextView tvProfileCompanyName = mView.findViewById(R.id.tvProfileCompanyName);
        TextView textViewChangePassword = mView.findViewById(R.id.tvProfileChangePass);

        textViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_profile, new ChangePasswordFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if (user != null) {
            mViewModel.getBatchDetails(user.getProfileDetails().getUserId()).observe(this, new Observer<Profile>() {

                @Override
                public void onChanged(Profile profile) {
                    Profile.ProfileDetails profileDetails = profile.getProfileDetails();
                    if (profileDetails != null) {
                        loadPhoto(profileDetails.getPhoto());
                        name.setText(profileDetails.getName());
                        email.setText(profileDetails.getEmail());
                        if (profileDetails.getMobile() != null) {
                            mobile.setText(String.format("%s", profileDetails.getMobile()));
                        }
                        tvProfileAadhar.setText(profileDetails.getAadharNo());
                        tvProfileEmployeeType.setText(profileDetails.getUserType());
                        tvProfileCompanyName.setText("");
                    }
                }
            });
        }

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileActivity.showExitPOP("Do you want to Logout?", true);
            }
        });
    }

    private void loadPhoto(String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_profile)
                .timeout(3000)
                .override(80, 80)
                .error(R.drawable.ic_profile);
        Glide.with(this).asBitmap().load(url).apply(options).into(photo);
    }

    @Override
    public void onResume() {
        super.onResume();
        profileActivity.setTitle("Profile");
    }
}
