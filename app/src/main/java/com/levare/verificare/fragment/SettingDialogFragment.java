package com.levare.verificare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.levare.verificare.R;
import com.levare.verificare.activity.DashboardActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingDialogFragment extends DialogFragment {

    private SwitchCompat switchCompatSound;
    private SwitchCompat switchCompatNFC;
    private DashboardActivity dashboardActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_setting, container, false);
        return view;
    }

    public static SettingDialogFragment getInstance() {
        SettingDialogFragment fragment = new SettingDialogFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchCompatNFC = view.findViewById(R.id.swNFC);
        switchCompatSound = view.findViewById(R.id.swScanSound);
        dashboardActivity = (DashboardActivity) getActivity();

        if (dashboardActivity.getPreference("sound")) {
            switchCompatSound.setChecked(true);
        }
        if (dashboardActivity.getPreference("nfc")) {
            switchCompatSound.setChecked(true);
        }

        switchCompatSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dashboardActivity.setPreference("sound", isChecked);
            }
        });

        switchCompatNFC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dashboardActivity.setPreference("nfc", isChecked);
            }
        });
    }

}
