package com.levare.verificare.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.levare.verificare.R;
import com.levare.verificare.activity.CurrentUser;
import com.levare.verificare.activity.DashboardActivity;
import com.levare.verificare.activity.DetailsViewActivity;
import com.levare.verificare.activity.ScannerActivity;
import com.levare.verificare.adapter.AssetListAdapter;
import com.levare.verificare.adapter.DialogListViewAdapter;
import com.levare.verificare.model.AssetList;
import com.levare.verificare.model.BatchDetail;
import com.levare.verificare.model.SubmitBatch;
import com.levare.verificare.model.User;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

public class AssetListFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private static final String TAG = "AssetListFragment";

    private AssetListViewModel mViewModel;
    private DetailsViewModel mDetailViewModel;
    private ArrayList<AssetList.AssetsDetail> list = new ArrayList<>();
    private ArrayList<AssetList.AssetsDetail> tempList = new ArrayList<>();
    AssetListAdapter assetListAdapter;
    private View mView;
    private EditText esAssetSearch;

    public static AssetListFragment newInstance() {
        return new AssetListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.lay_asset_list_fragmnet_fragment, container, false);

        esAssetSearch = mView.findViewById(R.id.edtAssetSearch);
        esAssetSearch.addTextChangedListener(this);

        final ImageView back = mView.findViewById(R.id.ivBack);
        back.setOnClickListener(this);

        FrameLayout assetSort = mView.findViewById(R.id.flAssetSort);
        assetSort.setOnClickListener(this);

        TextView submitBatch = mView.findViewById(R.id.tvSubmitBatch);
        submitBatch.setOnClickListener(this);

        DashboardActivity activity=(DashboardActivity) getActivity();
        activity.imgDashBoard.setImageResource(R.drawable.ic_home_screen);
        activity.imgSetBatch.setImageResource(R.drawable.ic_batch_screen);
        activity.imgBatchList.setImageResource(R.drawable.ic_mask_group_225);
        activity.imgLocation.setImageResource(R.drawable.ic_address__2_);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(AssetListViewModel.class);
        mDetailViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        RecyclerView rv = mView.findViewById(R.id.rvListAsset);
        assetListAdapter = new AssetListAdapter(list, new AssetListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Object object) {

                AssetList.AssetsDetail assetsDetail = (AssetList.AssetsDetail) object;
                if (assetsDetail.getIsScan().equals("True")) {
                    new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
                } else {
                    CallActivity(assetsDetail);
                }
            }
        });
        rv.setAdapter(assetListAdapter);

        BatchDetail getCurrentBatch = SetCurrentBatch.getInstance().getCurrentBatch();
        if (getCurrentBatch != null) {
            mViewModel.getAssetDetilas(getCurrentBatch.getBatchId(), getCurrentBatch.getProjectId()).observe(this, new Observer<AssetList>() {

                @Override
                public void onChanged(AssetList assetList) {
                    if (!assetList.getRESPONSESTATUS().equals("0")) {
                        list.addAll(assetList.getAssetsDetails());
                        tempList.addAll(list);
                        assetListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
                dashboardActivity.showToolBar();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.flAssetSort:
                showSortingPopup();
                break;
            case R.id.tvSubmitBatch:
                submitAllBatch();
                break;
        }
    }

    private void submitAllBatch() {

        BatchDetail batchDetail = SetCurrentBatch.getInstance().getCurrentBatch();
        User user = CurrentUser.getInstance().getUser();
        if (batchDetail != null) {
            mViewModel.submitBatchAll(batchDetail.getBatchId(), batchDetail.getProjectId(), user.getProfileDetails().getUserId()).observe(this, new Observer<SubmitBatch>() {

                @Override
                public void onChanged(SubmitBatch submitBatch) {
                    Toast.makeText(getActivity(), "" + submitBatch.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void CallActivity(AssetList.AssetsDetail assetsDetail) {

        BatchDetail getCurrentBatch = SetCurrentBatch.getInstance().getCurrentBatch();
        mViewModel.getAssetDetails(getCurrentBatch.getBatchId(), getCurrentBatch.getProjectId(), "", "0", "0", "", assetsDetail.getAssetId());

        Intent i = new Intent(getActivity(), DetailsViewActivity.class);
        i.putExtra("Code", assetsDetail.getAssetId());
        i.putExtra("isFromAsset", true);
        startActivity(i);
    }


    public Dialog showSortingPopup() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.lay_fillter_popup_view);
        ListView listView = dialog.findViewById(R.id.mListView);

        List<String> list = new ArrayList<>();
        list.add("Completed quantity");
        list.add("Pending quantity");

        list.add("Auto Scan mode");
        list.add("Manual Scan mode");
        list.add("Reset");

        DialogListViewAdapter adapter = new DialogListViewAdapter(getActivity(), list, new DialogListViewAdapter.onClick() {

            @Override
            public void onClickEvent(Object e, int pos) {
                dialog.dismiss();
                sort(pos);
            }
        });
        listView.setAdapter(adapter);
        dialog.show();
        return dialog;
    }

    private void sort(int pos) {

        switch (pos) {
            case 0:
                sortCompleted();
                break;
            case 1:
                sortPending();
                break;

            case 2:
                sortAuto();
                break;
            case 3:
                sortManual();
                break;
            case 4:
                reset();
                break;
        }
    }
    private  void reset(){
        list.clear();
        for (AssetList.AssetsDetail detail : tempList) {

                list.add(detail);

        }

        assetListAdapter.notifyDataSetChanged();
    }

    private void sortCompleted() {
        list.clear();
        for (AssetList.AssetsDetail detail : tempList) {
            if (detail.getAssetStatus().equals("completed")) {
                list.add(detail);
            }
        }
        check();
        assetListAdapter.notifyDataSetChanged();

    }

    private void sortPending() {
        list.clear();
        for (AssetList.AssetsDetail detail : tempList) {
            if (detail.getAssetStatus().equals("pending")) {
                list.add(detail);
            }
        }
        check();
        assetListAdapter.notifyDataSetChanged();
    }

    private void sortManual() {
        list.clear();
        for (AssetList.AssetsDetail detail : tempList) {
            if (detail.getIsScan().equals("False")) {
                list.add(detail);
            }
        }
        check();
        assetListAdapter.notifyDataSetChanged();
    }

    private void sortAuto() {
        list.clear();
        for (AssetList.AssetsDetail detail : tempList) {
            if (detail.getIsScan().equals("True")) {
                list.add(detail);
            }
        }
        check();
        assetListAdapter.notifyDataSetChanged();
    }

    private void sortDate() {

    }

    private void check() {
        if (list.size() == 0) {
            Toast.makeText(getActivity(), "No item found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        Log.d("gaurav", "afterTextChanged: " + s.toString());
        filter(s.toString());
    }

    private void filter(String text) {

        //new array list that will hold the filtered data
        ArrayList<AssetList.AssetsDetail> filterdData = new ArrayList<>();

        //looping through existing elements
        for (AssetList.AssetsDetail assetsDetail : list) {

            //if the existing elements contains the search input
            if (assetsDetail.getAssetNumber().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                Log.d("gaurav", "filter: 1");
                filterdData.add(assetsDetail);
            } else {
                Log.d("gaurav", "filter: 0");
            }
        }
        assetListAdapter.filterList(filterdData);
    }
}
