package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MRecycleAdapter extends RecyclerView.Adapter<MRecycleAdapter.MViewHolder> {
    private List<String> availableMs;
    private List<String> currentMs;
    private Context context;
    private String mode;
    private UndergradStudent student;
    private String advisor;

    static class MViewHolder extends RecyclerView.ViewHolder {
        TextView mView;

        MViewHolder(final View view) {
            super(view);
            mView = view.findViewById(R.id.mTitleView);
        }
    }

    public MRecycleAdapter(List<String> availableMs, Context context, List<String> currentMs, String mode, UndergradStudent student, String advisor) {
        this.availableMs = availableMs;
        this.context = context;
        this.currentMs = currentMs;
        this.mode = mode;
        this.student = student;
        this.advisor = advisor;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.m_item_view, parent, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MViewHolder holder, final int position) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String m = availableMs.get(position);
                if (!currentMs.contains(m)) { // if user selected a major that's not their's
                    if (mode.equals("Major")) {
                        if (m.equals(student.getMinor1()) || m.equals(student.getMinor2())) {
                            Snackbar.make(holder.mView, "ERROR: Already minoring in field. Drop minor first.", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }
                    if (mode.equals("Minor")) {
                        if (m.equals(student.getMajor1()) || m.equals(student.getMajor2())) {
                            Snackbar.make(holder.mView, "ERROR: Already majoring in field", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Switch %s", mode));
                    alertDialogBuilder.setMessage(String.format("Request to add %s %s?", mode.toLowerCase(), m))
                            .setPositiveButton("Switch", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    String uuid = String.valueOf(UUID.randomUUID());
                                    database.getReference("Accounts/" + advisor + "/" + "Notifications/" + uuid + "/Header").setValue("Switch " + mode.toLowerCase() + " request from " + student.getUserName());
                                    database.getReference("Accounts/" + advisor + "/" + "Notifications/" + uuid + "/Message").setValue((mode.equals("Major") ? student.getMajor1() : student.getMinor1()) + " to " + m);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    if (currentMs.size() == 1 || (mode.equals("Minor") && currentMs.size() == 0)) {
                        if (mode.equals("Major")) {
                            if (m.equals(student.getMinor1()) || m.equals(student.getMinor2())) {
                                Snackbar.make(holder.mView, "ERROR: Already minoring in field. Drop minor first.", Snackbar.LENGTH_LONG).show();
                                return;
                            }
                        }
                        if (mode.equals("Minor")) {
                            if (m.equals(student.getMajor1()) || m.equals(student.getMajor2())) {
                                Snackbar.make(holder.mView, "ERROR: Already majoring in field.", Snackbar.LENGTH_LONG).show();
                                return;
                            }
                        }
                        alertDialogBuilder.setMessage(String.format("Request to add %s to %s?", mode.toLowerCase(), m));
                        alertDialogBuilder.setNeutralButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String uuid = String.valueOf(UUID.randomUUID());
                                String num;
                                if (mode.equals("Minor")) {
                                    if (student.getMinor1() == null) {
                                        num = "1";
                                    } else num = "2";
                                } else {
                                    if (student.getMajor1() == null) {
                                        num = "1";
                                    } else num = "2";
                                }
                                database.getReference("Accounts/" + advisor + "/" + "Notifications/" + uuid + "/Header").setValue("Add " + mode.toLowerCase() + " request from " + student.getUserName());
                                database.getReference("Accounts/" + advisor + "/" + "Notifications/" + uuid + "/Message").setValue(m + num);
                            }
                        });
                    }
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    final int mNum;
                    if (mode.equals("Major")) {
                        if (currentMs.size() == 1) {
                            Snackbar.make(holder.mView, "ERROR: Cannot have less than one major.", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        if (m.equals(student.getMajor1())) mNum = 1;
                        else mNum = 2;
                    } else {
                        if (m.equals(student.getMinor1())) mNum = 1;
                        else mNum = 2;
                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Drop %s", mode));
                    alertDialogBuilder.setMessage(String.format("Confirm dropping of %s %s?", mode.toLowerCase(), m))
                            .setPositiveButton("Drop", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    String uuid = String.valueOf(UUID.randomUUID());
                                    database.getReference("Accounts/" + advisor + "/" + "Notifications/" + uuid + "/Header").setValue("Drop " + mode.toLowerCase() + " request from " + student.getUserName());
                                    database.getReference("Accounts/" + advisor + "/" + "Notifications/" + uuid + "/Message").setValue(m + " (" + (String.valueOf(mNum)) + ")");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });
        holder.mView.setText(availableMs.get(position));
    }
    @Override
    public int getItemCount() {
        return availableMs.size();
    }
}