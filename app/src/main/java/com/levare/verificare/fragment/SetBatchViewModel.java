package com.levare.verificare.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.levare.verificare.model.BatchDetails;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetBatchViewModel extends ViewModel {
    private MutableLiveData<BatchDetails> mBatchDetailsMutableLiveData = new MutableLiveData<BatchDetails>();

    public LiveData<BatchDetails> getBatchDetails(String id) {
        if (mBatchDetailsMutableLiveData != null) {
            loadBatchDeatils(id);
        }
        return mBatchDetailsMutableLiveData;
    }
    private void loadBatchDeatils(String id) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<BatchDetails> aboutUs = serviceApi.getBatchdetails(id);
        aboutUs.enqueue(new Callback<BatchDetails>() {
            @Override
            public void onResponse(Call<BatchDetails> call, Response<BatchDetails> response) {
                BatchDetails batchDetails=response.body();
                if (batchDetails.getRESPONSESTATUS().equalsIgnoreCase("1")){

                }
                if (batchDetails != null) {
                    mBatchDetailsMutableLiveData.setValue(batchDetails);
                }
            }

            @Override
            public void onFailure(Call<BatchDetails> call, Throwable t) {


            }
        });
    }
}
