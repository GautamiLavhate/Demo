package com.levare.verificare.fragment;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.levare.verificare.R;
import com.levare.verificare.activity.AssetDetailActivity;
import com.levare.verificare.activity.DashboardActivity;
import com.levare.verificare.activity.DetailsViewActivity;
import com.levare.verificare.util.SystemSound;

/**
 * A simple {@link Fragment} subclass.
 */
public class RFIDDialogFragment extends DialogFragment {

    private EditText editTextRFID;
    private ImageView imageViewManualCode;
    private SystemSound sound;
    private DashboardActivity dashboardActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_rfid, container, false);
    }

    public static RFIDDialogFragment getInstance() {
        return new RFIDDialogFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextRFID = view.findViewById(R.id.text);
        dashboardActivity = (DashboardActivity) getActivity();
        imageViewManualCode = view.findViewById(R.id.icManualMode);
        sound = new SystemSound(getActivity());

        if (SetCurrentBatch.getIsDetailsOnly()) {
            imageViewManualCode.setVisibility(View.GONE);
        } else {
            imageViewManualCode.setVisibility(View.VISIBLE);
        }


        imageViewManualCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        editTextRFID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 9) {
                    if (dashboardActivity.getPreference("sound")) {
                        sound.successTune();
                    }
                    String data = editTextRFID.getText().toString();
                    sendResult(data);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.lay_custome_dialog);

        final EditText dailogText = dialog.findViewById(R.id.edEnterCode);
        final Spinner spinner = dialog.findViewById(R.id.spReasonRFID);
        Spinner spinnerGone = dialog.findViewById(R.id.sRegion);
        spinnerGone.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        Button dialogButton = dialog.findViewById(R.id.btSubmit);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String re = (String) spinner.getSelectedItem();
                final String text = dailogText.getText().toString();
                if (!text.isEmpty() && spinner.getSelectedItemPosition() != 0) {
                    dialog.dismiss();
                    if (SetCurrentBatch.getIsDetailsOnly()) {
                        Intent intent = new Intent(getActivity(), AssetDetailActivity.class);
                        intent.putExtra("Code", text);
                        startActivity(intent);
                    } else {
                        CallActivity(text, re);
                    }
                } else {
                    Toast.makeText(getContext(), "Please select reason & add code to proceed...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void sendResult(String s) {
        if (SetCurrentBatch.getIsDetailsOnly()) {
            Intent intent = new Intent(getActivity(), AssetDetailActivity.class);
            intent.putExtra("Code", s);
            startActivity(intent);
        } else {
            CallActivity(s, "");
        }

        dismiss();
    }

    private void CallActivity(String code, String re) {
        if (!code.isEmpty()) {
            if (re.isEmpty() && re.equalsIgnoreCase("")) {
                Intent i = new Intent(getActivity(), DetailsViewActivity.class);
                i.putExtra("Code", code);
                i.putExtra("Type", "rfid");
                i.putExtra("isManual", false);
                i.putExtra("r", re);
                startActivity(i);
            } else {
                Intent i = new Intent(getActivity(), DetailsViewActivity.class);
                i.putExtra("Code", code);
                i.putExtra("Type", "rfid");
                i.putExtra("isManual", true);
                i.putExtra("r", re);
                startActivity(i);
            }

        } else {
            Toast.makeText(getActivity(), "Scan RFID code again...", Toast.LENGTH_SHORT).show();
        }

    }
}
