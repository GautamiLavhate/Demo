package com.levare.verificare.network;

import com.levare.verificare.model.AboutUs;
import com.levare.verificare.model.AddLocation;
import com.levare.verificare.model.AddSubLocation;
import com.levare.verificare.model.AssetList;
import com.levare.verificare.model.BatchDetails;
import com.levare.verificare.model.Dashboard;
import com.levare.verificare.model.DetailScan;
import com.levare.verificare.model.Details;
import com.levare.verificare.model.Location;
import com.levare.verificare.model.Plant;
import com.levare.verificare.model.Profile;
import com.levare.verificare.model.Reson;
import com.levare.verificare.model.SendRespose;
import com.levare.verificare.model.SplashScreen;
import com.levare.verificare.model.SubLocation;
import com.levare.verificare.model.SubmitBatch;
import com.levare.verificare.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET("app_get_splashscreen.php")
    Call<SplashScreen> getSplashScreen();

    @GET("app_get_aboutus.php")
    Call<AboutUs> getAboutUs();

    @FormUrlEncoded
    @POST("app_login.php")
    Call<User> UserLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("device_id") String device_id,
            @Field("device_name") String device_name);

    @GET("app_get_batch.php")
    Call<BatchDetails> getBatchdetails(@Query("user_id") String user_id);

    @GET("app_get_dashboard_details.php")
    Call<Dashboard> getDashBoard(
            @Query("batch_id") String batchId,
            @Query("project_id") String ProjectId);

    @GET("app_get_assets.php")
    Call<AssetList> getAssetList(
            @Query("batch_id") String batchId,
            @Query("project_id") String ProjectId);

    @FormUrlEncoded
    @POST("app_verify_asset.php")
    Call<DetailScan> getScanResult(@Field("project_id") String ProjectId,
                                   @Field("batch_id") String batchId,
                                   @Field("qr_code") String qr_code,
                                   @Field("other_batch") String other_batch,
                                   @Field("re_verify") String re_verify,
                                   @Field("scan_by") String scantype);

    @GET("app_verify_asset_rfid.php")
    Call<DetailScan> getScanResultRFID(@Query("project_id") String ProjectId,
                                       @Query("batch_id") String batchId,
                                       @Query("code") String qr_code,
                                       @Query("other_batch") String other_batch,
                                       @Query("re_verify") String re_verify,
                                       @Query("scan_by") String scantype);

    @GET("app_get_asset_details.php")
    Call<DetailScan> getAssetDetail(
            @Query("batch_id") String batch_id,
            @Query("project_id") String project_id,
            @Query("unique_id") String unique_id,
            @Query("other_batch") String other_batch,
            @Query("re_verify") String re_verify,
            @Query("reason") String reason,
            @Query("asset_id") String asset_id);


    @POST("app_asset_info.php")
    @FormUrlEncoded
    Call<Details> getDetails(@Field("qr_code") String qr_code, @Field("code") String code);

    @GET("app_get_reason.php")
    Call<Reson> getAllResons();

    @Multipart
    @POST("app_submit_details.php")
    Call<SendRespose> SubmmitAsset(@Part("asset_id") RequestBody asset_id,
                                   @Part("project_id") RequestBody project_id,
                                   @Part("mode") RequestBody mode,
                                   @Part("qty") RequestBody qty,
                                   @Part("in_use") RequestBody in_use,
                                   @Part("batch_id") RequestBody batch_id,
                                   @Part("user_id") RequestBody user_id,
                                   @Part("reason_id") RequestBody reason_id,
                                   @Part("remark") RequestBody remark,
                                   @Part("in_use_qty") RequestBody in_use_qty,
                                   @Part("not_in_use_qty") RequestBody not_in_use_qty,
                                   @Part("not_found_qty") RequestBody not_found_qty,
                                   @Part("plant_id") RequestBody plant_id,
                                   @Part("location_id") RequestBody location_id,
                                   @Part("sub_location_id") RequestBody sub_location_id,
                                   @Part MultipartBody.Part image1,
                                   @Part MultipartBody.Part image2,
                                   @Part MultipartBody.Part image3,
                                   @Part MultipartBody.Part image4,
                                   @Part MultipartBody.Part image5,
                                   @Part("old_image1") RequestBody old_image1,
                                   @Part("old_image2") RequestBody old_image2,
                                   @Part("old_image3") RequestBody old_image3,
                                   @Part("old_image4") RequestBody old_image4,
                                   @Part("old_image5") RequestBody old_image5);

    @FormUrlEncoded
    @POST("app_submit_details.php")
    Call<SendRespose> SubmmitAssetOld(@Field("asset_id") String asset_id,
                                      @Field("project_id") String project_id,
                                      @Field("mode") String mode,
                                      @Field("qty") String qty,
                                      @Field("in_use") String in_use,
                                      @Field("batch_id") String batch_id,
                                      @Field("user_id") String user_id,
                                      @Field("reason_id") String reason_id,
                                      @Field("remark") String remark,
                                      @Field("in_use_qty") String in_use_qty,
                                      @Field("not_in_use_qty") String not_in_use_qty,
                                      @Field("not_found_qty") String not_found_qty,
                                      @Field("plant_id") String plant_id,
                                      @Field("location_id") String location_id,
                                      @Field("sub_location_id") String sub_location_id);

    @FormUrlEncoded
    @POST("app_submit_batch.php")
    Call<SubmitBatch> submitBatch(@Field("asset_id") String batchid,
                                  @Field("project_id") String project_id,@Field("user_id") String user_id);

    @GET("app_get_profile.php")
    Call<Profile> getProfile(@Query("user_id") String id);

    @GET("app_change_password.php")
    Call<Profile> changePassword(@Query("old_password") String old_password, @Query("new_password") String new_password, @Query("conf_password") String conf_password, @Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("app_get_plant.php")
    Call<Plant> getPlant(@Field("client_id") String client_id);

    @FormUrlEncoded
    @POST("app_get_location.php")
    Call<Location> getLocations(@Field("client_id") String client_id);


    @FormUrlEncoded
    @POST("app_get_location.php")
    Call<Location> getLocationAsPerPlant(@Field("client_id")String client_id,
                                         @Field("plant_id")String plant_id);

    @FormUrlEncoded
    @POST("app_add_location.php")
    Call<AddLocation> getAddLocationStatus(@Field("client_id")String client_id,
                                           @Field("plant_id")String plant_id,
                                           @Field("location_name")String location_name);


    @FormUrlEncoded
    @POST("app_get_sub_location.php")
    Call<SubLocation> getSubLocation(@Field("client_id") String client_id);

    @FormUrlEncoded
    @POST("app_get_sub_location.php")
    Call<SubLocation> getSubLocationAsPerLocation(@Field("client_id") String client_id,
                                                  @Field("location_id") String location_id);

    @FormUrlEncoded
    @POST("app_add_sub_location.php")
    Call<AddSubLocation> getAddSubLocationStatus(@Field("client_id")String client_id,
                                                 @Field("plant_id")String plant_id,
                                                 @Field("location_id")String location_id,
                                                 @Field("sub_location_name")String sub_location_name);

    @FormUrlEncoded
    @POST("app_logout.php")
    Call<User> logout(@Field("user_id") String user_id,
                      @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("app_delete_asset_image.php")
    Call<SendRespose> deleteImage(@Field("asset_id") String asset_id,
                           @Field("field") String field);

    @Multipart
    @POST("app_submit_details.php")
    Call<SendRespose> NewSubmitDetails(@Part("asset_id") RequestBody asset_id,
                                       @Part("project_id") RequestBody project_id,
                                       @Part("mode") RequestBody mode,
                                       @Part("qty") RequestBody qty,
                                       @Part("in_use") RequestBody in_use,
                                       @Part("batch_id") RequestBody batch_id,
                                       @Part("user_id") RequestBody user_id,
                                       @Part("reason_id") RequestBody reason_id,
                                       @Part("remark") RequestBody remark,
                                       @Part("in_use_qty") RequestBody in_use_qty,
                                       @Part("not_in_use_qty") RequestBody not_in_use_qty,
                                       @Part("not_found_qty") RequestBody not_found_qty,
                                       @Part("plant_id") RequestBody plant_id,
                                       @Part("location_id") RequestBody location_id,
                                       @Part("sub_location_id") RequestBody sub_location_id,
                                       @Part MultipartBody.Part image1,
                                       @Part MultipartBody.Part image2,
                                       @Part MultipartBody.Part image3,
                                       @Part MultipartBody.Part image4,
                                       @Part MultipartBody.Part image5,
                                       @Part("old_image1") RequestBody old_image1,
                                       @Part("old_image2") RequestBody old_image2,
                                       @Part("old_image3") RequestBody old_image3,
                                       @Part("old_image4") RequestBody old_image4,
                                       @Part("old_image5") RequestBody old_image5);
    /**
     *
     * http://applexinfotech.com/fav/api/app_submit_details.php?asset_id=101&project_id=4&mode=auto&qty=1&in_use=yes&batch_id=9&user_id=4&reason_id=&remark=&in_use_qty=&not_in_use_qty=&not_found_qty=

    /**
     *
     *
     *   http://applexinfotech.com/fav/api/app_verify_asset.php?project_id=1&batch_id=4&qr_code=7%201100%20VW14000036-0-0&other_batch=0&re_verify=0
     *
     */

}


