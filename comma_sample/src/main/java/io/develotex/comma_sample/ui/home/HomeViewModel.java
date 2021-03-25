package io.develotex.comma_sample.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import io.develotex.comma_sample.data.DataModel;
import io.develotex.comma_sample.data.SharedPrefs;
import io.develotex.comma_sample.ui.BaseViewModel;


public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<DataModel> saveCredentials(String userId, String userName) {
        SharedPrefs.getInstance().setUserId(userId);
        SharedPrefs.getInstance().setUserName(userName);

        return new MutableLiveData<>(DataModel.success(null));
    }

}