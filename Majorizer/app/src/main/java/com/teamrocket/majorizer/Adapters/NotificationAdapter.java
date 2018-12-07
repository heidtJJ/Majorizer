package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.AppUtility.LoginManager;
import com.teamrocket.majorizer.AppUtility.Notification;
import com.teamrocket.majorizer.AppUtility.NotificationManager;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Account;

import java.util.List;
import java.util.UUID;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private Account account = null;
    private Context context;
    private static NotificationManager notificationManager = null;

    public NotificationAdapter(final Account account, Context context) {
        this.account = account;
        this.context = context;
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
    public void onBindViewHolder(@NonNull final NotificationHolder holder, final int position) {
        List<Notification> notifications = account.getNotifications();
        holder.notificationHeaderView.setText(notifications.get(position).getHeader());
        final String header = notifications.get(position).getHeader();
        final String message = notifications.get(position).getMessage();
        Toast.makeText(context, "You have notifications!", Toast.LENGTH_SHORT).show();
        View.OnClickListener myClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (header.contains("Switch")) {
                    final String fromM = message.substring(0, message.indexOf(" to"));
                    final String toM = message.substring(message.indexOf(" to ") + 4, message.length());
                    final String userName = header.substring(header.indexOf("from") + 5, header.length());
                    final String mode = header.contains("minor") ? "Minor" : "Major";
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Approve %s Switch", mode));
                    alertDialogBuilder.setMessage(String.format("Approve %s to switch major from %s to %s?", userName, fromM, toM))
                            .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference("Accounts/" + userName + "/" + mode + "1");
                                    ref.setValue(toM);
                                    String uuid = String.valueOf(UUID.randomUUID());
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Header").setValue(mode + " switch request approved!");
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Message").setValue(mode + " has been switched from " + fromM + " to " + toM + ".");
                                }
                            })
                            .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    String uuid = String.valueOf(UUID.randomUUID());
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Header").setValue(mode + " switch request denied!");
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Message").setValue(mode + " remains " + toM + ".");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (header.contains("Locked")) {
                    final String addMessage = message;
                    final String userName = header.substring(header.indexOf("-") + 2, header.length());
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Unlock account - %s?", userName));
                    alertDialogBuilder.setMessage(String.format("Approve to unlock the account of %s?", userName))
                            .setPositiveButton("Unlock Account", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String loginAttemptsLocation = "/Accounts/" + userName;
                                    FirebaseDatabase.getInstance().getReference("/Accounts/" + userName).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            LoginManager.resetLoginAttempts(dataSnapshot, userName, context);
                                            Toast.makeText(context, "Successfully reset login attempts for this user!", Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (header.contains("Add")) {
                    final String addMessage = message;
                    final String userName = header.substring(header.indexOf("from") + 5, header.length());
                    final String mode = header.contains("minor") ? "Minor" : "Major";
                    final String num = addMessage.substring(addMessage.length() - 1, addMessage.length());
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Approve %s Add", mode));
                    alertDialogBuilder.setMessage(String.format("Approve %s to add %s %s?", userName, mode, addMessage))
                            .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference("Accounts/" + userName + "/" + mode + num);
                                    ref.setValue(addMessage.substring(0, addMessage.length() - 2));

                                    String uuid = String.valueOf(UUID.randomUUID());
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Header").setValue(mode + " add request approved!");
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Message").setValue(mode + " " + addMessage.substring(0, addMessage.length() - 1) + " has been added.");
                                }
                            })
                            .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    String uuid = String.valueOf(UUID.randomUUID());
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Header").setValue(mode + " add request denied!");
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Message").setValue(mode + " " + addMessage.substring(0, addMessage.length() - 1) + " has been denied.");
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (header.contains("Drop")) {
                    final String dropM = message.substring(0, message.indexOf("(") - 1);
                    final String dropI = message.substring(message.indexOf("(") + 1, message.length() - 1);
                    final String userName = header.substring(header.indexOf("from") + 5, header.length());
                    final String mode = header.contains("minor") ? "Minor" : "Major";
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Approve %s Drop", mode));
                    alertDialogBuilder.setMessage(String.format("Approve %s to drop %s %s?", userName, mode, dropM))
                            .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    final DatabaseReference ref = database.getReference("Accounts/" + userName + "/" + mode + dropI);
                                        FirebaseDatabase.getInstance().getReference("Accounts/" + userName + "/" + mode + "2").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (!dataSnapshot.getValue().equals("NULL")) {
                                                    DatabaseReference ref1 = database.getReference("Accounts/" + userName + "/" + mode + dropI);
                                                    ref.setValue(dataSnapshot.getValue());
                                                    ref1.setValue("NULL");

                                                } else {
                                                    ref.setValue("NULL");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    String uuid = String.valueOf(UUID.randomUUID());
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Header").setValue(mode + " drop request approved!");
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Message").setValue(mode + " " + dropM + " has been dropped.");
                                }
                            })
                            .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String uuid = String.valueOf(UUID.randomUUID());
                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Header").setValue(mode + " drop request denied!");
                                    database.getReference("Accounts/" + userName + "/" + "Notifications/" + uuid + "/Message").setValue(mode + " " + dropM + " has been denied.");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        };
        holder.notificationMessageView.setText(notifications.get(position).getMessage());
        holder.notificationHeaderView.setOnClickListener(myClick);
        holder.notificationMessageView.setOnClickListener(myClick);
        holder.notificationId.setText(notifications.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return account.getNotifications().size();
    }
}
