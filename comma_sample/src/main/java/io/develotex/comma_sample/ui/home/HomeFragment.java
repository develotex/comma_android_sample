package io.develotex.comma_sample.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import io.develotex.comma.Comma;
import io.develotex.comma.ConnectionListener;
import io.develotex.comma_sample.R;
import io.develotex.comma_sample.data.SharedPrefs;
import io.develotex.comma_sample.ui.BaseFragment;
import io.develotex.comma_sample.ui.call.CallType;
import io.develotex.comma_sample.ui.call.CallViewModel;


public class HomeFragment extends BaseFragment {

    private View mView;
    private TextView mCurrentUserIdTextView;
    private TextView mCurrentUserNameTextView;
    private Button mLogoutButton;

    private EditText mUserIdEditText;
    private Button mCallAudioButton;
    private Button mCallVideoButton;

    private HomeViewModel mViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initViews();

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        mCurrentUserIdTextView = mView.findViewById(R.id.fragment_home_current_user_id_text_view);
        mCurrentUserIdTextView.setText(getString(R.string.fragment_home_current_user_id, SharedPrefs.getInstance().getUserId()));
        mCurrentUserNameTextView = mView.findViewById(R.id.fragment_home_current_user_name_text_view);
        mCurrentUserNameTextView.setText(getString(R.string.fragment_home_current_user_name, SharedPrefs.getInstance().getUserName()));
        mLogoutButton = mView.findViewById(R.id.fragment_home_logout_button);
        mLogoutButton.setOnClickListener(view -> {
            Comma.getInstance().deleteDevice();
            SharedPrefs.getInstance().clear();
            getNavigation().popBackStack();
        });

        mUserIdEditText = mView.findViewById(R.id.fragment_home_user_id_edit_text);
        mCallAudioButton = mView.findViewById(R.id.fragment_home_call_audio_button);
        mCallAudioButton.setOnClickListener(view -> {
            String userId = mUserIdEditText.getText().toString();
            if(userId != null && !userId.isEmpty())
                call(Integer.valueOf(userId), CallType.OUTGOING_AUDIO_CALL);
        });
        mCallVideoButton = mView.findViewById(R.id.fragment_home_call_video_button);
        mCallVideoButton.setOnClickListener(view -> {
            String userId = mUserIdEditText.getText().toString();
            if(userId != null && !userId.isEmpty())
                call(Integer.valueOf(userId), CallType.OUTGOING_VIDEO_CALL);
        });

        Comma.getInstance().addConnectionListener(new ConnectionListener() {
            @Override
            public void onIncomingCall(Integer companionId, String companionName, Boolean isVideoRequested) {
                CallViewModel callViewModel = new ViewModelProvider(getActivity()).get(CallViewModel.class);
                callViewModel.setUserId(companionId);
                callViewModel.setCallType(isVideoRequested ? CallType.INCOMING_VIDEO_CALL : CallType.INCOMING_AUDIO_CALL);

                getNavigation().navigate(R.id.fragment_call);
            }
        });
    }

    private void call(Integer userId, CallType callType) {
        CallViewModel callViewModel = new ViewModelProvider(getActivity()).get(CallViewModel.class);
        callViewModel.setUserId(userId);
        callViewModel.setCallType(callType);

        getNavigation().navigate(R.id.fragment_call);
    }

}