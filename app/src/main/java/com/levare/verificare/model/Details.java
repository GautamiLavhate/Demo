package com.levare.verificare.model;

public class Details {

    private String RESPONSE_STATUS;
    private String RESPONSE_MSG;
    private AssetDetailsBean Asset_Details;

    public String getRESPONSE_STATUS() {
        return RESPONSE_STATUS;
    }

    public void setRESPONSE_STATUS(String RESPONSE_STATUS) {
        this.RESPONSE_STATUS = RESPONSE_STATUS;
    }

    public String getRESPONSE_MSG() {
        return RESPONSE_MSG;
    }

    public void setRESPONSE_MSG(String RESPONSE_MSG) {
        this.RESPONSE_MSG = RESPONSE_MSG;
    }

    public AssetDetailsBean getAsset_Details() {
        return Asset_Details;
    }

    public void setAsset_Details(AssetDetailsBean Asset_Details) {
        this.Asset_Details = Asset_Details;
    }

    public static class AssetDetailsBean {

        private String asset_id;
        private String asset_number;
        private String asset_class;
        private String asset_description;
        private String location;
        private String location_id;
        private String sub_location;
        private String sub_location_id;
        private String responsible_department;
        private String responsible_person;
        private String cap_date;
        private String book_qty;
        private String gross_block;
        private String cost_center;
        private CurrentImages current_images;
        private PastImages past_images;

        public String getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(String asset_id) {
            this.asset_id = asset_id;
        }

        public String getAsset_number() {
            return asset_number;
        }

        public void setAsset_number(String asset_number) {
            this.asset_number = asset_number;
        }

        public String getAsset_class() {
            return asset_class;
        }

        public void setAsset_class(String asset_class) {
            this.asset_class = asset_class;
        }

        public String getAsset_description() {
            return asset_description;
        }

        public void setAsset_description(String asset_description) {
            this.asset_description = asset_description;
        }

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

        public String getResponsible_person() {
            return responsible_person;
        }

        public void setResponsible_person(String responsible_person) {
            this.responsible_person = responsible_person;
        }

        public String getCap_date() {
            return cap_date;
        }

        public void setCap_date(String cap_date) {
            this.cap_date = cap_date;
        }

        public String getBook_qty() {
            return book_qty;
        }

        public void setBook_qty(String book_qty) {
            this.book_qty = book_qty;
        }

        public String getGross_block() {
            return gross_block;
        }

        public void setGross_block(String gross_block) {
            this.gross_block = gross_block;
        }

        public String getCost_center() {
            return cost_center;
        }

        public void setCost_center(String cost_center) {
            this.cost_center = cost_center;
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

        public CurrentImages getCurrent_images() {
            return current_images;
        }

        public void setCurrent_images(CurrentImages current_images) {
            this.current_images = current_images;
        }

        public PastImages getPast_images() {
            return past_images;
        }

        public void setPast_images(PastImages past_images) {
            this.past_images = past_images;
        }
    }

    public class CurrentImages{

    }

    public class PastImages{

    }
}
