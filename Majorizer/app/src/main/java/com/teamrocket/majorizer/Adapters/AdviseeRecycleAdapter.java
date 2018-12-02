package com.teamrocket.majorizer.Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
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

public class AdviseeRecycleAdapter extends RecyclerView.Adapter<AdviseeRecycleAdapter.AdviseeViewHolder>{
    private List<Student> students = null;
    private FragmentManager fm;

    static class AdviseeViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        AdviseeViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.adviseeNameView);
        }
    }

    public AdviseeRecycleAdapter(List<Student> students, FragmentManager fm) {
        this.students = students;
        this.fm = fm;
    }

    @NonNull
    @Override
    public AdviseeRecycleAdapter.AdviseeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.advisee_text_view, parent, false);
        return new AdviseeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdviseeViewHolder holder, final int position) {
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdviseeDataDialogFragment cddf = new AdviseeDataDialogFragment();
                Bundle args = new Bundle();
                args.putString("userName", String.valueOf((students.get(position).getUserName())));
                cddf.setArguments(args);
                cddf.show(fm, "");
            }
        });
        holder.nameView.setText(students.get(position).getFirstName() + " " + students.get(position).getLastName());

        holder.nameView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}