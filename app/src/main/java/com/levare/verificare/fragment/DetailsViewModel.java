package com.levare.verificare.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.levare.verificare.model.DetailScan;
import com.levare.verificare.model.Details;
import com.levare.verificare.model.Reson;
import com.levare.verificare.model.SendRespose;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends ViewModel {

    private MutableLiveData<DetailScan> mBatchDetailsMutableLiveData = new MutableLiveData<DetailScan>();
    private MutableLiveData<Reson> mResons = new MutableLiveData<>();
    private MutableLiveData<SendRespose> mSendResponse = new MutableLiveData<>();
    private MutableLiveData<DetailScan> mBatmDeatilResult = new MutableLiveData<DetailScan>();
    private MutableLiveData<Details> detailsResult = new MutableLiveData<>();

    public LiveData<DetailScan> getScanResult(String ProjectId,
                                              String batchId,
                                              String qr_code,
                                              String other_batch,
                                              String re_verify, String scan_by) {
        if (mBatchDetailsMutableLiveData != null) {
            loadScanResult(ProjectId, batchId, qr_code, other_batch, re_verify,scan_by);
        }
        return mBatchDetailsMutableLiveData;
    }

    public LiveData<DetailScan> getScanResultRFID(String ProjectId,
                                                  String batchId,
                                                  String code,
                                                  String other_batch,
                                                  String re_verify, String scan_by) {
        if (mBatchDetailsMutableLiveData != null) {
            loadScanResultRFID(ProjectId, batchId, code, other_batch, re_verify, scan_by);
        }
        return mBatchDetailsMutableLiveData;
    }

    public LiveData<SendRespose> SubmitAsset(String asset_id,
                                             String project_id,
                                             String mode,
                                             String qty,
                                             String in_use,
                                             String batch_id,
                                             String user_id,
                                             String reason_id,
                                             String remark,
                                             String in_use_qty,
                                             String not_in_use_qty,
                                             String not_found_qty,
                                             String plant_id,
                                             String location_id,
                                             String sub_location_id) {
        if (mSendResponse != null) {
            SubmitAssetApi( asset_id,
                    project_id,
                    mode,
                    qty,
                    in_use,
                    batch_id,
                    user_id,
                    reason_id,
                    remark,
                    in_use_qty,
                    not_in_use_qty,
                    not_found_qty,
                    plant_id,
                    location_id,
                    sub_location_id);
        }
        return mSendResponse;
    }

    private void loadScanResult(String ProjectId,
                                String batchId,
                                String qr_code,
                                String other_batch,
                                String re_verify, String scan_by) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<DetailScan> aboutUs = serviceApi.getScanResult(ProjectId, batchId, qr_code, other_batch, re_verify,scan_by);
        aboutUs.enqueue(new Callback<DetailScan>() {
            @Override
            public void onResponse(Call<DetailScan> call, Response<DetailScan> response) {
                DetailScan batchDetails = response.body();

                if (batchDetails != null) {
                    mBatchDetailsMutableLiveData.setValue(batchDetails);
                }
            }

            @Override
            public void onFailure(Call<DetailScan> call, Throwable t) {


            }
        });
    }

    private void loadScanResultRFID(String ProjectId,
                                    String batchId,
                                    String code,
                                    String other_batch,
                                    String re_verify, String scan_by) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<DetailScan> aboutUs = serviceApi.getScanResultRFID(ProjectId, batchId, code, other_batch, re_verify, scan_by);
        aboutUs.enqueue(new Callback<DetailScan>() {
            @Override
            public void onResponse(Call<DetailScan> call, Response<DetailScan> response) {
                DetailScan batchDetails = response.body();

                if (batchDetails != null) {
                    mBatchDetailsMutableLiveData.setValue(batchDetails);
                }
            }

            @Override
            public void onFailure(Call<DetailScan> call, Throwable t) {


            }
        });
    }

    public LiveData<Reson> getAllResons() {
        if (mResons != null) {
            loadResons();
        }
        return mResons;
    }

    private void loadResons() {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<Reson> resons = serviceApi.getAllResons();
        resons.enqueue(new Callback<Reson>() {
            @Override
            public void onResponse(Call<Reson> call, Response<Reson> response) {
                Reson re = response.body();
                if (re != null) {
                    mResons.setValue(re);
                }
            }

            @Override
            public void onFailure(Call<Reson> call, Throwable t) {

            }
        });
    }

    private void SubmitAssetApi(String asset_id,
                                String project_id,
                                String mode,
                                String qty,
                                String in_use,
                                String batch_id,
                                String user_id,
                                String reason_id,
                                String remark,
                                String in_use_qty,
                                String not_in_use_qty,
                                String not_found_qty,
                                String plant_id,
                                String location_id,
                                String sub_location_id) {

        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final  Call<SendRespose> sendResposeCall=serviceApi.SubmmitAssetOld( asset_id,
                project_id,
                mode,
                qty,
                in_use,
                batch_id,
                user_id,
                reason_id,
                remark,
                in_use_qty,
                not_in_use_qty,
                not_found_qty,
                plant_id,
                location_id,
                sub_location_id);
        sendResposeCall.enqueue(new Callback<SendRespose>() {
            @Override
            public void onResponse(Call<SendRespose> call, Response<SendRespose> response) {
                SendRespose sendRespose = response.body();
                if (sendRespose != null) {
                    mSendResponse.setValue(sendRespose);
                }
            }

            @Override
            public void onFailure(Call<SendRespose> call, Throwable t) {

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


    public LiveData<Details> getDetails(String qr_code, String code) {
        if (detailsResult != null) {
            scanResult(qr_code, code);
        }
        return detailsResult;
    }

    private void scanResult(String qr_code, String code) {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<Details> deatils = serviceApi.getDetails(qr_code, code);
        deatils.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                Details assetDetails = response.body();
                if (assetDetails != null) {
                    detailsResult.setValue(assetDetails);
                }
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {


            }
        });
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
}
