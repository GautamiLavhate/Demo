package com.levare.verificare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SplashScreen {

    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Splashscreen_Details")
    @Expose
    private SplashscreenDetails splashscreenDetails;

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

    public SplashscreenDetails getSplashscreenDetails() {
        return splashscreenDetails;
    }

    public void setSplashscreenDetails(SplashscreenDetails splashscreenDetails) {
        this.splashscreenDetails = splashscreenDetails;
    }




public static class SplashscreenDetails {

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("title")
    @Expose
    private String title;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

}