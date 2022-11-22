package com.levare.verificare.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailScan {

    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Asset_Details")
    @Expose
    private AssetDetails assetDetails;

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

    public AssetDetails getAssetDetails() {
        return assetDetails;
    }

    public void setAssetDetails(AssetDetails assetDetails) {
        this.assetDetails = assetDetails;
    }

    public class AssetDetails {

        @SerializedName("asset_id")
        @Expose
        private String assetId;
        @SerializedName("plant_id")
        @Expose
        private String plant_id;
        @SerializedName("asset_number")
        @Expose
        private String assetNumber;
        @SerializedName("asset_class")
        @Expose
        private String assetClass;
        @SerializedName("asset_description")
        @Expose
        private String assetDescription;
        @SerializedName("cap_date")
        @Expose
        private String capDate;
        @SerializedName("book_qty")
        @Expose
        private String bookQty;
        @SerializedName("gross_block")
        @Expose
        private String grossBlock;
        @SerializedName("cost_center")
        @Expose
        private String costCenter;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("location_id")
        @Expose
        private String location_id;
        @SerializedName("sub_location")
        @Expose
        private String sub_location;
        @SerializedName("sub_location_id")
        @Expose
        private String sub_location_id;
        @SerializedName("responsible_department")
        @Expose
        private String responsible_department;
        @SerializedName("current_images")
        @Expose
        private CurrentImagess current_images;


        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSub_location() {
            return sub_location;
        }

        public void setSub_location(String sub_location) {
            this.sub_location = sub_location;
        }

        public String getResponsible_department() {
            return responsible_department;
        }

        public void setResponsible_department(String responsible_department) {
            this.responsible_department = responsible_department;
        }

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

        public String getAssetClass() {
            return assetClass;
        }

        public void setAssetClass(String assetClass) {
            this.assetClass = assetClass;
        }

        public String getAssetDescription() {
            return assetDescription;
        }

        public void setAssetDescription(String assetDescription) {
            this.assetDescription = assetDescription;
        }

        public String getCapDate() {
            return capDate;
        }

        public void setCapDate(String capDate) {
            this.capDate = capDate;
        }

        public String getBookQty() {
            return bookQty;
        }

        public void setBookQty(String bookQty) {
            this.bookQty = bookQty;
        }

        public String getGrossBlock() {
            return grossBlock;
        }

        public void setGrossBlock(String grossBlock) {
            this.grossBlock = grossBlock;
        }

        public String getCostCenter() {
            return costCenter;
        }

        public void setCostCenter(String costCenter) {
            this.costCenter = costCenter;
        }

        public String getLocation_id() {
            return location_id;
        }

        public void setLocation_id(String location_id) {
            this.location_id = location_id;
        }

        public String getSub_location_id() {
            return sub_location_id;
        }

        public void setSub_location_id(String sub_location_id) {
            this.sub_location_id = sub_location_id;
        }

        public String getPlant_id() {
            return plant_id;
        }

        public void setPlant_id(String plant_id) {
            this.plant_id = plant_id;
        }

        public CurrentImagess getCurrent_images() {
            return current_images;
        }

        public void setCurrent_images(CurrentImagess current_images) {
            this.current_images = current_images;
        }


    }

    public class  CurrentImagess{
        @SerializedName("image1")
        @Expose
        private String image1;
        @SerializedName("image2")
        @Expose
        private String image2;
        @SerializedName("image3")
        @Expose
        private String image3;
        @SerializedName("image4")
        @Expose
        private String image4;
        @SerializedName("image5")
        @Expose
        private String image5;

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public String getImage4() {
            return image4;
        }

        public void setImage4(String image4) {
            this.image4 = image4;
        }

        public String getImage5() {
            return image5;
        }

        public void setImage5(String image5) {
            this.image5 = image5;
        }
    }






}