package com.levare.verificare.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.levare.verificare.R;
import com.levare.verificare.BuildConfig;
import com.levare.verificare.fragment.AddLocationFragment;
import com.levare.verificare.fragment.AddSubLocationFragment;
import com.levare.verificare.fragment.AssetListFragment;
import com.levare.verificare.fragment.DashBoardFragment;
import com.levare.verificare.fragment.LocationFragment;
import com.levare.verificare.fragment.RFIDDialogFragment;
import com.levare.verificare.fragment.SetBatchFragment;
import com.levare.verificare.fragment.SetCurrentBatch;
import com.levare.verificare.fragment.SettingDialogFragment;
import com.levare.verificare.fragmnetlistener.IActivityListener;
import com.levare.verificare.fragmnetlistener.IFragmentListener;
import com.levare.verificare.model.BatchDetail;
import com.levare.verificare.model.User;
import com.levare.verificare.network.RetrofitClient;
import com.levare.verificare.network.ServiceApi;
import com.levare.verificare.network.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements IFragmentListener {

    private Toolbar mToolbar;
    private User user;
    private IActivityListener mIActivityListener;
    public static String RFID = "";
    String code;
    private SetBatchFragment setBatchFragment;
    private Boolean isVisible = false;
    private Boolean isVisibleMenu = true;
    private MenuItem menuQR;
    private MenuItem menuRFID;

    private FloatingActionButton flScan;
    private FloatingActionButton flQR;
    private FloatingActionButton flRFID;


    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    SessionManager sessionManager;

    private ArrayList<BatchDetail> batchList = new ArrayList<>();

    public ImageView imgDashBoard,imgSetBatch,imgBatchList,imgLocation;


    public void registerActivityListener(IActivityListener mIActivityListener) {
        this.mIActivityListener = mIActivityListener;
    }

    @Override
    public void sendBatchDetails(BatchDetail batchDetail) {
        mIActivityListener.getBatchDetails(batchDetail);
    }

    public interface OnBackPress {
        boolean onHardKeyBackPress();
    }

    private OnBackPress mOnBackPress;

    public void registerEventListener(OnBackPress mOnBackPress) {
        this.mOnBackPress = mOnBackPress;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_dashbord);

        mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        sessionManager=new SessionManager(DashboardActivity.this);

        RelativeLayout setBatch = findViewById(R.id.ivSetBatch);
        LinearLayout mScan = findViewById(R.id.ivCameraIcon);
        flScan = findViewById(R.id.fbDashboardScan);
        flQR = findViewById(R.id.fbDashboardQR);
        flRFID = findViewById(R.id.fbDashboardRFID);

        imgDashBoard = findViewById(R.id.imgDashBoard);
        imgSetBatch = findViewById(R.id.imgSetBatch);
        imgBatchList = findViewById(R.id.imgBatchList);
        imgLocation = findViewById(R.id.imgLocation);

        imgDashBoard.setImageResource(R.drawable.ic_mask_group_215);
        imgSetBatch.setImageResource(R.drawable.ic_batch_screen);
        imgBatchList.setImageResource(R.drawable.ic_asset_screen);
        imgLocation.setImageResource(R.drawable.ic_address__2_);
        editor = getSharedPreferences("User", 0).edit();
        prefs = getSharedPreferences("User", 0);

        user = getIntent().getExtras().getParcelable("user");

        if (user.getRESPONSESTATUS().equals("4")) {
            showExitPOP(user.getRESPONSEMSG(), false);
        } else if ((user.getRESPONSESTATUS().equals("5"))) {
            showExitPOP(user.getRESPONSEMSG(), false);
        }

        flScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    flRFID.hide();
                    flQR.hide();
                } else {
                    flRFID.show();
                    flQR.show();
                }
                isVisible = !isVisible;
            }
        });

        flRFID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SetCurrentBatch.getInstance().getCurrentBatch() != null) {
                    SetCurrentBatch.setIsDetailsOnly(false);
                    RFIDDialogFragment dialogFragment = RFIDDialogFragment.getInstance();
                    dialogFragment.show(getSupportFragmentManager(), "Test");
                } else {
                    Toast.makeText(DashboardActivity.this, "Please Set current Batch", Toast.LENGTH_SHORT).show();
                }
            }
        });

        flQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SetCurrentBatch.getInstance().getCurrentBatch() != null) {
                    SetCurrentBatch.setIsDetailsOnly(false);
                    new IntentIntegrator(DashboardActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                } else {
                    Toast.makeText(DashboardActivity.this, "Please Set current Batch", Toast.LENGTH_SHORT).show();
                }
            }
        });



        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Choose Scan-Type to proceed.")
                        .setTitle("Scan Type")
                        .setCancelable(false)
                        .setPositiveButton("Barcode", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("RFID", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //rfidDialog();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        addFrag(new DashBoardFragment(), DashBoardFragment.class.getName(), false);
        callSetBatchFragment();
        setBatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgDashBoard.setImageResource(R.drawable.ic_home_screen);
                imgSetBatch.setImageResource(R.drawable.ic_select_batch);
                imgBatchList.setImageResource(R.drawable.ic_asset_screen);
                imgLocation.setImageResource(R.drawable.ic_address__2_);
                callSetBatchFragment();
            }
        });

        RelativeLayout dashboard = findViewById(R.id.ivDashBord);

        dashboard.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                flScan.show();
                mToolbar.setVisibility(View.VISIBLE);
                imgDashBoard.setImageResource(R.drawable.ic_mask_group_215);
                imgSetBatch.setImageResource(R.drawable.ic_batch_screen);
                imgBatchList.setImageResource(R.drawable.ic_asset_screen);
                imgLocation.setImageResource(R.drawable.ic_address__2_);
                getSupportFragmentManager().popBackStack();
                addFrag(new DashBoardFragment(), DashBoardFragment.class.getName(), false);
            }
        });
        RelativeLayout assetList = findViewById(R.id.ivBatchList);

        assetList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgDashBoard.setImageResource(R.drawable.ic_home_screen);
                imgSetBatch.setImageResource(R.drawable.ic_batch_screen);
                imgBatchList.setImageResource(R.drawable.ic_mask_group_225);
                imgLocation.setImageResource(R.drawable.ic_address__2_);
                callAssetListFragment();
            }
        });

        final RelativeLayout location = findViewById(R.id.ivLocation);



        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDashBoard.setImageResource(R.drawable.ic_home_screen);
                imgSetBatch.setImageResource(R.drawable.ic_batch_screen);
                imgBatchList.setImageResource(R.drawable.ic_asset_screen);
                imgLocation.setImageResource(R.drawable.ic_address__3_);
                flRFID.hide();
                flQR.hide();
                callLocationFragment();
            }
        });
    }

    private void callSetBatchFragment() {
        flScan.show();
        getSupportFragmentManager().popBackStack();
        setBatchFragment = new SetBatchFragment();
        Bundle b = new Bundle();
        b.putParcelable("user", user);
        setBatchFragment.setArguments(b);
        addFrag(setBatchFragment, SetBatchFragment.class.getName(), true);
        mToolbar.setVisibility(View.GONE);
    }

    public void callAssetListFragment() {
        flScan.show();
        mToolbar.setVisibility(View.GONE);
        getSupportFragmentManager().popBackStack();
        addFrag(new AssetListFragment(), AssetListFragment.class.getName(), true);
    }

    private void callLocationFragment(){
        flScan.hide();
        mToolbar.setVisibility(View.GONE);
        getSupportFragmentManager().popBackStack();
        addFrag(new LocationFragment(), LocationFragment.class.getName(), true);
    }

    private void callAddLocationFragment(){
        mToolbar.setVisibility(View.GONE);
        getSupportFragmentManager().popBackStack();
        addFrag(new AddLocationFragment(), AddLocationFragment.class.getName(), true);
    }
    private void callAddSubLocationFragment(){
        mToolbar.setVisibility(View.GONE);
        getSupportFragmentManager().popBackStack();
        addFrag(new AddSubLocationFragment(), AddSubLocationFragment.class.getName(), true);
    }
    public void showToolBar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    private Fragment getFrag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public void addFrag(Fragment fragment, String Tag, boolean isAddTobackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.lay_dashbordView, fragment, Tag);
        if (isAddTobackStack)
            transaction.addToBackStack(Tag);
        transaction.commit();
    }

    public void addFrag(Fragment fragment, String Tag) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fullScreenView, fragment, Tag);
        transaction.addToBackStack(Tag);
        transaction.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menuQR = menu.findItem(R.id.action_scan_qr);
        menuRFID = menu.findItem(R.id.action_scan_rfid);

        if (isVisibleMenu) {
            menuQR.setVisible(false);
            menuRFID.setVisible(false);
        } else {
            menuQR.setVisible(true);
            menuRFID.setVisible(true);
        }
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        menuQR = menu.findItem(R.id.action_scan_qr);
//        menuRFID = menu.findItem(R.id.action_scan_rfid);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_profile:
                Intent i = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(i);
                return true;
            case R.id.action_share_app:
                shareApp();
                return true;

            case R.id.action_about_us:
                Intent intent = new Intent(this, AboutUsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_info:
                isVisibleMenu = !isVisibleMenu;
                invalidateOptionsMenu();
                return true;

            case android.R.id.home:
                showExitPOP("", true);
                return true;

            case R.id.action_setting:
                SettingDialogFragment settingDialogFragment = SettingDialogFragment.getInstance();
                settingDialogFragment.show(getSupportFragmentManager(), "Test");
                return true;

            case R.id.action_scan_qr:
                isVisibleMenu = !isVisibleMenu;
                SetCurrentBatch.setIsDetailsOnly(true);
                SetCurrentBatch.setType("qr");
                new IntentIntegrator(DashboardActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                return true;

            case R.id.action_scan_rfid:
                isVisibleMenu = !isVisibleMenu;
                SetCurrentBatch.setType("rfid");
                SetCurrentBatch.setIsDetailsOnly(true);
                RFIDDialogFragment dialogFragment = RFIDDialogFragment.getInstance();
                dialogFragment.show(getSupportFragmentManager(), "Test");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "VERIFICARE");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        if (mOnBackPress != null) {
            mOnBackPress.onHardKeyBackPress();
        } else {

            mToolbar.setVisibility(View.VISIBLE);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() < 1)
            showExitPOP("", true);
        else {
            super.onBackPressed();
        }
    }

    private void ExitApp() {
        CurrentUser.getInstance().setCurrentUser(null);
        SetCurrentBatch.getInstance().setCurrentBatchDetails(null);
        this.finish();
    }

    private void clearStack(String name) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
                //callView(2);
            } else {
                //callView(1);
                //show dialogue with result
                try {
                    List<String> arrayList = new ArrayList<>(Arrays.asList(result.getContents().split("\\|")));
                    code = arrayList.get(0);
                    callView(1, code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 7 1100 VW14000036-0-0 | Computers | SAP No VW14000036 | Lasear jet multi functional printer | Qty 1
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void callView(int event, final String code) {
        switch (event) {
            case 1:
                if (SetCurrentBatch.getIsDetailsOnly()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getBaseContext(), AssetDetailActivity.class);
                                    intent.putExtra("Code", code);
                                    startActivity(intent);
                                }
                            });
                        }
                    }, 10);
                } else {
                    viewHolder();
                }

                break;
            case 2:
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        callAssetListFragment();
                    }
                }, 10);
                break;
        }
    }

    void viewHolder() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // mToolbar.setVisibility(View.GONE);
                        callDetailActivity();
                    }
                });
            }
        }, 10);
    }

    void callDetailActivity() {
        Intent i = new Intent(this, DetailsViewActivity.class);
        i.putExtra("user", user);
        i.putExtra("Code", code);
        startActivity(i);
    }

    public Dialog showExitPOP(String text, boolean isShowTwoButton) {
        final Dialog dialog = new Dialog(this);
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
                //ExitApp();
                String android_id = Settings.Secure.getString(DashboardActivity.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                logout(sessionManager.getKEY_user_id(),android_id);//"12345678999999"
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }

    private void rfidDialog() {
        final Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_rfid);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        final EditText text = (EditText) dialog.findViewById(R.id.text);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 9) {
                    String data = text.getText().toString();
                    Toast.makeText(getApplicationContext(), "ID " + data, Toast.LENGTH_LONG).show();
                    RFID = "";
                    RFID = s.toString();
                    dialog.dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.show();
    }
    private void logout(String user_id,String device_id){
        final ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        final Call<User> aboutUs = serviceApi.logout(user_id,device_id);
        aboutUs.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null && user.getRESPONSESTATUS().equals("1")) {
                    Toast.makeText(DashboardActivity.this, "" +user.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                    ExitApp();

                } else {
                    Toast.makeText(DashboardActivity.this, "" + user.getRESPONSEMSG(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    public Boolean getPreference(String key) {
        if (key.equalsIgnoreCase("sound")) {
            return prefs.getBoolean(key, true);
        } else {
            return prefs.getBoolean(key, false);
        }
    }

    public void setPreference(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

}
