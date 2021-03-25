package io.develotex.comma_sample.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import io.develotex.comma_sample.App;

public class SharedPrefs {

    private static final String PREFS_NAME = "io.develotex.comma_sample";
    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";

    private static SharedPrefs mInstance;
    private SharedPreferences mPrefs;

    private SharedPrefs() {
        mPrefs = App.getInstance().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance() {
        if(mInstance == null)
            mInstance = new SharedPrefs();
        return mInstance;
    }

    public String getUserId() {
        return getString(USER_ID, null);
    }

    public void setUserId(String userId) {
        save(USER_ID, userId);
    }

    public String getUserName() {
        return getString(USER_NAME, null);
    }

    public void setUserName(String userName) {
        save(USER_NAME, userName);
    }

    public void clear() {
        save(USER_ID, null);
        save(USER_NAME, null);
    }

    private String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    private void save(String key, String value) {
        Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

}