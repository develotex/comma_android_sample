package io.develotex.comma_sample.ui.call;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import io.develotex.comma.CallListener;
import io.develotex.comma.Comma;
import io.develotex.comma_sample.R;
import io.develotex.comma_sample.ui.BaseFragment;

public class CallFragment extends BaseFragment {

    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String CALL_TYPE = "CALL_TYPE";

    private static final int CALL_PERMISSION_REQUEST = 1005;

    private View mView;
    private ConstraintLayout mRootLayout;
    private TextView mCurrentStepType;
    private TextView mOtherUserNameTextView;
    private Chronometer mCallChronometerView;
    private long mChronometerBase;
    private long mStartTimeInMillis;
    private long mEndTimeInMillis;
    private ConstraintLayout mLocalVideoLayout;
    private ConstraintLayout mRemoteVideoLayout;

    private CountDownTimer mAnswerCountDownTimer;

    private Button mMicrophoneButton;
    private boolean mIsMicrophoneMuted = false;
    private Button mSpeakerButton;
    private boolean mIsSpeakerEnabled = false;
    private Button mRotateCameraButton;

    private Button mDeclineButton;
    private Button mAnswerButton;
    private Button mVideoChatButton;

    private CallViewModel mViewModel;
    private CallListener mCallListener = null;
    private boolean mIsVideoRequested = false;

    public CallFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_call, container, false);

        initViews();

        mViewModel = new ViewModelProvider(getActivity()).get(CallViewModel.class);
        if(getArguments() != null && getArguments().getInt(CallFragment.USER_ID) != 0
                && !getArguments().getString(CallFragment.USER_NAME).isEmpty()
                && getArguments().getInt(CallFragment.CALL_TYPE) != 0) {
            mViewModel.setUserId(getArguments().getInt(CallFragment.USER_ID));
            mIsVideoRequested = getArguments().getInt(CallFragment.CALL_TYPE) == 2;

            if(mIsVideoRequested) mViewModel.setCallType(CallType.INCOMING_VIDEO_CALL);
            else mViewModel.setCallType(CallType.INCOMING_AUDIO_CALL);
        }

        if(checkCallPermissions())
            startFlow();

        return mView;
    }

    @Override
    public void onStop() {
        super.onStop();

        if(mAnswerCountDownTimer != null)
            mAnswerCountDownTimer.cancel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PERMISSION_REQUEST) {
            boolean granted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    granted = false;
                    break;
                } else granted = true;
            }

            if (granted) {
                startFlow();
            } else {
                Toast.makeText(getContext(), getString(R.string.fragment_call_permissions), Toast.LENGTH_LONG).show();
                getNavigation().popBackStack();
            }
        }
    }

    private CallListener getCallListener() {
        if(mCallListener == null)
            mCallListener = new CallListener() {

                @Override
                public void onConnecting() {
                    /*getActivity().runOnUiThread(() -> {
                        changeScene(mViewModel.getCallType());
                    });*/
                }

                @Override
                public void onConnected(boolean isVideoEnabled) {
                    getActivity().runOnUiThread(() -> {
                        changeScene(isVideoEnabled ? CallType.VIDEO_CALL_IN_PROGRESS : CallType.AUDIO_CALL_IN_PROGRESS);
                    });
                }

                @Override
                public void onDisconnected() {
                    getActivity().runOnUiThread(() -> {
                        changeScene(CallType.CALL_ENDED);
                    });
                }

                @Override
                public void onError(String error) {
                    Log.d(CallFragment.class.getName(), "ERROR " + error);
                }

                @Override
                public void onLog(String log) {
                    Log.d(CallFragment.class.getName(), "LOG " + log);
                }

            };

        return mCallListener;
    }

    private void initViews() {
        mRootLayout = mView.findViewById(R.id.fragment_call_root_layout);
    }

    private void startFlow() {
        if(mViewModel.getCallType() == CallType.OUTGOING_AUDIO_CALL) {
            mIsVideoRequested = false;
            changeScene(CallType.OUTGOING_AUDIO_CALL);
            Comma.getInstance().callAudio(mViewModel.getUserId(), getCallListener());
        } else if(mViewModel.getCallType() == CallType.OUTGOING_VIDEO_CALL) {
            mIsVideoRequested = true;
            changeScene(CallType.OUTGOING_VIDEO_CALL);
            Comma.getInstance().callVideo(mViewModel.getUserId(), getCallListener());
        } else if(mViewModel.getCallType() == CallType.INCOMING_AUDIO_CALL) {
            changeScene(CallType.INCOMING_AUDIO_CALL);
        } else if(mViewModel.getCallType() == CallType.INCOMING_VIDEO_CALL) {
            changeScene(CallType.INCOMING_VIDEO_CALL);
        }
    }

    private boolean checkCallPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA },
                    CALL_PERMISSION_REQUEST);
            return false;
        } else return true;
    }

    private void changeScene(CallType callType) {
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeBounds());
        set.addTransition(new Fade());
        set.setDuration(300);
        set.setInterpolator(new AccelerateInterpolator());
        Scene scene = null;

        switch (callType) {
            case OUTGOING_AUDIO_CALL:
                scene = Scene.getSceneForLayout(mRootLayout, R.layout.fragment_call_outgoing_call_scene, getContext());
                scene.setEnterAction(() -> {
                    initAndFillViews(callType);
                });
                break;
            case OUTGOING_VIDEO_CALL:
                scene = Scene.getSceneForLayout(mRootLayout, R.layout.fragment_call_outgoing_video_call_scene, getContext());
                scene.setEnterAction(() -> {
                    initAndFillViews(callType);
                });
                break;
            case INCOMING_AUDIO_CALL:
                scene = Scene.getSceneForLayout(mRootLayout, R.layout.fragment_call_incoming_call_scene, getContext());
                scene.setEnterAction(() -> {
                    initAndFillViews(callType);
                });
                break;
            case INCOMING_VIDEO_CALL:
                scene = Scene.getSceneForLayout(mRootLayout, R.layout.fragment_call_incoming_video_call_scene, getContext());
                scene.setEnterAction(() -> {
                    initAndFillViews(callType);
                });
                break;
            case AUDIO_CALL_IN_PROGRESS:
                scene = Scene.getSceneForLayout(mRootLayout, R.layout.fragment_call_in_progress_scene, getContext());
                scene.setEnterAction(() -> {
                    initAndFillViews(callType);
                });
                break;
            case VIDEO_CALL_IN_PROGRESS:
                scene = Scene.getSceneForLayout(mRootLayout, R.layout.fragment_call_video_in_progress_scene, getContext());
                scene.setEnterAction(() -> {
                    initAndFillViews(callType);
                });
                break;
            case CALL_ENDED:
                mChronometerBase = mCallChronometerView.getBase();
                scene = Scene.getSceneForLayout(mRootLayout, R.layout.fragment_call_finish_scene, getContext());
                scene.setEnterAction(() -> {
                    initAndFillViews(callType);
                });
                break;
        }

        if(scene != null) {
            try {
                TransitionManager.go(scene, set);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initAndFillViews(CallType callType) {
        mCurrentStepType = mView.findViewById(R.id.fragment_call_current_step_type_text_view);
        mOtherUserNameTextView = mView.findViewById(R.id.fragment_call_other_user_name_text_view);
        mCallChronometerView = mView.findViewById(R.id.fragment_call_chronometer_view);

        mLocalVideoLayout = mView.findViewById(R.id.fragment_call_local_video_layout);
        mRemoteVideoLayout = mView.findViewById(R.id.fragment_call_remote_video_layout);

        mOtherUserNameTextView.setText(mViewModel.getUserId().toString());

        mMicrophoneButton = mView.findViewById(R.id.fragment_call_microphone_button);
        mSpeakerButton = mView.findViewById(R.id.fragment_call_speaker_button);
        mRotateCameraButton = mView.findViewById(R.id.fragment_call_rotate_camera_button);
        mDeclineButton = mView.findViewById(R.id.fragment_call_decline_button);
        mAnswerButton = mView.findViewById(R.id.fragment_call_audio_answer_button);
        mVideoChatButton = mView.findViewById(R.id.fragment_call_video_answer_button);

        switch (callType) {
            case OUTGOING_AUDIO_CALL:
            case OUTGOING_VIDEO_CALL:
                startCountDownTimerForAnswer();
                mDeclineButton.setOnClickListener(view -> {
                    Comma.getInstance().hangUp();
                });
                break;
            case INCOMING_AUDIO_CALL:
                mAnswerButton.setOnClickListener(view -> {
                    Comma.getInstance().answerCall(false, getCallListener());
                });
                mDeclineButton.setOnClickListener(view -> {
                    Comma.getInstance().rejectCall();
                });
                break;
            case INCOMING_VIDEO_CALL:
                mVideoChatButton.setOnClickListener(view -> {
                    Comma.getInstance().answerCall(true, getCallListener());
                });
                mDeclineButton.setOnClickListener(view -> {
                    Comma.getInstance().rejectCall();
                });
                break;
            case AUDIO_CALL_IN_PROGRESS:
                if(mAnswerCountDownTimer != null) mAnswerCountDownTimer.cancel();
                getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                mCallChronometerView.start();
                mStartTimeInMillis = System.currentTimeMillis();
                mDeclineButton.setOnClickListener(view -> {
                    Comma.getInstance().hangUp();
                });
                mMicrophoneButton.setOnClickListener(view -> muteOrUnmute());
                mSpeakerButton.setOnClickListener(view -> setSpeakerEnabled(!mIsSpeakerEnabled));
                setSpeakerEnabled(false);
                break;
            case VIDEO_CALL_IN_PROGRESS:
                Comma.getInstance().attachRemoteSourceToContainer(mRemoteVideoLayout);
                Comma.getInstance().attachLocalSourceToContainer(mLocalVideoLayout);
                if(mAnswerCountDownTimer != null) mAnswerCountDownTimer.cancel();
                mCallChronometerView.start();
                mStartTimeInMillis = System.currentTimeMillis();
                mDeclineButton.setOnClickListener(view -> {
                    Comma.getInstance().hangUp();
                });
                mRotateCameraButton.setOnClickListener(view -> Comma.getInstance().changeCamera());
                mMicrophoneButton.setOnClickListener(view -> muteOrUnmute());
                setSpeakerEnabled(true);
                break;
            case CALL_ENDED:
                mCallChronometerView.stop();
                mCallChronometerView.setBase(mChronometerBase);
                mEndTimeInMillis = System.currentTimeMillis();

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        try {
                            getNavigation().popBackStack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }.start();
                break;
        }
    }

    private void startCountDownTimerForAnswer() {
        mAnswerCountDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                Comma.getInstance().hangUp();
            }

        }.start();
    }

    private void muteOrUnmute() {
        if(mIsMicrophoneMuted)
            Comma.getInstance().unmute();
        else Comma.getInstance().mute();

        mIsMicrophoneMuted = !mIsMicrophoneMuted;
    }

    private void setSpeakerEnabled(boolean isEnabled) {
        mIsSpeakerEnabled = isEnabled;
        Comma.getInstance().setSpeakerEnabled(mIsSpeakerEnabled);
    }

}