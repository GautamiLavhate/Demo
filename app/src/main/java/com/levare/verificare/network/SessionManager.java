package com.levare.verificare.network;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String KEY_user_name = "user_name";
    public static final String KEY_email= "email";
    public static final String KEY_name = "name";
    public static final String KEY_photo ="photo";
    public static final String KEY_client_id ="client_id";
    public static final String KEY_user_id ="user_id";
    public static final String KEY_last_login="last_login";
    public static final String KEY_online_time="online_time";
    public static final String KEY_loggedin="loggedin";
    public static final String KEY_user_type="user_type";

    public static final String KEY_new_project_id="new_project_id";
    public static final String KEY_new_client_id="new_client_id";
    public static final String KEY_new_plant_id="new_plant_id";
    public static final String KEY_new_batch_id="new_batch_id";
    public static final String KEY_new_batch_name="new_batch_name";
    public static final String KEY_due_date="new_due_date";
    public static final String KEY_batch_status="new_batch_status";
    public static final String KEY_total_quantity="new_total_quantity";

    // Sharedpref file name
    private static final String PREF_NAME = "VerificareDemo";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLogin(String user_name,
                            String email,
                            String name,
                            String photo,
                            String client_id,
                            String user_id,
                            String last_login
                            ,String user_type){
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_user_name, user_name);
        editor.putString(KEY_email, email);
        editor.putString(KEY_name, name);
        editor.putString(KEY_photo, photo);
        editor.putString(KEY_client_id, client_id);
        editor.putString(KEY_user_id, user_id);
        editor.putString(KEY_last_login, last_login);
        editor.putString(KEY_user_type,user_type);
        editor.commit();

    }

    public void createBatchDetails(String project_id,
                                   String client_id,
                                   String plant_id,
                                   String batch_id,
                                   String batch_name,
                                   String due_date,
                                   String batch_status,String total_quantity){
        editor.putString(KEY_new_project_id, project_id);
        editor.putString(KEY_new_client_id, client_id);
        editor.putString(KEY_new_plant_id, plant_id);
        editor.putString(KEY_new_batch_id, batch_id);
        editor.putString(KEY_new_batch_name, batch_name);
        editor.putString(KEY_due_date, due_date);
        editor.putString(KEY_batch_status, batch_status);
        editor.putString(KEY_total_quantity,total_quantity);
        editor.commit();
    }

    public String getKEY_user_name() {
        String user_name = pref.getString(KEY_user_name, "");
        return user_name;
    }

    public String getKEY_email() {
        String email = pref.getString(KEY_email, "");
        return email;
    }

    public String getKEY_name() {
        String name = pref.getString(KEY_name, "");
        return name;
    }

    public String getKEY_photo() {
        String photo = pref.getString(KEY_photo, "");
        return photo;
    }

    public String getKEY_client_id() {
        String client_id = pref.getString(KEY_client_id, "");
        return client_id;
    }

    public String getKEY_user_id() {
        String id = pref.getString(KEY_user_id, "");
        return id;
    }

    public String getKEY_last_login() {
        String last_login = pref.getString(KEY_last_login, "");
        return last_login;
    }

    public String getKEY_online_time() {
        String online_time = pref.getString(KEY_online_time, "");
        return online_time;
    }

    public  String getKEY_loggedin() {
        String loggedin = pref.getString(KEY_loggedin, "");
        return loggedin;
    }

    public  String getKEY_user_type() {
        String user_type = pref.getString(KEY_user_type, "");
        return user_type;
    }

    public String getKEY_new_project_id() {
        String new_project_id = pref.getString(KEY_new_project_id, "");
        return new_project_id;
    }

    public String getKEY_new_client_id() {
        String new_client_id = pref.getString(KEY_new_client_id, "");
        return new_client_id;
    }

    public String getKEY_new_plant_id() {
        String new_plant_id = pref.getString(KEY_new_plant_id, "");
        return new_plant_id;
    }

    public String getKEY_new_batch_id() {
        String new_batch_id = pref.getString(KEY_new_batch_id, "");
        return new_batch_id;
    }

    public String getKEY_new_batch_name() {
        String new_batch_name = pref.getString(KEY_new_batch_name, "");
        return new_batch_name;
    }

    public String getKEY_due_date() {
        String new_due_date = pref.getString(KEY_due_date, "");
        return new_due_date;
    }

    public String getKEY_batch_status() {
        String new_batch_status = pref.getString(KEY_batch_status, "");
        return new_batch_status;
    }

    public String getKEY_total_quantity() {
        String new_total_quantity = pref.getString(KEY_total_quantity, "");
        return new_total_quantity;
    }
}
