package io.develotex.comma_sample.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

public class CallNotificationManager {



//    private void incomingCallNotification(Context context, CallPushNotification callPushNotification) {
//        if (callPushNotification == null) {
//            return;
//        }
//
//        Bundle bundle = new Bundle();
//        bundle.putInt(DEEPLINK_DESTINATION, DEEPLINK_CALL);
//        bundle.putInt(CallFragment.USER_ID, callPushNotification.getUserId());
//        bundle.putString(CallFragment.USER_NAME, callPushNotification.getUserName());
//        bundle.putInt(CallFragment.CALL_TYPE, callPushNotification.getType());
//
//        PendingIntent pendingIntent = new NavDeepLinkBuilder(context)
//                .setGraph(R.navigation.nav_graph_main)
//                .setDestination(R.id.fragment_launch)
//                .setArguments(bundle)
//                .createPendingIntent();
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationChannel channel = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            channel = new NotificationChannel(
//                    getString(R.string.firebase_call_notifications_channel_id),
//                    getString(R.string.firebase_call_notifications_channel_name),
//                    importance);
//
//            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound_ringing),
//                    new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                            .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
//                            .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT).build());
//
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//
//        String title = getString(callPushNotification.getTypeEnum() == MessageType.INCOMING_VIDEO_CALL ?
//                        R.string.firebase_incoming_video_call_title : R.string.firebase_incoming_call_title,
//                callPushNotification.getUserName());
//
//        Notification notification = new NotificationCompat.Builder(context, channel != null ? channel.getId() : null)
//                .setChannelId(getString(R.string.firebase_call_notifications_channel_id))
//                .setContentTitle(title)
//                .setContentText(getString(R.string.firebase_incoming_call_text))
//                .setSmallIcon(R.drawable.ic_answer_white)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .build();
//        notificationManager.notify(CALL_NOTIFICATION_ID, notification);
//    }

}