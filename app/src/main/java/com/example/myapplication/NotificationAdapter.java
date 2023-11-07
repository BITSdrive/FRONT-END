package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification> notifications = new ArrayList();

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notificationviewholder, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.notificationText.setText(notification.getMessage());
    }
    public void clearNotifications() {
        notifications.clear();
        notifyDataSetChanged(); // 데이터가 변경되었음을 알립니다.
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notifyDataSetChanged(); // 데이터가 변경되었음을 알립니다.
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationText;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.notificationText); // R.id.notificationText는 XML 레이아웃에 정의된 TextView의 ID입니다.
        }
    }

    // 나머지 NotificationAdapter 클래스 코드
}
