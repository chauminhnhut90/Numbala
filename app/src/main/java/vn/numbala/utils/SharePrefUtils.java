package vn.numbala.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by chauminhnhut on 5/20/16.
 */
public class SharePrefUtils {

    public static final String IDs = "ids";

    static SharePrefUtils instance;
    private SharedPreferences preferences;

    public SharePrefUtils(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static SharePrefUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefUtils(context);
        }
        return instance;
    }

    ////////////////////////////////

    public static String getDefaultSharedPreferencesName(Context context) {

        String s = context.getFilesDir().getAbsolutePath();

        return s + "/" + context.getPackageName() + "_preferences";
    }

    public boolean save(String key, String value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, value);
        return prefsEditor.commit();
    }

    public String read(String key) {
        String json = preferences.getString(key, null);
        return json;
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


}
