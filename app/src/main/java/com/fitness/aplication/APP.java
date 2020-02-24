package com.fitness.aplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class APP extends Application {
    public static Context mContext;
    public static SharedPreferences sPref;
    private PreferencesHelper appPreferences;
    private static APP instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        sPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        // Set Application Mode
        Config.setMode(Config.MODE.DEVELOPMENT);
        //Config.setMode(Config.MODE.PRODUCTION);

        //ii harus di set ketika mau buat app ke play store
        Config.isDebuging = true;
        //Config.isDebuging = false;

        initPreferences();
    }

    private void initPreferences() {
        this.appPreferences = new PreferencesHelper(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void log(String message) {
        if (Config.isDebuging) {
            Log.i(Config.APP_NAME, message);
        }
    }

    public static void logError(String message) {
        if (Config.isDebuging) {
            Log.e(Config.APP_NAME + "ERROR", message);
        }
    }

    public static APP getInstance() {
        return instance;
    }

    public static void setPreference(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clearSyncDatePreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Preference.TOKEN, "");
        editor.apply();
    }

    public static void setPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setPreference(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getStringPref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static boolean getBooleanPref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }

    public static int getIntPref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }

    public static void removePreference(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
