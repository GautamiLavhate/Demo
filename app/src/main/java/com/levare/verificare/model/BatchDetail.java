package com.levare.verificare.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BatchDetail implements Parcelable
{

    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("plant_id")
    @Expose
    private String plantId;
    @SerializedName("batch_id")
    @Expose
    private String batchId;
    @SerializedName("batch_name")
    @Expose
    private String batchName;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("batch_status")
    @Expose
    private String batchStatus;
    @SerializedName("total_quantity")
    @Expose
    private Integer totalQuantity;
    @SerializedName("permission")
    @Expose
    private ArrayList<String> permissions = null;

    public final static Parcelable.Creator<BatchDetail> CREATOR = new Creator<BatchDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BatchDetail createFromParcel(Parcel in) {
            return new BatchDetail(in);
        }

        public BatchDetail[] newArray(int size) {
            return (new BatchDetail[size]);
        }

    };

    protected BatchDetail(Parcel in) {
        this.projectId = ((String) in.readValue((String.class.getClassLoader())));
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
        this.plantId = ((String) in.readValue((String.class.getClassLoader())));
        this.batchId = ((String) in.readValue((String.class.getClassLoader())));
        this.batchName = ((String) in.readValue((String.class.getClassLoader())));
        this.dueDate = ((String) in.readValue((String.class.getClassLoader())));
        this.batchStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.totalQuantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public BatchDetail() {
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(projectId);
        dest.writeValue(clientId);
        dest.writeValue(plantId);
        dest.writeValue(batchId);
        dest.writeValue(batchName);
        dest.writeValue(dueDate);
        dest.writeValue(batchStatus);
        dest.writeValue(totalQuantity);
    }

    public int describeContents() {
        return 0;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }
}