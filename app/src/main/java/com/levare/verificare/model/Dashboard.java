package com.levare.verificare.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Dashboard implements Parcelable {
    @SerializedName("RESPONSE_STATUS")
    @Expose
    private String rESPONSESTATUS;
    @SerializedName("RESPONSE_MSG")
    @Expose
    private String rESPONSEMSG;
    @SerializedName("Dashboard_Details")
    @Expose
    private DashboardDetails dashboardDetails;

    public final static Parcelable.Creator<Dashboard> CREATOR = new Creator<Dashboard>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Dashboard createFromParcel(Parcel in) {
            return new Dashboard(in);
        }

        public Dashboard[] newArray(int size) {
            return (new Dashboard[size]);
        }

    };

    protected Dashboard(Parcel in) {
        this.rESPONSESTATUS = ((String) in.readValue((String.class.getClassLoader())));
        this.rESPONSEMSG = ((String) in.readValue((String.class.getClassLoader())));
        this.dashboardDetails = ((DashboardDetails) in.readValue((DashboardDetails.class.getClassLoader())));
    }

    public Dashboard() {
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

    public DashboardDetails getDashboardDetails() {
        return dashboardDetails;
    }

    public void setDashboardDetails(DashboardDetails dashboardDetails) {
        this.dashboardDetails = dashboardDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rESPONSESTATUS);
        dest.writeValue(rESPONSEMSG);
        dest.writeValue(dashboardDetails);
    }

    public int describeContents() {
        return 0;
    }


public    static class DashboardDetails implements Parcelable {
        @SerializedName("dates")
        @Expose
        private List<Date> dates = null;
        @SerializedName("total_quantity")
        @Expose
        private Integer totalQuantity;
        @SerializedName("completed")
        @Expose
        private Integer completed;
        @SerializedName("pending")
        @Expose
        private Integer pending;
        @SerializedName("completed_percentage")
        @Expose
        private Double completedPercentage;
        @SerializedName("pending_percentage")
        @Expose
        private Double pendingPercentage;
        @SerializedName("in_use")
        @Expose
        private Integer inUse;
        @SerializedName("not_in_use")
        @Expose
        private Integer notInUse;
        @SerializedName("in_use_percentage")
        @Expose
        private Double inUsePercentage;
        @SerializedName("not_in_use_percentage")
        @Expose
        private Double notInUsePercentage;
        @SerializedName("auto_mode")
        @Expose
        private Integer autoMode;
        @SerializedName("manual_mode")
        @Expose
        private Integer manualMode;
        public final static Parcelable.Creator<DashboardDetails> CREATOR = new Creator<DashboardDetails>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public DashboardDetails createFromParcel(Parcel in) {
                return new DashboardDetails(in);
            }

            public DashboardDetails[] newArray(int size) {
                return (new DashboardDetails[size]);
            }

        };

        protected DashboardDetails(Parcel in) {
            in.readList(this.dates, (Date.class.getClassLoader()));
            this.totalQuantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.completed = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.pending = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.completedPercentage = ((Double) in.readValue((Integer.class.getClassLoader())));
            this.pendingPercentage = ((Double) in.readValue((Integer.class.getClassLoader())));
            this.inUse = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.notInUse = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.inUsePercentage = ((Double) in.readValue((Integer.class.getClassLoader())));
            this.notInUsePercentage = ((Double) in.readValue((Integer.class.getClassLoader())));
            this.autoMode = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.manualMode = ((Integer) in.readValue((Integer.class.getClassLoader())));
        }

        public DashboardDetails() {
        }

        public List<Date> getDates() {
            return dates;
        }

        public void setDates(List<Date> dates) {
            this.dates = dates;
        }

        public Integer getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(Integer totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public Integer getCompleted() {
            return completed;
        }

        public void setCompleted(Integer completed) {
            this.completed = completed;
        }

        public Integer getPending() {
            return pending;
        }

        public void setPending(Integer pending) {
            this.pending = pending;
        }

        public Double getCompletedPercentage() {
            return completedPercentage;
        }

        public void setCompletedPercentage(Double completedPercentage) {
            this.completedPercentage = completedPercentage;
        }

        public Double getPendingPercentage() {
            return pendingPercentage;
        }

        public void setPendingPercentage(Double pendingPercentage) {
            this.pendingPercentage = pendingPercentage;
        }

        public Integer getInUse() {
            return inUse;
        }

        public void setInUse(Integer inUse) {
            this.inUse = inUse;
        }

        public Integer getNotInUse() {
            return notInUse;
        }

        public void setNotInUse(Integer notInUse) {
            this.notInUse = notInUse;
        }

        public Double getInUsePercentage() {
            return inUsePercentage;
        }

        public void setInUsePercentage(Double inUsePercentage) {
            this.inUsePercentage = inUsePercentage;
        }

        public Double getNotInUsePercentage() {
            return notInUsePercentage;
        }

        public void setNotInUsePercentage(Double notInUsePercentage) {
            this.notInUsePercentage = notInUsePercentage;
        }

        public Integer getAutoMode() {
            return autoMode;
        }

        public void setAutoMode(Integer autoMode) {
            this.autoMode = autoMode;
        }

        public Integer getManualMode() {
            return manualMode;
        }

        public void setManualMode(Integer manualMode) {
            this.manualMode = manualMode;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(dates);
            dest.writeValue(totalQuantity);
            dest.writeValue(completed);
            dest.writeValue(pending);
            dest.writeValue(completedPercentage);
            dest.writeValue(pendingPercentage);
            dest.writeValue(inUse);
            dest.writeValue(notInUse);
            dest.writeValue(inUsePercentage);
            dest.writeValue(notInUsePercentage);
            dest.writeValue(autoMode);
            dest.writeValue(manualMode);
        }

        public int describeContents() {
            return 0;
        }


       public static class Date implements Parcelable {
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("count")
            @Expose
            private Integer count;
            public final static Parcelable.Creator<Date> CREATOR = new Creator<Date>() {


                @SuppressWarnings({
                        "unchecked"
                })
                public Date createFromParcel(Parcel in) {
                    return new Date(in);
                }

                public Date[] newArray(int size) {
                    return (new Date[size]);
                }

            };

            protected Date(Parcel in) {
                this.date = ((String) in.readValue((String.class.getClassLoader())));
                this.count = ((Integer) in.readValue((Integer.class.getClassLoader())));
            }

            public Date() {
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(date);
                dest.writeValue(count);
            }

            public int describeContents() {
                return 0;
            }

        }
    }
}