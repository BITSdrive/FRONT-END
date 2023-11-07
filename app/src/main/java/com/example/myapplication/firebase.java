package com.example.myapplication;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class firebase extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingSvc";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // FCM 메시지 수신 처리
        if (remoteMessage.getData().size() > 0) {
            // 데이터 페이로드가 있는 경우 데이터 처리
            String message = remoteMessage.getData().get("message");
            Log.d("FCM", "Message received: " + message);

            // 여기에서 원하는 작업 수행
        } else if (remoteMessage.getNotification() != null) {
            // 알림 메시지가 있는 경우
            Log.e("FCM", "Notification received: " + remoteMessage.getNotification().getBody());

            // 여기에서 원하는 작업 수행
        }


        // 알림을 띄우기 위한 코드
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default_channel_id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("새 메시지")
                .setContentText(remoteMessage.getData().get("message")) // FCM 메시지의 내용을 표시
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }

    // 이 메서드를 호출하여 토픽을 구독할 수 있습니다.
    public static void subscribeToTopic() {
        Log.d(TAG, "subscribeToTopic() is called");
        FirebaseMessaging.getInstance().subscribeToTopic("BITSdrive")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            // 구독 실패한 경우
                            Log.w(TAG, "Subscribing to topic failed", task.getException());
                        } else {
                            // 구독 성공한 경우
                            Log.d(TAG, "Subscribed to BITSdrive topic");
                        }
                    }
                });
    }

}