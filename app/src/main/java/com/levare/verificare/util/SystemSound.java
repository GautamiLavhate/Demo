package com.levare.verificare.util;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

import com.levare.verificare.R;


public class SystemSound {
    private Context activity;

    public SystemSound(Activity act) {
        activity = act.getApplication().getApplicationContext();
    }

    public SystemSound(Context act) {
        activity = act;
    }

    public void successTune() {
        try {
            MediaPlayer mp = MediaPlayer.create(activity, R.raw.success);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyTune() {
        try {
            MediaPlayer mp = MediaPlayer.create(activity, R.raw.notify);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void errorTune() {
        try {
            MediaPlayer mp = MediaPlayer.create(activity, R.raw.error);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
