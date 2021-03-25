package io.develotex.comma_sample.ui.launch;

import android.app.Application;

import androidx.annotation.NonNull;

import io.develotex.comma_sample.data.SharedPrefs;
import io.develotex.comma_sample.ui.BaseViewModel;


public class LaunchViewModel extends BaseViewModel {

    public LaunchViewModel(@NonNull Application application) {
        super(application);
    }

    public String getUserId() {
        return SharedPrefs.getInstance().getUserId();
    }

    public String getUserName() {
        return SharedPrefs.getInstance().getUserName();
    }

}