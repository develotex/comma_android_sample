package io.develotex.comma_sample.ui.launch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.messaging.FirebaseMessaging;

import io.develotex.comma.CaptureConfig;
import io.develotex.comma.Comma;
import io.develotex.comma.ConnectionListener;
import io.develotex.comma.IncomingCall;
import io.develotex.comma_sample.App;
import io.develotex.comma_sample.R;
import io.develotex.comma_sample.ui.BaseFragment;
import io.develotex.comma_sample.ui.call.CallType;
import io.develotex.comma_sample.ui.call.CallViewModel;


public class LaunchFragment extends BaseFragment {

    private View mView;

    private LaunchViewModel mViewModel;

    public LaunchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_launch, container, false);

        mViewModel = new ViewModelProvider(this).get(LaunchViewModel.class);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String userId = mViewModel.getUserId();
        String userName = mViewModel.getUserName();

        if(userId != null && userName != null)
            auth(userId, userName);
        else getNavigation().navigate(R.id.fragment_auth);
    }

    private void auth(String userId, String userName) {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(pushToken -> {
                    new Comma.Builder(App.getInstance())
                            .librarySideAuth(
                                    Integer.valueOf(getString(R.string.comma_api_id)),
                                    getString(R.string.comma_api_key),
                                    Integer.valueOf(getString(R.string.comma_app_id)),
                                    Integer.valueOf(userId),
                                    userName,
                                    pushToken)
                            .setCaptureConfig(CaptureConfig.FHD)
                            .setConnectionListener(new ConnectionListener() {

                                @Override
                                public void onStarted(IncomingCall incomingCall) {
                                    if(incomingCall == null)
                                        getNavigation().navigate(R.id.fragment_home);
                                    else {
                                        CallViewModel callViewModel = new ViewModelProvider(getActivity()).get(CallViewModel.class);
                                        callViewModel.setUserId(incomingCall.getCompanionId());
                                        callViewModel.setCallType(incomingCall.isVideoRequested() ? CallType.INCOMING_VIDEO_CALL : CallType.INCOMING_AUDIO_CALL);

                                        getNavigation().navigate(R.id.fragment_call);
                                    }
                                }

                                @Override
                                public void onLog(String log) {
                                    Log.d(LaunchFragment.class.getName(), log);
                                }
                            })
                            .build();
                });
    }

}