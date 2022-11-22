package com.levare.verificare.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.levare.verificare.R;

import com.levare.verificare.model.AboutUs;
import com.levare.verificare.model.AboutusDetails;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.levare.verificare.util.Utility;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {

    private static final String TAG = "AboutUsActivity";

    ServiceApi serviceApi;
    ImageView ivAboutUs;
    TextView company,tvAboutUsContent;
    TextView telephone,tvMobile;
    TextView email;
    TextView website;
    WebView desc_webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ivAboutUs=findViewById(R.id.ivAboutUs);
        tvAboutUsContent = findViewById(R.id.tvAboutUsContent);
        company = findViewById(R.id.tvCompany);
        telephone = findViewById(R.id.tvTelephone);
        email = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvMobile);
        website = findViewById(R.id.tvWebsite);
        desc_webview = findViewById(R.id.desc_webview);
        desc_webview.setVerticalScrollBarEnabled(false);
        desc_webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        desc_webview.setLongClickable(false);
        desc_webview.setHapticFeedbackEnabled(false);

        getSupportActionBar().setTitle(getResources().getString(R.string.about_us));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void getData() {

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<AboutUs> aboutUs = serviceApi.getAboutUs();
        aboutUs.enqueue(new Callback<AboutUs>() {

            @Override
            public void onResponse(Call<AboutUs> call, Response<AboutUs> response) {

                AboutUs aboutUs = response.body();
                if (aboutUs != null) {
                    AboutusDetails aboutUsDetails = aboutUs.getAboutusDetails();
                    if (aboutUsDetails != null) {

                        Log.d(TAG, "onResponse: " + aboutUsDetails.getCompanyName());
                        Glide.with(getApplicationContext()).load(aboutUsDetails.getLogo()).into(ivAboutUs);
                        String htmlData = Utility.getJustifyString(aboutUsDetails.getAbout());
                        desc_webview.loadData(htmlData, "text/html; charset=utf-8", "utf-8");
                        tvAboutUsContent.setText(aboutUsDetails.getAbout());
                        company.setText(aboutUsDetails.getCompanyName());
                        telephone.setText(aboutUsDetails.getCompanyPhone());
                        email.setText(aboutUsDetails.getCompanyEmail());
                        website.setText(aboutUsDetails.getCompanyDomain());
                        tvMobile.setText(aboutUsDetails.getCompanyMobile());
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUs> call, Throwable t) {

                Toast.makeText(AboutUsActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                aboutUs.cancel();
            }
        });
    }
}
