package com.levare.verificare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.levare.verificare.R;
import com.levare.verificare.model.BatchDetail;
import com.levare.verificare.network.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class SetBatchListAdapter extends RecyclerView.Adapter<SetBatchHolder> {

    private List<BatchDetail> batchDetailList;
    private OnClickItem mOnClickItem;
    SessionManager sessionManager;

    Context context;
    public SetBatchListAdapter(List<BatchDetail> viewItemList, OnClickItem mOnClickItem) {
        this.batchDetailList = viewItemList;
        this.mOnClickItem = mOnClickItem;
    }

    @Override
    public SetBatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_set_item, parent, false);
        SetBatchHolder setBatchHolder = new SetBatchHolder(itemView);
        context=parent.getContext();
        return setBatchHolder;
    }

    @Override
    public void onBindViewHolder(SetBatchHolder setBatchHolder, int position) {
        sessionManager=new SessionManager(context);
        if (batchDetailList != null) {
            BatchDetail viewItem = batchDetailList.get(position);
            setBatchHolder.bind(viewItem, mOnClickItem);

            if (viewItem != null) {
                setBatchHolder.getBatchId().setText(viewItem.getBatchName());
                setBatchHolder.getBatchDate().setText(viewItem.getDueDate());
                setBatchHolder.getTotalQuntity().setText("Total quantity " + viewItem.getTotalQuantity());
                //Toast.makeText(context,viewItem.getBatchId(),Toast.LENGTH_LONG).show();
                sessionManager.createBatchDetails(viewItem.getProjectId(),viewItem.getClientId(),viewItem.getPlantId(),viewItem.getBatchId(),viewItem.getBatchName(),viewItem.getDueDate(),viewItem.getBatchStatus(),""+viewItem.getTotalQuantity());
//                if (batchDetailList.get(position).getPermissions().get(position).contains("scan")){
//                    Constant.scan=true;
//
//                }
                if (viewItem.getBatchStatus().equals("in_progress")) {
                    setBatchHolder.getImg().setBackgroundResource(R.drawable.circular_img);
                } else {
                    setBatchHolder.getImg().setBackgroundResource(R.drawable.circukar_yellow_img);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (batchDetailList != null) {
            ret = batchDetailList.size();
        }
        return ret;
    }

    public void filterList(ArrayList<BatchDetail> filteredData) {
        batchDetailList = filteredData;
        notifyDataSetChanged();
    }

    public interface OnClickItem {

        void onClick(Object object);
    }
}

class SetBatchHolder extends RecyclerView.ViewHolder {

    private TextView batchId;
    private TextView batchDate;
    private TextView totalQuantity;
    private ImageView imgIcon;

    public SetBatchHolder(View itemView) {
        super(itemView);

        batchId = itemView.findViewById(R.id.batchId);
        batchDate = itemView.findViewById(R.id.tvDate);
        totalQuantity = itemView.findViewById(R.id.tvTotal);
        imgIcon = itemView.findViewById(R.id.imgIcon);
    }

    public void bind(final Object item, final SetBatchListAdapter.OnClickItem listener) {

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClick(item);
            }
        });
    }

    public TextView getBatchId() {
        return batchId;
    }

    public TextView getBatchDate() {
        return batchDate;
    }

    public TextView getTotalQuntity() {
        return totalQuantity;
    }

    public ImageView getImg() {
        return imgIcon;
    }
}