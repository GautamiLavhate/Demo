package com.levare.verificare.model;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetList {

    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Assets_Details")
    @Expose
    private List<AssetsDetail> assetsDetails = null;

    public String getRESPONSESTATUS() {
        return rESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String rESPONSESTATUS) {
        this.rESPONSESTATUS = rESPONSESTATUS;
    }

    public String getRESPONSEMSG() {
        return rESPONSEMSG;
    }

    public void setRESPONSEMSG(String rESPONSEMSG) {
        this.rESPONSEMSG = rESPONSEMSG;
    }

    public List<AssetsDetail> getAssetsDetails() {
        return assetsDetails;
    }

    public void setAssetsDetails(List<AssetsDetail> assetsDetails) {
        this.assetsDetails = assetsDetails;
    }


    public static class AssetsDetail {

        @SerializedName("asset_id")
        @Expose
        private String assetId;
        @SerializedName("asset_number")
        @Expose
        private String assetNumber;
        @SerializedName("asset_status")
        @Expose
        private String assetStatus;
        @SerializedName("is_scan")
        @Expose
        private String isScan;

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public String getAssetNumber() {
            return assetNumber;
        }

        public void setAssetNumber(String assetNumber) {
            this.assetNumber = assetNumber;
        }

        public String getAssetStatus() {
            return assetStatus;
        }

        public void setAssetStatus(String assetStatus) {
            this.assetStatus = assetStatus;
        }

        public String getIsScan() {
            return isScan;
        }

        public void setIsScan(String isScan) {
            this.isScan = isScan;
        }
    }
}