package com.levare.verificare.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetails implements Parcelable
{

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("online_time")
    @Expose
    private Integer onlineTime;
    @SerializedName("loggedin")
    @Expose
    private Boolean loggedin;
    @SerializedName("user_type")
    @Expose
    private String userType;

    public final static Parcelable.Creator<ProfileDetails> CREATOR = new Creator<ProfileDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProfileDetails createFromParcel(Parcel in) {
            return new ProfileDetails(in);
        }

        public ProfileDetails[] newArray(int size) {
            return (new ProfileDetails[size]);
        }

    }
            ;

    protected ProfileDetails(Parcel in) {
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.lastLogin = ((String) in.readValue((String.class.getClassLoader())));
        this.onlineTime = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.loggedin = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.userType = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ProfileDetails() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Integer onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Boolean getLoggedin() {
        return loggedin;
    }

    public void setLoggedin(Boolean loggedin) {
        this.loggedin = loggedin;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userName);
        dest.writeValue(email);
        dest.writeValue(name);
        dest.writeValue(photo);
        dest.writeValue(clientId);
        dest.writeValue(userId);
        dest.writeValue(lastLogin);
        dest.writeValue(onlineTime);
        dest.writeValue(loggedin);
        dest.writeValue(userType);
    }

    public int describeContents() {
        return 0;
    }

}
