package com.levare.verificare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.levare.verificare.R;
import com.levare.verificare.model.AssetList;

import java.util.ArrayList;
import java.util.List;

public class AssetListAdapter extends RecyclerView.Adapter<AssetListHolder> {

    private List<AssetList.AssetsDetail> assetsDetailList;
    private OnItemClickListener mOnItemClickListener;

    public AssetListAdapter(List<AssetList.AssetsDetail> assetsDetailList, OnItemClickListener mOnItemClickListener) {
        this.assetsDetailList = assetsDetailList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public AssetListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_asset_list, parent, false);
        return new AssetListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssetListHolder assetListHolder, int position) {
        if (assetsDetailList != null) {

            AssetList.AssetsDetail viewItem = assetsDetailList.get(position);
            assetListHolder.bind(viewItem, mOnItemClickListener);

            if (viewItem != null) {
                viewItem.getAssetNumber();
                // Set car item title.
                assetListHolder.getTextView().setText(viewItem.getAssetNumber());
                if (viewItem.getIsScan().equals("True")) {
                    assetListHolder.getImagScan().setBackgroundResource(R.drawable.ic_scan_entry);
                } else {
                    assetListHolder.getImagScan().setBackgroundResource(R.drawable.ic_manual_entry);
                }
                if (viewItem.getAssetStatus().equals("pending")) {
                    assetListHolder.getStatusImage().setBackgroundResource(R.drawable.circukar_yellow_img);
                } else {
                    assetListHolder.getStatusImage().setBackgroundResource(R.drawable.circular_img);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (assetsDetailList != null) {
            ret = assetsDetailList.size();
        }
        return ret;
    }

    public void filterList(ArrayList<AssetList.AssetsDetail> filteredData) {
        assetsDetailList = filteredData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Object object);
    }
}

class AssetListHolder extends RecyclerView.ViewHolder {

    private TextView assetNumber;
    private ImageView mScanMode, imgStatus;

    public AssetListHolder(View itemView) {
        super(itemView);

        assetNumber = itemView.findViewById(R.id.assetId);
        mScanMode = itemView.findViewById(R.id.ivTypeOfScanner);
        imgStatus = itemView.findViewById(R.id.imgStatus);
    }

    public void bind(final Object item, final AssetListAdapter.OnItemClickListener listener) {

        mScanMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

    public ImageView getStatusImage() {
        return imgStatus;
    }

    public ImageView getImagScan() {
        return mScanMode;
    }

    public TextView getTextView() {
        return assetNumber;
    }
}