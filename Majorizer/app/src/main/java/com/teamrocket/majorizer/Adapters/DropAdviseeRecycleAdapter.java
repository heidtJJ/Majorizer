package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.AppUtility.AdviseeDataDialogFragment;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.List;

public class DropAdviseeRecycleAdapter extends RecyclerView.Adapter<DropAdviseeRecycleAdapter.DropAdviseeViewHolder>{
    private List<Student> students = null;
    private FragmentManager fm;
    private Context context;

    static class DropAdviseeViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        DropAdviseeViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.adviseeNameView);
        }
    }

    public DropAdviseeRecycleAdapter(List<Student> students, FragmentManager fm, Context context) {
        this.students = students;
        this.fm = fm;
        this.context = context;
    }

    @NonNull
    @Override
    public DropAdviseeRecycleAdapter.DropAdviseeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.advisee_text_view, parent, false);
        return new DropAdviseeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DropAdviseeViewHolder holder, final int position) {
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Drop Student");
                alertDialogBuilder
                        .setMessage(String.format("Are you sure you want to drop %s?", students.get(position).getFirstName() + " " + students.get(position).getLastName()))
                        .setPositiveButton("Drop", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDo: Edit students adviser
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
        });
        holder.nameView.setText(students.get(position).getFirstName() + " " + students.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}