package com.levare.verificare.model;



import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User implements Parcelable
{
    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Profile_Details")
    @Expose
    private ProfileDetails profileDetails;
    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
            ;

    protected User(Parcel in) {
        this.rESPONSESTATUS = ((String) in.readValue((String.class.getClassLoader())));
        this.rESPONSEMSG = ((String) in.readValue((String.class.getClassLoader())));
        this.profileDetails = ((ProfileDetails) in.readValue((ProfileDetails.class.getClassLoader())));
    }

    public User() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rESPONSESTATUS);
        dest.writeValue(rESPONSEMSG);
        dest.writeValue(profileDetails);
    }

    public int describeContents() {
        return 0;
    }

}