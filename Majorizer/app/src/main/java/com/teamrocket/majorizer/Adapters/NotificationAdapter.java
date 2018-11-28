package com.teamrocket.majorizer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.AppUtility.Notification;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Account;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private Account account = null;

    public NotificationAdapter(Account account) {
        this.account = account;
    }

    static class NotificationHolder extends RecyclerView.ViewHolder {
        TextView notificationHeaderView, notificationMessageView;
        Button deleteNotificationButton;

        NotificationHolder(final View view) {
            super(view);
            notificationHeaderView = view.findViewById(R.id.notificationHeader);
            notificationMessageView = view.findViewById(R.id.notificationMessage);
            deleteNotificationButton = view.findViewById(R.id.removeNotificationButton);
            deleteNotificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        List<Notification> notifications = account.getNotifications();
        holder.notificationHeaderView.setText(notifications.get(position).getHeader());
        holder.notificationMessageView.setText(notifications.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return account.getNotifications().size();
    }
}
