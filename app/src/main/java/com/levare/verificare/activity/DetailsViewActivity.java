package com.levare.verificare.activity;

import static android.graphics.BitmapFactory.decodeStream;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.journeyapps.barcodescanner.CameraPreview;
import com.levare.verificare.R;
import com.levare.verificare.fragment.DetailsViewModel;
import com.levare.verificare.fragment.SetCurrentBatch;
import com.levare.verificare.model.BatchDetail;
import com.levare.verificare.model.BatchDetails;
import com.levare.verificare.model.DetailScan;
import com.levare.verificare.model.Location;
import com.levare.verificare.model.Plant;
import com.levare.verificare.model.Reson;
import com.levare.verificare.model.SendRespose;
import com.levare.verificare.model.SubLocation;
import com.levare.verificare.model.User;
import com.levare.verificare.network.Constant;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.levare.verificare.network.SessionManager;
import com.levare.verificare.util.VolleyMultipartRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DetailsViewActivity extends AppCompatActivity {

    private DetailsViewModel mViewModel;
    private int EVENT = 0;
    private String code;
    private Dialog dialog;
    private DetailScan.AssetDetails assetDetails;
    private BatchDetail batchDetail;
    private User mUser;
    private Spinner reason;
    private DetailsViewActivity mDetailsViewActivity;
    private Spinner SplReson;
    private ArrayList<Reson.ReasonDetail> detailsReson;
    private boolean isFromAsset, isManulScan;
    private EditText inUse, notInUse, notFound;
    private Spinner condition;
    private EditText remark;
    private String RegionScan = "";
    TextView assetNo;
    TextView tvAssetClassDescription;
    TextView tvassetDescription;
    TextView tvDateCapitalization;
    TextView tvBookQt;
    TextView tvGvalue;
    TextView tvCostCenter;
    LinearLayout assetView;
    private TextView lastScanStatus;
    TextView seleCtcodntitle;
    TextView tvResponsibledepartment;
    TextView tvLocation;
    TextView tvSubLocation;
    private LinearLayout llDetails;
    private String type;
    private ProgressDialog progressDialog;
    Spinner spinnerSelectPlan;
    Spinner spinnerSelectLocation;
    Spinner spinnerSelectSubLocation;
    RelativeLayout relativeLayoutPlant,relativeLayoutLocation,relativeLayoutSubLocation;
    ImageView imgPlantdropdown,imgSyncLocation,imgdropdownSelectLocation,imgSyncSubLocation,imgdropdownSubSelectLocation;
    ArrayList <Plant.PlantDetails> plantDetails;
    ArrayList <Location.LocationDetails> locationDetails;
    ArrayList <SubLocation.SubLocationDetails> subLocationDetails;
    String strPlantId,strLocationName,strLocationId,strSubLocation;
    Button b;
    SessionManager sessionManager;
    int selectedLocation,selectedSubLocation,selectedPlantId;
    LinearLayout llAddImgAsset1,llAddImgAsset2,llAddImgAsset3,llAddImgAsset4,llAddImgAsset5;
    LinearLayout llAvailableImgAsset1,llAvailableImgAsset2,llAvailableImgAsset3,llAvailableImgAsset4,llAvailableImgAsset5;

    ImageView ivEditImgasset1,ivEditImgasset2,ivEditImgasset3,ivEditImgasset4,ivEditImgasset5;

    String assetId;
    Bitmap bitmapFinal,bitmapFinal2,bitmapFinal3,bitmapFinal4,bitmapFinal5;
    ImageView imgasset1,imgasset2,imgasset3,imgasset4,imgasset5;
    ImageView imgAddAsset1,imgAddAsset2,imgAddAsset3,imgAddAsset4,imgAddAsset5;
    ArrayList<MultipartBody.Part> arrayListImageId;
    String strImageId1,strImageId2,strImageId3,strImageId4,strImageId5;

    String Path1="",Path2="",Path3="",Path4="",Path5="";
    private String PATH,PATH2,PATH3,PATH4,PATH5;


    // Define the pic id

    private static final int take_a_photo1 = 61;
    private static final int take_a_photo2 = 62;
    private static final int take_a_photo3 = 63;
    private static final int take_a_photo4 = 64;
    private static final int take_a_photo5 = 65;

    private static final int edit_take_a_photo1 = 66;
    private static final int edit_take_a_photo2 = 67;
    private static final int edit_take_a_photo3 = 68;
    private static final int edit_take_a_photo4 = 69;
    private static final int edit_take_a_photo5 = 70;

    private Socket socket;

    String filePath1="";

    List<Uri> files = new ArrayList<>();
    List<String> image_id =new ArrayList<>();

    List<MultipartBody.Part> fileList = new ArrayList<>();
    List<MultipartBody.Part> imageIdList = new ArrayList<>();

    // image list

    String Base_url= Constant.Base_url;
    String strImage1="",strImage2="",strImage3="",strImage4="",strImage5="";
    String strOldImage1="",strOldImage2="",strOldImage3="",strOldImage4="",strOldImage5="";

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    BottomSheetDialog bottomSheetDialog1,bottomSheetDialog2,bottomSheetDialog3,bottomSheetDialog4,bottomSheetDialog5;

    File demoFile;

    Bitmap bitmap;
    private File destination;
    InputStream is1=null;
    InputStream is2=null;
    InputStream is3=null;
    InputStream is4=null;
    InputStream is5=null;
    int width;
    String strImageArray="0";

    String straddimg1="0",straddimg2="0",straddimg3="0",straddimg4="0",straddimg5="0";
    Boolean boolimg1=false,boolimg2=false,boolimg3=false,boolimg4=false,boolimg5=false;
    Boolean boolValidate=false;
    ArrayList<String> imageArray ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        socket=new Socket();
        mViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        assetNo = findViewById(R.id.tvAssetNumber);
        tvAssetClassDescription = findViewById(R.id.tvAssetClassDescription);
        tvassetDescription = findViewById(R.id.tvassetDescription);
        tvDateCapitalization = findViewById(R.id.tvDateCapitalization);
        tvBookQt = findViewById(R.id.tvBookQt);
        tvGvalue = findViewById(R.id.tvGvalue);
        llDetails = findViewById(R.id.llDetails);
        tvCostCenter = findViewById(R.id.tvCostCenter);
        lastScanStatus = findViewById(R.id.lastScanStatus);
        condition = findViewById(R.id.spConditionBase);
        remark = findViewById(R.id.EdRemark);
        spinnerSelectPlan = findViewById(R.id.spinnerSelectPlan);
        spinnerSelectLocation = findViewById(R.id.spinnerSelectLocation);
        spinnerSelectSubLocation=findViewById(R.id.spinnerSelectSubLocation);
        inUse = findViewById(R.id.edInUse);
        notInUse = findViewById(R.id.edNotInUse);
        notFound = findViewById(R.id.edNotFound);
        relativeLayoutPlant=findViewById(R.id.relativeLayoutPlant);
        relativeLayoutLocation=findViewById(R.id.relativeLayoutLocation);
        relativeLayoutSubLocation=findViewById(R.id.relativeLayoutSubLocation);

        imgPlantdropdown=findViewById(R.id.imgPlantdropdown);
        imgSyncLocation=findViewById(R.id.imgSyncLocation);
        imgdropdownSelectLocation=findViewById(R.id.imgdropdownSelectLocation);
        imgSyncSubLocation=findViewById(R.id.imgSyncSubLocation);
        imgdropdownSubSelectLocation=findViewById(R.id.imgdropdownSubSelectLocation);
        //reason = findViewById(R.id.spSelctReson);
        assetView = findViewById(R.id.fromAsset);

        seleCtcodntitle = findViewById(R.id.tvSelectCondn);
        tvResponsibledepartment=findViewById(R.id.tvResponsibledepartment);
        tvLocation=findViewById(R.id.tvLocation);
        tvSubLocation=findViewById(R.id.tvSubLocation);
        sessionManager=new SessionManager(DetailsViewActivity.this);
        mDetailsViewActivity = this;

        llAddImgAsset1=findViewById(R.id.llAddImgAsset1);
        llAddImgAsset2=findViewById(R.id.llAddImgAsset2);
        llAddImgAsset3=findViewById(R.id.llAddImgAsset3);
        llAddImgAsset4=findViewById(R.id.llAddImgAsset4);
        llAddImgAsset5=findViewById(R.id.llAddImgAsset5);

        llAvailableImgAsset1=findViewById(R.id.llAvailableImgAsset1);
        llAvailableImgAsset2=findViewById(R.id.llAvailableImgAsset2);
        llAvailableImgAsset3=findViewById(R.id.llAvailableImgAsset3);
        llAvailableImgAsset4=findViewById(R.id.llAvailableImgAsset4);
        llAvailableImgAsset5=findViewById(R.id.llAvailableImgAsset5);

        imgasset1=findViewById(R.id.imgasset1);
        imgasset2=findViewById(R.id.imgasset2);
        imgasset3=findViewById(R.id.imgasset3);
        imgasset4=findViewById(R.id.imgasset4);
        imgasset5=findViewById(R.id.imgasset5);

        imgAddAsset1=findViewById(R.id.imgAddAsset1);
        imgAddAsset2=findViewById(R.id.imgAddAsset2);
        imgAddAsset3=findViewById(R.id.imgAddAsset3);
        imgAddAsset4=findViewById(R.id.imgAddAsset4);
        imgAddAsset5=findViewById(R.id.imgAddAsset5);

        ivEditImgasset1=findViewById(R.id.ivEditImgasset1);
        ivEditImgasset2=findViewById(R.id.ivEditImgasset2);
        ivEditImgasset3=findViewById(R.id.ivEditImgasset3);
        ivEditImgasset4=findViewById(R.id.ivEditImgasset4);
        ivEditImgasset5=findViewById(R.id.ivEditImgasset5);

        arrayListImageId=new ArrayList<>();
        imageArray=new ArrayList<>();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        ImageView iv = findViewById(R.id.back);
        Bundle bundle = getIntent().getExtras();
        condition.getSelectedItem();


        //  showPOP = true;
        mUser = CurrentUser.getInstance().getUser();
        if (mUser != null) {
            Log.d("User", "onCreate: " + mUser.getRESPONSEMSG());
        }
        Intent intent = getIntent();
        if (intent != null) {
            code = intent.getStringExtra("Code");
            type = intent.getStringExtra("Type");
            isFromAsset = intent.getBooleanExtra("isFromAsset", false);
            isManulScan = intent.getBooleanExtra("isManual", false);
            RegionScan = intent.getStringExtra("r");
        }

        ImageView img = findViewById(R.id.back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDetails("0");
        getPlant(sessionManager.getKEY_new_client_id());
        //getLocation(sessionManager.getKEY_new_client_id(),"1");
//        getSubLocation(sessionManager.getKEY_new_client_id());
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<BatchDetails> batchDetailCall=serviceApi.getBatchdetails(sessionManager.getKEY_user_id());

        batchDetailCall.enqueue(new Callback<BatchDetails>() {
            @Override
            public void onResponse(Call<BatchDetails> call, Response<BatchDetails> response) {
                BatchDetails batchDetails=response.body();
                String batch_id;
                int position;
                ArrayList<String> arrayListBatchId=new ArrayList<>();
                if (batchDetails !=null && batchDetails.getRESPONSESTATUS().equals("1")){
                    BatchDetail getCurrentBatch = SetCurrentBatch.getInstance().getCurrentBatch();
                    for (int i=0;i<batchDetails.getBatchDetails().size();i++){
                        arrayListBatchId.add(batchDetails.getBatchDetails().get(i).getBatchId());
                    }

                    if(getCurrentBatch != null){
                        batch_id=getCurrentBatch.getBatchId();
                        if (arrayListBatchId.contains(batch_id)){
                            position=arrayListBatchId.indexOf(batch_id);
                            if (batchDetails.getBatchDetails().get(position).getPermissions().contains("update")){
                                //Toast.makeText(getApplicationContext(),batchDetails.getBatchDetails().get(position).getPermissions().toString(),Toast.LENGTH_LONG).show();

                                spinnerSelectPlan.setEnabled(true);
                                spinnerSelectPlan.setClickable(true);
                                spinnerSelectLocation.setEnabled(true);
                                spinnerSelectLocation.setClickable(true);
                                spinnerSelectSubLocation.setEnabled(true);
                                spinnerSelectSubLocation.setClickable(true);

                            }else {

                                spinnerSelectPlan.setEnabled(false);
                                spinnerSelectPlan.setClickable(false);
                                spinnerSelectLocation.setEnabled(false);
                                spinnerSelectLocation.setClickable(false);
                                spinnerSelectSubLocation.setEnabled(false);
                                spinnerSelectSubLocation.setClickable(false);
                                relativeLayoutPlant.setBackgroundColor(getResources().getColor(R.color.selectedcard));
                                relativeLayoutLocation.setBackgroundColor(getResources().getColor(R.color.selectedcard));
                                relativeLayoutSubLocation.setBackgroundColor(getResources().getColor(R.color.selectedcard));
                                imgPlantdropdown.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                                imgSyncLocation.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                                imgdropdownSelectLocation.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                                imgSyncSubLocation.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                                imgdropdownSubSelectLocation.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                            }
                            //Toast.makeText(getApplicationContext(),"contains"+position,Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(),"batch id"+batch_id,Toast.LENGTH_LONG).show();
                        }
                        //batchDetails.getBatchDetails().get(Integer.parseInt(batch_id)).getPermissions();
                    }
//                    for (int i=0;i<batchDetails.getBatchDetails().size();i++){
//                        for (int j=0;j<batchDetails.getBatchDetails().get(i).getPermissions().size();j++){
//
//                            if (batchDetails.getBatchDetails().get(i).getPermissions().contains("update")){
//                                Toast.makeText(getApplicationContext(),batchDetails.getBatchDetails().get(1).getPermissions().toString(),Toast.LENGTH_LONG).show();
//
//                                spinnerSelectPlan.setEnabled(false);
//                                spinnerSelectPlan.setClickable(false);
//                                spinnerSelectLocation.setEnabled(false);
//                                spinnerSelectLocation.setClickable(false);
//                                spinnerSelectSubLocation.setEnabled(false);
//                                spinnerSelectSubLocation.setClickable(false);
//
//
//                            }else {
//
//                                spinnerSelectPlan.setEnabled(true);
//                                spinnerSelectPlan.setClickable(true);
//                                spinnerSelectLocation.setEnabled(true);
//                                spinnerSelectLocation.setClickable(true);
//                                spinnerSelectSubLocation.setEnabled(true);
//                                spinnerSelectSubLocation.setClickable(true);
//
//                            }
//                        }
//
//                    }
                }
            }

            @Override
            public void onFailure(Call<BatchDetails> call, Throwable t) {

            }
        });
        final ArrayList<String> resonString = new ArrayList<>();
        detailsReson = new ArrayList<>();
        final ArrayAdapter<String> resonAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, resonString);
        SplReson = findViewById(R.id.spSelctReson);
        resonString.add("-Select Reason-");
        resonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SplReson.setAdapter(resonAdapter);


        mViewModel.getAllResons().observe(this, new Observer<Reson>() {
            @Override
            public void onChanged(Reson reson) {
                if (reson.getReasonDetails() != null) {
                    for (int i = 0; i < reson.getReasonDetails().size(); i++) {
                        detailsReson.add(reson.getReasonDetails().get(i));
                        resonString.add(reson.getReasonDetails().get(i).getReasonTitle());
                        resonAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        b = findViewById(R.id.btSubmit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmmitAssetApi();
            }
        });

        imgAddAsset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //Apply for multiple permissions together
                    ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}
                            , REQUEST_CODE_ASK_PERMISSIONS);
                }else {
                    straddimg1="1";
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it, take_a_photo1);



                }
            }
        });

        imgasset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DetailsViewActivity.this,FullScreenImageActivity.class);
                intent1.putExtra("image_name",strImage1);
                startActivity(intent1);
            }
        });


        imgAddAsset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //Apply for multiple permissions together
                    ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}
                            , REQUEST_CODE_ASK_PERMISSIONS);
                }else {
                    straddimg2="1";
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it, take_a_photo2);
                }
            }
        });

        imgasset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DetailsViewActivity.this,FullScreenImageActivity.class);
                intent1.putExtra("image_name",strImage2);
                startActivity(intent1);
            }
        });

        imgAddAsset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //Apply for multiple permissions together
                    ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}
                            , REQUEST_CODE_ASK_PERMISSIONS);
                }else {
                    straddimg3="1";
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it, take_a_photo3);
                }
            }
        });
        imgasset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DetailsViewActivity.this,FullScreenImageActivity.class);
                intent1.putExtra("image_name",strImage3);
                startActivity(intent1);
            }
        });

        imgAddAsset4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //Apply for multiple permissions together
                    ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}
                            , REQUEST_CODE_ASK_PERMISSIONS);
                }else {
                    straddimg4="1";
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it, take_a_photo4);
                }
            }
        });
        imgasset4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DetailsViewActivity.this,FullScreenImageActivity.class);
                intent1.putExtra("image_name",strImage4);
                startActivity(intent1);
            }
        });
        imgAddAsset5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //Apply for multiple permissions together
                    ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}
                            , REQUEST_CODE_ASK_PERMISSIONS);
                }else {
                    straddimg5="1";
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it, take_a_photo5);
                }

            }
        });

        imgasset5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DetailsViewActivity.this,FullScreenImageActivity.class);
                intent1.putExtra("image_name",strImage5);
                startActivity(intent1);
            }
        });

        ivEditImgasset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog1 = new BottomSheetDialog(DetailsViewActivity.this);
                bottomSheetDialog1.setContentView(R.layout.bottom_sheet_edit);
                bottomSheetDialog1.setCancelable(true);
                bottomSheetDialog1.setCanceledOnTouchOutside(true);
                TextView edit = bottomSheetDialog1.findViewById(R.id.txtEditImage);
                TextView delete = bottomSheetDialog1.findViewById(R.id.txtDeleteImage);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                            //Apply for multiple permissions together
                            ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}
                                    , REQUEST_CODE_ASK_PERMISSIONS);
                        } else {
                            straddimg1="0";
                            Intent camera_intent
                                    = new Intent(MediaStore
                                    .ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, edit_take_a_photo1);
                        }
                    }
                });
                bottomSheetDialog1.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (straddimg1.equals("1")){
                            llAvailableImgAsset1.setVisibility(View.GONE);
                            llAddImgAsset1.setVisibility(View.VISIBLE);
                            bottomSheetDialog1.dismiss();
                        }else {
                            deleteImage(assetId,"image1");
                            bottomSheetDialog1.dismiss();
                        }

                    }
                });
            }
        });
        ivEditImgasset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog2 = new BottomSheetDialog(DetailsViewActivity.this);
                bottomSheetDialog2.setContentView(R.layout.bottom_sheet_edit);
                bottomSheetDialog2.setCancelable(true);
                bottomSheetDialog2.setCanceledOnTouchOutside(true);
                TextView edit = bottomSheetDialog2.findViewById(R.id.txtEditImage);
                TextView delete = bottomSheetDialog2.findViewById(R.id.txtDeleteImage);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                            //Apply for multiple permissions together
                            ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}
                                    , REQUEST_CODE_ASK_PERMISSIONS);
                        } else {
                            straddimg2="0";
                            Intent camera_intent
                                    = new Intent(MediaStore
                                    .ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, edit_take_a_photo2);
                        }
                    }
                });

                bottomSheetDialog2.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (straddimg2.equals("1")){
                            llAvailableImgAsset2.setVisibility(View.GONE);
                            llAddImgAsset2.setVisibility(View.VISIBLE);
                            bottomSheetDialog2.dismiss();
                        }else {
                            deleteImage(assetId,"image2");
                            bottomSheetDialog2.dismiss();
                        }

                    }
                });
            }
        });
        ivEditImgasset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog3 = new BottomSheetDialog(DetailsViewActivity.this);
                bottomSheetDialog3.setContentView(R.layout.bottom_sheet_edit);
                bottomSheetDialog3.setCancelable(true);
                bottomSheetDialog3.setCanceledOnTouchOutside(true);
                TextView edit = bottomSheetDialog3.findViewById(R.id.txtEditImage);
                TextView delete = bottomSheetDialog3.findViewById(R.id.txtDeleteImage);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                            //Apply for multiple permissions together
                            ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}
                                    , REQUEST_CODE_ASK_PERMISSIONS);
                        } else {
                            straddimg3="0";
                            Intent camera_intent
                                    = new Intent(MediaStore
                                    .ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, edit_take_a_photo3);
                        }
                    }
                });

                bottomSheetDialog3.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (straddimg3.equals("1")){
                            llAvailableImgAsset3.setVisibility(View.GONE);
                            llAddImgAsset3.setVisibility(View.VISIBLE);
                            bottomSheetDialog3.dismiss();
                        }else {
                            boolimg3=false;
                            strImageArray="0";
                            deleteImage(assetId,"image3");
                            bottomSheetDialog3.dismiss();
                        }

                    }
                });
            }
        });
        ivEditImgasset4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog4 = new BottomSheetDialog(DetailsViewActivity.this);
                bottomSheetDialog4.setContentView(R.layout.bottom_sheet_edit);
                bottomSheetDialog4.setCancelable(true);
                bottomSheetDialog4.setCanceledOnTouchOutside(true);
                TextView edit = bottomSheetDialog4.findViewById(R.id.txtEditImage);
                TextView delete = bottomSheetDialog4.findViewById(R.id.txtDeleteImage);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                            //Apply for multiple permissions together
                            ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}
                                    , REQUEST_CODE_ASK_PERMISSIONS);
                        } else {
                            straddimg4="0";
                            Intent camera_intent
                                    = new Intent(MediaStore
                                    .ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, edit_take_a_photo4);
                        }
                    }
                });

                bottomSheetDialog4.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (straddimg4.equals("1")){
                            llAvailableImgAsset4.setVisibility(View.GONE);
                            llAddImgAsset4.setVisibility(View.VISIBLE);
                            bottomSheetDialog4.dismiss();
                        }else {
                            boolimg4=false;
                            strImageArray="0";
                            deleteImage(assetId,"image4");
                            bottomSheetDialog4.dismiss();
                        }

                    }
                });
            }
        });
        ivEditImgasset5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog5 = new BottomSheetDialog(DetailsViewActivity.this);
                bottomSheetDialog5.setContentView(R.layout.bottom_sheet_edit);
                bottomSheetDialog5.setCancelable(true);
                bottomSheetDialog5.setCanceledOnTouchOutside(true);
                TextView edit = bottomSheetDialog5.findViewById(R.id.txtEditImage);
                TextView delete = bottomSheetDialog5.findViewById(R.id.txtDeleteImage);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(DetailsViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                            //Apply for multiple permissions together
                            ActivityCompat.requestPermissions(DetailsViewActivity.this, new String[]{Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}
                                    , REQUEST_CODE_ASK_PERMISSIONS);
                        } else {
                            straddimg5="0";
                            Intent camera_intent
                                    = new Intent(MediaStore
                                    .ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, edit_take_a_photo5);
                        }
                    }
                });

                bottomSheetDialog5.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (straddimg5.equals("1")) {
                            llAvailableImgAsset5.setVisibility(View.GONE);
                            llAddImgAsset5.setVisibility(View.VISIBLE);
                            bottomSheetDialog5.dismiss();
                        }else {
                            boolimg5=false;
                            strImageArray="0";
                            deleteImage(assetId,"image5");
                            bottomSheetDialog5.dismiss();
                        }

                    }
                });
            }
        });

    }

    void getPlant(String client_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<Plant> plantCall=serviceApi.getPlant(client_id);
        plantCall.enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                Plant plant=response.body();
                if (plant != null && plant.getrESPONSESTATUS().equals("1")){
                    int de = 0;
                    b.setEnabled(true);
                    plantDetails=plant.getPlantDetails();
                    ArrayList<String> name=new ArrayList<>();
                    name.add("-Select Plant-");
                    for (Plant.PlantDetails data1 : plantDetails) {
                        name.add(data1.getDescription());
                    }
                    ArrayList<String> id=new ArrayList<>();
                    id.add("ids");
                    for (Plant.PlantDetails data1 : plantDetails) {
                        id.add(data1.getId());
                    }
                    //Toast.makeText(getApplicationContext(),""+selectedPlantId,Toast.LENGTH_LONG).show();
                    for (int i=0;i<id.size();i++){
                        if (id.contains(String.valueOf(selectedPlantId))){
                            de=id.indexOf(String.valueOf(selectedPlantId));
                            //Toast.makeText(getApplicationContext(),"id.toString()"+":"+id.indexOf(String.valueOf(selectedLocation)),Toast.LENGTH_LONG).show();
                        }
                    }
                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(DetailsViewActivity.this,
                            R.layout.spinner_text, name) ;
                    adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSelectPlan.setAdapter(adapterState);
                    spinnerSelectPlan.setSelection(de);
                    getLocation(sessionManager.getKEY_new_client_id(), String.valueOf(selectedPlantId));
                    spinnerSelectPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position==0){
                                strPlantId = plantDetails.get(position).getId();
                                strPlantId="0";
                                //getLocation("1",strPlantId);
                            }
                            else {
                                if (position==plantDetails.size()){
                                    selectedPlantId=position;
                                    strPlantId = plantDetails.get(position-1).getId();
                                    getLocation(sessionManager.getKEY_new_client_id(),strPlantId);
                                }else {
                                    selectedPlantId=position-1;
                                    strPlantId = plantDetails.get(position-1).getId();
                                    getLocation(sessionManager.getKEY_new_client_id(),strPlantId);
                                }


                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else {

                    Toast.makeText(DetailsViewActivity.this,plant.getrESPONSEMSG(),Toast.LENGTH_LONG).show();
                    b.setEnabled(false);
                }

            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {

            }
        });
    }

    void getLocation(String client_id, String plant_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<Location> locationCall=serviceApi.getLocationAsPerPlant(client_id,plant_id);
        locationCall.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location=response.body();
                if (location !=null && location.getrESPONSESTATUS().equals("1")){
                    int de = 0;
                    locationDetails=location.getLocationDetails();
                    ArrayList<String> name=new ArrayList<>();
                    name.add("-Select Location-");
                    for (Location.LocationDetails data1 : locationDetails) {
                        name.add(data1.getName());
                    }
                    ArrayList<String> id=new ArrayList<>();
                    id.add("ids");
                    for (Location.LocationDetails data2 : locationDetails) {
                        id.add(data2.getId());
                    }
                    for (int i=0;i<id.size();i++){
                        if (id.contains(String.valueOf(selectedLocation))){
                            de=id.indexOf(String.valueOf(selectedLocation));
                            //Toast.makeText(getApplicationContext(),"id.toString()"+":"+id.indexOf(String.valueOf(selectedLocation)),Toast.LENGTH_LONG).show();
                        }
                    }
                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(DetailsViewActivity.this,
                            R.layout.spinner_text, name) ;
                    adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSelectLocation.setAdapter(adapterState);
                    spinnerSelectLocation.setSelection(de);
                    getSubLocation(sessionManager.getKEY_new_client_id(), String.valueOf(selectedLocation));
                    spinnerSelectLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position==0){
                                strLocationId = locationDetails.get(position).getId();
                                strLocationId="0";
                                selectedLocation= Integer.parseInt(strLocationId);
                                getSubLocation(sessionManager.getKEY_new_client_id(),strLocationId);
                            }else {
                                strLocationId = locationDetails.get(position-1).getId();
                                selectedLocation= Integer.parseInt(strLocationId);
                                getSubLocation(sessionManager.getKEY_new_client_id(),strLocationId);
                                //Toast.makeText(getContext(),strLocationName,Toast.LENGTH_LONG).show();
                            }
                            //spinnerSelectLocation.setSelection(selectedLocation);

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

    void getSubLocation(String client_id,String location_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<SubLocation> subLocationCall=serviceApi.getSubLocationAsPerLocation(client_id,location_id);
        subLocationCall.enqueue(new Callback<SubLocation>() {
            @Override
            public void onResponse(Call<SubLocation> call, Response<SubLocation> response) {
                SubLocation subLocation=response.body();
                if (subLocation != null && subLocation.getrESPONSESTATUS().equals("1")){
                    int de = 0;
                    subLocationDetails=subLocation.getSubLocationDetails();
                    ArrayList<String> name=new ArrayList<>();
                    name.add("-Select Sub-Location-");
                    for (SubLocation.SubLocationDetails data1 : subLocationDetails){
                        name.add(data1.getName());
                    }
                    ArrayList<String> id=new ArrayList<>();
                    id.add("ids");
                    for (SubLocation.SubLocationDetails data2 : subLocationDetails){
                        id.add(data2.getId());
                    }
                    for (int i=0;i<id.size();i++){
                        if (id.contains(String.valueOf(selectedSubLocation))){
                            de=id.indexOf(String.valueOf(selectedSubLocation));
                            //Toast.makeText(getApplicationContext(),"id.toString()"+":"+id.indexOf(String.valueOf(selectedLocation)),Toast.LENGTH_LONG).show();
                        }else {
                            de=0;
                        }
                    }
                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(DetailsViewActivity.this,
                            R.layout.spinner_text, name);
                    adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSelectSubLocation.setAdapter(adapterState);
                    spinnerSelectSubLocation.setSelection(de);
                    spinnerSelectSubLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            if (position==0){
//                                strSubLocation = subLocationDetails.get(position).getId();
//                                strSubLocation="0";
//                                //getLocation("1",strPlantId);
//                            }else {
//                                strSubLocation = subLocationDetails.get(position-1).getId();
//                                //Toast.makeText(getContext(),strLocationName,Toast.LENGTH_LONG).show();
//                            }
                            //spinnerSelectSubLocation.setSelection(selectedSubLocation);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SubLocation> call, Throwable t) {

            }
        });
    }
    // private boolean showPOP = false;
    private boolean isOtherBatch=false;

    private void getDetails(final String verify) {
        //progressDialog.show();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (isFromAsset || isManulScan) {
            if (isFromAsset) {
                seleCtcodntitle.setVisibility(View.GONE);
                condition.setVisibility(View.GONE);
                assetView.setVisibility(View.VISIBLE);
            }

            batchDetail = SetCurrentBatch.getInstance().getCurrentBatch();
            String data = "";
            String unique_code = "";

            if (isFromAsset) {
                data = data + code;
            } else if (isManulScan) {
                unique_code = unique_code + code;
            }
            String other_batch = "";
            if (isOtherBatch) {
                other_batch += "1";
            } else {
                other_batch += "0";
            }
//            Toast.makeText(mDetailsViewActivity, "asset" , Toast.LENGTH_SHORT).show();
            mViewModel.getAssetDetails(batchDetail.getBatchId(), batchDetail.getProjectId(), unique_code, other_batch, verify, "", data).observe(this, new Observer<DetailScan>() {
                @Override
                public void onChanged(DetailScan detailScan) {
                    progressDialog.cancel();
                    if (detailScan.getRESPONSESTATUS().equals("0")) {
                        llDetails.setVisibility(View.GONE);
                        Toast.makeText(mDetailsViewActivity, "" + detailScan.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        llDetails.setVisibility(View.VISIBLE);
                        Toast.makeText(mDetailsViewActivity, "" + detailScan.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                        assetDetails = detailScan.getAssetDetails();

                        if (detailScan.getRESPONSESTATUS().equals("3")) {
                            isOtherBatch = true;
                            String[] dates = detailScan.getRESPONSEMSG().split(" ");
                            lastScanStatus.setText(dates[(dates.length - 1)]);
                            if(verify=="1"){

                            }else {
                                Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
                                showOverridePopup(detailScan.getRESPONSEMSG(), "1");
                            }
                        } else if (detailScan.getRESPONSESTATUS().equals("2")) {
                            isOtherBatch = true;
                            showCodeEnterDialog(detailScan.getRESPONSEMSG(), false, false);
                        } else if (detailScan.getRESPONSESTATUS().equals("1")) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.cancel();
                            }
                            if (assetDetails != null) {

                                assetId=assetDetails.getAssetId();
                                assetNo.setText("" + assetDetails.getAssetNumber());
                                tvAssetClassDescription.setText("" + assetDetails.getAssetClass());
                                tvassetDescription.setText(assetDetails.getAssetDescription());
                                tvBookQt.setText(assetDetails.getBookQty());
                                tvCostCenter.setText(assetDetails.getCostCenter());
                                tvDateCapitalization.setText(assetDetails.getCapDate());
                                tvGvalue.setText(assetDetails.getGrossBlock());
                                tvResponsibledepartment.setText(assetDetails.getResponsible_department());
                                //Toast.makeText(getApplicationContext(),"id:"+assetDetails.getSub_location_id(),Toast.LENGTH_LONG).show();
                                if (assetDetails.getPlant_id()!=null){
                                    selectedPlantId= Integer.parseInt(assetDetails.getPlant_id());
                                }else {
                                    selectedPlantId=0;
                                }
                                if (assetDetails.getLocation_id()!=null){
                                    selectedLocation= Integer.parseInt(assetDetails.getLocation_id());
                                }else {
                                    selectedLocation= 0;
                                }
                                if (assetDetails.getSub_location_id()!=null){
                                    selectedSubLocation= Integer.parseInt(assetDetails.getSub_location_id());
                                }else {
                                    selectedSubLocation= 0;
                                }

//                                try{
//                                    selectedPlantId= Integer.parseInt(assetDetails.getPlant_id());
//                                    selectedLocation= Integer.parseInt(assetDetails.getLocation_id());
//                                    selectedSubLocation= Integer.parseInt(assetDetails.getSub_location_id());
//                                }catch(NumberFormatException ex){ // handle your exception
//
//                                }
//                                selectedPlantId= Integer.parseInt(assetDetails.getPlant_id());
                                //Toast.makeText(getApplicationContext(),"1"+assetDetails.getLocation_id(),Toast.LENGTH_LONG).show();
//                                selectedLocation= Integer.parseInt(assetDetails.getLocation_id());
                                tvLocation.setText(assetDetails.getLocation());
//                                selectedSubLocation= Integer.parseInt(assetDetails.getSub_location_id());
                                tvSubLocation.setText(assetDetails.getSub_location());

                                if (assetDetails.getCurrent_images().getImage1().equals("")){
                                    llAvailableImgAsset1.setVisibility(View.GONE);
                                    llAddImgAsset1.setVisibility(View.VISIBLE);
                                    strOldImage1="";
                                }else {
                                    llAddImgAsset1.setVisibility(View.GONE);
                                    llAvailableImgAsset1.setVisibility(View.VISIBLE);
                                    straddimg1="0";
                                    strImage1=Base_url+assetDetails.getCurrent_images().getImage1();
                                    imageArray.add(strImage1);
                                    strOldImage1=assetDetails.getCurrent_images().getImage1();
                                    Glide.with(getApplicationContext()).load(strImage1).into(imgasset1);
                                }

                                if (assetDetails.getCurrent_images().getImage2().equals("")){
                                    llAvailableImgAsset2.setVisibility(View.GONE);
                                    llAddImgAsset2.setVisibility(View.VISIBLE);
                                    strOldImage2="";

                                }else {
                                    llAddImgAsset2.setVisibility(View.GONE);
                                    llAvailableImgAsset2.setVisibility(View.VISIBLE);
                                    straddimg2="0";
                                    strImage2=Base_url+assetDetails.getCurrent_images().getImage2();
                                    strOldImage2=assetDetails.getCurrent_images().getImage2();
                                    imageArray.add(strImage2);
                                    Glide.with(getApplicationContext()).load(strImage2).into(imgasset2);
                                }

                                if (assetDetails.getCurrent_images().getImage3().equals("")){
                                    llAvailableImgAsset3.setVisibility(View.GONE);
                                    llAddImgAsset3.setVisibility(View.VISIBLE);
                                    strOldImage3="";
                                    boolimg3=false;
                                    strImageArray="0";
                                }else {
                                    llAddImgAsset3.setVisibility(View.GONE);
                                    llAvailableImgAsset3.setVisibility(View.VISIBLE);
                                    straddimg3="0";
                                    strImage3=Base_url+assetDetails.getCurrent_images().getImage3();
                                    strOldImage3=assetDetails.getCurrent_images().getImage3();
                                    imageArray.add(strImage3);
                                    Glide.with(getApplicationContext()).load(strImage3).into(imgasset3);
                                }

                                if (assetDetails.getCurrent_images().getImage4().equals("")){
                                    llAvailableImgAsset4.setVisibility(View.GONE);
                                    llAddImgAsset4.setVisibility(View.VISIBLE);
                                    strOldImage4="";
                                    boolimg4=false;
                                    strImageArray="0";
                                }else {
                                    llAddImgAsset4.setVisibility(View.GONE);
                                    llAvailableImgAsset4.setVisibility(View.VISIBLE);
                                    straddimg4="0";
                                    strImage4=Base_url+assetDetails.getCurrent_images().getImage4();
                                    strOldImage4=assetDetails.getCurrent_images().getImage4();
                                    imageArray.add(strImage4);
                                    Glide.with(getApplicationContext()).load(strImage4).into(imgasset4);
                                }

                                if (assetDetails.getCurrent_images().getImage5().equals("")){
                                    llAvailableImgAsset5.setVisibility(View.GONE);
                                    llAddImgAsset5.setVisibility(View.VISIBLE);
                                    strOldImage5="";
                                    boolimg5=false;
                                    strImageArray="0";
                                }else {
                                    llAddImgAsset5.setVisibility(View.GONE);
                                    llAvailableImgAsset5.setVisibility(View.VISIBLE);
                                    straddimg5="0";
                                    strImage5=Base_url+assetDetails.getCurrent_images().getImage5();
                                    strOldImage5=assetDetails.getCurrent_images().getImage5();
                                    imageArray.add(strImage5);
                                    Glide.with(getApplicationContext()).load(strImage5).into(imgasset5);
                                }
                            }

                        }
                    }
                }
            });
        } else {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            assetView.setVisibility(View.GONE);
            condition.setVisibility(View.VISIBLE);
            seleCtcodntitle.setVisibility(View.VISIBLE);
            batchDetail = SetCurrentBatch.getInstance().getCurrentBatch();

            String other_batch = "";
            if (isOtherBatch) {

                other_batch += "1";
            } else {
                other_batch += "0";
            }

            if (type != null && type.equalsIgnoreCase("rfid")) {
                //Toast.makeText(mDetailsViewActivity, "rfid" , Toast.LENGTH_SHORT).show();
                mViewModel.getScanResultRFID(batchDetail.getProjectId(), batchDetail.getBatchId(), code, other_batch, verify, "rfid").observe(this, new Observer<DetailScan>() {
                    @Override
                    public void onChanged(DetailScan detailScan) {
                        progressDialog.cancel();
                        if (detailScan.getRESPONSESTATUS().equals("0")) {
                            llDetails.setVisibility(View.GONE);
                            Toast.makeText(mDetailsViewActivity, "" + detailScan.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            llDetails.setVisibility(View.VISIBLE);
                            assetDetails = detailScan.getAssetDetails();

                            if (detailScan.getRESPONSESTATUS().equals("3")) {
                                isOtherBatch = true;
                                String[] dates = detailScan.getRESPONSEMSG().split(" ");
                                lastScanStatus.setText(dates[(dates.length - 1)]);
                                Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                                showOverridePopup(detailScan.getRESPONSEMSG(), "1");

                            } else if (detailScan.getRESPONSESTATUS().equals("2")) {
                                isOtherBatch = true;
                                showCodeEnterDialog(detailScan.getRESPONSEMSG(), false, false);
                            }
                            if (assetDetails != null) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                assetId=assetDetails.getAssetId();
                                assetNo.setText("" + assetDetails.getAssetNumber());
                                tvAssetClassDescription.setText("" + assetDetails.getAssetClass());
                                tvassetDescription.setText(assetDetails.getAssetDescription());
                                tvBookQt.setText(assetDetails.getBookQty());
                                tvCostCenter.setText(assetDetails.getCostCenter());
                                tvDateCapitalization.setText(assetDetails.getCapDate());
                                tvGvalue.setText(assetDetails.getGrossBlock());
                                tvResponsibledepartment.setText(assetDetails.getResponsible_department());
                                tvLocation.setText(assetDetails.getLocation());
                                tvSubLocation.setText(assetDetails.getSub_location());
                                if (assetDetails.getPlant_id()!=null){
                                    selectedPlantId= Integer.parseInt(assetDetails.getPlant_id());
                                }else {
                                    selectedPlantId=0;
                                }
                                if (assetDetails.getLocation_id()!=null){
                                    selectedLocation= Integer.parseInt(assetDetails.getLocation_id());
                                }else {
                                    selectedLocation= 0;
                                }
                                if (assetDetails.getSub_location_id()!=null){
                                    selectedSubLocation= Integer.parseInt(assetDetails.getSub_location_id());
                                }else {
                                    selectedSubLocation= 0;
                                }

                                if (assetDetails.getCurrent_images().getImage1().equals("")){
                                    llAvailableImgAsset1.setVisibility(View.GONE);
                                    llAddImgAsset1.setVisibility(View.VISIBLE);
                                    strOldImage1="";
                                }else {
                                    llAddImgAsset1.setVisibility(View.GONE);
                                    llAvailableImgAsset1.setVisibility(View.VISIBLE);
                                    straddimg1="0";
                                    strImage1=Base_url+assetDetails.getCurrent_images().getImage1();
                                    imageArray.add(strImage1);
                                    strOldImage1=assetDetails.getCurrent_images().getImage1();
                                    Glide.with(getApplicationContext()).load(strImage1).into(imgasset1);
                                }

                                if (assetDetails.getCurrent_images().getImage2().equals("")){
                                    llAvailableImgAsset2.setVisibility(View.GONE);
                                    llAddImgAsset2.setVisibility(View.VISIBLE);
                                    strOldImage2="";
                                }else {
                                    llAddImgAsset2.setVisibility(View.GONE);
                                    llAvailableImgAsset2.setVisibility(View.VISIBLE);
                                    straddimg2="0";
                                    strImage2=Base_url+assetDetails.getCurrent_images().getImage2();
                                    imageArray.add(strImage2);
                                    strOldImage2=assetDetails.getCurrent_images().getImage2();
                                    Glide.with(getApplicationContext()).load(strImage2).into(imgasset2);
                                }

                                if (assetDetails.getCurrent_images().getImage3().equals("")){
                                    llAvailableImgAsset3.setVisibility(View.GONE);
                                    llAddImgAsset3.setVisibility(View.VISIBLE);
                                    strOldImage3="";
                                }else {
                                    llAddImgAsset3.setVisibility(View.GONE);
                                    llAvailableImgAsset3.setVisibility(View.VISIBLE);
                                    straddimg3="0";
                                    strImage3=Base_url+assetDetails.getCurrent_images().getImage3();
                                    imageArray.add(strImage3);
                                    strOldImage3=assetDetails.getCurrent_images().getImage3();
                                    Glide.with(getApplicationContext()).load(strImage3).into(imgasset3);
                                }

                                if (assetDetails.getCurrent_images().getImage4().equals("")){
                                    llAvailableImgAsset4.setVisibility(View.GONE);
                                    llAddImgAsset4.setVisibility(View.VISIBLE);
                                    strOldImage4="";
                                }else {
                                    llAddImgAsset4.setVisibility(View.GONE);
                                    llAvailableImgAsset4.setVisibility(View.VISIBLE);
                                    straddimg4="0";
                                    strImage4=Base_url+assetDetails.getCurrent_images().getImage4();
                                    imageArray.add(strImage4);
                                    strOldImage4=assetDetails.getCurrent_images().getImage4();
                                    Glide.with(getApplicationContext()).load(strImage4).into(imgasset4);
                                }

                                if (assetDetails.getCurrent_images().getImage5().equals("")){
                                    llAvailableImgAsset5.setVisibility(View.GONE);
                                    llAddImgAsset5.setVisibility(View.VISIBLE);
                                    strOldImage5="";
                                }else {
                                    llAddImgAsset5.setVisibility(View.GONE);
                                    llAvailableImgAsset5.setVisibility(View.VISIBLE);
                                    straddimg5="0";
                                    strImage5=Base_url+assetDetails.getCurrent_images().getImage5();
                                    imageArray.add(strImage5);
                                    strOldImage5=assetDetails.getCurrent_images().getImage5();
                                    Glide.with(getApplicationContext()).load(strImage5).into(imgasset5);
                                }

                                //Toast.makeText(getApplicationContext(),"2"+assetDetails.getLocation_id(),Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
            } else {
    //            Toast.makeText(getApplicationContext(),"a:"+batchDetail.getProjectId()+"b:"+ batchDetail.getBatchId()+"c:" +code+ "d:"+other_batch+ "e:"+verify+"f:"+ "qrcode",Toast.LENGTH_LONG).show();
                mViewModel.getScanResult(batchDetail.getProjectId(), batchDetail.getBatchId(), code, other_batch, verify, "qrcode").observe(this, new Observer<DetailScan>() {
                    @Override
                    public void onChanged(DetailScan detailScan) {
                        progressDialog.cancel();
                        if (detailScan.getRESPONSESTATUS().equals("0")) {
                            llDetails.setVisibility(View.GONE);
                            Toast.makeText(mDetailsViewActivity, "" + detailScan.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            llDetails.setVisibility(View.VISIBLE);
                            assetDetails = detailScan.getAssetDetails();

                            if(verify=="1"){


                            }else {
                                if (detailScan.getRESPONSESTATUS().equals("3")) {
                                    isOtherBatch = true;
                                    String[] dates = detailScan.getRESPONSEMSG().split(" ");
                                    lastScanStatus.setText(dates[(dates.length - 1)]);
                                    //Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_LONG).show();
                                    showOverridePopup(detailScan.getRESPONSEMSG(), "1");

                                } else if (detailScan.getRESPONSESTATUS().equals("2")) {
                                    isOtherBatch = true;
                                    showCodeEnterDialog(detailScan.getRESPONSEMSG(), false, false);
                                }
                            }
                            if (assetDetails != null) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                assetId=assetDetails.getAssetId();
                                assetNo.setText("" + assetDetails.getAssetNumber());
                                tvAssetClassDescription.setText("" + assetDetails.getAssetClass());
                                tvassetDescription.setText(assetDetails.getAssetDescription());
                                tvBookQt.setText(assetDetails.getBookQty());
                                tvCostCenter.setText(assetDetails.getCostCenter());
                                tvDateCapitalization.setText(assetDetails.getCapDate());
                                tvGvalue.setText(assetDetails.getGrossBlock());
                                tvResponsibledepartment.setText(assetDetails.getResponsible_department());
                                tvLocation.setText(assetDetails.getLocation());
                                tvSubLocation.setText(assetDetails.getSub_location());
                                if (assetDetails.getPlant_id()!=null){
                                    selectedPlantId= Integer.parseInt(assetDetails.getPlant_id());
                                    //spinnerSelectPlan.setSelection(selectedPlantId);
                                }else {
                                    selectedPlantId=0;
                                    //spinnerSelectPlan.setSelection(selectedPlantId);
                                }
//                                selectedPlantId= Integer.parseInt(assetDetails.getPlant_id());
//                                spinnerSelectPlan.setSelection(selectedPlantId);

                                //selectedLocation= Integer.parseInt(assetDetails.getLocation_id());
                                if (assetDetails.getLocation_id()!=null){
                                    selectedLocation= Integer.parseInt(assetDetails.getLocation_id());
                              //      Toast.makeText(getApplicationContext(),"nonnull Location"+selectedLocation,Toast.LENGTH_LONG).show();
                                }else {
                                    selectedLocation= 0;
                              //      Toast.makeText(getApplicationContext(),"null Location"+selectedLocation,Toast.LENGTH_LONG).show();
                                }
                                //getLocation(sessionManager.getKEY_new_client_id(), String.valueOf(selectedPlantId),selectedLocation);
                                //Toast.makeText(getApplicationContext(),"3"+"Location id:"+selectedLocation,Toast.LENGTH_LONG).show();

                                if (assetDetails.getSub_location_id()!=null){
                                    selectedSubLocation= Integer.parseInt(assetDetails.getSub_location_id());
                               //     Toast.makeText(getApplicationContext(),"nonnull SubLocation"+selectedSubLocation,Toast.LENGTH_LONG).show();
                                }else {
                                    selectedSubLocation= 0;
                                //    Toast.makeText(getApplicationContext(),"null SubLocation"+selectedSubLocation,Toast.LENGTH_LONG).show();
                                }
                                //selectedSubLocation= Integer.parseInt(assetDetails.getSub_location_id());

                                if (assetDetails.getCurrent_images().getImage1().equals("")){
                                    llAvailableImgAsset1.setVisibility(View.GONE);
                                    llAddImgAsset1.setVisibility(View.VISIBLE);
                                    strOldImage1="";
                                }else {
                                    llAddImgAsset1.setVisibility(View.GONE);
                                    llAvailableImgAsset1.setVisibility(View.VISIBLE);
                                    straddimg1="0";
                                    strImage1=Base_url+assetDetails.getCurrent_images().getImage1();
                                    imageArray.add(strImage1);
                                    strOldImage1=assetDetails.getCurrent_images().getImage1();
                                    Glide.with(getApplicationContext()).load(strImage1).into(imgasset1);
                                }

                                if (assetDetails.getCurrent_images().getImage2().equals("")){
                                    llAvailableImgAsset2.setVisibility(View.GONE);
                                    llAddImgAsset2.setVisibility(View.VISIBLE);
                                    strOldImage2="";
                                }else {
                                    llAddImgAsset2.setVisibility(View.GONE);
                                    llAvailableImgAsset2.setVisibility(View.VISIBLE);
                                    straddimg2="0";
                                    strImage2=Base_url+assetDetails.getCurrent_images().getImage2();
                                    imageArray.add(strImage2);
                                    strOldImage2=assetDetails.getCurrent_images().getImage2();
                                    Glide.with(getApplicationContext()).load(strImage2).into(imgasset2);
                                }

                                if (assetDetails.getCurrent_images().getImage3().equals("")){
                                    llAvailableImgAsset3.setVisibility(View.GONE);
                                    llAddImgAsset3.setVisibility(View.VISIBLE);
                                    strOldImage3="";
                                }else {
                                    llAddImgAsset3.setVisibility(View.GONE);
                                    llAvailableImgAsset3.setVisibility(View.VISIBLE);
                                    straddimg3="0";
                                    strImage3=Base_url+assetDetails.getCurrent_images().getImage3();
                                    imageArray.add(strImage3);
                                    strOldImage3=assetDetails.getCurrent_images().getImage3();
                                    Glide.with(getApplicationContext()).load(strImage3).into(imgasset3);
                                }

                                if (assetDetails.getCurrent_images().getImage4().equals("")){
                                    llAvailableImgAsset4.setVisibility(View.GONE);
                                    llAddImgAsset4.setVisibility(View.VISIBLE);
                                    strOldImage4="";
                                }else {
                                    llAddImgAsset4.setVisibility(View.GONE);
                                    llAvailableImgAsset4.setVisibility(View.VISIBLE);
                                    straddimg4="0";
                                    strImage4=Base_url+assetDetails.getCurrent_images().getImage4();
                                    imageArray.add(strImage4);
                                    strOldImage4=assetDetails.getCurrent_images().getImage4();
                                    Glide.with(getApplicationContext()).load(strImage4).into(imgasset4);
                                }

                                if (assetDetails.getCurrent_images().getImage5().equals("")){
                                    llAvailableImgAsset5.setVisibility(View.GONE);
                                    llAddImgAsset5.setVisibility(View.VISIBLE);
                                    strOldImage5="";
                                }else {
                                    llAddImgAsset5.setVisibility(View.GONE);
                                    llAvailableImgAsset5.setVisibility(View.VISIBLE);
                                    straddimg5="0";
                                    strImage5=Base_url+assetDetails.getCurrent_images().getImage5();
                                    imageArray.add(strImage5);
                                    strOldImage5=assetDetails.getCurrent_images().getImage5();
                                    Glide.with(getApplicationContext()).load(strImage5).into(imgasset5);
                                }





                            }
                        }
                    }
                });


            }
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void SubmmitAssetApi() {
        if (assetDetails != null) {
            String text = (String) condition.getSelectedItem();

            if (text.equalsIgnoreCase("In Use")) {
                text = "yes";
            } else if (text.equalsIgnoreCase("-Select Condition-")) {
                text = "";
            } else {
                text = "no";
            }

            String use = isFromAsset ? inUse.getEditableText().toString() : "0";
            String notInuse = isFromAsset ? notInUse.getEditableText().toString() : "0";
            String notF = isFromAsset ? notFound.getEditableText().toString() : "0";
            int post = SplReson.getSelectedItemPosition();

            String rgId = "";

            if (SplReson.getSelectedItemPosition()== 0) {
                rgId = "0";
            } else {
                if (post != 0) {
                    Reson.ReasonDetail reasonDetail = detailsReson.get(post-1);
                    rgId = reasonDetail.getReasonId();
                }
            }
            //Please connect device

            String mode = "";
            if (isFromAsset || isManulScan) {
                //mode = mode + "manual";
                mode = mode + "auto";
            } else {
                mode = mode + "auto";
            }
            if ((isFromAsset == false) && (isManulScan == false) && text.isEmpty() && text.equals("")) {
                Toast.makeText(mDetailsViewActivity, "Please Select condition", Toast.LENGTH_SHORT).show();
                return;
            }

            int BookQT = Integer.parseInt(assetDetails.getBookQty());
            int useint = 0, notuse = 0, notFint = 0;
            if (!use.isEmpty()) {
                useint = Integer.parseInt(use);
            }
            if (!notInuse.isEmpty()) {
                notuse = Integer.parseInt(notInuse);
            }
            if ((!notF.isEmpty())) {
                notFint = Integer.parseInt(notF);
            }
            if (isFromAsset) {
                int total = useint + notuse + notFint;
                if (BookQT > total) {
                    Toast.makeText(mDetailsViewActivity, " total of use,not in use and not found must be greater than book Quantity.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (mode.equals("auto")) {
                if (text.isEmpty()) {
                    Toast.makeText(mDetailsViewActivity, "Select condition", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (strPlantId.equalsIgnoreCase("0")){
                Toast.makeText(DetailsViewActivity.this,"Please confirm location!",Toast.LENGTH_LONG).show();
            }else {
                RequestBody rbasset_id = RequestBody.create(MediaType.parse("multipart/form-data"),assetDetails.getAssetId());
                RequestBody rbproject_id = RequestBody.create(MediaType.parse("multipart/form-data"), batchDetail.getProjectId());
                RequestBody rbmode = RequestBody.create(MediaType.parse("multipart/form-data"), mode);
                RequestBody rbqty = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
                RequestBody rbin_use = RequestBody.create(MediaType.parse("multipart/form-data"),text);
                RequestBody rbbatch_id = RequestBody.create(MediaType.parse("multipart/form-data"), batchDetail.getBatchId());
                RequestBody rbuser_id = RequestBody.create(MediaType.parse("multipart/form-data"), mUser.getProfileDetails().getUserId());
                RequestBody rbreason_id = RequestBody.create(MediaType.parse("multipart/form-data"), rgId);
                RequestBody rbremark = RequestBody.create(MediaType.parse("multipart/form-data"), remark.getText().toString());
                RequestBody rbin_use_qty = RequestBody.create(MediaType.parse("multipart/form-data"),use);
                RequestBody rbnot_in_use_qty = RequestBody.create(MediaType.parse("multipart/form-data"), notInuse);
                RequestBody rbnot_found_qty = RequestBody.create(MediaType.parse("multipart/form-data"), notF);
                RequestBody rbplant_id = RequestBody.create(MediaType.parse("multipart/form-data"), strPlantId);
                RequestBody rblocation_id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(selectedLocation));
                RequestBody rbsub_location_id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(selectedSubLocation));

                RequestBody rbold_image1 = RequestBody.create(MediaType.parse("multipart/form-data"), strOldImage1);
                RequestBody rbold_image2 = RequestBody.create(MediaType.parse("multipart/form-data"), strOldImage2);
                RequestBody rbold_image3 = RequestBody.create(MediaType.parse("multipart/form-data"), strOldImage3);
                RequestBody rbold_image4 = RequestBody.create(MediaType.parse("multipart/form-data"), strOldImage4);
                RequestBody rbold_image5 = RequestBody.create(MediaType.parse("multipart/form-data"), strOldImage5);

                //IMAGE1 START
                String image1=PATH;
                RequestBody imageFile1;
                MultipartBody.Part body1;
                if(!(image1==null)) {
                    byte[] imageBytes = new byte[0];

                    File file = new File(image1);
                    /* File file = new File(Fiximge,Fiximge);*/
                    String Filenale;
                    if (file.getName().contains(".png")) {
                        Filenale = file.getName();
                    } else {
                        Filenale = file.getName() + ".png";
                    }

                    try {
                        imageBytes = getBytes(is1,image1,file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageFile1 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), imageBytes);
                    body1 =
                            MultipartBody.Part.createFormData("image1", Filenale, imageFile1);
                }else {

                    imageFile1 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), "");
                    body1 =
                            MultipartBody.Part.createFormData("image1", "", imageFile1);
                }
                //IMAGE1 END

                //IMAGE2 START
                String image2=PATH2;
                RequestBody imageFile2;
                MultipartBody.Part body2;
                if(!(image2==null)) {
                    byte[] imageBytes = new byte[0];


                    File file = new File(image2);
                    /* File file = new File(Fiximge,Fiximge);*/
                    String Filenale;
                    if (file.getName().contains(".png")) {
                        Filenale = file.getName();
                    } else {
                        Filenale = file.getName() + ".png";
                    }

                    try {
                        imageBytes = getBytes(is2,image2,file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageFile2 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), imageBytes);
                    body2 =
                            MultipartBody.Part.createFormData("image2", Filenale, imageFile2);
                }else {

                    imageFile2 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), "");
                    body2 =
                            MultipartBody.Part.createFormData("image2", "", imageFile2);
                }
                //IMAGE2 END

                //IMAGE3 START
                String image3=PATH3;
                RequestBody imageFile3;
                MultipartBody.Part body3;
                if(!(image3==null)) {
                    byte[] imageBytes = new byte[0];

                    File file = new File(image3);
                    /* File file = new File(Fiximge,Fiximge);*/
                    String Filenale;
                    if (file.getName().contains(".png")) {
                        Filenale = file.getName();
                    } else {
                        Filenale = file.getName() + ".png";
                    }

                    try {
                        imageBytes = getBytes(is3,image3,file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageFile3 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), imageBytes);
                    body3 =
                            MultipartBody.Part.createFormData("image3", Filenale, imageFile3);
                }else {

                    imageFile3 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), "");
                    body3 =
                            MultipartBody.Part.createFormData("image3", "", imageFile3);
                }
                //IMAGE3 END

                //IMAGE4 START
                String image4=PATH4;
                RequestBody imageFile4;
                MultipartBody.Part body4;
                if(!(image4==null)) {
                    byte[] imageBytes = new byte[0];

                    File file = new File(image4);
                    /* File file = new File(Fiximge,Fiximge);*/
                    String Filenale;
                    if (file.getName().contains(".png")) {
                        Filenale = file.getName();
                    } else {
                        Filenale = file.getName() + ".png";
                    }

                    try {
                        imageBytes = getBytes(is4,image4,file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageFile4 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), imageBytes);
                    body4 =
                            MultipartBody.Part.createFormData("image4", Filenale, imageFile4);
                }else {

                    imageFile4 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), "");
                    body4 =
                            MultipartBody.Part.createFormData("image4", "", imageFile4);
                }
                //IMAGE4 END

                //IMAGE5 START
                String image5=PATH5;
                RequestBody imageFile5;
                MultipartBody.Part body5;
                if(!(image5==null)) {
                    byte[] imageBytes = new byte[0];

                    File file = new File(image5);
                    /* File file = new File(Fiximge,Fiximge);*/
                    String Filenale;
                    if (file.getName().contains(".png")) {
                        Filenale = file.getName();
                    } else {
                        Filenale = file.getName() + ".png";
                    }

                    try {
                        imageBytes = getBytes(is5,image5,file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    imageFile5 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), imageBytes);
                    body5 =
                            MultipartBody.Part.createFormData("image5", Filenale, imageFile5);
                }else {

                    imageFile5 =
                            RequestBody.create(MediaType.parse("multipart/form-data"), "");
                    body5 =
                            MultipartBody.Part.createFormData("image5", "", imageFile5);
                }
                //IMAGE5 END

//                Toast.makeText(DetailsViewActivity.this,"a:"+assetDetails.getAssetId()+
//                                "b:"+ batchDetail.getProjectId()+
//                                "c:"+ mode+
//                                "d:"+ "1"+
//                                "e:"+ text+
//                                "f:"+ batchDetail.getBatchId()+
//                                "g:"+ mUser.getProfileDetails().getUserId()+
//                                "h:"+rgId+
//                                "i:"+ remark.getText().toString()+
//                                "j:"+ use+
//                                "k:"+ notInuse+
//                                "l:"+ notF+
//                                "m:"+strPlantId+
//                                "n:"+ String.valueOf(selectedLocation)+
//                                "o:"+ String.valueOf(selectedSubLocation),
//                        Toast.LENGTH_LONG).show();

                if (imageArray.size()!=0){
                    NewSubmitAsset(rbasset_id,rbproject_id,rbmode,rbqty,rbin_use,
                            rbbatch_id,rbuser_id,rbreason_id,rbremark,rbin_use_qty,rbnot_in_use_qty,rbnot_found_qty,
                            rbplant_id,rblocation_id,rbsub_location_id,body1,body2,body3,body4,body5,rbold_image1,rbold_image2,rbold_image3,rbold_image4,rbold_image5);
                }else {
                    Toast.makeText(this,"Add at least one image.",Toast.LENGTH_LONG).show();
                }
//                mViewModel.SubmitAsset(assetDetails.getAssetId(), batchDetail.getProjectId(), mode, "1", text, batchDetail.getBatchId(), mUser.getProfileDetails().getUserId(),
//                        rgId, remark.getText().toString(), use, notInuse, notF,strPlantId, String.valueOf(selectedLocation), String.valueOf(selectedSubLocation)).observe(this, new Observer<SendRespose>() {
//                    @Override
//                    public void onChanged(SendRespose sendRespose) {
//                        Toast.makeText(mDetailsViewActivity, "" + sendRespose.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
//                        finish();
//
//                    }
//
//                });

//                submitDetails(assetDetails.getAssetId(), batchDetail.getProjectId(), mode, "1", text, batchDetail.getBatchId(), mUser.getProfileDetails().getUserId(),
//                        rgId, remark.getText().toString(), use, notInuse, notF,strPlantId, String.valueOf(selectedLocation), String.valueOf(selectedSubLocation),"",
//                        "","","","",getBitmap(),getBitmap2(),getBitmap3(),getBitmap4(),getBitmap5());
            }
        } else {
            Toast.makeText(mDetailsViewActivity, "item not found ReScan", Toast.LENGTH_SHORT).show();
        }
    }
    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showCodeEnterDialog(String text, final boolean isOverwrite, final Boolean reVerify) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_custome_enter_manualcode);
        TextView test = dialog.findViewById(R.id.textmsg);
        test.setText(text);

        final Button dialogButton = dialog.findViewById(R.id.mSubmit);
        Button Skip = dialog.findViewById(R.id.mSkip);

        if (!isOverwrite) {
            dialogButton.setText("Yes");
            Skip.setText("No");
        }

        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                mDetailsViewActivity.finish();
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (reVerify) {
                    getDetails("1");
                    getPlant(sessionManager.getKEY_new_client_id());
//                    getLocation(sessionManager.getKEY_new_client_id(), String.valueOf(selectedPlantId));
//                    getSubLocation(sessionManager.getKEY_new_client_id());
                } else {
                    getDetails("0");
                    getPlant(sessionManager.getKEY_new_client_id());
//                    getLocation(sessionManager.getKEY_new_client_id(),String.valueOf(selectedPlantId));
//                    getSubLocation(sessionManager.getKEY_new_client_id());
                }
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void showOverridePopup(String text, final String reVerify) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_custome_enter_manualcode);
        TextView test = dialog.findViewById(R.id.textmsg);
        test.setText(text);

        final Button dialogButton = dialog.findViewById(R.id.mSubmit);
        Button Skip = dialog.findViewById(R.id.mSkip);

        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mDetailsViewActivity.finish();
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getDetails(reVerify);
                getPlant(sessionManager.getKEY_new_client_id());
            }
        });

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == take_a_photo1){
                bitmap = (Bitmap) data.getExtras().get("data");
                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                PATH = destination.getAbsolutePath().toString();
                try {
                    //is1=getApplicationContext().getAssets().open(PATH);
                    is1 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageArray.add(PATH);
                imgasset1.setImageBitmap(bitmap);
                llAddImgAsset1.setVisibility(View.GONE);
                llAvailableImgAsset1.setVisibility(View.VISIBLE);
            }else if(requestCode == edit_take_a_photo1){
                bitmap = (Bitmap) data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename;
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                PATH = destination.getAbsolutePath();
                try {
                    is1 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //imageArray.add(PATH);
                imgasset1.setImageBitmap(bitmap);
            }else if (requestCode == take_a_photo2){
                bitmap = (Bitmap) data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename;
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                PATH2 = destination.getAbsolutePath();
                try {
                    is2 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imageArray.add(PATH2);
                imgasset2.setImageBitmap(bitmap);
                llAddImgAsset2.setVisibility(View.GONE);
                llAvailableImgAsset2.setVisibility(View.VISIBLE);

            }else if (requestCode == edit_take_a_photo2){
                bitmap = (Bitmap) data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename;
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                PATH2 = destination.getAbsolutePath();
                try {
                    is2 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //imageArray.add(PATH2);
                imgasset2.setImageBitmap(bitmap);
            }else if (requestCode == take_a_photo3){

                bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap.createScaledBitmap(bitmap, 50, 50, true);
                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename;
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                PATH3 = destination.getAbsolutePath();
                try {
                    is3 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imageArray.add(PATH3);
                imgasset3.setImageBitmap(bitmap);
                llAddImgAsset3.setVisibility(View.GONE);
                llAvailableImgAsset3.setVisibility(View.VISIBLE);

            }else if (requestCode == edit_take_a_photo3){
                bitmap = (Bitmap) data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename;
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                PATH3 = destination.getAbsolutePath();
                try {
                    is3 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //imageArray.add(PATH3);
                boolimg3=true;
                strImageArray="1";
                imgasset3.setImageBitmap(bitmap);
            }else if (requestCode == take_a_photo4){
                bitmap = (Bitmap) data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename;
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                PATH4 = destination.getAbsolutePath();
                try {
                    is4 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                imageArray.add(PATH4);
                boolimg4=true;
                strImageArray="1";
                imgasset4.setImageBitmap(bitmap);
                llAddImgAsset4.setVisibility(View.GONE);
                llAvailableImgAsset4.setVisibility(View.VISIBLE);
            }else if (requestCode == edit_take_a_photo4){
                bitmap = (Bitmap) data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename;
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                PATH4 = destination.getAbsolutePath();
                try {
                    is4 =getContentResolver().openInputStream(Uri.fromFile(destination));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //imageArray.add(PATH4);
                boolimg4=true;
                strImageArray="1";
                imgasset4.setImageBitmap(bitmap);
            }else if (requestCode == take_a_photo5){
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        bitmap = (Bitmap) extras.get("data");
                        if (bitmap != null){
                            String partFilename = currentDateFormat();
                            storeCameraPhotoInSDCard(bitmap, partFilename);
                            String storeFilename = "photo_" + partFilename;
                            Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                            PATH5 = destination.getAbsolutePath();
                            try {
                                is5=getContentResolver().openInputStream(Uri.fromFile(destination));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            imageArray.add(PATH5);
                            boolimg5=true;
                            strImageArray="1";
                            imgasset5.setImageBitmap(bitmap);
                            llAddImgAsset5.setVisibility(View.GONE);
                            llAvailableImgAsset5.setVisibility(View.VISIBLE);
                        }


                    }
                }
            }else if (requestCode == edit_take_a_photo5){
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        bitmap = (Bitmap) extras.get("data");
                        if (bitmap != null){
                            String partFilename = currentDateFormat();
                            storeCameraPhotoInSDCard(bitmap, partFilename);
                            String storeFilename = "photo_" + partFilename;
                            Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                            PATH5 = destination.getAbsolutePath();
                            try {
                                is5=getContentResolver().openInputStream(Uri.fromFile(destination));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                           // imageArray.add(PATH5);
                            boolimg5=true;
                            strImageArray="1";
                            imgasset5.setImageBitmap(bitmap);
                        }


                    }
                }
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SubmmitAssetApi();
                }
                else {
                    Toast.makeText(DetailsViewActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void NewSubmitAsset(RequestBody asset_id,
                                RequestBody project_id,
                                RequestBody mode,
                                RequestBody qty,
                                RequestBody in_use,
                                RequestBody batch_id,
                                RequestBody user_id,
                                RequestBody reason_id,
                                RequestBody remark,
                                RequestBody in_use_qty,
                                RequestBody not_in_use_qty,
                                RequestBody not_found_qty,
                                RequestBody plant_id,
                                RequestBody location_id,
                                RequestBody sub_location_id,
                                MultipartBody.Part image1,
                                MultipartBody.Part image2,
                                MultipartBody.Part image3,
                                MultipartBody.Part image4,
                                MultipartBody.Part image5,
                                RequestBody old_image1,
                                RequestBody old_image2,
                                RequestBody old_image3,
                                RequestBody old_image4,
                                RequestBody old_image5){
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<SendRespose> call = serviceApi.NewSubmitDetails(asset_id,
                project_id,
                mode,
                qty,
                in_use,
                batch_id,
                user_id,
                reason_id,
                remark,
                in_use_qty,
                not_in_use_qty,
                not_found_qty,
                plant_id,
                location_id,
                sub_location_id,
                image1,
                image2,
                image3,
                image4,
                image5,
                old_image1,
                old_image2,
                old_image3,
                old_image4,
                old_image5);
        call.enqueue(new Callback<SendRespose>() {
            @Override
            public void onResponse(Call<SendRespose> call, Response<SendRespose> response) {
                if (response.body().getRESPONSESTATUS().equals("1")){
                    Toast.makeText(DetailsViewActivity.this,response.body().getRESPONSEMSG(),Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(DetailsViewActivity.this,response.body().getRESPONSEMSG(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SendRespose> call, Throwable t) {
                //Toast.makeText(DetailsViewActivity.this,"Network Error"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void deleteImage(String asset_id,String image_id){
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        final Call<SendRespose> call = serviceApi.deleteImage(asset_id,image_id);
        call.enqueue(new Callback<SendRespose>() {
            @Override
            public void onResponse(Call<SendRespose> call, Response<SendRespose> response) {
                if (response.body().getRESPONSESTATUS().equals("1")){
                    Toast.makeText(DetailsViewActivity.this,response.body().getRESPONSEMSG(),Toast.LENGTH_LONG).show();
                    DetailsViewActivity.this.recreate();
                }else {
                    Toast.makeText(DetailsViewActivity.this,response.body().getRESPONSEMSG(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SendRespose> call, Throwable t) {


            }
        });
    }


    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        destination = new File(Environment.getExternalStorageDirectory()  +"/"+ filename);
        try {
            FileInputStream fis = new FileInputStream(destination);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }


    public byte[] getBytes( InputStream is,String str,File file) throws IOException{
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        file=new File(str);
        str=file.getAbsolutePath();
        is =getContentResolver().openInputStream(Uri.fromFile(file));
        //is=getApplicationContext().getAssets().open(str);
        InputStream finalIs = is;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                int buffSize = 1024;
                byte[] buff =new byte[buffSize];

                int len = 0 ;
                try {

                    if (finalIs !=null){
                        while ((len = finalIs.read(buff)) != -1){
                            byteBuff.write(buff,0,len);
                        }
                    }


                }catch (IOException e){

                }

            }
        });
        //is=socket.getInputStream();

        return  byteBuff.toByteArray();
    }





}
