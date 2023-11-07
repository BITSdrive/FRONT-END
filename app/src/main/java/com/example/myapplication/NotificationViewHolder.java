package com.example.myapplication;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView notificationText;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        notificationText = itemView.findViewById(R.id.notificationText);
    }
}