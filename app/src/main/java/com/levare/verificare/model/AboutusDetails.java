
package com.levare.verificare.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutusDetails implements Parcelable {

    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("company_domain")
    @Expose
    private String companyDomain;
    @SerializedName("company_email")
    @Expose
    private String companyEmail;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_phone")
    @Expose
    private String companyPhone;
    @SerializedName("company_mobile")
    @Expose
    private String companyMobile;
    public final static Creator<AboutusDetails> CREATOR = new Creator<AboutusDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AboutusDetails createFromParcel(Parcel in) {
            return new AboutusDetails(in);
        }

        public AboutusDetails[] newArray(int size) {
            return (new AboutusDetails[size]);
        }

    };

    protected AboutusDetails(Parcel in) {
        this.about = ((String) in.readValue((String.class.getClassLoader())));
        this.logo = ((String) in.readValue((String.class.getClassLoader())));
        this.companyDomain = ((String) in.readValue((String.class.getClassLoader())));
        this.companyEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.companyName = ((String) in.readValue((String.class.getClassLoader())));
        this.companyPhone = ((String) in.readValue((String.class.getClassLoader())));
        this.companyMobile= ((String) in.readValue((String.class.getClassLoader())));
    }

    public AboutusDetails() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(about);
        dest.writeValue(logo);
        dest.writeValue(companyDomain);
        dest.writeValue(companyEmail);
        dest.writeValue(companyName);
        dest.writeValue(companyPhone);
        dest.writeValue(companyMobile);
    }

    public int describeContents() {
        return 0;
    }

}
