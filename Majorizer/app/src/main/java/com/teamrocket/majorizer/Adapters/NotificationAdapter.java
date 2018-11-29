package com.teamrocket.majorizer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.Notification;
import com.teamrocket.majorizer.AppUtility.NotificationManager;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Account;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private Account account = null;
    private static NotificationManager notificationManager = null;

    public NotificationAdapter(Account account) {
        this.account = account;
        this.notificationManager = new NotificationManager(account);
    }

    public void removeAt(final int position, final String notificationId) {
        // Remove notification from database.
        notificationManager.removeNotification(notificationId);
        account.removeNotification(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    protected class NotificationHolder extends RecyclerView.ViewHolder {
        private TextView notificationHeaderView;
        private TextView notificationMessageView;
        private TextView notificationId;
        private Button deleteNotificationButton;

        private NotificationHolder(final View view) {
            super(view);
            notificationHeaderView = view.findViewById(R.id.notificationHeader);
            notificationMessageView = view.findViewById(R.id.notificationMessage);
            deleteNotificationButton = view.findViewById(R.id.removeNotificationButton);
            notificationId = view.findViewById(R.id.notificationId);
            deleteNotificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    String notificationIdStr = notificationId.getText().toString();
                    removeAt(getAdapterPosition(), notificationIdStr);
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
        holder.notificationId.setText(notifications.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return account.getNotifications().size();
    }
}
