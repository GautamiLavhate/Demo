package com.levare.verificare.fragment;

import com.levare.verificare.model.BatchDetail;

public class SetCurrentBatch {

    private SetCurrentBatch(){

    }

    private BatchDetail mBatchDetail;
    private static SetCurrentBatch mSetCurrentBatch=null;
    private static Boolean isDetailsOnly = false;
    private static String type;


    public static SetCurrentBatch getInstance(){
        if (mSetCurrentBatch==null){
            mSetCurrentBatch=new SetCurrentBatch();
        }
        return mSetCurrentBatch;
    }

    public void setCurrentBatchDetails(BatchDetail batchDetails){
        mBatchDetail=batchDetails;
    }

    public BatchDetail getCurrentBatch(){
        return mBatchDetail;
    }

    public static Boolean getIsDetailsOnly() {
        return isDetailsOnly;
    }

    public static void setIsDetailsOnly(Boolean isDetailsOnly) {
        SetCurrentBatch.isDetailsOnly = isDetailsOnly;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        SetCurrentBatch.type = type;
    }
}
