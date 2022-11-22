package com.levare.verificare.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.levare.verificare.model.Dashboard;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardViewModel extends ViewModel {

    private MutableLiveData<Dashboard> mBatchDetailsMutableLiveData = new MutableLiveData<Dashboard>();

    public LiveData<Dashboard> DashBoard(String batch_id, String proj_id) {
        if (mBatchDetailsMutableLiveData != null) {
            loadDashBoard(batch_id,proj_id);
        }
        return mBatchDetailsMutableLiveData;
    }
    private void loadDashBoard(String batch_id,String proj_id) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<Dashboard> aboutUs = serviceApi.getDashBoard(batch_id,proj_id);
        aboutUs.enqueue(new Callback<Dashboard>() {
            @Override
            public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                Dashboard dashboardDetails=response.body();

                if (dashboardDetails != null) {
                    mBatchDetailsMutableLiveData.setValue(dashboardDetails);
                }
            }

            @Override
            public void onFailure(Call<Dashboard> call, Throwable t) {


            }
        });
    }

}
