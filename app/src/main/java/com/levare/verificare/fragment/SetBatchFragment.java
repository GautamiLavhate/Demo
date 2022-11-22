package com.levare.verificare.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.levare.verificare.activity.ScannerActivity;
import com.levare.verificare.adapter.DialogListViewAdapter;
import com.levare.verificare.adapter.SetBatchListAdapter;
import com.levare.verificare.fragmnetlistener.IFragmentListener;
import com.levare.verificare.model.BatchDetail;
import com.levare.verificare.model.BatchDetails;
import com.levare.verificare.model.User;
import com.levare.verificare.network.SessionManager;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

public class SetBatchFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private static final String TAG = "SetBatchFragment";

    private SetBatchViewModel mViewModel;
    private View mView;
    private User mUser;
    private IFragmentListener mIFragmentListener;
    private SetBatchListAdapter setBatchListAdapter;
    private ArrayList<BatchDetail> batchList = new ArrayList<>();
    private ArrayList<BatchDetail> batchTempList = new ArrayList<>();
    private EditText edtBatchSearch;
    private static final int TARGET_FRAGMENT_REQUEST_CODE = 1;
    private static final String EXTRA_GREETING_MESSAGE = "message";
    private ImageView imageViewQR;
    private ImageView imageViewRFID;
    private ImageView imageViewInfo;
    private Boolean isVisible = false;
    SessionManager sessionManager;

    public static SetBatchFragment newInstance() {
        return new SetBatchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.set_batch_fragment, container, false);

        edtBatchSearch = mView.findViewById(R.id.edtBatchSearch);
        imageViewQR = mView.findViewById(R.id.icQR);
        imageViewRFID = mView.findViewById(R.id.icRFID);
        imageViewInfo = mView.findViewById(R.id.icInfo);
        edtBatchSearch.addTextChangedListener(this);
        DashboardActivity activity=(DashboardActivity) getActivity();
        activity.imgDashBoard.setImageResource(R.drawable.ic_home_screen);
        activity.imgSetBatch.setImageResource(R.drawable.ic_select_batch);
        activity.imgBatchList.setImageResource(R.drawable.ic_asset_screen);
        activity.imgLocation.setImageResource(R.drawable.ic_address__2_);
        sessionManager=new SessionManager(getContext());
        final ImageView back = mView.findViewById(R.id.ivBack);
        back.setOnClickListener(this);

        FrameLayout batchSort = mView.findViewById(R.id.flBatchSort);
        batchSort.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == TARGET_FRAGMENT_REQUEST_CODE) {
            String rfid = data.getStringExtra(EXTRA_GREETING_MESSAGE);
            edtBatchSearch.setText(rfid);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(SetBatchViewModel.class);
        mUser = CurrentUser.getInstance().getUser();

        imageViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    imageViewQR.setVisibility(View.GONE);
                    imageViewRFID.setVisibility(View.GONE);
                } else {
                    imageViewQR.setVisibility(View.VISIBLE);
                    imageViewRFID.setVisibility(View.VISIBLE);
                }
                isVisible = !isVisible;
            }
        });

        imageViewQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetCurrentBatch.setIsDetailsOnly(true);
                SetCurrentBatch.setType("qr");
                new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });

        imageViewRFID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetCurrentBatch.setType("rfid");
                SetCurrentBatch.setIsDetailsOnly(true);
                RFIDDialogFragment dialogFragment = RFIDDialogFragment.getInstance();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "Test");
            }
        });


        final RecyclerView batchRecyclerView = mView.findViewById(R.id.rvListBatch);
        setBatchListAdapter = new SetBatchListAdapter(batchList, new SetBatchListAdapter.OnClickItem() {

            @Override
            public void onClick(Object item) {
                DashboardActivity activity = (DashboardActivity) getActivity();
                mIFragmentListener = (IFragmentListener) getActivity();
                activity.showToolBar();
                mIFragmentListener.sendBatchDetails((BatchDetail) item);
                SetCurrentBatch.getInstance().setCurrentBatchDetails((BatchDetail) item);
                for (int i=0;i<batchList.size();i++){

                }

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        batchRecyclerView.setAdapter(setBatchListAdapter);

        if (mUser != null && mUser.getProfileDetails() != null) {
            mViewModel.getBatchDetails(mUser.getProfileDetails().getUserId()).observe(getViewLifecycleOwner(), new Observer<BatchDetails>() {

                @Override
                public void onChanged(BatchDetails batchDetails) {

                    if (batchDetails.getRESPONSESTATUS().equals("0")) {
                        showPopup(batchDetails.getRESPONSEMSG(), false);
                        return;
                    }
                    List<BatchDetail> batchDetail = batchDetails.getBatchDetails();

                    if (batchDetail != null && batchDetail.size() > 0) {
                        batchList.addAll(batchDetails.getBatchDetails());
                        batchTempList.addAll(batchList);
                        setBatchListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.ivBack) {
            getActivity().getSupportFragmentManager().popBackStack();
            DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
            dashboardActivity.showToolBar();
        } else if (view.getId() == R.id.flBatchSort) {
            showSortingPopup();
        }
    }

    public Dialog showPopup(String text, boolean isShowTwoButton) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.lay_exit_dialog);

        TextView test = dialog.findViewById(R.id.textmsg);
        if (!isShowTwoButton) {
            test.setText(text);
        }

        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        if (isShowTwoButton) {
            btnNo.setVisibility(View.VISIBLE);
            btnNo.setText("No");
            btnYes.setVisibility(View.VISIBLE);
            btnYes.setText("Yes");
        } else {
            btnNo.setVisibility(View.GONE);
            btnYes.setVisibility(View.VISIBLE);
            btnYes.setText("Ok");
        }

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
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
        list.add("Completed and pending");
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
                sortCompletedPending();
                break;
            case 3:
                reset();
                break;
        }
    }

    private void reset(){
        batchList.clear();
        for (BatchDetail detail : batchTempList) {

            batchList.add(detail);

        }

        setBatchListAdapter.notifyDataSetChanged();
    }

    private void sortCompleted() {
        batchList.clear();
        for (BatchDetail detail : batchTempList) {
            if (detail.getBatchId().equals("completed")) {
                batchList.add(detail);
            }
        }
        check();
        setBatchListAdapter.notifyDataSetChanged();

    }

    private void sortPending() {
        batchList.clear();
        for (BatchDetail detail : batchTempList) {
            if (detail.getBatchStatus().equals("in_progress")) {
                batchList.add(detail);
            }
        }
        check();
        setBatchListAdapter.notifyDataSetChanged();
    }

    private void sortCompletedPending() {
        batchList.clear();
        for (BatchDetail detail : batchTempList) {
//            if (detail.getIsScan().equals("False")) {
//                batchList.add(detail);
//            }
        }
        check();
        setBatchListAdapter.notifyDataSetChanged();
    }

    private void check() {
        if (batchList.size() == 0) {
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
        filter(s.toString());
    }

    private void filter(String text) {

        ArrayList<BatchDetail> filterdData = new ArrayList<>();
        for (BatchDetail batchDetail : batchList) {
            if (batchDetail.getBatchName().toLowerCase().contains(text.toLowerCase())) {
                filterdData.add(batchDetail);
            }
        }
        setBatchListAdapter.filterList(filterdData);
    }

}
