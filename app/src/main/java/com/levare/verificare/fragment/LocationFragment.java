package com.levare.verificare.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.levare.verificare.R;
import com.levare.verificare.activity.DashboardActivity;
import com.levare.verificare.activity.ScannerActivity;
import com.levare.verificare.adapter.AdapterLocation;
import com.levare.verificare.adapter.AdapterSubLocation;
import com.levare.verificare.model.BatchDetails;
import com.levare.verificare.model.Location;
import com.levare.verificare.model.SubLocation;
import com.levare.verificare.network.Constant;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.levare.verificare.network.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {

    TextView txtLocation,txtSubLocation;
    View viewLocation,viewSubLocation;
    ListView listViewLocation,listViewSubLocation;
    AdapterLocation adapterLocation;
    AdapterSubLocation adapterSubLocation;
    private ImageView imageViewQR;
    private ImageView imageViewRFID;
    private ImageView imageViewInfo;
    ImageView ivBack;
    private Boolean isVisible = false;
    public FloatingActionButton fabAddLocation;
    ArrayList<Location.LocationDetails> locationDetails;
    ArrayList<SubLocation.SubLocationDetails> subLocationDetails;
    SessionManager sessionManager;
    TextView txtNodata;
    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_location, container, false);
        txtLocation=view.findViewById(R.id.txtLocation);
        txtSubLocation=view.findViewById(R.id.txtSubLocation);
        viewLocation=view.findViewById(R.id.viewLocation);
        viewSubLocation=view.findViewById(R.id.viewSubLocation);
        listViewLocation=view.findViewById(R.id.listViewLocation);
        listViewSubLocation=view.findViewById(R.id.listViewSubLocation);
        imageViewQR = view.findViewById(R.id.icQR);
        imageViewRFID = view.findViewById(R.id.icRFID);
        imageViewInfo = view.findViewById(R.id.icInfo);
        fabAddLocation = view.findViewById(R.id.fabAddLocation);
        ivBack = view.findViewById(R.id.ivBack);
        txtNodata = view.findViewById(R.id.txtNodata);
        sessionManager=new SessionManager(getContext());
        DashboardActivity activity=(DashboardActivity) getActivity();
        activity.imgDashBoard.setImageResource(R.drawable.ic_home_screen);
        activity.imgSetBatch.setImageResource(R.drawable.ic_batch_screen);
        activity.imgBatchList.setImageResource(R.drawable.ic_asset_screen);
        activity.imgLocation.setImageResource(R.drawable.ic_address__3_);
        Constant.isLocation=true;
        Constant.isSubLocation=false;
//        listViewLocation.setVisibility(View.VISIBLE);
//        listViewSubLocation.setVisibility(View.GONE);
//
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<BatchDetails> batchDetailCall=serviceApi.getBatchdetails(sessionManager.getKEY_user_id());

        batchDetailCall.enqueue(new Callback<BatchDetails>() {
            @Override
            public void onResponse(Call<BatchDetails> call, Response<BatchDetails> response) {
                BatchDetails batchDetails=response.body();
                if (batchDetails !=null && batchDetails.getRESPONSESTATUS().equals("1")){
                    for (int i=0;i<batchDetails.getBatchDetails().size();i++){
                        for (int j=0;j<batchDetails.getBatchDetails().get(i).getPermissions().size();j++){

                            if (batchDetails.getBatchDetails().get(i).getPermissions().contains("add")){
                                //Toast.makeText(DashboardActivity.this,batchDetails.getBatchDetails().get(i).getPermissions().toString(),Toast.LENGTH_LONG).show();
                                fabAddLocation.show();
                            }else {
                                fabAddLocation.clearAnimation();
                                fabAddLocation.hide();

                            }
//
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<BatchDetails> call, Throwable t) {

            }
        });
        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.isLocation=true;
                Constant.isSubLocation=false;
                txtLocation.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtSubLocation.setTextColor(getResources().getColor(R.color.gray));
                viewLocation.setVisibility(View.VISIBLE);
                viewSubLocation.setVisibility(View.INVISIBLE);
                listViewLocation.setVisibility(View.VISIBLE);
                listViewSubLocation.setVisibility(View.GONE);
            }
        });
        txtSubLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.isSubLocation=true;
                Constant.isLocation=false;
                txtSubLocation.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtLocation.setTextColor(getResources().getColor(R.color.gray));
                viewSubLocation.setVisibility(View.VISIBLE);
                viewLocation.setVisibility(View.INVISIBLE);
                listViewSubLocation.setVisibility(View.VISIBLE);
                listViewLocation.setVisibility(View.GONE);
                getSubLocation(sessionManager.getKEY_new_client_id());
            }
        });
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
        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.isLocation){
                    Fragment fragment = new AddLocationFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.lay_dashbordView, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if (Constant.isSubLocation){
                    Fragment fragment = new AddSubLocationFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.lay_dashbordView, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
                dashboardActivity.showToolBar();
            }
        });

        //Toast.makeText(getContext(),sessionManager.getKEY_new_client_id(),Toast.LENGTH_LONG).show();
        getLocation(sessionManager.getKEY_new_client_id());
        //getSubLocation(sessionManager.getKEY_new_client_id());
        return view;
    }

    void getLocation(String client_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<Location> locationCall=serviceApi.getLocations(client_id);
        locationCall.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location=response.body();
                if (location !=null && location.getrESPONSESTATUS().equals("1")){
                    txtNodata.setVisibility(View.GONE);
                    locationDetails=location.getLocationDetails();
                    if (getActivity() != null){
                        adapterLocation=new AdapterLocation(getActivity(),locationDetails);
                        listViewLocation.setAdapter(adapterLocation);
                    }

                }else {
                    listViewLocation.setVisibility(View.GONE);
                    txtNodata.setVisibility(View.VISIBLE);
                    txtNodata.setText(location.getrESPONSEMSG());
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {

            }
        });
    }

    void getSubLocation(String client_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<SubLocation> subLocationCall=serviceApi.getSubLocation(client_id);
        subLocationCall.enqueue(new Callback<SubLocation>() {
            @Override
            public void onResponse(Call<SubLocation> call, Response<SubLocation> response) {
                SubLocation subLocation=response.body();
                if (subLocation !=null && subLocation.getrESPONSESTATUS().equals("1")){
                    txtNodata.setVisibility(View.GONE);
                    listViewSubLocation.setVisibility(View.VISIBLE);
                    subLocationDetails=subLocation.getSubLocationDetails();
                    adapterSubLocation=new AdapterSubLocation(getContext(),subLocationDetails);
                    listViewSubLocation.setAdapter(adapterSubLocation);

                }else {
                    listViewSubLocation.setVisibility(View.GONE);
                    txtNodata.setVisibility(View.VISIBLE);
                    txtNodata.setText(subLocation.getrESPONSEMSG());
                }


            }

            @Override
            public void onFailure(Call<SubLocation> call, Throwable t) {

            }
        });

    }
}