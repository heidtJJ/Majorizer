package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.Advisor.Advisor;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.ArrayList;
import java.util.List;

public class DropAdviseeRecycleAdapter extends RecyclerView.Adapter<DropAdviseeRecycleAdapter.DropAdviseeViewHolder> {
    private List<Student> students = null;
    private Context context = null;
    private Advisor advisor = null;

    static class DropAdviseeViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        DropAdviseeViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.objectNameView);
        }
    }

    public DropAdviseeRecycleAdapter(final Advisor advisor, final Context context) {
        this.advisor = advisor;
        this.students = new ArrayList<>(advisor.getStudents().values());
        this.context = context;
    }

    @NonNull
    @Override
    public DropAdviseeRecycleAdapter.DropAdviseeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.object_text_view, parent, false);
        return new DropAdviseeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DropAdviseeViewHolder holder, final int position) {
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Student student = students.get(position);
                final String name = student.getFirstName() + " " + student.getLastName();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Drop Student");
                alertDialogBuilder
                        .setMessage(String.format("Are you sure you want to drop %s?", name))
                        .setPositiveButton("Drop", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                advisor.dropStudentFromAdvisees(student, context);
                                students.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, context.getText(R.string.SuccessfulAdviseeDrop), Toast.LENGTH_LONG).show();
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