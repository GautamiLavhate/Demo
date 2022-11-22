package com.levare.verificare.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.levare.verificare.R;
import com.levare.verificare.activity.DashboardActivity;
import com.levare.verificare.adapter.RecyclerHorizantalViewDataAdapter;
import com.levare.verificare.fragmnetlistener.IActivityListener;
import com.levare.verificare.model.BatchDetail;
import com.levare.verificare.model.Dashboard;


import java.util.ArrayList;

public class DashBoardFragment extends Fragment  implements IActivityListener {

    private DashBoardViewModel mViewModel;
    private TextView mTotalCount,mtvCompleted,mtvPendingCount,mtvAutoModeCount,mtvManualModeCount,mtvInUseCount,mtvNotinUseCount,mCompletedPestange,mtvCenterNotinUse;
    private View mView;
    ProgressBar circularProgressbar;
    ProgressBar circularnotinuseProgressbar;
    DashboardActivity dashboardActivity;

    private TextView batchId;
    private ArrayList<Dashboard.DashboardDetails.Date> list=new ArrayList<>();

    RecyclerHorizantalViewDataAdapter mRecyclerHorizantalViewDataAdapter;

    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.dash_board_fragment, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        mTotalCount=mView.findViewById(R.id.tvTotalCountNumber);
        mtvCompleted=mView.findViewById(R.id.tvCompleted);
        mtvPendingCount=mView.findViewById(R.id.tvPendingCount);
        mtvAutoModeCount=mView.findViewById(R.id.tvAutoModeCount);
        mtvCompleted=mView.findViewById(R.id.tvCompleted);
        mtvInUseCount=mView.findViewById(R.id.tvInUseCount);
        mtvManualModeCount=mView.findViewById(R.id.tvManualModeCount);
        mtvNotinUseCount=mView.findViewById(R.id.tvNotinUseCount);
        batchId=mView.findViewById(R.id.btBatchId);
        DashboardActivity activity=(DashboardActivity) getActivity();
        activity.registerActivityListener(this);
        activity.imgDashBoard.setImageResource(R.drawable.ic_mask_group_215);
        activity.imgSetBatch.setImageResource(R.drawable.ic_batch_screen);
        activity.imgBatchList.setImageResource(R.drawable.ic_asset_screen);
        activity.imgLocation.setImageResource(R.drawable.ic_address__2_);
        circularnotinuseProgressbar=mView.findViewById(R.id.circularnotinuseProgressbar);
        circularProgressbar=mView.findViewById(R.id.circularProgressbar);
        mCompletedPestange=mView.findViewById(R.id.tv);
        mtvCenterNotinUse=mView.findViewById(R.id.tvCenterNotinUse);


        RecyclerView mRecyclerView=mView.findViewById(R.id.rcDateTime);
        mRecyclerHorizantalViewDataAdapter=new RecyclerHorizantalViewDataAdapter(list);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(horizontalLayoutManager);

        mRecyclerView.setAdapter(mRecyclerHorizantalViewDataAdapter);

        if (SetCurrentBatch.getInstance().getCurrentBatch()!=null){
            getBatchDetails(SetCurrentBatch.getInstance().getCurrentBatch());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (SetCurrentBatch.getInstance().getCurrentBatch()!=null){
            getBatchDetails(SetCurrentBatch.getInstance().getCurrentBatch());
        }

    }

    boolean isShowToast;
    @Override
    public void getBatchDetails(BatchDetail batchDetail) {
        batchId.setText("Selected Batch :"+batchDetail.getBatchName());
        mTotalCount.setText(""+batchDetail.getTotalQuantity());
        isShowToast=true;

        mViewModel.DashBoard(batchDetail.getBatchId(),batchDetail.getProjectId()).observe(this, new Observer<Dashboard>() {
            @Override
            public void onChanged(Dashboard dashboardDetails) {
                Log.d("Tag", "onChanged: ."+dashboardDetails);
                Dashboard.DashboardDetails data = dashboardDetails.getDashboardDetails();
                if (data!=null) {
                    list.clear();
                    list.addAll(data.getDates());
                    mRecyclerHorizantalViewDataAdapter.notifyDataSetChanged();
                    mtvCompleted.setText("" + data.getCompleted());
                    mtvAutoModeCount.setText("" + data.getAutoMode());
                    mtvPendingCount.setText("" + data.getPending());
                    mtvInUseCount.setText("" + data.getInUse());
                    mtvManualModeCount.setText("" + data.getManualMode());
                    mtvNotinUseCount.setText("" + data.getNotInUse());
                    double d =data.getInUsePercentage();
                    int InUsePercentage = (int) d;

                    double d2 =data.getCompletedPercentage();
                    int CompletedPercentage = (int) d2;
                    circularnotinuseProgressbar.setProgress(InUsePercentage);
                    circularProgressbar.setProgress(CompletedPercentage);
                    mCompletedPestange.setText(""+data.getCompletedPercentage()+"%");
                    mtvCenterNotinUse.setText(""+data.getInUsePercentage()+"%");
                }else {
                    if (isShowToast==true){
                    Toast.makeText(getActivity(), ""+dashboardDetails.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                    isShowToast=false;
                    }
                }

            }
        });
    }
}
