package io.develotex.comma_sample.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public <T extends MutableLiveData> void notify(T liveData) {
        liveData.setValue(liveData.getValue());
    }

}