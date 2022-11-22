package com.levare.verificare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Reson {

    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Reason_Details")
    @Expose
    private List<ReasonDetail> reasonDetails = null;

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

    public List<ReasonDetail> getReasonDetails() {
        return reasonDetails;
    }

    public void setReasonDetails(List<ReasonDetail> reasonDetails) {
        this.reasonDetails = reasonDetails;
    }
    public class ReasonDetail {

        @SerializedName("reason_id")
        @Expose
        private String reasonId;
        @SerializedName("reason_title")
        @Expose
        private String reasonTitle;

        public String getReasonId() {
            return reasonId;
        }

        public void setReasonId(String reasonId) {
            this.reasonId = reasonId;
        }

        public String getReasonTitle() {
            return reasonTitle;
        }

        public void setReasonTitle(String reasonTitle) {
            this.reasonTitle = reasonTitle;
        }

    }
}