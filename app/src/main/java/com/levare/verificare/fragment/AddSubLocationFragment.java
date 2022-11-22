package com.levare.verificare.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.levare.verificare.R;
import com.levare.verificare.activity.DashboardActivity;
import com.levare.verificare.model.AddSubLocation;
import com.levare.verificare.model.BatchDetails;
import com.levare.verificare.model.Location;
import com.levare.verificare.model.Plant;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.levare.verificare.network.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddSubLocationFragment extends Fragment {

    Button btnSave;
    ArrayList <Plant.PlantDetails> plantDetails;
    ArrayList <Location.LocationDetails> locationDetails;
    Spinner spinnerSelectPlant,spinnerSelectLocation;
    String strPlantId,strLocationName,strLocationId;
    EditText edttxtSubLocationName;
    ImageView ivBack;

    SessionManager sessionManager;
    Boolean add=false;
    public AddSubLocationFragment() {
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
        View view=inflater.inflate(R.layout.fragment_add_sub_location, container, false);
        btnSave=view.findViewById(R.id.btnSave);
        spinnerSelectPlant=view.findViewById(R.id.spinnerSelectPlant);
        spinnerSelectLocation=view.findViewById(R.id.spinnerSelectLocation);
        edttxtSubLocationName=view.findViewById(R.id.edttxtSubLocationName);
        sessionManager=new SessionManager(getContext());
        ivBack=view.findViewById(R.id.ivBack);
        getPlant(sessionManager.getKEY_new_client_id());
        getPermission();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edttxtSubLocationName.getText().toString().equals("")||
                        strPlantId.equalsIgnoreCase("0")||
                strLocationId.equalsIgnoreCase("0")){
                    Toast.makeText(getContext(),"Enter valid data!",Toast.LENGTH_LONG).show();
                }else {
                    if (add){
                        addSubLocation(sessionManager.getKEY_new_client_id(), strPlantId, strLocationId, edttxtSubLocationName.getText().toString());

                    }else {
                        Toast.makeText(getContext(),"You dont have permission to add Sub-Location!",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                DashboardActivity dashboardActivity = (DashboardActivity) getActivity();
                //dashboardActivity.showToolBar();
            }
        });

        return view;
    }


    void getPlant(String client_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<Plant> plantCall=serviceApi.getPlant(client_id);

        plantCall.enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                Plant plant=response.body();
                if (plant != null && plant.getrESPONSESTATUS().equals("1")){
                    btnSave.setEnabled(true);
                    plantDetails=plant.getPlantDetails();
                    ArrayList<String> name=new ArrayList<>();
                    name.add("Select Plant");
                    for (Plant.PlantDetails data1 : plantDetails) {
                        name.add(data1.getDescription());
                    }
                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_text, name) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            ((TextView) v).setTextColor(Color.parseColor("#000000"));
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            ((TextView) v).setTextColor(Color.parseColor("#000000"));
                            return v;
                        }
                    };
                    adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSelectPlant.setAdapter(adapterState);
                    spinnerSelectPlant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position==0){
                                strPlantId = plantDetails.get(position).getId();
                                strPlantId="0";
                                //getLocation("1",strPlantId);
                            }else {
                                strPlantId = plantDetails.get(position-1).getId();
                                getLocation(sessionManager.getKEY_new_client_id(),strPlantId);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else {
                    btnSave.setEnabled(false);
                    Toast.makeText(getContext(),plant.getrESPONSEMSG(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {

            }
        });
    }


    void getLocation(String client_id,String plant_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<Location> locationCall=serviceApi.getLocationAsPerPlant(client_id,plant_id);
        locationCall.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location=response.body();
                if (location !=null && location.getrESPONSESTATUS().equals("1")){
                    locationDetails=location.getLocationDetails();
                    ArrayList<String> name=new ArrayList<>();
                    name.add("Select Location");
                    for (Location.LocationDetails data1 : locationDetails) {
                        name.add(data1.getName());
                    }
                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getContext(),
                            R.layout.spinner_text, name) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            ((TextView) v).setTextColor(Color.parseColor("#000000"));
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            ((TextView) v).setTextColor(Color.parseColor("#A9A9A9"));
                            return v;
                        }
                    };
                    adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSelectLocation.setAdapter(adapterState);

                    spinnerSelectLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position==0){
                                strLocationId = locationDetails.get(position).getId();
                                strLocationId="0";
                                //getLocation("1",strPlantId);
                            }else {
                                strLocationId = locationDetails.get(position-1).getId();
                                //Toast.makeText(getContext(),strLocationName,Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {

            }
        });
    }

    void addSubLocation(String client_id,String plant_id,String location_id,String sub_location_name){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<AddSubLocation> subLocationCall=serviceApi.getAddSubLocationStatus(client_id,plant_id,location_id,sub_location_name);
        subLocationCall.enqueue(new Callback<AddSubLocation>() {
            @Override
            public void onResponse(Call<AddSubLocation> call, Response<AddSubLocation> response) {
                AddSubLocation addSubLocation=response.body();
                if (addSubLocation != null && addSubLocation.getrESPONSESTATUS().equals("1")){
                    Toast.makeText(getContext(),addSubLocation.getrESPONSEMSG(),Toast.LENGTH_LONG).show();
                    Fragment fragment = new LocationFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.lay_dashbordView, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onFailure(Call<AddSubLocation> call, Throwable t) {

            }
        });

    }
    void getPermission(){
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
                                add=true;
                            }else {
                                add=false;
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
    }
}