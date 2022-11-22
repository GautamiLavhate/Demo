package com.levare.verificare.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.levare.verificare.R;
import com.levare.verificare.model.Dashboard;

import java.util.List;

public class RecyclerHorizantalViewDataAdapter extends RecyclerView.Adapter<CustomRecyclerViewHolder> {

    private List<Dashboard.DashboardDetails.Date> viewItemList;

    public RecyclerHorizantalViewDataAdapter(List<Dashboard.DashboardDetails.Date> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public CustomRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.lay_horizantal_list_iteam, parent, false);

        // Create and return our customRecycler View Holder object.
        CustomRecyclerViewHolder ret = new CustomRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            Dashboard.DashboardDetails.Date viewItem = viewItemList.get(position);

            if(viewItem != null) {
                // Set car item title.
                Log.d("TAG", "onBindViewHolder: "+viewItem.getDate());
                String [] dates=viewItem.getDate().split("-");
                holder.getDate().setText(""+dates[2]);
                holder.getMonth().setText(""+returnMonth(dates[1]));
                holder.getTotalCount().setText(""+viewItem.getCount());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(viewItemList!=null)
        {
            ret = viewItemList.size();
        }
        return ret;
    }
    private String returnMonth(String no){

        String month="";
        switch (no){

            case "01":
                month="Jan";
                break;
            case "02":
                month="Feb";
                break;
            case "03":
                month="Mar";
                break;
            case "04":
                month="Apr";
                break;
            case "05":
                month="May";
                break;
            case "06":
                month="June";
                break;
            case "07":
                month="July";
                break;
            case "08":
                month="Aug";
                break;
            case "09":
                month="Sept";
                break;
            case "10":
                month="Oct";
                break;
            case "11":
                month="Nov";
                break;
            case "12":
                month="Dec";
                break;
        }
        return month;
    }
}


class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView month = null;
    private TextView mTotalCount = null;
    private TextView mDate = null;

    public CustomRecyclerViewHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            month = itemView.findViewById(R.id.month);
            mTotalCount = itemView.findViewById(R.id.tvTotalCount);
            mDate = itemView.findViewById(R.id.tvDate);
        }
    }

    public TextView getMonth() {
        return month;
    }
    public TextView getDate() {
        return mDate;
    }
    public TextView getTotalCount() {
        return mTotalCount;
    }
}