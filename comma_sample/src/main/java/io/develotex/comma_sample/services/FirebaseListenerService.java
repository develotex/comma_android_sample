package io.develotex.comma_sample.services;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.develotex.comma.Comma;

public class FirebaseListenerService extends FirebaseMessagingService {


    private static final String TAG = FirebaseListenerService.class.getName();

    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(Comma.getCallPush(remoteMessage.getData()) != null) {
            Log.d(TAG, "Call push = " + Comma.getCallPush(remoteMessage.getData()).toString());
        } else {
            Log.d(TAG, "Push = " + remoteMessage.toString());
        }
    }

}