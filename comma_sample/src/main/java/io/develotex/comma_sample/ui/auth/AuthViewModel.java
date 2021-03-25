package io.develotex.comma_sample.ui.auth;

import android.app.Application;
import androidx.annotation.NonNull;

import io.develotex.comma_sample.data.SharedPrefs;
import io.develotex.comma_sample.ui.BaseViewModel;


public class AuthViewModel extends BaseViewModel {

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveCredentials(String userId, String userName) {
        SharedPrefs.getInstance().setUserId(userId);
        SharedPrefs.getInstance().setUserName(userName);
    }

}