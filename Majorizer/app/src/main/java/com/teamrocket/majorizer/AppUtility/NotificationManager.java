package com.teamrocket.majorizer.AppUtility;

import android.content.res.Resources;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotificationManager {

    private static final String LOCKED_OUT_1 = "The user ";
    private static final String LOCKED_OUT_2 = " had been locked out of their account.";
    private static final String ADMIN_REFERENCE = "/Accounts/admin/Notifications/";
    private static final String LOCKED_OUT_HEADER = "Locked Out - ";
    private static final String MESSAGE = "Message";
    private static final String HEADER = "Header";
    private Account account = null;

    public NotificationManager(final Account account) {
        this.account = account;
    }

    public void removeNotification(String id) {
        // This query removes the notification from the user's account.
        String username = account.getUserName();
        DatabaseReference accountsReference = FirebaseDatabase.getInstance().getReference("/Accounts/" + username + "/Notifications/" + id);
        accountsReference.removeValue();
    }

    public static void getNotifications(final DataSnapshot notificationsSnapshot, Resources resources, final Account account) {
        // Iterate through user's notifications in the database adding them to their Account data structure.
        System.out.println(" fufsd " + resources.getText(R.string.title_notifications).toString() + " " + notificationsSnapshot.getChildrenCount());
        for (DataSnapshot notification : notificationsSnapshot.getChildren()) {
            String notificationId = notification.getKey();
            String notificationHeader = notification.child(HEADER).getValue().toString();
            String notificationMessage = notification.child(MESSAGE).getValue().toString();

            System.out.println(notificationId + " fuck " + notificationHeader);

            account.addNotification(new Notification(notificationHeader, notificationMessage, notificationId));
        }
    }

    public static void notifyAdminLockedUser(final String studentName) {
        DatabaseReference accountsReference = FirebaseDatabase.getInstance().getReference(ADMIN_REFERENCE);
        UUID uuid = UUID.randomUUID();
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put(HEADER, LOCKED_OUT_HEADER + studentName);
        taskMap.put(MESSAGE, LOCKED_OUT_1 + studentName + LOCKED_OUT_2);
        accountsReference.child(uuid.toString()).setValue(taskMap);
    }
}