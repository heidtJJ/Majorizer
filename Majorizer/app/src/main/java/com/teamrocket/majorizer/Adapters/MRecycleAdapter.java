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
import java.util.List;

public class MRecycleAdapter extends RecyclerView.Adapter<MRecycleAdapter.MViewHolder> {
    private List<String> availableMs;
    private List<String> currentMs;
    private Context context;
    private String mode;
    private UndergradStudent student;

    static class MViewHolder extends RecyclerView.ViewHolder {
        TextView mView;

        MViewHolder(final View view) {
            super(view);
            mView = view.findViewById(R.id.mTitleView);
        }
    }

    public MRecycleAdapter(List<String> availableMs, Context context, List<String> currentMs, String mode, UndergradStudent student) {
        this.availableMs = availableMs;
        this.context = context;
        this.currentMs = currentMs;
        this.mode = mode;
        this.student = student;
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Switch %s", mode));
                    alertDialogBuilder.setMessage(String.format("Request to add %s %s?", mode.toLowerCase(), m))
                            .setPositiveButton("Switch", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // switch a major or minor.
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference("Accounts/" + student.getUserName());
                                    if (mode.equals("Major")) { // problem if multiple majors
                                        ref.child("Major1").setValue(m);
                                        student.setMajor1(m);
                                    }
                                    else {
                                        ref.child("Minor1").setValue(m);
                                        student.setMinor1(m);
                                    }
                                    Toast.makeText(context, "Please sign in to see major/minor changes.", Toast.LENGTH_SHORT).show();
                                    Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName() );
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(i);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    if (currentMs.size() == 1 || (mode.equals("Minor") && currentMs.size() == 0)) {
                        alertDialogBuilder.setMessage(String.format("Request to add/switch %s to %s?", mode.toLowerCase(), m));
                        alertDialogBuilder.setNeutralButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference("Accounts/" + student.getUserName());
                                if (mode.equals("Major")) {
                                    ref.child("Major2").setValue(m);
                                    student.setMajor2(m);
                                }
                                else {
                                    if (currentMs.size() == 0) {
                                        ref.child("Minor1").setValue(m);
                                        student.setMinor1(m);
                                    }
                                    else {
                                        ref.child("Minor2").setValue(m);
                                        student.setMinor2(m);
                                    }
                                }
                                Toast.makeText(context, "Please sign in to see major/minor changes.", Toast.LENGTH_SHORT).show();
                                Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(i);
                            }
                        });
                    }
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(String.format("Drop %s", mode));
                    alertDialogBuilder.setMessage(String.format("Confirm dropping of %s %s?", mode.toLowerCase(), m))
                            .setPositiveButton("Drop", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    if (mode.equals("Major")) {
                                        if (currentMs.size() != 1) {
                                            DatabaseReference ref = database.getReference("Accounts/" + student.getUserName());
                                            if (student.getMajor2().equals(m)) ref.child("Major2").setValue("NULL");
                                            else {
                                                ref.child("Major1").setValue(student.getMajor2());
                                                ref.child("Major2").setValue("NULL");
                                            }
                                        }
                                        else Toast.makeText(context, "ERROR: Must have at least one major!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (mode.equals("Minor")) {
                                        DatabaseReference ref = database.getReference("Accounts/" + student.getUserName());
                                        if (currentMs.size() == 1) ref.child("Minor1").setValue("NULL");
                                        else {
                                            if (student.getMinor1().equals(m)) {
                                                ref.child("Minor1").setValue(student.getMinor2());
                                                ref.child("Minor2").setValue("NULL");
                                            }
                                            else {
                                                ref.child("Minor2").setValue("NULL");
                                            }
                                        }
                                    }
                                    Toast.makeText(context, "Please sign in to see major/minor changes.", Toast.LENGTH_SHORT).show();
                                    Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName() );
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(i);
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