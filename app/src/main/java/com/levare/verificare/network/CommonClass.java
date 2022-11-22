package com.levare.verificare.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;



/**
 * Created by shyam on 11-01-2019.
 */

public class CommonClass {

    Context activity;
    public SharedPreferences userSession;


    public CommonClass(Context mContext) {
        this.activity = mContext;
        userSession = activity.getSharedPreferences("Session", 0);
    }

    public void setUserSession(String key, String value) {
        userSession.edit().putString(key, value).apply();
    }

    public String getUserSession(String key) {
        return userSession.getString(key, "");
    }


    public void setToastMessage(String msg) {
        Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public boolean is_internet_connected() {
        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


}
