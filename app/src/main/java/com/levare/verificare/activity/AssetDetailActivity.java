package com.levare.verificare.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.levare.verificare.R;
import com.levare.verificare.fragment.DetailsViewModel;
import com.levare.verificare.fragment.SetCurrentBatch;
import com.levare.verificare.model.Details;

public class AssetDetailActivity extends AppCompatActivity {
    private TextView textViewAssetNumber;
    private TextView textViewAssetClassDescription;
    private TextView textViewAssetDescription;
    private TextView textViewDateCapitalization;
    private TextView textViewBookQuantity;
    private TextView textViewGrossBlock;
    private TextView textViewCostCentre;
    private TextView textViewLocation;
    private TextView textViewSubLocation;
    private TextView textViewDepartment;
    private TextView textViewResponsiblePerson;
    private TextView textViewLastScanStatus;
    private String code;
    private DetailsViewModel mViewModel;

    private LinearLayout llDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);


        code = getIntent().getStringExtra("Code");
        initialization();
        mViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        getDetails(code);

    }

    private void getDetails(String code) {
        if (SetCurrentBatch.getType().equalsIgnoreCase("rfid")) {
            mViewModel.getDetails("0", code).observe(this, new Observer<Details>() {
                @Override
                public void onChanged(Details details) {
                    if (details.getRESPONSE_STATUS().equals("0")) {
                        llDetails.setVisibility(View.GONE);
                        Toast.makeText(AssetDetailActivity.this, "" + details.getRESPONSE_MSG(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Details.AssetDetailsBean bean = details.getAsset_Details();

                        llDetails.setVisibility(View.VISIBLE);
                        textViewAssetNumber.setText(bean.getAsset_number());
                        textViewAssetClassDescription.setText(bean.getAsset_class());
                        textViewAssetDescription.setText(bean.getAsset_description());
                        textViewDateCapitalization.setText(bean.getCap_date());
                        textViewBookQuantity.setText(bean.getBook_qty());
                        textViewGrossBlock.setText(bean.getGross_block());
                        textViewCostCentre.setText(bean.getCost_center());
                        textViewLocation.setText(bean.getLocation());
                        textViewSubLocation.setText(bean.getSub_location());
                        textViewDepartment.setText(bean.getResponsible_department());
                        textViewResponsiblePerson.setText(bean.getResponsible_person());
                    }
                }
            });
        } else if (SetCurrentBatch.getType().equalsIgnoreCase("qr")) {
            mViewModel.getDetails(code, "0").observe(this, new Observer<Details>() {
                @Override
                public void onChanged(Details details) {
                    if (details.getRESPONSE_STATUS().equals("0")) {
                        llDetails.setVisibility(View.GONE);
                        Toast.makeText(AssetDetailActivity.this, "" + details.getRESPONSE_MSG(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        llDetails.setVisibility(View.VISIBLE);

                        Details.AssetDetailsBean bean = details.getAsset_Details();
                        textViewAssetNumber.setText(bean.getAsset_number());
                        textViewAssetClassDescription.setText(bean.getAsset_class());
                        textViewAssetDescription.setText(bean.getAsset_description());
                        textViewDateCapitalization.setText(bean.getCap_date());
                        textViewBookQuantity.setText(bean.getBook_qty());
                        textViewGrossBlock.setText(bean.getGross_block());
                        textViewCostCentre.setText(bean.getCost_center());
                        textViewLocation.setText(bean.getLocation());
                        textViewSubLocation.setText(bean.getSub_location());
                        textViewDepartment.setText(bean.getResponsible_department());
                        textViewResponsiblePerson.setText(bean.getResponsible_person());
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialization() {
        llDetails = findViewById(R.id.llDetails);
        textViewAssetNumber = findViewById(R.id.tvAssetNumber);
        textViewAssetClassDescription = findViewById(R.id.tvAssetClassDescription);
        textViewAssetDescription = findViewById(R.id.tvassetDescription);
        textViewDateCapitalization = findViewById(R.id.tvDateCapitalization);
        textViewBookQuantity = findViewById(R.id.tvBookQt);
        textViewGrossBlock = findViewById(R.id.tvGvalue);
        textViewCostCentre = findViewById(R.id.tvCostCenter);
        textViewLocation = findViewById(R.id.tvLocation);
        textViewSubLocation = findViewById(R.id.tvSubLocation);
        textViewDepartment = findViewById(R.id.tvDepartment);
        textViewResponsiblePerson = findViewById(R.id.tvResponsiblePerson);
        textViewLastScanStatus = findViewById(R.id.tvLastScanStatus);
    }
}
