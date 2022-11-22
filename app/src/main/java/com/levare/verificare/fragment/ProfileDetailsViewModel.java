package com.levare.verificare.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.levare.verificare.model.Profile;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetailsViewModel extends ViewModel {
    private MutableLiveData<Profile> mProfileDetails = new MutableLiveData<Profile>();

    public LiveData<Profile> getBatchDetails(String id) {
        if (mProfileDetails != null) {
            loadProfileDetails(id);
        }
        return mProfileDetails;
    }
    private void loadProfileDetails(String id) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<Profile> aboutUs = serviceApi.getProfile(id);
        aboutUs.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Profile profile=response.body();

                if (profile != null) {
                    mProfileDetails.setValue(profile);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {


            }
        });
    }
}
