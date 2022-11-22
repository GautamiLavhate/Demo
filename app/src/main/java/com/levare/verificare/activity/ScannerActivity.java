package com.levare.verificare.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.levare.verificare.R;
import com.levare.verificare.fragment.SetCurrentBatch;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScannerActivity extends AppCompatActivity implements
        DecoratedBarcodeView.TorchListener {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private Button switchFlashlightButton;
    private boolean isFlashLightOn = false;
    public static final int RESULT=140;
    private Button bt_get_Job_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // getSupportActionBar().hide();
        setContentView(R.layout.activity_scanner);
        ImageView manualMode=findViewById(R.id.icManualMode);
        manualMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        if (SetCurrentBatch.getIsDetailsOnly()) {
//            manualMode.setVisibility(View.GONE);
//        } else {
//            manualMode.setVisibility(View.VISIBLE);
//        }

        //Initialize barcode scanner view
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);

        //set torch listener
        barcodeScannerView.setTorchListener(this);

        //start capture
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }


    /**
     * Check if the device's camera has a Flashlight.
     *
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight() {
        if (isFlashLightOn) {
            barcodeScannerView.setTorchOff();
            isFlashLightOn = false;
        } else {
            barcodeScannerView.setTorchOn();
            isFlashLightOn = true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    boolean isBack=false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.lay_custome_dialog);

        final EditText dailogText=dialog.findViewById(R.id.edEnterCode);
        final Spinner spinner=dialog.findViewById(R.id.sRegion);
        Button dialogButton = dialog.findViewById(R.id.btSubmit);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String re=(String) spinner.getSelectedItem();
                final String text=dailogText.getText().toString();
                if (!text.isEmpty() && spinner.getSelectedItemPosition() != 0) {
                    dialog.dismiss();
                    if (SetCurrentBatch.getIsDetailsOnly()) {
                        Intent intent = new Intent(getBaseContext(), AssetDetailActivity.class);
                        intent.putExtra("Code", text);
                        startActivity(intent);
                    } else {
                        CallActivity(text, re);
                    }
                } else {
                    Toast.makeText(ScannerActivity.this, "Please select reason & add code to proceed...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }

    private void CallActivity(String code,String re){
        if (!code.isEmpty()) {
            Intent i = new Intent(this, DetailsViewActivity.class);
            i.putExtra("Code", code);
            i.putExtra("isManual",true);
            i.putExtra("r",re);
            startActivity(i);
        }else {
            Toast.makeText(this, "Enter QR Code", Toast.LENGTH_SHORT).show();
        }

    }
}