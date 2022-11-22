package com.levare.verificare.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.levare.verificare.model.AssetList;
import com.levare.verificare.model.DetailScan;
import com.levare.verificare.model.SubmitBatch;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetListViewModel extends ViewModel {
    private MutableLiveData<AssetList> mBatchDetailsMutableLiveData = new MutableLiveData<AssetList>();

    private MutableLiveData<DetailScan> mBatmDeatilResult = new MutableLiveData<DetailScan>();

    private MutableLiveData<SubmitBatch> mSbmmitBatchDetails = new MutableLiveData<SubmitBatch>();

    public LiveData<SubmitBatch> submitBatchAll(String batchid,String projId,String user_id){
        if (mSbmmitBatchDetails!=null){
            submitBatch(batchid,projId,user_id);
        }
        return mSbmmitBatchDetails;
    }


    public LiveData<AssetList> getAssetDetilas(String batchid, String ProjectId) {
        if (mBatchDetailsMutableLiveData != null) {
            loadBatchDeatils(batchid, ProjectId);
        }
        return mBatchDetailsMutableLiveData;
    }

    private void loadBatchDeatils(String batchid, String prjId) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<AssetList> aboutUs = serviceApi.getAssetList(batchid, prjId);
        aboutUs.enqueue(new Callback<AssetList>() {
            @Override
            public void onResponse(Call<AssetList> call, Response<AssetList> response) {
                AssetList assetList = response.body();

                if (assetList != null) {
                    mBatchDetailsMutableLiveData.setValue(assetList);
                }
            }

            @Override
            public void onFailure(Call<AssetList> call, Throwable t) {


            }
        });
    }


    public LiveData<DetailScan> getAssetDetails(String batch_id,
                                              String project_id,
                                              String unique_id,
                                              String other_batch,
                                              String re_verify,
                                              String reason,
                                              String asset_id) {
        if (mBatmDeatilResult != null) {
            loadScanResult(batch_id,
                    project_id,
                    unique_id,
                    other_batch,
                    re_verify,
                    reason,
                    asset_id);
        }
        return mBatmDeatilResult;
    }

    private void loadScanResult(String batch_id,
                                String project_id,
                                String unique_id,
                                String other_batch,
                                String re_verify,
                                String reason,
                                String asset_id) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<DetailScan> aboutUs = serviceApi.getAssetDetail(batch_id,
                project_id,
                unique_id,
                other_batch,
                re_verify,
                reason,
                asset_id);
        aboutUs.enqueue(new Callback<DetailScan>() {
            @Override
            public void onResponse(Call<DetailScan> call, Response<DetailScan> response) {
                DetailScan batchDetails = response.body();

                if (batchDetails != null) {
                    mBatmDeatilResult.setValue(batchDetails);
                }
            }

            @Override
            public void onFailure(Call<DetailScan> call, Throwable t) {


            }
        });
    }

    private void submitBatch(String batchId,String ProjectId,String user_id){
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        Call<SubmitBatch> submit=serviceApi.submitBatch(batchId,ProjectId,user_id);
        submit.enqueue(new Callback<SubmitBatch>() {
            @Override
            public void onResponse(Call<SubmitBatch> call, Response<SubmitBatch> response) {
                SubmitBatch submitBatch=response.body();
                if (submitBatch!=null){
                    mSbmmitBatchDetails.setValue(submitBatch);
                }

            }

            @Override
            public void onFailure(Call<SubmitBatch> call, Throwable t) {

            }
        });
    }
}
