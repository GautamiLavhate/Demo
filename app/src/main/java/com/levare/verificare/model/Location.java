package com.levare.verificare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Location {
    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Location_Details")
    @Expose
    private ArrayList<LocationDetails> locationDetails;

    public String getrESPONSESTATUS() {
        return rESPONSESTATUS;
    }

    public void setrESPONSESTATUS(String rESPONSESTATUS) {
        this.rESPONSESTATUS = rESPONSESTATUS;
    }

    public String getrESPONSEMSG() {
        return rESPONSEMSG;
    }

    public void setrESPONSEMSG(String rESPONSEMSG) {
        this.rESPONSEMSG = rESPONSEMSG;
    }

    public ArrayList<LocationDetails> getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(ArrayList<LocationDetails> locationDetails) {
        this.locationDetails = locationDetails;
    }

    public class LocationDetails{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("plant_name")
        @Expose
        private String plant_name;
        @SerializedName("plant_code")
        @Expose
        private String plant_code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlant_name() {
            return plant_name;
        }

        public void setPlant_name(String plant_name) {
            this.plant_name = plant_name;
        }

        public String getPlant_code() {
            return plant_code;
        }

        public void setPlant_code(String plant_code) {
            this.plant_code = plant_code;
        }
    }
}
