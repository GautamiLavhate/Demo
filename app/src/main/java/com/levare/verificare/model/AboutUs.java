
package com.levare.verificare.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUs implements Parcelable {

    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Aboutus_Details")
    @Expose
    private AboutusDetails aboutusDetails;
    public final static Creator<AboutUs> CREATOR = new Creator<AboutUs>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AboutUs createFromParcel(Parcel in) {
            return new AboutUs(in);
        }

        public AboutUs[] newArray(int size) {
            return (new AboutUs[size]);
        }

    };

    protected AboutUs(Parcel in) {
        this.rESPONSESTATUS = ((String) in.readValue((String.class.getClassLoader())));
        this.rESPONSEMSG = ((String) in.readValue((String.class.getClassLoader())));
        this.aboutusDetails = ((AboutusDetails) in.readValue((AboutusDetails.class.getClassLoader())));
    }

    public AboutUs() {
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

    public AboutusDetails getAboutusDetails() {
        return aboutusDetails;
    }

    public void setAboutusDetails(AboutusDetails aboutusDetails) {
        this.aboutusDetails = aboutusDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rESPONSESTATUS);
        dest.writeValue(rESPONSEMSG);
        dest.writeValue(aboutusDetails);
    }

    public int describeContents() {
        return 0;
    }

}
