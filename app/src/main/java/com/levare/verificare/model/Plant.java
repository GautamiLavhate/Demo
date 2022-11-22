package com.levare.verificare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Plant {
    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Plant_Details")
    @Expose
    private ArrayList<PlantDetails> plantDetails;


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

    public ArrayList<PlantDetails> getPlantDetails() {
        return plantDetails;
    }

    public void setPlantDetails(ArrayList<PlantDetails> plantDetails) {
        this.plantDetails = plantDetails;
    }

    public class PlantDetails{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("description")
        @Expose
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
