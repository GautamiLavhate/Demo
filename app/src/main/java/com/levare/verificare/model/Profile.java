package com.levare.verificare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Profile_Details")
    @Expose
    private ProfileDetails profileDetails;

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

    public ProfileDetails getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(ProfileDetails profileDetails) {
        this.profileDetails = profileDetails;
    }


    public class ProfileDetails {

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("photo")
        @Expose
        private String photo;
        @SerializedName("mobile")
        @Expose
        private Object mobile;
        @SerializedName("aadhar_no")
        @Expose
        private String aadharNo;
        @SerializedName("user_type")
        @Expose
        private String userType;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public String getAadharNo() {
            return aadharNo;
        }

        public void setAadharNo(String aadharNo) {
            this.aadharNo = aadharNo;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}