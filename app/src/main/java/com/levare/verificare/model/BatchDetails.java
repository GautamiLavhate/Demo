package com.levare.verificare.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class BatchDetails implements Parcelable
{
    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Batch_Details")
    @Expose
    private List<BatchDetail> batchDetails = null;
    public final static Parcelable.Creator<BatchDetails> CREATOR = new Creator<BatchDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BatchDetails createFromParcel(Parcel in) {
            return new BatchDetails(in);
        }

        public BatchDetails[] newArray(int size) {
            return (new BatchDetails[size]);
        }

    }
            ;

    protected BatchDetails(Parcel in) {
        this.rESPONSESTATUS = ((String) in.readValue((String.class.getClassLoader())));
        this.rESPONSEMSG = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.batchDetails, (BatchDetail.class.getClassLoader()));
    }

    public BatchDetails() {
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

    public List<BatchDetail> getBatchDetails() {
        return batchDetails;
    }

    public void setBatchDetails(List<BatchDetail> batchDetails) {
        this.batchDetails = batchDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rESPONSESTATUS);
        dest.writeValue(rESPONSEMSG);
        dest.writeList(batchDetails);
    }

    public int describeContents() {
        return 0;
    }

}