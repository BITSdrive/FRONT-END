package com.example.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.google.firebase.messaging.FirebaseMessaging;

public class Result extends Activity {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        FirebaseApp.initializeApp(this);

        firebase.subscribeToTopic();

        // XML 레이아웃에서 텍스트뷰를 찾습니다.
        TextView textView = findViewById(R.id.textView);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter();
        recyclerView.setAdapter(notificationAdapter);

        // Firebase Realtime Database 경로 설정
        databaseReference = FirebaseDatabase.getInstance().getReference("notifications");


        // Firebase Realtime Database에서 데이터 변경 감지
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificationAdapter.clearNotifications();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notification notification = snapshot.getValue(Notification.class);
                    notificationAdapter.addNotification(notification);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터베이스 읽기 오류 처리
            }
        });
    }

    private BroadcastReceiver fcmUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // UI 업데이트 로직. 예: 알림 추가
            String message = intent.getStringExtra("message");
            Notification notification = new Notification(message);
            notificationAdapter.addNotification(notification);

            Log.d("FCM1", "Received FCM message: " + message);

        }


    };

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(fcmUpdateReceiver, new IntentFilter("FCM_UPDATE_INTENT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcmUpdateReceiver);
    }
}
