package io.develotex.comma_sample.ui.call;

import android.app.Application;

import androidx.annotation.NonNull;

import io.develotex.comma_sample.ui.BaseViewModel;

public class CallViewModel extends BaseViewModel {

    private Integer mUserId;
    private CallType mCallType;

    public CallViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserId(Integer userId) {
        mUserId = userId;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public void setCallType(CallType callType) {
        mCallType = callType;
    }

    public CallType getCallType() {
        return mCallType;
    }

}